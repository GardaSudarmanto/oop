import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(new IncomingReader(in)).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            out.println(message);
        }
    }

    private static class IncomingReader implements Runnable {
        private BufferedReader in;

        public IncomingReader(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}