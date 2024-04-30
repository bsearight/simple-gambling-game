package Server;
import Resources.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerConnectionHandler implements Runnable
{
    private Socket clientSocket;
    ServerController controller;
    ServerModel model;
    public ServerConnectionHandler(Socket socket, ServerController controller, ServerModel model)
    {
        this.clientSocket = socket;
        this.controller = controller;
        this.model = model;
    }

    @Override
    public void run()
    {
        InputStreamReader inputStreamReader = null;
        try
        {
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            String line;
            while((line = reader.readLine()) != null)
            {
                switch(line) {
                    case "auth_user":
                        auth_user(writer, reader);
                        break;
                    case "get_leaderboard":
                        System.out.println("Server: Entering leaderboard()");
                        leaderboard(writer);
                        break;
                    case "get_coinflip":
                        coinflip(writer);
                        break;
                    case "get_balance":
                        balance(writer);
                        break;
                    case "confirm_bet":
                        confirm_bet(writer, reader);
                        break;
                    case "create_user":
                        create_user(writer, reader);
                        break;
                    default:
                        System.out.format("ServerConnectionHandler: Case For (%s) Not Found", line);
                        break;
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    //add private functions for each case.

    private void auth_user(PrintWriter writer, BufferedReader reader)
    {
        try
        {
            String username = "";
            String password = "";
            username = reader.readLine();
            password = reader.readLine();
            System.out.println(password);
            String retval = controller.userAuth(username, password);
            writer.println(retval);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    private void leaderboard(PrintWriter writer)
    {
        String retval = model.getLeaderboard();
        writer.println(retval);
    }


    private void coinflip(PrintWriter writer){
        int retval = controller.getCoinFlipResult();
        if (retval == 1) writer.println("1");
        else writer.println("0");
    }


    private void balance(PrintWriter writer)
    {
        int balance = controller.getBalance();
        writer.println(Integer.toString(balance));
    }


    private void confirm_bet(PrintWriter writer, BufferedReader reader)
    {
        String retval = "";
        int option = 0;
        int bet = 0;
        try
        {
            retval = reader.readLine();
            System.out.println("server received: " + retval);
            if (retval == "Heads") option = 1;
            else if (retval == "Tails") option = 0;
            retval = reader.readLine();
            bet = Integer.parseInt(retval);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            //dies of cringe
            //if this happens literally everything will break
            controller.quit();
        }
        controller.confirmBetting(bet, option);
    }

    private void create_user(PrintWriter writer, BufferedReader reader){
        String username = "";
        String password = "";
        try
        {
            username = reader.readLine();
            System.out.format("username: %s\n", username);
            password = reader.readLine();
            System.out.format("password: %s\n", password);
            String retval = model.addNewPlayer(username, password);
            writer.println(retval);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void quit()
    {
        System.exit(0);

    }
}
