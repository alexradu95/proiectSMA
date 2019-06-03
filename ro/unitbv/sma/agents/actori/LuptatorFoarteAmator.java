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
import ro.unitbv.sma.agents.FightSimulator;

public class LuptatorFoarteAmator extends Agent {

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
				else {
					FightSimulator.textArea.append(this.getAgent().getLocalName() + " da cu pumnul!" + "\n");
					ACLMessage send = received.createReply();
					send.setContent("Pumn!");
					send(send);
				}
			}
		}

	}

}
