package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    ServerModel model;

    public ServerController() {
        model = new ServerModel(this);
        init();
    }
/*
Server Logic:
Concurrent Connections: Handles multiple concurrent socket connections with players.
Database: Stores player info, including password hashes and balances. Optionally, consider including leaderboard data.
Game and Betting Logic: Implements actual game and betting logic.
No Back-End Support: Server does not require a separate back-end system.
*  */
    private void init() {
        // wait for connections from clients
        try {
            ServerSocket server = new ServerSocket(6000); //create a socket_listener on port 6000
            while (true) {
                System.out.println("waiting for clients: ");
                Socket serverSocket = server.accept(); //socket endpoint for communication, once request is received it is filled.
                System.out.println("Client Connected");
                Thread thread = new Thread(new ClientHandler(serverSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void quit()
    {
        System.exit(0);
    }
}
