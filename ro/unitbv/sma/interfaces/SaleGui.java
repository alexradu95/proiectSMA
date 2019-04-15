package ro.unitbv.sma.interfaces;

import jade.core.AID;
import ro.unitbv.sma.agents.SellerAgent;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * 
 */
public class SaleGui extends JFrame {
    
    private SellerAgent myAgent;
    private JTextField productName, productPrice;
    

    public SaleGui(SellerAgent a) {
        super(a.getLocalName());
        myAgent = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 2));
        p.add(new JLabel("Nume produs: "));
        productName = new JTextField(15);
        p.add(productName);
        
        p.add(new JLabel("Pret: "));
        productPrice = new JTextField(15);
        p.add(productPrice);
        
        getContentPane().add(p, BorderLayout.CENTER);

        JButton addButton = configureAddButton();
        JButton deleteButton = configureDeleteButton();
        
        p.add(addButton);
        p.add(deleteButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgent.doDelete();
            }
        });

        setResizable(false);
    }

	private JButton configureAddButton() {
		JButton addButton = new JButton("Adauga in stoc");
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String title = productName.getText().trim();
                    String price = productPrice.getText().trim();
                    myAgent.updateInventory(title, Integer.parseInt(price));
                    productName.setText("");
                    productPrice.setText("");
                    JOptionPane.showMessageDialog(SaleGui.this, "Produs adaugat!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(SaleGui.this, "Valoare invalida " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
		return addButton;
	}
	
	private JButton configureDeleteButton() {
		JButton addButton = new JButton("Elimina stoc");
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String title = productName.getText().trim();
                    myAgent.deleteInventory(title);
                    productName.setText("");
                    productPrice.setText("");
                    JOptionPane.showMessageDialog(SaleGui.this, "Product deleted from inventory!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(SaleGui.this, "Invalid value " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
		return addButton;
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
