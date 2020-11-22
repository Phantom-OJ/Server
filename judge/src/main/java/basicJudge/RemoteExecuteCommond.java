package basicJudge;



import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.*;

public class RemoteExecuteCommond {

    //字符编码
    public static String DEFAULTCHART ="utf-8";
    public static Connection conn;
    public String ip;
    public String userName;
    public String userPwd;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public RemoteExecuteCommond(String ip, String userName, String userPwd) {
        this.ip = ip;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public RemoteExecuteCommond() {
    }

    /**
     * 远程登录Linux主机
     * @return
     * */
    public boolean login()
    {
        boolean flag =false;
        conn = new Connection(ip);
        try {
            conn.connect();
            flag=conn.authenticateWithPassword(userName,userPwd);
            if(flag)
            {
                //System.out.println("认证成功");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 远程执行shell脚本或命令
     * */
    public String execute(String cmd)
    {
        String result="";
        try{
            if(login())
            {
                Session session  = conn.openSession();
                session.execCommand(cmd);
                result = processStdout(session.getStdout(),DEFAULTCHART);
                if(result.equals("") || result==null){
                    result = processStdout(session.getStderr(),DEFAULTCHART);
                }
                conn.close();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String executeSuccess(String cmd)
    {
        String result="";
        try{
            if(login())
            {
                Session session = conn.openSession();
                session.execCommand(cmd);
                result = processStdout(session.getStdout(),DEFAULTCHART);
                conn.close();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 解析脚本执行返回的结果集
     * */
    public static String processStdout(InputStream is,String charset)
    {
        InputStream inputStream = new StreamGobbler(is);
        StringBuffer sb = new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,charset));
            String line =null;
            while ((line = br.readLine()) !=null){
                sb.append(line +"\n");
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void main(String[]args)
    {
        RemoteExecuteCommond rec = new RemoteExecuteCommond("10.20.87.51","maozunyao","qweasdzxc");
        try {
            if (rec.login()) {
                System.out.println("-------- 启动连接--------");
                Session session = conn.openSession();
                session.execCommand("sh /home/maozunyao/createContainer.sh");
                String result = processStdout(session.getStdout(),DEFAULTCHART);
                if(result.equals("")|| result==null){
                    System.out.println("--------出错啦--------");
                    result=processStdout(session.getStderr(),DEFAULTCHART);
                }
                System.out.println(result);
                session.close();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}