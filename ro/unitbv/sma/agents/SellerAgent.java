package ro.unitbv.sma.agents;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ro.unitbv.sma.interfaces.SaleGui;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;

public class SellerAgent extends Agent {
	
	private static final long serialVersionUID = 1L;
	private Hashtable<String, Integer> storeInventory;
    private SaleGui storeInterface;

    protected void setup() {
        System.out.println("Magazinul " + getAID().getName() + " a fost creat.");
        storeInventory = new Hashtable<String, Integer>();

        // Create and show the GUI
        storeInterface = new SaleGui(this);
        storeInterface.showGui();

        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("product-sale");
        sd.setName("Investment portfolio JADE");
        
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Add the behaviour serving queries from buyer agents
        addBehaviour(new OfferRequestsServer());

        // Add the behaviour serving purchase orders from buyer agents
        addBehaviour(new PurchaseOrdersServer());
    }

    // Put agent clean-up operations here
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Close the GUI
        storeInterface.dispose();
        // Printout a dismissal message
        System.out.println("Seller Agent " + getAID().getName() + " has finished.");
    }

    /**
     * This is invoked by the GUI when the user adds a new book for sale
     */
    public void updateInventory(final String product, final int price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                storeInventory.put(product, new Integer(price));
                System.out.println("New product added to " + getAID().getName() + " inventory: " + product + " ,price = " + price);
            }
        });
    }
    
    /**
     * This is invoked by the GUI when the user deletes a product
     */
    public void deleteInventory(final String product) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                storeInventory.remove(product);
                System.out.println("Product " + product + " has been deleted from inventory");
            }
        });
    }
    
        /**
     * Inner class OfferRequestsServer.
     * This is the behaviour used by product-seller agents to serve incoming requests
     * for offer from buyer agents.
     * If the requested product is in the local inventory the seller agent replies
     * with a PROPOSE message specifying the price. Otherwise a REFUSE message is
     * sent back.
     */
    private class OfferRequestsServer extends CyclicBehaviour {
        
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // CFP Message received. Process it
                String product = msg.getContent();
                ACLMessage answer = msg.createReply();

                Integer price = (Integer) storeInventory.get(product);
                if (price != null) {
                    // The requested product is available for sale. Reply with the price
                    answer.setPerformative(ACLMessage.PROPOSE);
                    answer.setContent(String.valueOf(price.intValue()));
                } else {
                    // The requested product is NOT available for sale.
                    answer.setPerformative(ACLMessage.REFUSE);
                    answer.setContent("Not Available");
                }
                myAgent.send(answer);
            } else {
                block();
            }
        }
        
    }  // End of inner class OfferRequestsServer

    
    /**
     * Inner class PurchaseOrdersServer.
     * This is the behaviour used by product-seller agents to serve incoming
     * offer acceptances (i.e. purchase orders) from buyer agents.
     * The seller agent removes the purchased product from its inventory
     * and replies with an INFORM message to notify the buyer that the
     * purchase has been successfully completed.
     */
    private class PurchaseOrdersServer extends CyclicBehaviour {
        
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // ACCEPT_PROPOSAL Message received. Process it
                String product = msg.getContent();
                ACLMessage answer = msg.createReply();

                Integer price = (Integer) storeInventory.remove(product);
                if (price != null) {
                    answer.setPerformative(ACLMessage.INFORM);
                    System.out.println("The product " + product + " was sold to agent " + msg.getSender().getName());
                } else {
                    // The requested book has been sold to another buyer in the meanwhile .
                    answer.setPerformative(ACLMessage.FAILURE);
                    answer.setContent("Not Available");
                }
                myAgent.send(answer);
            } else {
                block();
            }
        }
        
    }  // End of inner class OfferRequestsServer


	public Hashtable<String, Integer> getStoreInventory() {
		return storeInventory;
	}

	public void setStoreInventory(Hashtable<String, Integer> storeInventory) {
		this.storeInventory = storeInventory;
	}
    
    
    
}
