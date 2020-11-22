package sustech.edu.phantom.dboj.basicJudge;//package basicJudge;
//
//import ch.ethz.ssh2.Session;
//
//import javax.sql.DataSource;
//import java.io.*;
//import java.sql.*;
//import java.util.ArrayList;
//
//import static sustech.edu.phantom.dboj.basicJudge.RemoteExecuteCommond.processStdout;
//
//
//public class JudgeInsafe {
//
//    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
//        String ip = "";
//        String username = "";
//        String password = "";
//        String cmd="/usr/bin/docker -H tcp://127.0.0.1 run --rm   -v pgdata:/var/lib/postgresql/data -p 12002:5432 -e POSTGRES_PASSWORD=abc123 -d postgres:12";
//        String result="";
//        RemoteExecuteCommond rec = new RemoteExecuteCommond(ip,username,password);
//        long total=0;
//        ArrayList<String> result1=new ArrayList<>();
//        ArrayList<String> result2=new ArrayList<>();
//        for (int i = 0; i < loopTime+1; i++) {
//            long timeStart = System.currentTimeMillis();
//            if (rec.login()) {
//                System.out.println("-------- 启动连接--------");
//                Session session = RemoteExecuteCommond.conn.openSession();
//                session.execCommand(cmd);
//                result = processStdout(session.getStdout(), DEFAULTCHART);
//                if (result.equals("") || result == null) {
//                    System.out.println("--------出错啦--------");
//                    result = processStdout(session.getStderr(), DEFAULTCHART);
//                }
//                session.close();
//                long judgeStart=System.currentTimeMillis();
//                //System.out.println(result);
//               // Class.forName("org.postgresql.Driver");
////                Connection connection = DriverManager.getConnection("jdbc:postgresql://10.20.87.51:12002/filmdb",
////                        "postgres", "abc123");
//
//
//              //  System.out.println("Opened database successfully");
//                for (int j = 0; j < assignment2.length ; j++) {
//                   // if(i==0){   result1 = JudgeInsafe.getResult(sqls[j], connection);}
//                    result1 = JudgeInsafe.getResult(assignment1[j], connection);
//                    result2 = JudgeInsafe.getResult(assignment1[j], connection);
//                    boolean correct=JudgeInsafe.judge(result1, result2);
//                    //System.out.println(correct);
//                }
//                long judgeEnd=System.currentTimeMillis();
//                Session sessionexit = RemoteExecuteCommond.conn.openSession();
//                sessionexit.execCommand("docker kill "+result);
//                //sessionexit.wait();
//                session.close();
//                long dockerEnd=System.currentTimeMillis();
//
//
//                if(i>0){
//                    //System.out.println("本次执行时间："+timeElapse);
//                   // total += totalTime;
//                    totalTime+=dockerEnd-timeStart;
//                    judgeElapse+=judgeEnd-judgeStart;
//                    sessionStartDocker+=judgeStart-timeStart;
//                    sessionEnd +=dockerEnd-judgeEnd;
//                }
//            }
//        }
//        //System.out.printf("time cost:%d, and average time:%f\n",total,(double)total/loopTime);
//        System.out.printf("totalTime:%d,judgeTime:%d,dockerstarttime:%d,sessionEndTime:%d",totalTime/loopTime,judgeElapse/loopTime,sessionStartDocker/loopTime,sessionEnd/loopTime);
//
//
//    }
//    public static ArrayList<String> getResult(String sql,Connection connection) throws SQLException {
//        ArrayList<String> resultRow=new ArrayList<>();
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        ResultSetMetaData metadata=resultSet.getMetaData();
//        StringBuilder sb=new StringBuilder();
//        int colNum=metadata.getColumnCount();
//        for (int i = 1; i <=colNum; i++) {
////            String rolumnDbClass = metadata.getColumnTypeName(i);
////
////            System.out.println("数据库设置的列类型:" + rolumnDbClass);
//            //获取数据库类型与java相对于的类型
////            String rolumnClass = metadata.getColumnClassName(i);
////            System.out.println("java对应:" + rolumnClass);
//            //获取列名
//            String columnName = metadata.getColumnName(i);
//           // System.out.println("获取列名:" + columnName);
//            sb.append(columnName);
//            if(i>1&&i<colNum) {
//                sb.append("|");
//            }
//        }
//        resultRow.add(sb.toString());
//        sb.delete(0,sb.length());
//        while (resultSet.next()) {
//            sb.delete(0,sb.length());
//            for (int i = 1; i <=colNum; i++) {
//                sb.append(resultSet.getString( i));
//                if(i<colNum) {
//                    sb.append("|");
//                }
//            }
//            resultRow.add(sb.toString());
//            //System.out.println(sb.toString());
//        }
//        return resultRow;
//    }
//
//    public static ArrayList<String> getResult(String sql,DataSource dataSource) throws SQLException {
//        ArrayList<String> resultRow=new ArrayList<>();
//        Connection connection = dataSource.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        ResultSetMetaData metadata=resultSet.getMetaData();
//        StringBuilder sb=new StringBuilder();
//        int colNum=metadata.getColumnCount();
//        for (int i = 1; i <=colNum; i++) {
////            String rolumnDbClass = metadata.getColumnTypeName(i);
////
////            System.out.println("数据库设置的列类型:" + rolumnDbClass);
//            //获取数据库类型与java相对于的类型
////            String rolumnClass = metadata.getColumnClassName(i);
////            System.out.println("java对应:" + rolumnClass);
//            //获取列名
//            String columnName = metadata.getColumnName(i);
//            System.out.println("获取列名:" + columnName);
//            sb.append(columnName);
//            if(i>1&&i<colNum) {
//                sb.append("|");
//            }
//        }
//        resultRow.add(sb.toString());
//        sb.delete(0,sb.length());
//        while (resultSet.next()) {
//            sb.delete(0,sb.length());
//            for (int i = 1; i <=colNum; i++) {
//                sb.append(resultSet.getString( i));
//                if(i<colNum) {
//                    sb.append("|");
//                }
//            }
//            resultRow.add(sb.toString());
//            System.out.println(sb.toString());
//        }
//        return resultRow;
//    }
//
//    public static boolean judge(ArrayList<String> studentResult,ArrayList<String> correctResult){
//        int flag=1;
//
//        if(correctResult.size()!=studentResult.size()){
//            return false;
//        }
//
//        for (int index=0;index<correctResult.size();index++
//             ) {
//            String[] correctCols=correctResult.get(index).split("|");
//            String[] studentCols=studentResult.get(index).split("|");
//            if(correctCols.length!=studentCols.length){
//                return false;
//            }
//            for (int i = 0; i <correctCols.length; i++) {
//                if(!correctCols[i].equals(studentCols[i])){
//                    System.out.printf("error in %d-th row and %d-th colexpected:%s\tyour answer:%s",index,i,correctCols[i],studentCols[i]);
//                    flag=0;
//                }
//            }
//        }
//        return flag == 1;
//
//    }
//    static String DEFAULTCHART = "UTF-8";
//    static String[] assignment1 ={
//            "select title, country, year_released from movies\n" +
//                    "where country <> 'us'\n" +
//                    "  and year_released = 1991\n" +
//                    "  and title like 'The%';",
//
//            "select count(*) cnt\n" +
//                    "from (\n" +
//                    "    select count(movieid) c from credits\n" +
//                    "    where credited_as = 'A'\n" +
//                    "    group by peopleid\n" +
//                    "    )x\n" +
//                    "where c > 30;",
//
//            "select round((100.0 * sum(case country when 'us' then 1 else 0 end)/count(*)), 2) us_percent\n" +
//                    "from movies\n" +
//                    "where year_released >= 1970\n" +
//                    "  and year_released < 1980;"
//
//            ,
//
//            "select case nation\n" +
//                    "    when 'kr' then (surname||' '||coalesce(first_name, ' '))\n" +
//                    "    when 'hk' then (surname||' '||coalesce(first_name, ' '))\n" +
//                    "    else (coalesce(first_name, ' ')||' '||surname)\n" +
//                    "    end director\n" +
//                    "from (select distinct c.peopleid, m.country nation from credits c\n" +
//                    "join movies m on c.movieid = m.movieid\n" +
//                    "where m.country in ('kr', 'hk', 'gb', 'ph')\n" +
//                    "  and m.year_released = 2016\n" +
//                    "  and c.credited_as = 'D') x\n" +
//                    "join people p on p.peopleid = x.peopleid\n" +
//                    "order by director;",
//
//            "with u1 as\n" +
//                    "        (select c.movieid movieid, count(c.peopleid) character_cnt\n" +
//                    "        from credits c\n" +
//                    "            join movies m on c.movieid = m.movieid\n" +
//                    "            join people p on c.peopleid = p.peopleid\n" +
//                    "        where m.year_released >= 2000\n" +
//                    "          and c.credited_as = 'A'\n" +
//                    "          and p.born >= 2000\n" +
//                    "        group by c.movieid) ,\n" +
//                    "     u2 as\n" +
//                    "         (select max(character_cnt) max_cnt\n" +
//                    "          from (select c.movieid movieid, count(c.peopleid) character_cnt\n" +
//                    "                from credits c\n" +
//                    "                         join movies m on c.movieid = m.movieid\n" +
//                    "                         join people p on c.peopleid = p.peopleid\n" +
//                    "                where m.year_released >= 2000\n" +
//                    "                  and c.credited_as = 'A'\n" +
//                    "                  and p.born >= 2000\n" +
//                    "                group by c.movieid) x\n" +
//                    "         )\n" +
//                    "select u1.movieid, u1.character_cnt as cnt from u1\n" +
//                    "join u2 on u1.character_cnt = u2.max_cnt;"
//
//            ,"select count(distinct peopleid)-1 count\n" +
//            "from\n" +
//            "    (select distinct c.movieid movieid\n" +
//            "    from people p\n" +
//            "    join credits c on p.peopleid = c.peopleid\n" +
//            "    where p.surname = 'Liu'\n" +
//            "      and p.first_name = 'Yifei'\n" +
//            "    ) Yifei_movieid\n" +
//            "join credits\n" +
//            "on credits.movieid = Yifei_movieid.movieid\n" +
//            "    and credits.credited_as = 'A';"};
//    static Connection connection;
//    static long totalTime=0;
//    static long judgeElapse=0;
//    static long sessionStartDocker=0;
//    static long sessionEnd =0;
//
//
//    static {
////        try {
////            connection = DriverManager.getConnection("jdbc:postgresql://10.20.87.51:12002/filmdb",
////                    "postgres", "abc123");
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
//    }
//    static int loopTime=50;
//    static String []assignment2={
//            "select s.station_id as a_station\n" +
//                    "from line_detail\n" +
//                    "         join stations s on line_detail.station_id = s.station_id\n" +
//                    "where line_id = 1\n" +
//                    "  and s.station_id not in (\n" +
//                    "    select s.station_id\n" +
//                    "    from line_detail\n" +
//                    "             join stations s on line_detail.station_id = s.station_id\n" +
//                    "    where line_id = 2);",
//            //
//            "select *,rank() over(order by number) from (\n" +
//                    "select district,count(*)number from stations s join line_detail ld on s.station_id = ld.station_id where line_id=1 group by district)sub;",
//            //
//            "select *, rank() over (order by number desc)\n" +
//                    "from (\n" +
//                    "         select district, count(distinct line_id) number\n" +
//                    "         from line_detail line\n" +
//                    "                  join stations s on line.station_id = s.station_id\n" +
//                    "         where district <> ''\n" +
//                    "         group by district) sub\n" +
//                    ";",
//            //
//            "select *,\n" +
//                    "       rank() over (\n" +
//                    "           partition by line_id order by cnt desc)\n" +
//                    "from (\n" +
//                    "         select line_id, bl.station_id, count(*) cnt\n" +
//                    "         from stations\n" +
//                    "                  join bus_lines bl on stations.station_id = bl.station_id\n" +
//                    "                  join line_detail ld on stations.station_id = ld.station_id\n" +
//                    "         group by line_id, bl.station_id) sub\n" +
//                    "where cnt >= 10\n" +
//                    "order by line_id,cnt,station_id desc\n" +
//                    "limit 10\n" +
//                    "offset\n" +
//                    "15\n" +
//                    ";",
//            "select district,chr,cnt from (\n" +
//                    "select district,chr,cnt,max(cnt) over (partition by district ) maxcnt from (\n" +
//                    "select substr(chinese_name,1,1)chr,district,count(*) as cnt from stations\n" +
//                    "where district<>''\n" +
//                    "group by chr,district\n" +
//                    "order by cnt desc)sub)sub\n" +
//                    "where cnt=maxcnt\n" +
//                    ";"
//    };
//
//
//
//}
