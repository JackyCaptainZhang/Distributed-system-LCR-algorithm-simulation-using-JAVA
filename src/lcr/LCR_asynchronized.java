package lcr;
import java.util.ArrayList;

public class LCR_asynchronized {
//constructor
	public LCR_asynchronized() {};
	public int stage = 1;
	public int leaderid;
	

public void LCR(ArrayList<Nodes> ring) {
	System.out.println("------------------------------------");
	System.out.println("Asynchronized MAIN ring LCR algorithm starting....");
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
	System.out.println(" etc....");
	System.out.println("------------------------------------");
	System.out.println("Stage " + stage + ": 'Sending the id message' Finished");      // Stage 1 only sending the id(loop 1)
	System.out.println("------------------------------------");
	stage++;
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
				System.out.println("Stage " + ": 'Finding the leader in the MAIN ring' Finished");          // Stage 2 sending the remaining id and find out the leader(loop 2)
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
	stage++;
	System.out.println("------------------------------------");
	System.out.println("Stage " + stage + ": Broadcasting the leader to all the nodes in the MAIN ring");  //stage 3 Broadcasting the leader to all the nodes(loop 3)
	System.out.println("------------------------------------");
	Nodes.round  += 1;
	for(int k = 0; k< ring.size(); k++) {
		Nodes node = ring.get(k);
		//node.setLeaderid(leaderid);
		node.sendleader(leaderid , node);
		System.out.println("The node " + (k+1) + " with id " + node.getMyID() + " knows the leader is node " + node_num + " with id "  + node.getLeaderid());
	}
	stage++;
	System.out.println("------------------------------------");
	System.out.println("Stage " + stage + ": Terminating all the nodes....");  //stage 4 Terminating all the nodes(loop 4)
	System.out.println("------------------------------------");
	Nodes.round  += 1;
	for (int i = 0; i<ring.size(); i++) {
		ring.get(i).setLeaderid(0);
		ring.get(i).setstoreID(0);
		ring.get(i).setTerminate(true);
		ring.get(i).setMyid(0);
		}
	System.out.println("All MAIN ring nodes terminated! ");
	System.out.println("------------------------------------");
	System.out.println("Program complete! The final leader in distributed system is node " + node_num + " with id " + leaderid);
}
}
