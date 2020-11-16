package sustech.edu.phantom.dboj.basicJudge;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();

        String command = "docker run -d --name testdata --rm -v pgdata:/var/lib/postgresql/data -p 12003:5432 postgres:12";

        JSch jsch = new JSch();
        Session session = jsch.getSession("maozunyao", "10.20.87.51", 22);
        session.setPassword("qweasdzxc");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(60 * 1000);
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        channel.setInputStream(null);

        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();

        channel.connect();

        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) continue;
                System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
        }
        channel.disconnect();
        session.disconnect();

        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("Jsch方式"+(currentTimeMillis1-currentTimeMillis));
    }

}