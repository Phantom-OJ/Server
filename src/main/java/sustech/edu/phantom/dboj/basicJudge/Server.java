//package sustech.edu.phantom.dboj.basicJudge;
//
//import com.google.gson.Gson;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.CharBuffer;
//import java.nio.charset.StandardCharsets;
//import java.sql.SQLException;
//
//public class Server {
//    public static void main(String[] args) throws IOException, InterruptedException {
//        ServerSocket ss = new ServerSocket(6666); // 监听指定端口
//        System.out.println("server is running...");
//        for (;;) {
//            Socket sock = ss.accept();//当有新的连接进来时，开启新的socket
//            System.out.println("connected from " + sock.getRemoteSocketAddress());
//            Thread thread = new Thread(new Handler(sock));
//            thread.start();
//            System.out.println("一次通信结束");
//        }
//    }
//}
//
//class Handler implements Runnable {
//    Socket sock;
//
//    public Handler(Socket sock) {
//        this.sock = sock;
//    }
//
//    @Override
//    public void run() {
//        try (InputStream input = this.sock.getInputStream()) {
//            try (OutputStream output = this.sock.getOutputStream()) {
//                handle(input, output);
//            }
//        } catch (Exception e) {
//            try {
//                this.sock.close();
//            } catch (IOException ioe) {
//            }
//            System.out.println("client disconnected.");
//        }
//    }
//
//    public void printRequest(InputStream input, OutputStream output) throws IOException{
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
//        while (true){
//            String s=reader.readLine();
//        }
//    }
//
//    private void handle(InputStream input, OutputStream output) throws IOException, SQLException {
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
////        DataOutputStream writer=new DataOutputStream(output);
////        DataInputStream inputStream=new DataInputStream(input);
//        writer.write("hello\n");
//        writer.flush();
//        char[] myBuffer=new char[200];
//        CharBuffer charBuffer=CharBuffer.wrap(myBuffer);
//        Gson gson=new Gson();
//
//        for (;;) {
//            String s = reader.readLine();
//            //System.out.println("read返回值："+a);
//            JudgeInput judgeInput=gson.fromJson(s,JudgeInput.class);
//            System.out.println("拿到的报文值："+ gson.toJson(judgeInput));
//            if(judgeInput.JudgeDatabase==null){
//                writer.write(gson.toJson(JudgeResult.CONNECTION_ERROR));
//                writer.newLine();
//                writer.flush();
//                System.out.println("database为空");
//            }
//            else {
//                JudgeResult judgeResult=JudgeService.judgeDecide(judgeInput);
//                System.out.println("返回的result:"+judgeResult);
//                writer.write(gson.toJson(judgeResult));
//                writer.newLine();
//                writer.flush();
//                System.out.println(judgeResult);
//
//            }
//        }
//    }
////    public static void handleTest(InputStream input, OutputStream output) throws IOException {
////        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
////        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
////        //DataInputStream dataInputStream=new DataInputStream(new InputStreamReader(input, StandardCharsets.UTF_8));
////        writer.write("hello\n");
////        ((BufferedWriter) writer).flush();
////        for (;;) {
////            String s = reader.readLine();
////            if (s.equals("bye")) {
////                writer.write("bye\n");
////                writer.flush();
////                break;
////            }
////            writer.write("ok: " + s + "\n");
////            writer.flush();
////        }
////    }
//}