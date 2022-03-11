package lcr;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {
	public static void main(String[] args) {
		System.out.println("Please type in the integer number of the nodes you want to use in the auto-generated MAIN ring: ");
		Scanner sc=new Scanner(System.in);
		int i=sc.nextInt();
		//int i= 5;
        ArrayList <Nodes> temp_main_ring = Nodes.random_ring(i);
        ArrayList <Nodes> main_ring_before_sub_network = Nodes.main_ring_contain_non_interface_nodes(temp_main_ring);
        ArrayList <Nodes> main_ring_after_sub_network = Nodes.sub_networks(main_ring_before_sub_network);
        System.out.println("------------------------------------");
    	System.out.println("Processed MAIN ring is: ");  
        for(int m = 0; m < main_ring_after_sub_network.size(); m++) {
        	System.out.println("Node " + (m + 1) + " with id " + main_ring_after_sub_network.get(m).getMyID());
        }
        System.out.println("Press ENTER to see the final result! ");
        Scanner enter = new Scanner(System.in);
        enter.nextLine();
        LCR_asynchronized LCR = new LCR_asynchronized();
        LCR.LCR(main_ring_after_sub_network);
        System.out.println("------------------------------------");
    	System.out.println("Program had a cycle of " + Nodes.round + " and has messages transferred " + (Nodes.message_send + Nodes.num_interface_nodes) + " times ");
    	System.out.println("Glad to see you and have a nice day !! :)) ");
    }
}
