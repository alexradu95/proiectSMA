package ro.unitbv.sma.agents;

import jade.core.*;
import jade.lang.acl.ACLMessage;
import jade.wrapper.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.TextArea;

public class Proiect extends Agent {
	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	public Agent prim = null;
	public PlatformController container = null;
	private JLabel jnr = null;
	public Proiect mast = this;
	public boolean apasat = false;
	private JButton jSend = null;
	private JButton jSendPunch = null;
	private JButton jSendKick = null;
	private JButton jSendDefend = null;
	private JComboBox jComboBox = null;
	private JTextArea jTextArea2 = null;
	public static TextArea textArea = null;

	public void setup() {
		prim = this;
		container = prim.getContainerController();
		System.out.println(prim.getLocalName());
		getJFrame();
		try {
			createFiveAgents();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(610, 274));
			jFrame.setContentPane(getJContentPane());
			jFrame.setVisible(true);
			jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					try {
						container.kill();
					} catch (ControllerException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jFrame;
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jnr = new JLabel();
			jnr.setBounds(new Rectangle(289, 13, 48, 16));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jnr, null);
			jContentPane.add(getJSend(), null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getJTextArea2(), null);
			jContentPane.add(getTextArea(), null);
			jContentPane.add(getPunchButton(), null);
			jContentPane.add(getKickButton(), null);
			jContentPane.add(getDefendButton(), null);
		}
		return jContentPane;
	}

	private JButton getPunchButton() {
		if (jSendPunch == null) {
			jSendPunch = new JButton();
			jSendPunch.setBounds(new Rectangle(20, 180, 100, 30));
			jSendPunch.setText("Punch!");
			jSendPunch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					sendMessageToAgents("Pumn");
				}
			});
		}
		return jSendPunch;
	}
	
	private JButton getKickButton() {
		if (jSendKick == null) {
			jSendPunch = new JButton();
			jSendPunch.setBounds(new Rectangle(20, 180, 100, 140));
			jSendPunch.setText("Punch!");
			jSendPunch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					sendMessageToAgents("Pumn");
				}
			});
		}
		return jSendPunch;
	}
	
	private JButton getDefendButton() {
		if (jSendDefend == null) {
			jSendPunch = new JButton();
			jSendPunch.setBounds(new Rectangle(20, 180, 100, 250));
			jSendPunch.setText("Punch!");
			jSendPunch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					sendMessageToAgents("Pumn");
				}
			});
		}
		return jSendPunch;
	}
	
	private void sendMessageToAgents(String message) {
		String item = jComboBox.getSelectedItem().toString();
		ACLMessage send = new ACLMessage(ACLMessage.INFORM);
		if (item.contains("All")) {
				send.addReceiver(new AID("Agent Box", false));
				send.addReceiver(new AID("Agent Box 2", false));
				send.addReceiver(new AID("Agent Karate", false));
				send.addReceiver(new AID("Agent Amator 1", false));
				send.addReceiver(new AID("Agent Amator 2", false));
		} else {
			AID id = new AID(item, false);
			send.addReceiver(id);
		}
		send.setContent(message);
		send(send);
	}

	private void createFiveAgents() throws StaleProxyException, ControllerException {
		jComboBox.removeAllItems();
		jComboBox.addItem("Agent Box");
		jComboBox.addItem("Agent Box 2");
		jComboBox.addItem("Agent Karate");
		jComboBox.addItem("Agent Amator 1");
		jComboBox.addItem("Agent Amator 2");
		jComboBox.addItem("All Agents");
		container = prim.getContainerController();
		container.createNewAgent("Agent Box", "ro.unitbv.sma.agents.actori.LuptatorBox", null).start();
		container.createNewAgent("Agent Box 2", "ro.unitbv.sma.agents.actori.LuptatorBox", null).start();
		container.createNewAgent("Agent Karate", "ro.unitbv.sma.agents.actori.LuptatorKarate", null).start();
		container.createNewAgent("Agent Amator 1", "ro.unitbv.sma.agents.actori.LuptatorFoarteAmator", null).start();
		container.createNewAgent("Agent Amator 2", "ro.unitbv.sma.agents.actori.LuptatorFoarteAmator", null).start();
	}

	private JButton getJSend() {
		if (jSend == null) {
			jSend = new JButton();
			jSend.setBounds(new Rectangle(72, 175, 132, 30));
			jSend.setText("Send!");
			jSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String item = jComboBox.getSelectedItem().toString();
					ACLMessage send = new ACLMessage(ACLMessage.INFORM);
					if (item.contains("All")) {
							send.addReceiver(new AID("Agent Box", false));
							send.addReceiver(new AID("Agent Box 2", false));
							send.addReceiver(new AID("Agent Karate", false));
							send.addReceiver(new AID("Agent Amator 1", false));
							send.addReceiver(new AID("Agent Amator 2", false));
					} else {
						AID id = new AID(item, false);
						send.addReceiver(id);
					}
					send.setContent(jTextArea2.getText());
					send(send);
				}
			});
		}
		return jSend;
	}

	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(32, 42, 123, 25));
		}
		return jComboBox;
	}

	private JTextArea getJTextArea2() {
		if (jTextArea2 == null) {
			jTextArea2 = new JTextArea();
			jTextArea2.setBounds(new Rectangle(18, 103, 241, 57));
		}
		return jTextArea2;
	}

	public static TextArea getTextArea() {
		if (textArea == null) {
			textArea = new TextArea();
			textArea.setBounds(new Rectangle(375, 4, 214, 227));
		}
		return textArea;
	}

}
