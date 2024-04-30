package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import Resources.Player;

public class ServerController {
    ServerModel model;
    ServerSocket server;
    Collection<Player> loggedInPlayers;
    Player currentPlayer;
    public ServerController()
    {
        model = new ServerModel(this);
        loggedInPlayers = new ArrayList<Player>();
        currentPlayer = new Player();
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
            server = new ServerSocket(6000); //create a socket_listener on port 6000
            System.out.println("waiting for clients: ");
            Socket serverSocket = server.accept(); //socket endpoint for communication, once request is received it is filled.
            System.out.println("Client Connected");
            Thread thread = new Thread(new ServerConnectionHandler(serverSocket, this, model));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected ArrayList<String> parseLeaderboard() {
        System.out.println("Entered: parseleaderboard");
        Collection<Player> leaderboard = model.getLeaderboard(); // Retrieve leaderboard using the getLeaderboard() method
        ArrayList<String> lines = new ArrayList<>();

        for (Player player : leaderboard) {
            String line = String.format("Player ID: %d, Username: %s, Balance: %d", player.getId(), player.getUsername(), player.getBalance());
            lines.add(line);
        }

        return lines;
    }
    protected String userAuth(String username, String password)
    {
        currentPlayer.setUsername(username);
        currentPlayer.setPHash(password);
        boolean retval = model.userAuth(username, password);
        if (retval == true) return "auth_confirm";
        else return "auth_failure";
    }
    protected int getCoinFlipResult()
    {
        Random random = new Random();
        return random.nextInt(2);
    }
    protected int getDiceRollResult()
    {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
    protected void calculatePlayerBalance()
    {
        // take player balance and modify by the logged bet value
    }
    protected void addNewPlayer(String username, String password)
    {
        model.addNewPlayer(username, password);
    }
    public void quit()
    {
        try
        {
            server.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.exit(0);
    }
}