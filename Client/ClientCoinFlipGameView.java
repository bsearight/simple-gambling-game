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
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ClientCoinFlipGameView extends ClientGameView
{
    private static final int HEADS = 1;
    private static final int TAILS = 0;
    private ClientController controller;
    private JFrame frame;
    private JButton quitButton;
    private JButton betButton;
    private JButton flipButton;
    private JButton okayButton;
    private JPanel switchPanel;
    private JPanel defaultState;
    private JPanel postBetState;
    private JPanel cleanupState;
    private JLabel coinFlip;
    private JLabel coinHeads;
    private JLabel coinTails;
    private ImageIcon icon;
    private boolean confirmBetting;

    public ClientCoinFlipGameView(ClientController controller)
    {
        this.controller = controller;
        init();
    }
    private void init()
    {
        frame = new JFrame("Coin Flip Game");
        switchPanel = new JPanel(new CardLayout());
        defaultState = new JPanel(new FlowLayout(FlowLayout.CENTER));
        postBetState = new JPanel(new BorderLayout());
        cleanupState = new JPanel(new BorderLayout());
        initButtons();
        switchPanel.add(defaultState, "Default");
        switchPanel.add(postBetState, "Post Bet");
        switchPanel.add(cleanupState, "Cleanup");
        frame.add(BorderLayout.CENTER, switchPanel);
        coinFlip = getIcon("/Resources/coinflip.gif");
        coinHeads = getIcon("/Resources/coinheads.png");
        coinTails = getIcon("/Resources/cointails.png");
        defaultState.add(betButton, BorderLayout.SOUTH);
        defaultState.add(quitButton, BorderLayout.SOUTH);
        postBetState.add(flipButton, BorderLayout.SOUTH);
        postBetState.add(coinFlip, BorderLayout.CENTER);
        cleanupState.add(okayButton, BorderLayout.SOUTH);
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
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
    private JLabel getIcon(String location) 
    {
        try
        {
            icon = new ImageIcon(getClass().getResource(location));
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        if (icon != null) return new JLabel(icon);
        else return new JLabel("image not found");
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
    private void cleanupState(int result)
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        if (result == HEADS) cleanupState.add(coinHeads, BorderLayout.CENTER);
        else if (result == TAILS) cleanupState.add(coinTails, BorderLayout.CENTER);
        else cleanupState.add(new JLabel("result error"));
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
                cleanupState(controller.getCoinFlip());
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
