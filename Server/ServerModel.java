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
            String cmd = "CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY, name TEXT, pass TEXT, balance INTEGER);";
            st.execute(cmd);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
