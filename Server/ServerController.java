package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import Resources.Player;

public class ServerController {
    ServerModel model;
    ServerSocket server;
    
    public ServerController()
    {
        model = new ServerModel(this);
        init();
    }
    private void init() {
        // wait for connections from clients
        try {
            server = new ServerSocket(6000); //create a socket_listener on port 6000
            System.out.println("waiting for clients: ");
            Socket serverSocket = server.accept(); //socket endpoint for communication, once request is received it is filled.
            System.out.println("Client Connected");
            Thread thread = new Thread(new ServerConnectionHandler(serverSocket, this));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected String userAuth(String username, String password)
    {
        boolean retval = model.userAuth(username, password);
        if (retval == true) return "auth_confirm";
        else return "auth_failure";
    }
    protected int getCoinFlipResult()
    {
        Random random = new Random();
        int result = random.nextInt(2);
        return result;
    }
    protected int getDiceRollResult()
    {
        Random random = new Random();
        int result = random.nextInt(6) + 1;
        return result;
    }
    protected void confirmBetting(int bet, String username)
    {
        Player current = model.getPlayer(username);
        current.setBetValue(bet);
        model.updatePlayer(current.getUsername(), current.getBalance(), bet);
        System.out.println("set user: " + current.getUsername() + " to balance: " + current.getBalance() + " and bet: " + bet);
    }
    protected int getBalance(String username)
    {
        Player current = model.getPlayer(username);
        return current.getBalance();
    }
    protected void calculatePlayerBalance(int result, String username, int userOption)
    {
        Player current = model.getPlayer(username);
        int balance = current.getBalance();
        int bet = current.getBetValue();
        System.out.println("retrieved user option: " + userOption + " vs retrieved result: " + result);
        if (userOption == result) // win
        {  
            balance += bet;
            System.out.println("user won: " + bet);
        }
        else // lose
        {
            balance -= bet;
            System.out.println("user lost: " + bet);
        }
        System.out.println("new balance: " + balance);
        model.updatePlayer(username, balance, 0);
    }
    protected String getLeaderboard()
    {
        return model.getLeaderboard();
    }
    protected String addNewPlayer(String username, String password)
    {
        boolean retval = model.addNewPlayer(username, password);
        if (retval == true) return "create_confirm";
        else return "create_failure";
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