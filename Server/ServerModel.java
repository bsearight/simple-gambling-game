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
            String drop = "DROP TABLE IF EXISTS player;";
            st.execute(drop);
            String cmd = "CREATE TABLE IF NOT EXISTS player (player_ID INT NOT NULL PRIMARY KEY, username varchar(30) NOT NULL, password VARCHAR(30) NOT NULL, balance INT DEFAULT 100);";
            st.execute(cmd);
            cmd = String.format("INSERT INTO player (player_ID, username, password, balance) VALUES (%d, '%s', '%s', %d);", 1, "sas","63c728094326428181dbf7c15247035f", 1000);
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
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(player_ID) FROM player");
            int maxID = 0;
            if (rs.next()) {
                maxID = rs.getInt(1);
            }
            int player_ID = maxID + 1;
            String cmd = String.format("INSERT INTO player (player_ID, username, password, balance) VALUES (%d, '%s', '%s', %d);", player_ID, username, pHash, 1000);
            st.execute(cmd);
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
            String cmd = "SELECT password FROM player WHERE player_ID = ?;";
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

    protected Collection<Player> getLeaderboard() {
        Collection<Player> leaderboard = new ArrayList<Player>();
        try {
            Statement st = connection.createStatement();
            String cmd = "SELECT player_ID, username, balance FROM player ORDER BY balance DESC;"; // Fetch all players ordered by balance
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Player player = new Player();
                player.setId(rs.getInt("player_ID"));
                player.setUsername(rs.getString("username"));
                player.setBalance(rs.getInt("balance"));
                leaderboard.add(player);
            }
            rs.close(); // Close the ResultSet
            st.close(); // Close the Statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in your application
        }
        return leaderboard;
    }
    protected Player getPlayer(String username)
    {
        Player player = new Player();
        try 
        {
            String cmd = "SELECT id, username, balance FROM player WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                player.setId(rs.getInt("id"));
                player.setUsername(rs.getString("username"));
                player.setBalance(rs.getInt("balance"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return player;
    }
    protected Player getPlayer(int id)
    {
        Player player = new Player();
        try 
        {
            String cmd = "SELECT id, username, balance FROM player WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                player.setId(rs.getInt("id"));
                player.setUsername(rs.getString("username"));
                player.setBalance(rs.getInt("balance"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return player;
    }
    protected void updatePlayer(Player player)
    {
        try 
        {
            String cmd = "UPDATE player SET username = ?, balance = ? WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, player.getUsername());
            ps.setInt(2, player.getBalance());
            ps.setInt(3, player.getId());
            ps.executeQuery();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
