package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController
{
    ServerModel model;
    public ServerController()
    {
        model = new ServerModel(this);
        init();
    }
    private void init()
    {
        // wait for connections from clients
        try {
            ServerSocket server = new ServerSocket(6000);
            while(true)
            {
                System.out.println("waiting for clients: ");
                Socket serverSocket = server.accept(); //waits for connection N, returns new socket connection
                System.out.println("Client Connected");
                while(true)
                {

                }
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void quit()
    {
        System.exit(0);
    }
}
