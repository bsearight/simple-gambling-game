package Client;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClientLeaderboardPane
{
    ClientController controller;
    JTextArea leaderboard;
    public ClientLeaderboardPane(ClientController controller)
    {
        this.controller = controller;
        init();
    }
    private void init()
    {
        leaderboard = new JTextArea();
        leaderboard.setRows(3);
        leaderboard.setText(controller.getLeaderboard());
        JOptionPane.showMessageDialog(null, leaderboard, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }
}
