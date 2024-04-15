package Client;
import Resources.Player;

import java.util.ArrayList;
import java.util.Collection;

// this class is mainly used to hold player data for the leaderboard function
public class ClientModel
{
    ClientController controller;
    Collection<Player> players;
    Player currentPlayer;

    public ClientModel(ClientController controller) {
        this.controller = controller;
        init();
    }
    
    private void init()
    {
        currentPlayer = new Player();
        players = new ArrayList<Player>();
    }

    protected void setCurrentPlayer(String username, String phash)
    {
        currentPlayer.setUsername(username);
        currentPlayer.setPHash(phash);
    }

    protected Player getCurrentPlayer()
    {
        return currentPlayer;
    }
}
