package sustech.edu.phantom.dboj.basicJudge;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.*;


public class JudgeService {
    public static void main(String[] args) throws SQLException {
        JudgeInput judgeInput = new JudgeInput();
        judgeInput.userInput = JudgeInsafe.assignment1[0];
        judgeInput.standardAnswer = getResult(JudgeInsafe.assignment1[1], connection);
        judgeInput.timeLimit = 2L;
        JudgeResult judgeResult = judgeSingle(judgeInput);
        System.out.println(judgeResult.toString());

    }

    static Connection connection;
//    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
static ExecutorService executorService = Executors.newFixedThreadPool(2);
    final static int AC = 0;
    final static int WRONG_ANSWER = 3;
    final static int TIMEOUT = 2;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "abc123");
        } catch (SQLException e) {
            System.out.println("zzzz");
            e.printStackTrace();
        }
    }

    public static JudgeResult judgeSingle(JudgeInput judgeInput) throws SQLException {

        Future<ArrayList<String>> future = executorService.submit(new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() throws Exception {
                Thread.sleep(5000);
                return getResult(judgeInput.userInput, connection);
            }
        });
        int timeOutFlag = 0;

        JudgeResult judgeResult = new JudgeResult();
        //由老师输入的sql，通常为ddl或插入数据
        Statement beforeStatement = connection.createStatement();
        if (judgeInput.beforeInput != null) {
            boolean beforeSuccess = beforeStatement.execute(judgeInput.getBeforeInput());
        }
        ArrayList<String> userResult = null;
        Long timeStart = System.currentTimeMillis();
        try {
            userResult = future.get(judgeInput.timeLimit, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true);
            timeOutFlag = 1;
            System.out.println("任务超时。");
        }
        finally {
            executorService.shutdown();
        }
        Long timeEnd = System.currentTimeMillis();
        Long runtime = timeEnd - timeStart;
        // ArrayList<String> userResult=getResult(judgeInput.userInput,connection);

        if (timeOutFlag == 1) {
            judgeResult.code = TIMEOUT;
            judgeResult.codeDescription = "Answer Correct";
            judgeResult.userAnswer = userResult;
            judgeResult.runTime = judgeInput.timeLimit;
            return judgeResult;
        }
        int compareResult = compareResult(userResult, judgeInput.standardAnswer);
        if (compareResult == -1) {
            judgeResult.code = AC;
            judgeResult.codeDescription = "Answer Correct";
            judgeResult.userAnswer = userResult;
            judgeResult.runTime = runtime;
        } else {
            judgeResult.code = WRONG_ANSWER;
            judgeResult.codeDescription = "Wrong Answer";
            judgeResult.userAnswer = userResult;
            judgeResult.runTime = runtime;
        }
        //在学生的sql之后执行的代码，通常为检验trigger是否成功，貌似这样的话一条sql不太够,先不作为判定依据
        Statement afterStatement = connection.createStatement();
        if (judgeInput.afterInput != null) {
            boolean afterSuccess = afterStatement.execute(judgeInput.afterInput);
        }
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
        ArrayList<String> resultRow = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
            if (i > 1 && i < colNum) {
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

}
