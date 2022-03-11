package lcr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Nodes {
	private int myID;
	private int storeID;
	private String status;
	private int leaderid;
	private boolean isTerminate;
	public static ArrayList<Integer> temp_number = new ArrayList<Integer>();     // function for the allocation of -1 for the interface node in the main ring
	public static int num_interface_nodes;                                      // record the random number of interface node in the main ring
	public static ArrayList<Integer> number = new ArrayList<Integer>();          // record the number that has not been allocated to the main/sub ring
	public static ArrayList<Integer> interface_node = new ArrayList<Integer>();  // record the node number of interface node in the main ring
	public static int message_send = 0;
	public static int round = 0;
	
	
	//constructor and default node parameter
	Nodes(int MyID){                
		myID = MyID;
		storeID = 0;
		leaderid = 0;
		status = "Unknown";
		isTerminate = false ;
}
	//getters and setters
	public int getstoreID() {
		return storeID;
	}
	public String getStatus() {
		return status;
	}
	public int getLeaderid() {
		return leaderid;
	}
	public int getMyID() {
		return myID;
	}
	public void setMyid(int i) {
	this.myID = i;
	}
	public void setStatus(String s) {
		status = s;
	}
	public void setstoreID(int i) {
		storeID = i;
	}
	public void setLeaderid(int Leader) {
		this.leaderid = Leader;
	}
	public boolean isTerminate() {
		return isTerminate;
	}
	public void setTerminate(boolean isTerminate) {
		this.isTerminate = isTerminate;
	}
	
	//Find the next node
	public Nodes neighbor(ArrayList <Nodes> n , Nodes node) {
		int currentindex = 0;
		Nodes return_node;
		for(int i = 0; i < n.size(); i++) {
			if(n.get(i) == node) {
				currentindex = i;
			}
		}
		if (currentindex < n.size()-1) {
			return_node = n.get(currentindex+1);
	}
		else {
			return_node = n.get(0);
			}
		return return_node;	
	}
	//send id
	public void send(int sendID, Nodes node){  
		message_send += 1;
		node.setstoreID(sendID);
		//System.out.println(message_send);
	}
	//send leader information
	public void sendleader(int leaderid , Nodes node) {  	
	message_send += 1;
	node.setLeaderid(leaderid);
	//System.out.println(message_send);
}

public static ArrayList<Nodes> random_ring(int i){
	//ArrayList<Integer> number = new ArrayList<Integer>(); 
	for(int j = 1; j< 10*i ; j++) {
		number.add(j);               // generate the list containing the number from 1 to 10*i
	}
	for(int k = 0; k<=2; k++) {
		Collections.shuffle(number); // shuffle the list twice to get a random ordered list
	}
	ArrayList<Nodes> ring = new ArrayList<Nodes>();  // allocate the number to the main ring and cut off the allocated number to ensure unique id for every node 
	for(int l = 0; l<i; l++) {
		ring.add(new Nodes(number.get(1)));
		temp_number.add(number.get(1));
		number.remove(1);
	}
	return ring;
}

public static ArrayList<Nodes> main_ring_contain_non_interface_nodes(ArrayList <Nodes> ring){
	System.out.println("------------------------------------");
	System.out.println("Generating the random MAIN ring!! ");
	System.out.println("------------------------------------");
	//num_interface_nodes = (int)(Math.random()*(ring.size()-2)) + 1; // you can change the number of sub rings to random by this line and turning off the next line
	num_interface_nodes = 3; // for testing convenience I set the number of sub-ring to the constant 3.
	for(int i = 0; i < num_interface_nodes; i++) {
		temp_number.remove(i);          // remove the initial id of the interface node in the list(did not contain -1)
		temp_number.add(i, -1);         // set the interface node id to -1 in the list[modified list(containing -1)]      
	}
	//Collections.shuffle(temp_number);
	ArrayList<Nodes> main_ring = new ArrayList<Nodes>();
	
	for(int l = 0; l<temp_number.size(); l++) {
		main_ring.add(new Nodes(temp_number.get(l)));  //allocate the modified list(containing -1) to the node in the main ring
		if(temp_number.get(l) == -1) {
			interface_node.add(l);                                  // record the node number of interface node in the main ring (node n has index n-1 in the list!)
			main_ring.get(l).setStatus("Interface node");           //set the status of node to interface or non-interface according to the id equals -1 or not
		}
		else {
			main_ring.get(l).setStatus("None-Interface node");
		}
		System.out.println(" Nodes "+(l+1) +" 's ID is: " + temp_number.get(l) + "( " + main_ring.get(l).getStatus() + " )");
	}
	return main_ring;
}

public static ArrayList<Nodes> sub_networks(ArrayList <Nodes> main_ring){
	int x = 0;
	System.out.println("------------------------------------");
	System.out.println("Prepare to generate the SUB ring(s) !!!");
	System.out.println("------------------------------------");
	for(int h = 1; h <= num_interface_nodes; h++) {
		System.out.println("(" + h + " of " + num_interface_nodes + ") SUB rings");
		Scanner sc = new Scanner(System.in);       //user input for the number of nodes in each sub ring!
		int i = sc.nextInt();
		//int i = 5;
        ArrayList <Nodes> sub_ring = new ArrayList <Nodes>();
        for(int l = 0; l<i; l++) {             //allocate the number from the list to the sub ring and cut off the allocated number to ensure unique id for every node
    		sub_ring.add(new Nodes(number.get(1)));
    		number.remove(1);
    	}
        LCR_asynchronized_for_sub_ring LCR_sub_ring = new LCR_asynchronized_for_sub_ring();  //the LCR for the sub ring(s)
        int leaderid = LCR_sub_ring.LCR(sub_ring);  //get the leader id in the sub ring(s)
        main_ring.get(interface_node.get(x)).setMyid(leaderid); // send the sub ring's leader id to the interface node whose initial id was -1
        x++;
	}
	return main_ring;  // return the processed main ring with all the id valid for LCR election!
}


}
