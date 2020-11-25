package sustech.edu.phantom.dboj.basicJudge;

import com.google.gson.Gson;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;


public class JudgeService {
    /*main方法可以断点调试judge方法*/
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        String path = "D:\\courseStation\\CS309\\phantom\\judge\\src\\main\\java\\basicJudge\\JudgeTestJson\\input2.json";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        Gson gson = new Gson();
        JudgeInput judgeInput = gson.fromJson(br, JudgeInput.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", "trigger");
        judgeInput.additionFields = hashMap;
        JudgeResult judgeResult = judgeDecide(judgeInput);
        System.out.println(judgeResult);
    }

    static String userName = "postgres";
    static String passWord = "abc123";
    static Connection connection;
    static ExecutorService executorService = Executors.newFixedThreadPool(2);//这个线程管理器待学习
    static Connection userDefineConnection;

    /*judgeService先调用judgeDecide方法，如果additional字段里有type=trigger字段，则
     * 调用judgeDDL方法判trigger题，否则默认select题
     * 后续还可以添加*/
    public static JudgeResult judgeDecide(JudgeInput judgeInput) {
        try {
            JudgeResult judgeResult = new JudgeResult();


            if (judgeInput != null) {
                HashMap<String, Object> additionFields = judgeInput.additionFields;
                if (judgeInput.additionFields != null) {
                    String type = (String) additionFields.get("type");
                    switch (type) {
                        case "select":
                            judgeResult = judgeSingle(judgeInput);
                            break;

                        case "trigger":
                            judgeResult = judgeDDL(judgeInput);
                            break;
                        default:
                            System.out.println("type不支持");
                            judgeResult = JudgeResult.METHOD_ERROR;
                            break;
                    }
                } else {
                    /*没有type字段，默认single*/
                    judgeResult = judgeSingle(judgeInput);
                }
            } else {
                /*报文为空，发生一些未知错误*/
                judgeResult = JudgeResult.FORMAT_ERROR;
            }

            return judgeResult;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建容器失败");
            return JudgeResult.UNKNOWN_ERROR;
        }
    }

    public static JudgeResult judgeDDL(JudgeInput judgeInput) {

        JudgeResult judgeResult = new JudgeResult();
        ArrayList<String> resultRow = new ArrayList<>();
        try {
            connection = getConnection(judgeInput);
            connection.setAutoCommit(true);

            Statement statement = connection.createStatement();
            /*由老师输入的sql，通常为ddl或插入数据*/
            if (judgeInput.beforeInput != null) {
                statement.execute(judgeInput.getBeforeInput());
            }
            FutureTask<Boolean> future = new FutureTask<>(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return statement.execute(judgeInput.userInput);
                }
            }
            );
            Long timeStart = System.currentTimeMillis();
            try {
                new Thread(future).start();
                future.get(judgeInput.timeLimit, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                System.out.println("超时");
                return JudgeResult.TIME_LIMIT_EXCEED;
            } catch (Exception e) {
                e.printStackTrace();
                Long timeEnd = System.currentTimeMillis();
                Long runtime = timeEnd - timeStart;
                System.out.println("执行错误");
                judgeResult.setCode(JudgeResult.RUN_TIME_ERROR.getCode());
                judgeResult.setCodeDescription(JudgeResult.RUN_TIME_ERROR.getCodeDescription());
                judgeResult.setRunTime(runtime);
                return judgeResult;
            }
            Long timeEnd = System.currentTimeMillis();
            Long runtime = timeEnd - timeStart;

            /*用户插入trigger后，执行之后的插入、查询判题*/
            String[] after = judgeInput.afterInput.split(";");
            for (int i = 0; i < after.length; i++) {
                try {
                    statement.execute(after[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            /*execute 有多个结果集，循环获取，select必须是最后一句*/
            while (true) {
                int rowCount = statement.getUpdateCount();
                if (rowCount > 0) { // 它是更新计数
                    System.out.println("Rows changed = " + rowCount);
                    statement.getMoreResults();
                    continue;
                }
                if (rowCount == 0) { // DDL 命令或 0 个更新
                    System.out.println(" No rows changed or statement was DDLcommand");
                    statement.getMoreResults();
                    continue;
                }
                // 执行到这里，证明有一个结果集
                // 或没有其它结果
                ResultSet resultSet = statement.getResultSet();
                ResultSetMetaData metadata = resultSet.getMetaData();
                StringBuilder sb = new StringBuilder();
                int colNum = metadata.getColumnCount();
                for (int i = 1; i <= colNum; i++) {
                    String columnName = metadata.getColumnName(i);
                    // System.out.println("获取列名:" + columnName);
                    sb.append(columnName);
                    if (i >= 1 && i < colNum) {
                        sb.append("|");
                    }
                }
                resultRow.add(sb.toString());
                sb.delete(0, sb.length());
                while (resultSet.next()) {
                    sb.delete(0, sb.length());
                    for (int i = 1; i <= colNum; i++) {
                        sb.append(resultSet.getString(i));
                        if (i < colNum) {
                            sb.append("|");
                        }
                    }
                    resultRow.add(sb.toString());
                    System.out.println(sb.toString());
                }
                break; // 没有其它结果
            }
            int resultFlag = compareResult(resultRow, judgeInput.standardAnswer);
            if (resultFlag == -1) {
                judgeResult = JudgeResult.ANSWER_CORRECT;
                judgeResult.runTime = runtime;
                judgeResult.userAnswer = resultRow;
                return judgeResult;
            } else {
                judgeResult.setCode(JudgeResult.WRONG_ANSWER.getCode());
                if(resultFlag!=0){
                judgeResult.setCodeDescription("row "+resultFlag+" does not equal");}
                else {
                    judgeResult.setCodeDescription("row or column number does not equal");
                }
                judgeResult.setRunTime(runtime);
                judgeResult.setUserAnswer(resultRow);
                return judgeResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            judgeResult.setCode(JudgeResult.UNKNOWN_ERROR.code);
            judgeResult.setCodeDescription("系统未知错误，请联系管理员");
            return judgeResult;
        }
    }

    public static Connection getConnection(JudgeInput judgeInput) throws Exception {
        DriverManager.setLoginTimeout(3);
        userDefineConnection = DriverManager.getConnection(judgeInput.JudgeDatabase, userName, passWord);

        if (userDefineConnection == null) {
            Exception e = new Exception("连接这是怎么了？");
            e.printStackTrace();
        }
        return userDefineConnection;
        //userDefineConnection.createArrayOf()

    }

    public static JudgeResult judgeSingle(JudgeInput judgeInput) {

        //给后端返回的json报文
        JudgeResult judgeResult = new JudgeResult();

        //默认是select提交,afterselect不执行,如果是1,则执行

        //TODO 用线程池来提交，从而实现超时截断,可能性能会差一点，后面有空再研究优化问题
        FutureTask<ArrayList<String>> future = new FutureTask<>(new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() throws Exception {
                return getResult(judgeInput.userInput, connection);
            }
        }
        );
        try {
            connection = getConnection(judgeInput);
            connection.setAutoCommit(false);
            /*由老师输入的sql，通常为ddl或插入数据*/
            Statement beforeStatement = connection.createStatement();
            if (judgeInput.beforeInput != null) {
                try {
                    beforeStatement.execute(judgeInput.getBeforeInput());
                } catch (Exception e) {
                    judgeResult = JudgeResult.SYNTAX_ERROR;
                    return judgeResult;
                }
            }
            /*timeOutFlag: 1为超时，0为未超时*/
            int timeOutFlag = 0;
            int runTimeErrorFlag = 0;
            ArrayList<String> userResult = null;
            Long timeStart = System.currentTimeMillis();
            try {
                new Thread(future).start();
                userResult = future.get(judgeInput.timeLimit, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                e.printStackTrace();
                future.cancel(true);
                timeOutFlag = 1;
                System.out.println("任务超时。");
            } catch (InterruptedException | ExecutionException e) {
                Long timeEnd = System.currentTimeMillis();
                Long runtime = timeEnd - timeStart;
                e.printStackTrace();
                System.out.println("執行錯誤");
                future.cancel(true);
                judgeResult.setCode(JudgeResult.RUN_TIME_ERROR.getCode());
                judgeResult.setCodeDescription(JudgeResult.RUN_TIME_ERROR.getCodeDescription());
                judgeResult.setRunTime(runtime);
                ArrayList<String> arrayList=new ArrayList<>();
                arrayList.add(e.getMessage());
                judgeResult.userAnswer =arrayList;
                return judgeResult;

            }
            Long timeEnd = System.currentTimeMillis();
            Long runtime = timeEnd - timeStart;
            if (timeOutFlag == 1) {
                judgeResult.setCode(JudgeResult.TIME_LIMIT_EXCEED.code);
                judgeResult.setCodeDescription(JudgeResult.TIME_LIMIT_EXCEED.getCodeDescription());
                judgeResult.setRunTime(judgeInput.timeLimit);
                judgeResult.userAnswer = null;
                judgeResult.runTime = judgeInput.timeLimit;
                return judgeResult;
            }


            //-1为正确，其它为错误的行数
            int compareResult = compareResult(userResult, judgeInput.standardAnswer);
            System.out.println("学生测试结果：");
            for (int i = 0; i < userResult.size(); i++) {
                System.out.println(userResult.get(i));
            }
            System.out.println("标准答案:");
            for (int i = 0; i < judgeInput.standardAnswer.size(); i++) {
                System.out.println(judgeInput.standardAnswer.get(i));
            }
            System.out.println("compareResult:" + compareResult);
            if (compareResult == -1) {
                judgeResult = JudgeResult.ANSWER_CORRECT;
                judgeResult.userAnswer = userResult;
                judgeResult.runTime = runtime;
            } else {
                judgeResult = JudgeResult.WRONG_ANSWER;
                judgeResult.userAnswer = userResult;
                judgeResult.runTime = runtime;
            }
            connection.rollback();
        } catch (Exception e) {
            System.out.println("执行过程发生错误");
            e.printStackTrace();
            judgeResult=JudgeResult.RUN_TIME_ERROR;
            return judgeResult;
        }

        //select 不检查afterInput
        return judgeResult;
    }
    //结果正确返回-1，错误的话返回不匹配的行数，以0开始

    public static int compareResult(ArrayList<String> userResult, ArrayList<String> standardResult) {
        int flag = 1;
        int wrongRow = 0;

        if (userResult.size() != standardResult.size()) {
            System.out.printf("用户行数:%d,标准行数:%d", userResult.size(), standardResult.size());
            //这里没有返回不对的行数
            return 0;
        }

        for (int index = 0; index < userResult.size(); index++
        ) {

            String[] correctCols = standardResult.get(index).split("\\|");
            String[] studentCols = userResult.get(index).split("\\|");
            if (correctCols.length != studentCols.length) {
                flag = 0;
                System.out.printf("第%d行", index);
                System.out.printf("用户答案列数：%d，标准答案列数：%d\n", studentCols.length, correctCols.length);
                return 0;
            }
            for (int i = 0; i < correctCols.length; i++) {
                if (!correctCols[i].equals(studentCols[i])) {
                    System.out.printf("error in %d-th row and %d-th colexpected:%s\tyour answer:%s\n", index, i, correctCols[i], studentCols[i]);
                    flag = 0;
                    wrongRow = i;
                    return wrongRow;
                }
            }
        }
        if (flag == 1) {
            return -1;
        } else {
            return 0;
        }
    }

    public static ArrayList<String> getResult(String sql, Connection connection) throws SQLException {
        //TODO 多条sql如何执行？

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.addBatch();
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<String> resultRow = new ArrayList<>();
        ResultSetMetaData metadata = resultSet.getMetaData();
        StringBuilder sb = new StringBuilder();
        int colNum = metadata.getColumnCount();
        for (int i = 1; i <= colNum; i++) {
            String columnName = metadata.getColumnName(i);
            // System.out.println("获取列名:" + columnName);
            sb.append(columnName);
            if (i >= 1 && i < colNum) {
                sb.append("|");
            }
        }
        resultRow.add(sb.toString());
        sb.delete(0, sb.length());
        while (resultSet.next()) {
            sb.delete(0, sb.length());
            for (int i = 1; i <= colNum; i++) {
                sb.append(resultSet.getString(i));
                if (i < colNum) {
                    sb.append("|");
                }
            }
            resultRow.add(sb.toString());
            //System.out.println(sb.toString());
        }
        return resultRow;
    }

}
