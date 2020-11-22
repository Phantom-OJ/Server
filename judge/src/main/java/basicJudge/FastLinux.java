package basicJudge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

//import org.apache.commons.lang.StringUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class FastLinux {
    private static String DEFAULTCHART = "UTF-8";

    static Connection login(String ip, String username, String password) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = new Connection(ip);
            connection.connect();// 连接
            flag = connection.authenticateWithPassword(username, password);// 认证
            if (flag) {
                System.out.println("================登录成功==================");
                return connection;
            }
        } catch (IOException e) {
            System.out.println("=========登录失败=========" + e);
            connection.close();
        }
        return connection;
    }

    /**
     * 远程执行shll脚本或者命令
     *
     * @param cmd
     *            即将执行的命令
     * @return 命令执行完后返回的结果值
     */
     static String execmd(Connection connection, String cmd) {
        String result = "";
        try{
            if (connection != null) {
                Session session = connection.openSession();// 打开一个会话
                session.execCommand(cmd);// 执行命令
                result = processStdout(session.getStdout(), DEFAULTCHART);
                System.out.println(result);
                // 如果为得到标准输出为空，说明脚本执行出错了
                /*if (StringUtils.isBlank(result)) {
                    System.out.println("得到标准输出为空,链接conn:" + connection + ",执行的命令：" + cmd);
                    result = processStdout(session.getStderr(), DEFAULTCHART);
                } else {
                    System.out.println("执行命令成功,链接conn:" + connection + ",执行的命令：" + cmd);
                }*/
                connection.close();
                session.close();
            }
        } catch (IOException e) {
            System.out.println("执行命令失败,链接conn:" + connection + ",执行的命令：" + cmd + "  " + e);
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 解析脚本执行返回的结果集
     *
     * @param in
     *            输入流对象
     * @param charset
     *            编码
     * @return 以纯文本的格式返回
     */
     static String processStdout(InputStream in, String charset) {
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        ;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
                System.out.println(line);
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("解析脚本出错：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("解析脚本出错：" + e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void main(String[] args) throws IOException {
        //long currentTimeMillis = System.currentTimeMillis();
        String ip = "10.20.87.51";
        String username = "maozunyao";
        String password = "qweasdzxc";
        String cmd = "//usr//bin//docker run hello-world";
        Connection connection = login(ip, username, password);
        Session session = connection.openSession();// 打开一个会话
        session.execCommand(cmd);// 执行命令
        String result = processStdout(session.getStdout(), DEFAULTCHART);
        System.out.println(result);
        //long currentTimeMillis1 = System.currentTimeMillis();
       // System.out.println("ganymed-ssh2方式"+(currentTimeMillis1-currentTimeMillis));
    }
}