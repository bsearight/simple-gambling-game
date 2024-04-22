package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
                if (line.equals("auth_user_hash"))
                {
                     writer.println("auth_confirm");
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
