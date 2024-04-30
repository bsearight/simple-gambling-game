package Client;

import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class ClientLoginPane
{
    ClientController controller;
    JTextField uname;
    JPasswordField pword;
    public ClientLoginPane(ClientController controller)
    {
        this.controller = controller;
        init();
    }

    private void init()
    {
        uname = new JTextField();
        pword = new JPasswordField();
        Object[] contents =
        {
            "Username:", uname,
            "Password:", pword
        };
        int option = JOptionPane.showConfirmDialog(null, contents, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION)
        {
            if (controller.login(uname.getText(), pword.getText())) JOptionPane.showMessageDialog(null, "Login Success!", "", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
