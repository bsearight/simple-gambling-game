package Client;
import Resources.Player;

import java.util.ArrayList;
import java.util.Collection;

public class ClientModel
{
    ClientController controller;
    Collection<Player> players;

    public ClientModel(ClientController controller) {
        this.controller = controller;
        init();
    }
    
    private void init()
    {
        players = new ArrayList<Player>();
    }
}
