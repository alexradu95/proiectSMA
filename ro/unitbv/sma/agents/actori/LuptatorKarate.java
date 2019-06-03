package ro.unitbv.sma.agents.actori;

import jade.core.*;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.MessageTemplate;
import ro.unitbv.sma.agents.ActionsAndMessages;
import ro.unitbv.sma.agents.FightSimulator;

public class LuptatorKarate extends Agent {
	
	@Override
	public void setup() {
		try {
			System.out.println(this.getLocalName() + " a fost creat");
		} catch (Exception any) {
			any.printStackTrace();
		}
		Behaviour b = new ResponseBehaviour(this);
		addBehaviour(b);
	}

	class ResponseBehaviour extends CyclicBehaviour {

		public ResponseBehaviour(Agent a) {
			super(a);
		}

		public void action() {

			ACLMessage received = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

			if (received != null) {
				if (received.getContent().contains("kill")) {
					this.getAgent().doDelete();
				}
				else if (received.getContent().contains(ActionsAndMessages.PUNCH_ACTION)) {
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " l-a blocat cu mana!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Blocat!");
					send(send);
				}
				else if (received.getContent().contains(ActionsAndMessages.DEFEND_ACTION)) {
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " se apropie spre tine!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Apropie!");
					send(send);
				}
				else if (received.getContent().contains(ActionsAndMessages.KICK_ACTION)) {
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " sare in spate!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Sare!");
					send(send);
				}
				else {
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " mediteaza!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Mediteaza!");
					send(send);
				}
			}
		}

	}

}
