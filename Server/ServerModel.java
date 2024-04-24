package Server;
import java.sql.*;
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

    void addNewPlayer(String username, String pHash)
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
}
