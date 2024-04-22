import java.net.*;
import java.io.*;

public class MyServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(5555);
        System.out.println("Server Initiated, Waiting for Clients to Connect...");

        while (true) {
            Socket s = ss.accept();
            System.out.println("Client Connected: " + s);

            // Create a new thread to handle each client
            ClientHandler clientHandler = new ClientHandler(s);
            clientHandler.start();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("Client: " + clientMessage);

                // Echo the message back to the client
                writer.println("Server: " + clientMessage);

                if (clientMessage.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
