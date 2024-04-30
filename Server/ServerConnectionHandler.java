package Server;
import Resources.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
                    case "auth_user_hash":
                        auth_user(writer, reader);
                        break;
                    case "get_leaderboard":
                        //send leaderboard
                        System.out.println("Server: Entering leaderboard()");
                        leaderboard(writer);
                        break;
                    case "get_coinflip":
                        //generate between 0 and 1 for coinflip
                        coinflip(writer);
                        break;
                    case "get_balance":
                        balance(writer);
                        break;
                    case "confirm_betting":
                        confirm_bet(writer);
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

    private void auth_user(PrintWriter writer, BufferedReader reader){
        //receive u_name and p_word from usr. Query database for acc. send ack.
        String line;
        String uname = "";
        String pssword = "";
        try{
            for(int i = 0; i < 3; i++) {
                line = reader.readLine();
                if (i == 0) {
                    uname = line;
                    System.out.format("uname: %s\n", uname);
                } else if (i == 1) {
                    pssword = line;
                    System.out.format("password: %s\n", pssword);
                    i = 3;
                }
            }
            System.out.format("uname: %s\n password: %s\n", uname, pssword);
            String ret = controller.userAuth(uname, pssword);
            System.out.format("userAuth Return: %s\n", ret);
            writer.println(ret);

        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    private void leaderboard(PrintWriter writer) {
        ArrayList<String> leaderboard = controller.parseLeaderboard();
        for (String line : leaderboard) {
            writer.println(line);
        }
    }


    private void coinflip(PrintWriter writer){
        System.out.println("Java: Received get_coinflip");
        int Result = controller.getCoinFlipResult();
        writer.println(Result);
    }


    private void balance(PrintWriter writer){
        System.out.println("Java: Received get_balance");
    }


    private void confirm_bet(PrintWriter writer){
        System.out.println("Java: Received confirm_bet");
        //send confirm message,

        //receive bet amount for heads or tails

        //server hold data locally.
    }

    private void create_user(PrintWriter writer, BufferedReader reader){
        //receive u_name and p_word from usr. Query database for acc. send ack.
        System.out.println("Entered: create_user");
        String line;
        String uname = "";
        String pssword = "";
        try{
            for(int i = 0; i < 3; i++) {
                line = reader.readLine();
                if (i == 0) {
                    uname = line;
                    System.out.format("user = %s\n", uname);
                } else if (i == 1) {
                    pssword = line;
                    System.out.format("password = %s\n", pssword);
                    i = 3;
                }
                System.out.println("still in loop!!!");
            }
            System.out.println("Escaped for loop?");
            model.addNewPlayer(uname, pssword);
            writer.println("auth_confirm");
            System.out.println("user_created");
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void quit()
    {
        System.exit(0);

    }
}
