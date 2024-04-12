package Server;
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
    }
    public void quit()
    {
        System.exit(0);
    }
}
