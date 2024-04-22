package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.codec.digest.DigestUtils;
import Resources.Player;
// tried using MessageDigest for password encryption, but wasn't sure how to handly byte arrays
// https://stackoverflow.com/questions/332079/in-java-how-do-i-convert-a-byte-array-to-a-string-of-hex-digits-while-keeping-l
// which led me to finding apache commons codec
// https://commons.apache.org/proper/commons-codec/apidocs/org/apache/commons/codec/digest/DigestUtils.html#md5Hex(byte%5B%5D)
// https://commons.apache.org/proper/commons-codec/

public class ClientController
{
    private ClientModel model;
    public ClientView view;
    boolean loginSuccess = false;
    private String phash;
    Socket clientSocket;
    InputStreamReader inputStreamReader;
    BufferedReader reader;
    PrintWriter writer;
    String retval;
    boolean offline = false;
    
    public ClientController()
    {
        model = new ClientModel(this);
        view = new ClientView(this);
        init();
    }
    /*Client Logic:
Communication: Interacts with the server via sockets for data exchange.
User Authentication: Authenticates users with local password hashing.
Temporary Local Storage: Stores temporary data locally, fetching/sending server info as needed.
Leaderboard: Displays players and their cash balances.
GUI: Drives all other logic through a graphical user interface.
*/
    private void init()
    {
        // initiate connection with server
        try
        {
            clientSocket = new Socket("127.0.0.1", 6000);
            System.out.println("Client: attempting Connection");
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        }
        catch (IOException ex)
        {
            System.out.println("Failed to connect to server");
            offline = true;
            ex.printStackTrace();
        }
    }
    public boolean login(String username, String password)
    {
        if (offline == true)
        {
            view.setIsLoggedIn(true);
            return true;
        }
        phash = DigestUtils.md5Hex(password);
        System.out.println(phash);
        model.setCurrentPlayer(username, phash);
        view.setIsLoggedIn(isLoggedIn());
        return isLoggedIn();
    }
    public void logout()
    {
        view.setIsLoggedIn(false);
    }
    public boolean isLoggedIn()
    {
        // confirm whether the current user is logged in on the server
        // this is a security measure to prevent spoofing logins locally
        // returns a boolean indicating whether it succeeded or not
        Player current = model.getCurrentPlayer();
        writer.println("auth_user_hash");
        writer.flush();
        writer.println(current.getUsername());
        writer.flush();
        writer.println(current.getPHash());
        writer.flush();
        try
        {
            if((retval = reader.readLine()) != null) {
                System.out.println(retval);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (retval != null)
        {
            if (retval.equals("auth_confirm")) return true;
            else return false;
        }
        return false;
    }
    public String getLeaderboard()
    {
        writer.println("get_leaderboard");
        int numLines = 0;
        String retval = "";
        try 
        {
            String count = reader.readLine();
            numLines = Integer.parseInt(count);
            for (int i = 0; i < numLines; i++)
            {
                retval += reader.readLine() + "\n";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        return retval;
    }
    protected int getCoinFlip()
    {
        // connect to server and request coin flip results
        // remember to reauth with isLoggedIn()
        return 1; // 1 is heads, 0 is tails
    }
    public void quit()
    {
        System.exit(0);
    }
}
