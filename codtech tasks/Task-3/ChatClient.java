import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true)) {
            
            // Create a thread to listen for incoming messages from the server
            Thread listenThread = new Thread(new ListenFromServer(serverInput));
            listenThread.start();
            
            // Allow the user to input messages and send them to the server
            String message;
            while ((message = userInput.readLine()) != null) {
                serverOutput.println(message);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}

class ListenFromServer implements Runnable {
    private BufferedReader serverInput;
    
    public ListenFromServer(BufferedReader serverInput) {
        this.serverInput = serverInput;
    }
    
    @Override
    public void run() {
        try {
            String message;
            while ((message = serverInput.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.err.println("Error reading from server: " + e.getMessage());
        }
    }
}
