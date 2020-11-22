package basicJudge;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;



public class JDBCtest {
    static  String s;
    public static void main(String[] args) throws Exception {
        s="123213";

        String path = "D:\\courseStation\\CS309\\phantom\\judge\\src\\main\\java\\basicJudge\\JudgeTestJson\\input2.json";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

        Gson gson = new Gson();
        JudgeInput judgeInput = gson.fromJson(br, JudgeInput.class);
        System.out.println(judgeInput);
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?allowMultiQueries=true&currentSchema=trigger_test_database&reWriteBatchedInserts=true",
                    "postgres", "abc123");
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            boolean b = statement.execute(judgeInput.userInput);
            String[] after = judgeInput.afterInput.split(";");
            for (int i = 0; i < after.length; i++) {
                try {
                    statement.execute(after[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
                ResultSet rs = statement.getResultSet();
                if (rs != null) {
                    ResultSet resultSet = statement.getResultSet();
                    ArrayList arrayList = new ArrayList();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    HashMap<String, Object> map = new HashMap<>();
                    int columnCount = metaData.getColumnCount();
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(resultSet.getString(i) + "\t");
                        }
                        System.out.println();
                    }
                    statement.getMoreResults();
                    continue;
                }
                break; // 没有其它结果
            }
            System.out.println(b);
        } catch (SQLException e) {
            System.out.println("zzzz");
            e.printStackTrace();
        }

    }
}
