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

public class FightSimulator extends Agent {
	private static final String HISTORY_AGENT = "History Agent";
	private static final String AGENT_AMATOR_2 = "Agent Amator 2";
	private static final String AGENT_AMATOR_1 = "Agent Amator 1";
	private static final String AGENT_BOX = "Agent Box";
	private static final String AGENT_BOX_2 = "Agent Box 2";
	private static final String AGENT_KARATE = "Agent Karate";
	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	public Agent prim = null;
	public PlatformController container = null;
	public FightSimulator mast = this;
	private JButton jSend = null;
	private JButton jSendPunch = null;
	private JButton jSendKick = null;
	private JButton jSendDefend = null;
	private JComboBox jComboBox = null;
	private JTextArea jTextArea2 = null;
	public static TextArea textArea = null;

	@Override
	public void setup() {
		prim = this;
		container = prim.getContainerController();
		System.out.println(prim.getLocalName());
		getJFrame();
		try {
			createFiveAgents();
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}

	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(610, 224));
			jFrame.setContentPane(getJContentPane());
			jFrame.setVisible(true);
			jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
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
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
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
			jSendPunch.setBounds(new Rectangle(20, 130, 100, 30));
			jSendPunch.setText("Punch!");
			jSendPunch.addActionListener(action -> sendMessageToAgents(ActionsAndMessages.KICK_ACTION));
		}
		return jSendPunch;
	}

	private JButton getKickButton() {
		if (jSendKick == null) {
			jSendPunch = new JButton();
			jSendPunch.setBounds(new Rectangle(130, 130, 100, 30));
			jSendPunch.setText("Kick!");
			jSendPunch.addActionListener(action -> sendMessageToAgents(ActionsAndMessages.DEFEND_ACTION));
		}
		return jSendPunch;
	}

	private JButton getDefendButton() {
		if (jSendDefend == null) {
			jSendPunch = new JButton();
			jSendPunch.setBounds(new Rectangle(240, 130, 100, 30));
			jSendPunch.setText("Defend!");
			jSendPunch.addActionListener(action -> sendMessageToAgents(ActionsAndMessages.KICK_ACTION));
		}
		return jSendPunch;
	}

	private void sendMessageToAgents(String message) {
		String item = jComboBox.getSelectedItem().toString();
		ACLMessage send = new ACLMessage(ACLMessage.INFORM);
		send.addReceiver(new AID("History", false));
		if (item.contains("All")) {
			send.addReceiver(new AID(AGENT_BOX, false));
			send.addReceiver(new AID(AGENT_BOX_2, false));
			send.addReceiver(new AID(AGENT_KARATE, false));
			send.addReceiver(new AID(AGENT_AMATOR_1, false));
			send.addReceiver(new AID(AGENT_AMATOR_2, false));
		} else {
			AID id = new AID(item, false);
			send.addReceiver(id);
		}
		send.setContent(message);
		send(send);
	}

	private void createFiveAgents() throws StaleProxyException, ControllerException {
		jComboBox.removeAllItems();
		jComboBox.addItem(AGENT_BOX);
		jComboBox.addItem(AGENT_BOX_2);
		jComboBox.addItem(AGENT_KARATE);
		jComboBox.addItem(AGENT_AMATOR_1);
		jComboBox.addItem(AGENT_AMATOR_2);
		jComboBox.addItem("All Agents");
		container = prim.getContainerController();
		container.createNewAgent(AGENT_BOX, "ro.unitbv.sma.agents.actori.LuptatorBox", null).start();
		container.createNewAgent(AGENT_BOX_2, "ro.unitbv.sma.agents.actori.LuptatorBox", null).start();
		container.createNewAgent(AGENT_KARATE, "ro.unitbv.sma.agents.actori.LuptatorKarate", null).start();
		container.createNewAgent(AGENT_AMATOR_1, "ro.unitbv.sma.agents.actori.LuptatorFoarteAmator", null).start();
		container.createNewAgent(AGENT_AMATOR_2, "ro.unitbv.sma.agents.actori.LuptatorFoarteAmator", null).start();
		container.createNewAgent(HISTORY_AGENT, "ro.unitbv.sma.agents.HistoryReceiverAgent", null).start();
	}

	private JButton getJSend() {
		if (jSend == null) {
			jSend = new JButton();
			jSend.setBounds(new Rectangle(270, 55, 70, 55));
			jSend.setText("Send!");
			jSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String item = jComboBox.getSelectedItem().toString();
					ACLMessage send = new ACLMessage(ACLMessage.INFORM);
					send.addReceiver(new AID(HISTORY_AGENT, false));
					if (item.contains("All")) {
						send.addReceiver(new AID(AGENT_BOX, false));
						send.addReceiver(new AID(AGENT_BOX_2, false));
						send.addReceiver(new AID(AGENT_KARATE, false));
						send.addReceiver(new AID(AGENT_AMATOR_1, false));
						send.addReceiver(new AID(AGENT_AMATOR_2, false));
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
			jComboBox.setBounds(new Rectangle(20, 20, 120, 25));
		}
		return jComboBox;
	}

	private JTextArea getJTextArea2() {
		if (jTextArea2 == null) {
			jTextArea2 = new JTextArea();
			jTextArea2.setBounds(new Rectangle(18, 53, 241, 57));
		}
		return jTextArea2;
	}

	public static TextArea getTextArea() {
		if (textArea == null) {
			textArea = new TextArea();
			textArea.setBounds(new Rectangle(375, 4, 214, 160));
		}
		return textArea;
	}

}
