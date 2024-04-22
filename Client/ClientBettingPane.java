package Client;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClientBettingPane
{
    ClientController controller;
    ClientCoinFlipGameView gameView;

    public ClientBettingPane(ClientController controller, ClientCoinFlipGameView gameView)
    {
        this.controller = controller;
        this.gameView = gameView;
        init();
    }

    private void init()
    {
        JTextField betValue = new JTextField();
        Object[] contents =
        {
            "Bet Amount:", betValue
        };
        int option = JOptionPane.showConfirmDialog(null, contents, "Betting", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION)
        {
            JOptionPane.showMessageDialog(null, "Bet Placed", "", JOptionPane.INFORMATION_MESSAGE);
            gameView.confirmBetting();
        }
            else 
        {
            JOptionPane.showMessageDialog(null, "Bet Cancelled", "Error", JOptionPane.ERROR_MESSAGE);
            gameView.cancelBetting();
        }
    }
}
