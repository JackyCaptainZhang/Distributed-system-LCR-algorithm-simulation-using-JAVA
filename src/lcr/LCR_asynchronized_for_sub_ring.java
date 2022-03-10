
package lcr;
import java.util.ArrayList;

public class LCR_asynchronized_for_sub_ring {
//constructor
public LCR_asynchronized_for_sub_ring() {};


public Integer LCR(ArrayList<Nodes> ring) {
	System.out.println("------------------------------------");	
	System.out.println("Sending ids....");	
	System.out.println("------------------------------------");	
	Nodes.round  += 1;
	for(int i = 0; i< ring.size(); i++) {
			Nodes node = ring.get(i);
			if(node.getMyID() > node.getstoreID()) {
			node.send(node.getMyID(), node.neighbor(ring, node));
			System.out.println(" Node " + (i + 1) + " with id " + node.getMyID() + " receives id " + node.getstoreID());
		}
			if(node.getMyID() < node.getstoreID()) {  
				node.send(node.getstoreID(), node.neighbor(ring, node));
				System.out.println(" Node " + (i + 1) + " with id " + node.getMyID() + " receives id " + node.getstoreID());
			}
		}
		System.out.println(" etc..... ");
		int leaderid = 0;
		int node_num = 0;
		if(ring.get(0).getMyID() != ring.get(0).getstoreID()) {
			Nodes.round  += 1;
			for(int j = 0; j< ring.size(); j++) {
				Nodes node = ring.get(j);
				if(node.getMyID() > node.getstoreID()) {
					node.send(node.getMyID(), node.neighbor(ring, node));
				}
				if(node.getMyID() < node.getstoreID()) {
					node.send(node.getstoreID(), node.neighbor(ring, node));
					}
				if(node.getMyID() == node.getstoreID()) {
					node.setStatus("Leader!");	
					leaderid = node.getMyID();
					node_num = j+1;
					System.out.println("------------------------------------");
					System.out.println("Stage " + ": 'Finding the leader in the SUB ring' Finished");     
					System.out.println("------------------------------------");
					System.out.println("Leader is the node " + node_num + " with id " + leaderid + " !!");
					break;
				}
			}
		}
		else {
				Nodes node = ring.get(0);
					node.setStatus("Leader!");	
					leaderid = node.getMyID();
					node_num = 1;
					System.out.println("------------------------------------");
					System.out.println(" Leader of SUB ring" + " is node " + node_num + " with id " + leaderid + " !!");  
					System.out.println("------------------------------------");
		}
		Nodes.round  += 1;
		for(int k = 0; k< ring.size(); k++) {
			Nodes node = ring.get(k);
			node.sendleader(leaderid , node);
			//node.setLeaderid(leaderid);
			System.out.println("The node" + " with id " + node.getMyID() + " knows the leader is node " + node_num + " with id "  + node.getLeaderid());
		}
		Nodes.round  += 1;
		for (int i = 0; i<ring.size(); i++) {
			ring.get(i).setLeaderid(0);
			ring.get(i).setstoreID(0);
			ring.get(i).setTerminate(true);
			ring.get(i).setMyid(0);
			}
		System.out.println("All nodes in the SUB ring terminated! ");
		return leaderid;
	}
}
