package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.commons.codec.digest.DigestUtils;
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
            Socket clientSocket = new Socket("127.0.0.1", 6000);
            System.out.println("Client: attempting Connection");
            while(true){
                //InputStreamReader stream = new InputStreamReader(socket.getInputStream());
                //BufferedReader reader = new BufferedReader(stream);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public boolean login(String username, String password)
    {
        String phash = DigestUtils.md5Hex(password);
        System.out.println(phash);
        // get response from server, if success, switch view panels
        loginSuccess = true;
        view.setIsLoggedIn(loginSuccess);
        return loginSuccess;
    }
    public void quit()
    {
        System.exit(0);
    }
}
