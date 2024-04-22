import java.net.*;
import java.io.*;

public class MyClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 5555);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

        new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println(serverMessage);
                    if (serverMessage.equals("Server: bye")) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
            }
        }).start();

        String clientMessage;
        while (true) {
            System.out.print("Client: ");
            clientMessage = keyboardReader.readLine();
            writer.println(clientMessage);
            if (clientMessage.equals("bye")) {
                break;
            }
        }

        socket.close();
        System.out.println("Connection Terminated");
    }
}
