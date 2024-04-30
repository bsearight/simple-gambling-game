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
    private final ClientModel model;
    public final ClientMainMenuView view;
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
        view = new ClientMainMenuView(this);
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
        boolean retval = isLoggedIn();
        view.setIsLoggedIn(retval);
        return retval;
    }
    public void logout()
    {
        view.setIsLoggedIn(false);
        try
        {
            if (writer != null) writer.close();
            if (clientSocket != null) clientSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public boolean isLoggedIn()
    {
        // confirm whether the current user is logged in on the server
        // this is a security measure to prevent spoofing logins locally
        // returns a boolean indicating whether it succeeded or not
        Player current = model.getCurrentPlayer();
        writer.println("auth_user_hash");
        writer.println(current.getUsername());
        writer.println(current.getPHash());
        try
        {
            if((retval = reader.readLine()) != null) {
                    if (retval == "auth_confirm")
                    {
                        return true;
                    }
                    else return false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public String getLeaderboard() {
        System.out.println("ClientController: Entered getLeaderboard()");
        writer.println("get_leaderboard");
        String retval = ""; // Define retval outside the try block
        try {
            retval = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, such as logging or returning a default value
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception, such as logging or returning a default value
        }
        return retval;
    }
    protected int getCoinFlip()
    {
        // connect to server and request coin flip results
        return 1; // 1 is heads, 0 is tails
    }
    protected int getDiceRoll()
    {
        return 4;
    }
    protected void confirmBetting(int bet)
    {
        // connect to server and submit bet value
    }
    protected boolean checkDuplicateUser(String username)
    {
        // connect to server and request duplicate user check
        // return a boolean to the Register Pane
        return false;
    }
    protected boolean registerUser(String username, String password)
    {
        if (offline == true)
        {
            view.setIsLoggedIn(true);
            return true;
        }
        phash = DigestUtils.md5Hex(password);
        System.out.println(phash);
        model.setCurrentPlayer(username, phash);
        boolean retval = createPlayer();
        view.setIsLoggedIn(retval);
        return retval;
    }
    private boolean createPlayer()
    {
        Player current = model.getCurrentPlayer();
        writer.println("create_user");
        writer.println(current.getUsername());
        writer.println(current.getPHash());
        try
        {
            if((retval = reader.readLine()) != null) {
                    if (retval.equals("auth_confirm"))
                    {
                        return true;
                    }
                    else return false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public void quit()
    {
        try 
        {
            logout();
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
