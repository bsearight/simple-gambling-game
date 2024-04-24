package Server;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import Resources.Player;

public class ServerModel
{
    ServerController controller;
    Connection connection = null;
    String uri = "jdbc:sqlite:database.db";

    public ServerModel(ServerController controller)
    {
        this.controller = controller;
        init();
    }

    private void init()
    {
        try
        {
            connection = DriverManager.getConnection(uri);
            System.out.println("Successfully connected to database.");
            Statement st = connection.createStatement();
            String cmd = "CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY, username TEXT, password TEXT, balance INTEGER);";
            st.execute(cmd);
            cmd = "INSERT OR IGNORE INTO player (username, password, balance) VALUES ('sasd', '912ec803b2ce49e4a541068d495ab570', 100000);";
            st.executeUpdate(cmd);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected void addNewPlayer(String username, String pHash)
    {
        try
        {
            String cmd = "INSERT INTO player (username, password, balance) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, username);
            ps.setString(2, pHash);
            ps.setInt(3, 10000);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected boolean userAuth(Player p)
    {
        String hash = "";
        try
        {
            String cmd = "SELECT password FROM enemy WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                hash = rs.getString("password");
            }
            else
            {
                System.out.println("Error: Unknown database failure");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        if (hash == p.getPHash()) return true;
        else return false;
    }

    protected Collection<Player> getLeaderboard()
    {
        Collection<Player> leaderboard = new ArrayList<Player>();
        try 
        {
            String cmd = "SELECT id, username, balance FROM player ORDER BY balance DESC LIMIT 10;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Player player = new Player();
                player.setId(rs.getInt("id"));
                player.setUsername(rs.getString("username"));
                player.setBalance(rs.getInt("balance"));
                leaderboard.add(player);
            }
            return leaderboard;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return leaderboard; // leaderboard will be NULL
    }
}
