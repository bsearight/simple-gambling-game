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
    
    public ClientController()
    {
        model = new ClientModel(this);
        view = new ClientView(this);
        init();
    }
    private void init()
    {
        // initiate connection with server
        try
        {
            clientSocket = new Socket("127.0.0.1", 6000);
            System.out.println("Client: attempting Connection");
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            writer = new PrintWriter(clientSocket.getOutputStream());
            while(true)
            {
                // 
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public boolean login(String username, String password)
    {
        phash = DigestUtils.md5Hex(password);
        System.out.println(phash);
        model.setCurrentPlayer(username, phash);
        // get response from server, if success, switch view panels
        loginSuccess = true;
        view.setIsLoggedIn(loginSuccess);
        return loginSuccess;
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
        writer.println(current.getPHash());
        try
        {
            retval = reader.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (retval != null)
        {
            if (retval == "auth_confirm") return true;
            else return false;
        }
        return false;
    }
    public void quit()
    {
        System.exit(0);
    }
}
