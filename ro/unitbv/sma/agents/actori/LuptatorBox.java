package ro.unitbv.sma.agents.actori;

import jade.core.*;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.*;
import ro.unitbv.sma.agents.ActionsAndMessages;
import ro.unitbv.sma.agents.Proiect;

public class LuptatorBox extends Agent {

	public ACLMessage mesaj = null;
	public Agent ag = this;
	public String[] numeA = null;
	AMSAgentDescription[] agents = null;
	SearchConstraints c = new SearchConstraints();
	AMSAgentDescription descriere = new AMSAgentDescription();
	public boolean trimis = false;

	public void setup() {
		c.setMaxResults(new Long(-1));
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
					ag.doDelete();
				}
				else if (received.getContent().contains(ActionsAndMessages.PUNCH_ACTION)) {
					Proiect.textArea.append(ag.getLocalName() + " dodged the punch!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Dodge");
					send(send);
				}
				else if (received.getContent().contains(ActionsAndMessages.DEFEND_ACTION)) {
					Proiect.textArea.append(ag.getLocalName() + " moved one foot closer!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Closer");
					send(send);
				}
				else if (received.getContent().contains(ActionsAndMessages.KICK_ACTION)) {
					Proiect.textArea.append(ag.getLocalName() + " dodged and launched a fist!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Counterattack");
					send(send);
				} else {
					Proiect.textArea.append(ag.getLocalName() + " it's confuzed!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Confuzed");
					send(send);
				}
			}
		}
	}

}
