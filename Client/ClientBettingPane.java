package Client;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClientBettingPane
{
    ClientController controller;
    ClientGameView gameView;
    boolean error = false;

    public ClientBettingPane(ClientController controller, ClientCoinFlipGameView gameView)
    {
        this.controller = controller;
        this.gameView = gameView;
        init();
    }
    public ClientBettingPane(ClientController controller, ClientDiceRollGameView gameView)
    {
        this.controller = controller;
        this.gameView = gameView;
        init();
    }

    private void init()
    {
        JTextField betValue = new JTextField();
        String[] coinOptions = {"Heads", "Tails"};
        JComboBox<String> coinOption = new JComboBox<>(coinOptions);
        Object[] contents =
        {
            "Bet Amount:", betValue,
            "Coin Option:", coinOption
        };
        int option = JOptionPane.showConfirmDialog(null, contents, "Betting", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION)
        {
            try
            {
                int retval = Integer.parseInt(betValue.getText());
                if (retval < 1) error();
            }
            catch (NumberFormatException e)
            {
                error();
            }
            if (error == false)
            {
                String selectedOption = coinOption.getSelectedItem().toString();
                String retval = "";
                if (selectedOption == "Heads") retval = "1";
                else if (selectedOption == "Tails") retval = "0";
                controller.confirmBetting(betValue.getText(), retval);
                gameView.confirmBetting();
                JOptionPane.showMessageDialog(null, "Bet Placed", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "Bet Cancelled", "Error", JOptionPane.ERROR_MESSAGE);
            gameView.cancelBetting();
        }
    }
    private void error()
    {
        JOptionPane.showMessageDialog(null, "Invalid Bet", "Error", JOptionPane.ERROR_MESSAGE);
        gameView.cancelBetting();
        error = true;
    }
}
