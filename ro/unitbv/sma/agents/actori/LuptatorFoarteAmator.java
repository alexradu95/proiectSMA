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
import ro.unitbv.sma.agents.Proiect;

public class LuptatorFoarteAmator extends Agent {

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
					ag.doDelete();
				}
				if (received.getContent().contains("Pumn")) {
					Proiect.textArea.append(ag.getLocalName() + " da cu pumnul!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Pumn!");
					send(send);
				}
				if (received.getContent().contains("Apara")) {
					Proiect.textArea.append(ag.getLocalName() + " da cu pumnul!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Pumn!");
					send(send);
				}
				if (received.getContent().contains("Picior")) {
					Proiect.textArea.append(ag.getLocalName() + " da cu pumnul!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Pumn!");
					send(send);
				}
				else {
					Proiect.textArea.append(ag.getLocalName() + " da cu pumnul!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Pumn!");
					send(send);
				}
			}
		}

	}

}
