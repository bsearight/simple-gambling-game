package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class ServerConnectionHandler implements Runnable
{
    private Socket clientSocket;
    ServerController controller;
    String username;
    int userOption;
    int result;
    public ServerConnectionHandler(Socket socket, ServerController controller)
    {
        this.clientSocket = socket;
        this.controller = controller;
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
                    case "get_diceroll":
                        diceRoll(writer);
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
    private void auth_user(PrintWriter writer, BufferedReader reader)
    {
        
        String username = "";
        try
        {
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
        this.username = username;
    }

    private void leaderboard(PrintWriter writer)
    {
        ArrayList<String> retval = controller.getLeaderboard();
        writer.println(retval.get(0));
        writer.println(retval.get(1));
        writer.println(retval.get(2));
    }


    private void coinflip(PrintWriter writer){
        result = controller.getCoinFlipResult();
        if (result == 1) writer.println("1");
        else if (result == 0) writer.println("0");
        controller.calculatePlayerBalance(result, username, userOption);
        result = -1;
    }

    private void diceRoll(PrintWriter writer)
    {
        result = controller.getDiceRollResult();
        switch (result)
        {
            case 1:
            writer.println("1");
            break;
            case 2:
            writer.println("2");
            break;
            case 3:
            writer.println("3");
            break;
            case 4:
            writer.println("4");
            break;
            case 5:
            writer.println("5");
            break;
            case 6:
            writer.println("6");
            break;
        }
        controller.calculatePlayerBalance(result, username, userOption);
        result = -1;
    }

    private void balance(PrintWriter writer)
    {
        int balance = controller.getBalance(username);
        writer.println(Integer.toString(balance));
    }


    private void confirm_bet(PrintWriter writer, BufferedReader reader)
    {
        String retval = "";
        int bet = 0;
        try
        {
            retval = reader.readLine();
            userOption = Integer.parseInt(retval);
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
        controller.confirmBetting(bet, username);
    }

    private void create_user(PrintWriter writer, BufferedReader reader){
        String username = "";
        String password = "";
        try
        {
            username = reader.readLine();
            password = reader.readLine();
            String retval = controller.addNewPlayer(username, password);
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
