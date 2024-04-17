package Client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
    private JButton quitButton;
    private JButton betButton;
    private JButton flipButton;
    private JButton okayButton;
    private JTextArea coinFlip;
    private JPanel switchPanel;
    private JPanel defaultState;
    private JPanel postBetState;
    private JPanel cleanupState;

    public ClientGameView(ClientController controller)
    {
        this.controller = controller;
        init();
    }
    private void init()
    {
        frame = new JFrame("Coin Flip Game");
        switchPanel = new JPanel(new CardLayout());
        defaultState = new JPanel(new FlowLayout(FlowLayout.CENTER));
        postBetState = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cleanupState = new JPanel(new FlowLayout(FlowLayout.CENTER));
        initButtons();
        switchPanel.add(defaultState, "Default");
        switchPanel.add(postBetState, "Post Bet");
        switchPanel.add(cleanupState, "Cleanup");
        coinFlip = new JTextArea();
        frame.add(BorderLayout.CENTER, switchPanel);
        defaultState.add(betButton);
        defaultState.add(quitButton);
        postBetState.add(flipButton);
        cleanupState.add(okayButton);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                controller.view.setGameViewOpen(false);
                controller.view.showWindow();
            }
        });
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    private void openBettingPane()
    {
        new ClientBettingPane(controller, this);
    }
    protected void cancelBetting()
    {
        defaultState();
    }
    protected void confirmBetting()
    {
        postBetState();
    }
    private void defaultState()
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        layout.show(switchPanel, "Default");
    }
    private void postBetState()
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        layout.show(switchPanel, "Post Bet");
    }
    private void cleanupState()
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        layout.show(switchPanel, "Cleanup");
    }
    private void initButtons()
    {
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.view.setGameViewOpen(false);
                controller.view.showWindow();
                frame.dispose();
            }
        });
        betButton = new JButton("Bet");
        betButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                openBettingPane();
            }
        });
        flipButton = new JButton("Flip");
        flipButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cleanupState();
            }
        });
        okayButton = new JButton("Okay");
        okayButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                defaultState();
            }
        });
    }
}
