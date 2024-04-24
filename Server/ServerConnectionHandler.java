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

    public ServerConnectionHandler(Socket socket)
    {
        this.clientSocket = socket;
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
                        writer.println("auth_confirm");
                        break;
                    case "get_leaderboard":
                        //send leaderboard
                        writer.println("");
                        break;
                    case "get_coinflip":
                        //generate between 0 and 1 for coinflip
                        writer.println("");
                        break;
                    case "get_balance":
                        //send balance.
                        writer.println("");
                        break;
                    case "confirm_betting":
                        //send confirm messge,

                        //receive bet ammount for or tails

                        //server hold data locally.
                        break;
                    default:
                        break;
                }
            }
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
