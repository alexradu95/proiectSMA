package ro.unitbv.sma.agents.actori;

import jade.core.*;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.lang.acl.MessageTemplate;
import ro.unitbv.sma.agents.ActionsAndMessages;
import ro.unitbv.sma.agents.FightSimulator;

public class LuptatorBox extends Agent {

	@Override
	public void setup() {
		try {
			System.out.println(this.getLocalName() + " has been created");
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
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " dodged the punch!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Dodge");
					send(send);
				}
				else if (received.getContent().contains(ActionsAndMessages.DEFEND_ACTION)) {
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " moved one foot closer!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Closer");
					send(send);
				}
				else if (received.getContent().contains(ActionsAndMessages.KICK_ACTION)) {
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " dodged and launched a fist!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Counterattack");
					send(send);
				} else {
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " it's confuzed!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Confuzed");
					send(send);
				}
			}
		}
	}

}
