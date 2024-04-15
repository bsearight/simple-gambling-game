package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClientGameView
{
    private ClientController controller;
    private JFrame frame;
    private JPanel buttonsPanel;
    private JButton quitButton;
    private JButton betButton;
    private JButton flipButton;
    private JButton okayButton;
    private JTextArea coinFlip;

    public ClientGameView(ClientController controller)
    {
        this.controller = controller;
        init();
    }
    private void init()
    {
        frame = new JFrame("Coin Flip Game");
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
            }
        });
        betButton = new JButton("Bet");
        betButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // open betting pane
            }
        });
        flipButton = new JButton("Flip");
        flipButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // flip the coin
            }
        });
        okayButton = new JButton("Okay");
        okayButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // change panels
            }
        });
        coinFlip = new JTextArea();
        frame.add(BorderLayout.CENTER, coinFlip);
        buttonsPanel.add(betButton);
        buttonsPanel.add(okayButton);
        buttonsPanel.add(flipButton);
        buttonsPanel.add(quitButton);
        frame.add(BorderLayout.SOUTH, buttonsPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                controller.view.setGameViewOpen(false);
            }
        });
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
