package sustech.edu.phantom.dboj.basicJudge;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        clientTest();


    }

    public static void clientTest() throws IOException {
        Socket sock = null;
        try {
            sock = new Socket("localhost", 6666); // 连接指定服务器和端口
        } catch (Exception e) {
            System.out.println("连接失败");
            System.exit(0);
        }

        try (InputStream input = sock.getInputStream()) {
            try (OutputStream output = sock.getOutputStream()) {
                handle2(input, output);
            }
        }
        sock.close();
        System.out.println("disconnected.");
    }

    private static void handle2(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        String jsonpath = "D:\\courseStation\\CS309\\phantom\\src\\main\\java\\sustech\\edu\\phantom\\dboj\\basicJudge\\JudgeTestJson\\success.json";
        JudgeInput judgeInput = readJson(jsonpath);
        Gson gson=new Gson();

        Scanner scanner = new Scanner(System.in);
        System.out.println("[server] " + reader.readLine());
        for (;;) {
            System.out.print(">>> "); // 打印提示
            String s = scanner.nextLine(); // 读取一行输入
            s=gson.toJson(judgeInput);
            writer.write(s);
            writer.newLine();
            writer.flush();
            String resp = reader.readLine();
            System.out.println("<<< " + resp);
            if (resp.equals("bye")) {
                break;
            }
        }
    }



    private static void handle(InputStream input, OutputStream output) throws IOException {
        String jsonpath = "D:\\courseStation\\CS309\\phantom\\src\\main\\java\\sustech\\edu\\phantom\\dboj\\basicJudge\\JudgeTestJson\\success.json";
        JudgeInput judgeInput = readJson(jsonpath);
        DataOutputStream dataOutputStream = new DataOutputStream(output);
    //    DataInputStream dataInputStream = new DataInputStream(input);

//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
     //   System.out.println("[server] " + reader.readLine());
        Gson gson = new Gson();
        System.out.print(">>>");
       // StringBuilder json = new StringBuilder();
        String resp="";
        while (true) {
            System.out.print(">>> "); // 打印提示
            String s=scanner.nextLine();
            System.out.println(s);
            dataOutputStream.writeUTF(judgeInput.toString());
            // dataOutputStream.writeUTF();
            //writer.newLine();
            dataOutputStream.flush();
       //     json.delete(0, json.length() + 1);
            System.out.println("<<< " + resp);
            resp = reader.readLine();
            if (resp.equals("bye")) {
                break;
            }
        }

    }

    public static JudgeInput readJson(String path) throws IOException {
        String dataSourceFile = path;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
//        char[] buffer=new char[200];
//        br.read(buffer);
//        StringBuilder stringBuilder=new StringBuilder(Arrays.toString(buffer));
        Gson gson = new Gson();
        JudgeInput judgeInput = gson.fromJson(br, JudgeInput.class);
        System.out.println(judgeInput.toString());
        br.close();

        return judgeInput;

    }
}

//        JudgeInput judgeInput=new JudgeInput();
//        judgeInput.judgeDatabaseUrl="www.baidu.com";
//        judgeInput.userName="maozunyao";
//        judgeInput.password="abc123";
//        judgeInput.beforeInput="asdasdas";
//        judgeInput.afterInput=null;
//        judgeInput.timeLimit=1000L;
//        judgeInput.standardAnswer=new ArrayList<>();
//        judgeInput.standardAnswer.add("avd");
//        judgeInput.standardAnswer.add("qqq");
//     String json=gson.toJson(judgeInput);