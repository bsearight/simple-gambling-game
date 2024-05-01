package Server;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

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
            String cmd = "CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY, username TEXT UNIQUE, password TEXT, balance INTEGER DEFAULT 1000, betValue INTEGER);";
            st.execute(cmd);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected boolean addNewPlayer(String username, String pHash)
    {
        try
        {
            String cmd = "INSERT INTO player (username, password, balance) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, username);
            ps.setString(2, pHash);
            ps.setInt(3, 1000);
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean userAuth(String username, String password)
    {
        String hash = "";
        Player player = getPlayer(username);
        try {
            String cmd = "SELECT password FROM player WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setInt(1, player.getId());
            ResultSet rs = ps.executeQuery(); // Execute the prepared statement directly
            if (rs.next()) {
                hash = rs.getString(1); // Retrieve the password directly from the ResultSet
                System.out.println("Retrieved hashed password from the database: " + hash);
                System.out.println("Hashed password provided by the player: " + password);
            } else {
                System.err.println("Error: Player not found in the database");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(Objects.equals(hash, password)){
            return true;
        }
        return false;
    }

    protected ArrayList<String> getLeaderboard()
    {
        
        ArrayList<String> list = new ArrayList<String>();
        try {
            Statement st = connection.createStatement();
            String cmd = "SELECT username, balance FROM player ORDER BY balance DESC LIMIT 3;"; // Fetch all players ordered by balance
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next())
            {
                StringBuilder builder = new StringBuilder();
                builder.append(rs.getString("username")).append(": ").append(rs.getInt("balance"));
                list.add(builder.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }
    protected Player getPlayer(String username)
    {
        Player player = new Player();
        try 
        {
            String cmd = "SELECT id, username, balance, betValue FROM player WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                player.setId(rs.getInt("id"));
                player.setUsername(rs.getString("username"));
                player.setBalance(rs.getInt("balance"));
                player.setBetValue(rs.getInt("betValue"));
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
    protected void updatePlayer(String username, int balance, int betValue)
    {
        System.out.println("model's betValue: " + betValue);
        Player player = getPlayer(username);
        try 
        {
            String cmd = "UPDATE player SET username = ?, balance = ?, betValue = ? WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, player.getUsername());
            ps.setInt(2, balance);
            ps.setInt(3, betValue);
            ps.setInt(4, player.getId());
            ps.executeUpdate();
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
