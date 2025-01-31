import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    
    public static void main(String[] args) {
        System.out.println("Server started...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected...");
                
                // Create a new thread to handle this client
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread clientThread = new Thread(clientHandler);
                clientHandlers.add(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Error while setting up server: " + e.getMessage());
        }
    }
    
    // Broadcast message to all connected clients
    public static void broadcastMessage(String message, ClientHandler excludeClient) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != excludeClient) {
                clientHandler.sendMessage(message);
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            // Set up I/O streams
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            // Prompt the client for their name
            out.println("Enter your name: ");
            clientName = in.readLine();
            out.println("Welcome to the chat, " + clientName + "!");
            
            // Broadcast the user's arrival
            ChatServer.broadcastMessage(clientName + " has joined the chat.", this);
            
            // Read and broadcast messages from the client
            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                ChatServer.broadcastMessage(clientName + ": " + message, this);
            }
            
            // Handle client disconnection
            ChatServer.broadcastMessage(clientName + " has left the chat.", this);
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
    
    // Send a message to the client
    public void sendMessage(String message) {
        out.println(message);
    }
}
