# Distributed-system-LCR-algorithm-simulation-using-JAVA

WELCOME!!

Description of LCR:

The network considered in this assignment is a ring of rings, defined as follows (see Figure
1 for an illustration while reading the definition). There is a main ring G of n processors
V = {u1, u2, . . . , un}. There is a non-empty subset V ′ ⊆ V of **interface processors**, such
that every v ∈ V ′ **is associated with a distinct ring subnetwork** Rv. We call the remaining
processors non-interface processors; these are the **non-interface processors** of G and the
processors of the ring subnetworks. Initially, every **non-interface processor u has a unique id
stored in a variable myIDu, while every interface processor v has myIDv := −1** to denote
that its id is to be determined through its subnetwork. The subnetworks are not connected
to the main ring via communication links, instead every Rv is implicitly connected to its
interface v via shared variables. In particular, we make the assumption that whenever an
elected processor w of Rv sets the value of a variable leaderIDw, then myIDv := leaderIDw,
that is, as soon as a processor w is elected in Rv, **the interface v knows the id of the elected
processor through the shared variable leaderIDw. It follows, that every interface processor
can eventually obtain an elected id from its subnetwork.**
In our setting, the processors do not know the number of processors in the main ring
or in any of the subnetworks in advance, but they do know the general structure of the
network and all but the interface processors are equipped with unique ids. The ids are
not necessarily consecutive and for simplicity you can assume that they are chosen from
{1, 2, . . . , αN}, where α ≥ 1 is a small constant and N is the total number of non-interface
processors (e.g., for α = 3, the N non-interface processors will be every time assigned unique
ids from {1, 2, . . . , 3N − 1, 3N}). Processors execute in synchronous rounds.

![image](https://user-images.githubusercontent.com/55009904/157695492-f33eb8b0-c9d2-4fbf-9af5-acf86a205930.png)

Comments for codes are fully deployed in the code. You can download and check! No more comments at here!

**How to run:**

**1|** Please run the **Main.java** file to start the simulation.The system would ask you to enter the size of the main ring. 

**2|** The auto generated main ring with interface node ID '-1' will show on the screen.

**3|** the system would ask the user to enter the size of each subring.

**4|** At the end of the program, the process, leader of main ring, messages being transferred, cycle(round) and status of each node would be displayed on the screen.

--------------------------------------------------------------------------------------

**PS1:** For testing convenience, the number of subring is set to constant three. Check the **Nodes.java** **line 111 and 112** how to change the number of sub rings to random!!

**PS2:** Files "LCR_asynchronized.java" and "LCR_asynchronized_for_sub_ring.java" do not have any difference in the core code. Just one for main ring and one for sub rings for different user interface.

**PS3:** Four classes are constructed in this simulator. The main functionalities of the four classes are described as follows:

The **“Nodes”** class is constructed to serve as processors in rings. An ArrayList storing several “Nodes” objects is initialized as a ring. 
The **“LCR_asynchronized” and “LCR_asynchronized_for_sub_ring”** are responsible for creating main ring and subring networks
The **“Main”** class containing the main function can provide a user interface to execute the leader election

Enjoy and have a nice day!!

Any questions, leave comments!

Best

JAcky Captain
