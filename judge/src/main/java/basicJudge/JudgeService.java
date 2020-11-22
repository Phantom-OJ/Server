package basicJudge;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;


public class JudgeService {
    public static void main(String[] args) throws SQLException {
//        JudgeInput judgeInput = new JudgeInput();
//        judgeInput.userInput = JudgeInsafe.assignment1[0];
//        judgeInput.standardAnswer = getResult(JudgeInsafe.assignment1[1], connection);
//        judgeInput.timeLimit = 2L;
//        JudgeResult judgeResult = judgeSingle(judgeInput);
//        System.out.println(judgeResult.toString());

    }

    static Connection connection;
    //    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    static ExecutorService executorService = Executors.newFixedThreadPool(2);
    final static int AC = 0;
    final static int WRONG_ANSWER = 3;
    final static int TIMEOUT = 2;
    static Connection userDefineConnection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "abc123");
        } catch (SQLException e) {
            System.out.println("zzzz");
            e.printStackTrace();
        }
    }

    public static JudgeResult judgeDecide(JudgeInput judgeInput) throws SQLException {
        if (judgeInput != null) {
            HashMap<String, Object> additionFields = judgeInput.additionFields;
            if (judgeInput.additionFields != null) {
                String type=(String)additionFields.get("type");
                if (type.equals("select")){
                    return judgeSingle(judgeInput);
                }
                else if(type.equals("ddl")){
                    return null;
                }
            }
            return judgeSingle(judgeInput);
        }
        else {
            return JudgeResult.UNKNOWN_ERROR;
        }
    }

    public static JudgeResult judgeDDL(JudgeInput judgeInput)  {
        Future<ArrayList<String>> future = executorService.submit(new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() throws Exception {
                //Thread.sleep(5000);
                return getResult(judgeInput.userInput, userDefineConnection);
            }
        });
        try{
            connection=getConnection(judgeInput);
        }catch (Exception e){
            System.out.println("与判题数据库建立连接失败，输入报文不正确或数据库不存在");
            e.printStackTrace();
            return JudgeResult.CONNECTION_ERROR;
        }

        int timeOutFlag = 0;

        /*由老师输入的sql，通常为ddl或插入数据*/
        try{
        Statement beforeStatement = connection.createStatement();
        if (judgeInput.beforeInput != null) {
            beforeStatement.execute(judgeInput.getBeforeInput());
        }
        }catch (Exception e){

        }

        return null;
    }

    public static Connection getConnection(JudgeInput judgeInput) throws Exception{

            userDefineConnection = DriverManager.getConnection(judgeInput.judgeDatabaseUrl, judgeInput.userName, judgeInput.passWord);
            if (userDefineConnection == null) {
                Exception e = new Exception("连接这是怎么了？");
                e.printStackTrace();
            }
            return userDefineConnection;
            //userDefineConnection.createArrayOf()

    }

    public static JudgeResult judgeSingle(JudgeInput judgeInput) throws SQLException {

        //给后端返回的json报文
        JudgeResult judgeResult = new JudgeResult();

        //默认是select提交,afterselect不执行,如果是1,则执行
        int judgeType = 0;

        //TODO 用线程池来提交，从而实现超时截断,可能性能会差一点，后面有空再研究优化问题
        Future<ArrayList<String>> future = executorService.submit(new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() throws Exception {
                //Thread.sleep(5000);
                return getResult(judgeInput.userInput, userDefineConnection);
            }
        });
        try{
            connection=getConnection(judgeInput);
        }catch (Exception e){
            System.out.println("与判题数据库建立连接失败，输入报文不正确或数据库不存在");
            e.printStackTrace();
            return JudgeResult.CONNECTION_ERROR;
        }


        int timeOutFlag = 0;

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

        ArrayList<String> userResult = null;
        Long timeStart = System.currentTimeMillis();
        try {
            userResult = future.get(judgeInput.timeLimit, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true);
            timeOutFlag = 1;
            System.out.println("任务超时。");
        }
        //要想办法正常释放资源
//        } finally {
//            executorService.shutdown();
//        }
        Long timeEnd = System.currentTimeMillis();
        Long runtime = timeEnd - timeStart;
        if (timeOutFlag == 1) {
            judgeResult = JudgeResult.TIME_LIMIT_EXCEED;
            judgeResult.userAnswer = null;
            judgeResult.runTime = judgeInput.timeLimit;
            return judgeResult;
        }
        //-1为正确，其它为错误的行数
        int compareResult = compareResult(userResult, judgeInput.standardAnswer);
        if (compareResult == -1) {
            judgeResult = JudgeResult.ANSWER_CORRECT;
            judgeResult.userAnswer = userResult;
            judgeResult.runTime = runtime;
        } else {
            judgeResult = JudgeResult.WRONG_ANSWER;
            judgeResult.userAnswer = userResult;
            judgeResult.runTime = runtime;
        }
        //select 不检查afterInput
        return judgeResult;
    }
    //结果正确返回-1，错误的话返回不匹配的行数，以0开始

    public static int compareResult(ArrayList<String> userResult, ArrayList<String> standardResult) {
        int flag = 1;
        int wrongRow = 0;

        if (userResult.size() != standardResult.size()) {
            //这里没有返回不对的行数
            return 0;
        }

        for (int index = 0; index < userResult.size(); index++
        ) {
            String[] correctCols = standardResult.get(index).split("|");
            String[] studentCols = userResult.get(index).split("|");
            if (correctCols.length != studentCols.length) {
                return 0;
            }
            for (int i = 0; i < correctCols.length; i++) {
                if (!correctCols[i].equals(studentCols[i])) {
                    System.out.printf("error in %d-th row and %d-th colexpected:%s\tyour answer:%s", index, i, correctCols[i], studentCols[i]);
                    flag = 0;
                    wrongRow = i;
                    return wrongRow;
                }
            }
        }
        return -1;
    }

    public static ArrayList<String> getResult(String sql, Connection connection) throws SQLException {
        //TODO 多条sql如何执行？
        ArrayList<String> resultRow = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.addBatch();
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metadata = resultSet.getMetaData();
        StringBuilder sb = new StringBuilder();
        int colNum = metadata.getColumnCount();
        for (int i = 1; i <= colNum; i++) {
//            String rolumnDbClass = metadata.getColumnTypeName(i);
//
//            System.out.println("数据库设置的列类型:" + rolumnDbClass);
            //获取数据库类型与java相对于的类型
//            String rolumnClass = metadata.getColumnClassName(i);
//            System.out.println("java对应:" + rolumnClass);
            //获取列名
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
        return resultRow;
    }
//    static class JudgeMethodFactory{
//
//    }

}
