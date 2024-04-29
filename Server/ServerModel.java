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
            String cmd = "CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY, username TEXT, password TEXT, balance INTEGER DEFAULT 1000, betValue INTEGER);";
            st.execute(cmd);
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
            String cmd = "INSERT INTO player (username, password, balance) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, username);
            ps.setString(2, pHash);
            ps.setInt(3, 1000);
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
            String cmd = "SELECT password FROM player WHERE id = ?;";
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
            String cmd = "SELECT id, username, balance FROM player ORDER BY balance DESC;"; // Fetch all players ordered by balance
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Player player = new Player();
                player.setId(rs.getInt("id"));
                player.setUsername(rs.getString("username"));
                player.setBalance(rs.getInt("balance"));
                leaderboard.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            String cmd = "UPDATE player SET username = ?, balance = ?, betValue = ? WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, player.getUsername());
            ps.setInt(2, player.getBalance());
            ps.setInt(3, player.getBetValue());
            ps.setInt(4, player.getId());
            ps.executeQuery();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    protected boolean checkDuplicateUser(String username)
    {
        try 
        {
            String cmd = "SELECT * FROM player WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return true;
            else return false;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
