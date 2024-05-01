package Client;

import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class ClientRegisterPane
{
    ClientController controller;
    JTextField username;
    JPasswordField password;
    JPasswordField confirmPassword;
    public ClientRegisterPane(ClientController controller)
    {
        this.controller = controller;
        init();
    }

    @SuppressWarnings("deprecation")
    private void init()
    {
        username = new JTextField();
        password = new JPasswordField();
        confirmPassword = new JPasswordField();
        Object[] contents =
        {
            "Username:", username,
            "Password:", password,
            "Confirm Password:", confirmPassword
        };
        int option = JOptionPane.showConfirmDialog(null, contents, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION)
        {
            if (password.getText().equals(confirmPassword.getText()))
            {
                boolean retval = controller.registerUser(username.getText(), password.getText());
                if (retval == true) JOptionPane.showMessageDialog(null, "Registered Successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(null, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else JOptionPane.showMessageDialog(null, "Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
