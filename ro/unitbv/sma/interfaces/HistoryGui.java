package ro.unitbv.sma.interfaces;

import jade.core.AID;
import ro.unitbv.sma.agents.BuyerAgent;
import ro.unitbv.sma.agents.HistoryReceiverAgent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 */
public class HistoryGui extends JFrame {
    
    private HistoryReceiverAgent myAgent;
    private JPanel p;

    public HistoryGui(HistoryReceiverAgent a) {
        super();
        myAgent = a;

        p = new JPanel();
        p.add(new JLabel("History of operations: "));

        // Make the agent terminate when the user closes
        // the GUI using the button on the upper right corner
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgent.doDelete();
            }
        });

        setResizable(false);
    }
    
    public void addHistoryEntry(String entry) {
    	p.add(new JLabel(entry));
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
    
}
