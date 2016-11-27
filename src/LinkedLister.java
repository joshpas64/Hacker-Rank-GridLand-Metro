
public class LinkedLister {
	/*
	
	************************************************************************************************************************
	Hello this is the main data structure I created to solve the metro grid problem from HackerRank. 
	
	The premise of the problem is this:
	You have a grid of m x n spaces that you as a mayor want to install lamposts on. There are no limits on how many lamposts
	you can place or restrictions on the coordinates or layout of the lamposts either, basically saying you can place up to m*n 
	lamposts in the grid of land.
	
	The one catch is this:
	The area is also used to place railroad tracks and in a column or grid space where there is a railroad track, or part of a 
	railraod track present, a lampost CANNOT be placed there. The tracks are only horizontal, meaning it can span multiple columns
	but not more than 1 row. They can occupy 1 to n grid spaces. Also different railroad tracks can share the same row and intersect
	or share multiple columns at once.
	
	The input to initilize is as follows:
	First line of input has three integers: 
	The first is the number of rows in the grid
	Second is the number of columns in the grid
	Third is the number of railroad tracks in the grid, and since intersection is included, it can range from 0 to infinity
	Each following line of input will determine key data about each corresponding railroad track
	Basically if you put x as your number of tracks, you will have x input lines to set data for each track
	
	On each line, there will again be three input parameters, that will all be integers
	First is the track's row number
	Second is the track's starting column which can be a minimum of 1 and go up to the n (the grid's col width)
	Third is the track's ending column
	Also note starting and ending column can be the same meaning the track only occupies one column, or grid space in total
	From there the answer is just result =  (m * n) - total grid space occcupation of all tracks(discounting any intersection)
	**************************************************************************************************************************
	
	**************************************************************************************************************************
	The one approach I am doing in this implementation is to make a list or data structure for each row that contains arrays or fields
	of length two that contain the start and ending column of each track.
	The list would have to be dynamically allocated since different rows may have different number of tracks including no tracks at all.
	Also since the tracks are all objects that have methods and attributes each track in the array would have to be initialized some 
	way or another.
	
	So to make things easy, the row containing the track data should be a linked list that is sorted by starting and ending column values
	Every time a node is added or removed the linked must be sorted. Since where it could go and what could be removed can be in any
	range I chose to implement a doubly linked list. With a doubly linked list there are the customary remove, iterate, add methods as well
	as head and tail nodes as attributes and a length attribute for some specific cases.
	
	Also note that not only must the linked be sorted but it must filter out duplicates or intersections as well. Luckily with good initialization
	and add and remove functions, each potential node can be checked for intersections through just iterating through the linked list
	and checking. If the potential node starting and ending columns span columns not previously modified it will go to the proper node to add itself in
	and remove any nodes after it that are just intersections until it reaches a point where the intersections stop. 
	
	It may be weird to visualize but imagine you start out with an empty track row of width 20. You add a node with a start col of 3 and end col of 5.
	Since its empty, the check function returns quickly and the node is added. You then add a node of [17,19]. No intersections and comparing start and end col
	you put this node at the end after [3,5]. Next is a node of [4,7]. There is an intersection but new columns are spanned, so instead you make a new
	node of [3,7] and add it to the beginning, removing the [3,5] after it. Then an [11,14] node. No intersections and since sorted, through iterating, it is
	clear to put it after the [3,7] (the [11,14] prev pointer will point to the [3,7] node and its next will point to [17,19] node. 
	Then a node of [2,2] is proposed to be put in. Since being one away is also a way of intersecting, instead a new node with vals
	[2,7] is put in before the [3,7] node, removing that node after. Another node is proposed [4,6] but since its within another track,
	it is not added. Now a track of [5,16] is added because its ending column intersect with the [17,19] node abd its start column is within [2,7]
	AND it spans new columns, a new Node of [2,19] is created and put in the beginning removing the [2,7],[11,14],[17,19] before it. 
	You can see how checking for intersections is pretty easy since the range is very high (actually really close to the col width) and theres
	just one node to check.
	
	It may sound weird and complicated and way too abstract so you can scroll down to the code below to get a clearer picture. I might also post
	some form of psuedocode of the approach I made to updating this linked list.
	*/
	private int length; //Simple length counter to keep track of the list, its purpose will be seen in other classes that have this
	//version of a linked list as a variable to use
	private Node head; //Points to the first node with lowest start column in linked list, prev pointer points to null
	private Node tail; //Last node, next pointer always points to null used for iteration
	private class Node // Node's values are start and end col and its pointers
	{
		private int[] data = new int[2]; //Looking back, especially since I separated the start and end columns with two distinct get
		// and set methods just having two int variables is probably better since you dont have to worry about heap allocation and what not
		//Also Node is only needed in linked list class so class itself should be private
		/*
		This could work as well
		private int startCol;
		private int endCol;
		*/
		private Node next;
		private Node prev;
		public Node()
		{
			this.next = this.prev = null;//Empty nodes just have nullptr's and no data
		}
		public Node(int[] vals)//Sets data for a node, setting prev and next pointers is done by the linked list class
		{
			for(int i = 0; i < 2; i++)
			{
				this.data[i] = vals[i];
			}
			this.prev = null;
			this.next = null;
		}
		public Node(int start, int end)//Same premise just a different format
		{
			this.next = this.prev = null;
			this.data[0] = start;
			this.data[1] = end;
		}
		public Node getPrev()
		{
			return this.prev;
		}
		public Node getNext()
		{
			return this.next;
		}
		public int getStartCol()
		{
			return this.data[0];
		}
		public int getEndCol()
		{
			return this.data[1];
		}
		public void setPrev(Node node)
		{
			this.prev = node;
		}
		public void setNext(Node node)
		{
			this.next = node;
		}
		//Above are just normal getters and setters associated with most classes
		public String toString()
		{
			String result = "";
			result = "This node has data of\n";
			result = result + String.format("A starting column of: %d\n", data[0]);
			result = result + String.format("An ending column of: %d\n", data[1]);
			return result;
		}
		//This really isnt nexessary it is just something I implemented just to do test cases, since there are many scenarios
		// and boundary cases you have to check for when implementing the sorted add and remove methods.
	};
	public LinkedLister()
	{
		length = 0;
		this.head = this.tail = null;
		//Empty linked list, fairly simple set pointers to null and length to 0
	}
	public int getLength()
	{
		return length;//Return length of linked list, mainly used outside of this scope by other classes or in main 
	}
	public void add(int[] vals,int flag)
	{
		Node node = new Node(vals);//New node
		add(node,flag);//Quick add to linked list, without checking for intersections, flag means put it either at the beginning or end
	}
	public void add(int start, int end, int flag)
	{
		Node node = new Node(start,end); //Over loaded version of previous add method
		add(node,flag);
	}
	public void iterate()//Basically just a formatted output method, useful for testing but not needed in the main program
	{
		Node currNode = this.head;
		int index = 1;
		while(currNode != null)
		{
			System.out.println(String.format("Contents of Node: %d", index));
			System.out.println(currNode);
			System.out.println(String.format("This node point to the contents of the next Node or, Node: %d"
					, index + 1));
			currNode = currNode.getNext();
			index++;
		}
		System.out.println("This is the end of the linked list, the next pointer to a null, denotes its termination");
		System.out.println(String.format("In total there %d Nodes.",length));
	}
	public void remove(int index) //Remove a node at a specific index, useful when trying to add that may overlap with some tracks 
		// but still span a new column
 	{
		if(index >= length || index < 0)
			return;
		Node rNode = nodeAt(index);
		remove(rNode);
	}
	public int checkStart(int start, int end) //The BIG FUNCTION 
	//This function takes the proposed new Node's starting and ending column and check if it spans any new columns in the row and see what
	// columns it may combine or intersect with, with other tracks
	{
		if(this.head == null)//If the list is empty just make it the head of the linked list
		{
			Node newNode = new Node(start,end);
			add(newNode,1);//value of flag == 1 means add this node as the new head of the linked list
			return 0;
		}
		Node currNode = this.head; //Start at the head or beginning
		Node prevNode = currNode; //Going to be used as a pointer to the previous node that was iterated
		int index = 0; //Used to keep track of where you are in the list
		int check = -1; //Default is -1 or NOT A VALID NEW NODE
		while(currNode != null)
		{
			//Consider this like a switch statement is inside of a while loop just the boolean expressions are much more long
			//Once a one of the conditions in the body is met, the node is then processed and added or rejected
			if(currNode.getStartCol() <= start && currNode.getEndCol() >= end)
			// Translates to if the start column of the proposed is >= to the current Node's start column AND its end column
			// is <= to the current Node's ending column. It is basically columns within an already established or a copy of it 
			// and there is no need to add it.
			/*
			Example
			current Node = [3,9] proposed node = [4,7] this node is within the current Node and is therefor NOT unique and not worth adding
			so stop iteration to save time 
			*/
			{
				return -1;
			}
			else if(currNode.getStartCol() > start && prevNode.getEndCol() <= start)
			// Basically the start is less than the current Node's startCol AND is greater than or equal to the previous node's end column
			/*
			Example 
			prevNode = [3,4] currNode = [8,12] proposed = [6,7], Ending is adjacent to currNode's startCol and can be combined to node [6,12]
			OR
			prevNode = [3,4] currNode = [8,12] proposed = [5,9] : adjacent to prevNode and ending column is within currNode so it can be combined to [3,12]
			*/
			{
				check = index;
				filter(index,start,end,index); //Mark the index and process the new node seeing if anything needs to be combined with redundant
				return check; 			//interscting nodes being removed in the process
			}
			else if(currNode.getStartCol() > start) //Basically if the starting column is LESS THAN AND NOT EQUAL to the current node's start
			// If it was a node that was just within a previous node i.e. prevNode = [4,9] currNode = [13,16] proposed = [7,8] it would have hit the
			// first if statement when it was checking the previous node and returned, stopping iteration, so getting to the currentNode and past
			// first if statement means if it has gotten to this point with a startCol less than currNode's startCol it spans at least 1 new column
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if(currNode.getStartCol() <= start && prevNode.getEndCol() >= start && currNode != this.head)
			// Case where the starting column is <= to the prevNode's end and >= to currNode start
			//Used to combine node's with columns that overlap
			// Like prevNode = [2,4] currNode = [7,9] proposed [4,7] can combine into [2,9]
			// Also so note the one issue that may rise is on the firstNode since prevNode == currNode so make an exception for that case
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if(currNode.getStartCol() >= end && prevNode.getEndCol() <= start)
			// ending column is <= currNode start but the starting column is >= prevNode's end
			// currNode = [12,16] prevNode = [6,9] proposed = [10,11] can combine all 3 to [6,12]
			// OR currNode = [15,17] prevNode = [1,5] proposed = [8,11] where the proposed will be a new unique track in the linked list
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if(currNode.getStartCol() == start && currNode.getEndCol() < end)
			// case of start == currNode's start AND end > currNode's end
			// currNode = [5,9] proposed = [5,11] combine to [5,11]
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if((currNode.getNext() == null && (end > currNode.getEndCol())))
			//A way to possibly get to this step is to see if currNode == this.tail since objects are always pass-by-reference by default
			// Essentially means the proposed node spans across the maximum end col currently set in the linked list and this proposed
			// node should be set as the new tail of the linked list
			{
				check = index;
				filter(index,start,end,-1);
				return check;
			}
			prevNode = currNode;
			currNode = currNode.getNext();
			index++;
		}
		return check;
	}
	public int getSpaceOccupation() //Key method in solving the Grid problem 
	{
		int occupations = 0; //default of 0 occupations since all track rows start empty
		if(length == 0)
			return occupations; //Skip unnecessary if row is empty
		Node currNode = this.head; //Start at first node
		int trackOccupation; //Column span for each UNIQUE TRACK in row
		while(currNode != null)//In the outside classes like Track and TrackFunction tracks are added only by calling the public
			// checkStart method which will ensure the linked list is sorted and discounts intersection occurences
		{
			trackOccupation = currNode.getEndCol() - currNode.getStartCol();
			//For each node the column span (end - start) represents how many grid spaces it occupies
			// Also since 4,4 means an occupation of just 1 space, 1 must be added to the resulting difference
			trackOccupation++;
			occupations += trackOccupation; //Add column span for one track into the total for all tracks in row
			currNode = currNode.getNext();//Move to next node
		}
		return occupations; //Return total space occupation by train tracks in this row
	}
	public String toString()
	{
		//Not necessary for main program its just to make the description of what is going during each test and how linked lists are laid
		// out and how the linked list implementation can be used to find the amount of space available for lamps
		//More for newbie or beginner programmers to get a clearer more visual picture of what is going on
		String starter = "";
		starter += String.format("This linked list has a length of: %d\n\n", length);
		Node currNode = this.head;
		for(int i = 1; i < length + 1; i++)
		{
			if(i == 1)
				starter += "This is the first node, or the Head Node, of a linked list denoting its start\n";
			starter += String.format("This is Node %d of the linked list\n\n", i);
			starter += String.format("The node's contents are a Starting Column of: %d\nand an Ending Column of: %d\n\n", currNode.getStartCol(),currNode.getEndCol());
			if(i > 1)
				starter += String.format("The node's previous pointer will point Node: %d\nIts next pointer will point to Node: %d\n\n", i - 1, i + 1);
			else
				starter += "The head node's previous pointer usually points to null like the tail's next pointer\n\n";
			currNode = currNode.getNext();
		}
		starter += String.format("\nThat last node, Node: %d, was the Tail pointer\nIts next attribute Node: %d is just a null object denoting the end or termination\n"
				+ "It is like in C++ strings which terminate with a '\\0' character or ASCII 0\n\n", length, length + 1);
		starter += String.format("For this HackerRank problem we want to get the number of spaces the tracks in a row inhabit.\nWe also want to discount for intersections too\n" + 
				"The number for now is: %d spaces in the row\n",getSpaceOccupation());
		return starter;
	}
	private void addFromEmpty(Node node)
	{
		//Add a node to an empty linked list
		//Length = 1 and since there is only one Node it will act as head and tail
		this.head = node;
		this.head.setPrev(null);
		this.tail = node;
		this.tail.setNext(null);
		this.head.setNext(null);
		this.tail.setPrev(null);
		length++;
	}
	private void separate(Node node)
	{
		//Going from 1 Node to 2 nodes 
		//new node is tail
		// Pointers are set so that they point to each other respectively
		this.head.setNext(node);
		this.tail = node;
		this.tail.setPrev(this.head); //this.tail and this.head are now different objects but have pointers or references 
		this.tail.setNext(null); // to each other in their next or prev fields as well as the mark as being end or start of the 
		length++; //linked list
		// New node means length increased  by 1
	}
	private void resetTail(Node node)
	{	//Make this.tail equal to the node in the parameter
		if(this.tail == null && this.head == null) //Case where there is an empty list
		{
			addFromEmpty(node);
		}
		else if(this.head.getNext() == null && this.tail.getPrev() == null) //Case of only one Node
		{
			separate(node);
		}
		else
		{
			Node movedNode = this.tail; //Move tail into a temp Node
			movedNode.setPrev(this.tail.getPrev()); //Have its previous node match the tail's(Meaning keep them the same)
			movedNode.setNext(node); //Since the tail is now being replaced next will point to new tail instead of null
			this.tail = node; //Make node the new tail
			this.tail.setPrev(movedNode); //Have it point to the previous node, and thereby the rest of the linked list
			this.tail.setNext(null); //Set its status as the tail null next
			length++;
		}
	}
	private void putHead(Node node) //Replace or set the Head of the linked list
	{
		if(this.tail == null && this.head == null)//Case of an empty linked list
		{
			addFromEmpty(node);
		}
		else if(this.tail.getPrev() == null &&  this.head.getNext() == null)//Case of a linked list with only 1 node
		{
			separate(node);
		}
		else
		{
			//Same procedure as resetTail just with setPrev and setNext values swapped
			Node tempNode = this.head; 
			tempNode.setPrev(node);
			tempNode.setNext(this.head.getNext());
			this.head = node;
			this.head.setPrev(null);
			this.head.setNext(tempNode);
			length++;
		}
	}
	private Node nodeAt(int index)
	{
		if(index >= length || index < 0)
			return null; // Invalid Input
		int ticker = 0;
		if(index == ticker) //Index = 0
		{
			return this.head;
		}
		Node node = this.head; //Start at the head
		while(ticker < index)
		{
			node = node.getNext(); //Continue until you reach the index and return back the node
			ticker++;
		}
		return node;
	}
	private void add(Node node, int flag)
	{
		if(this.head == null && this.tail == null) //Empty list case
		{
			addFromEmpty(node);
			return;
		}
		else if(flag == 0) //Add to the tail of the linked list
		{
				resetTail(node);
		}
		else if(flag != 0) // Add as the new head of the linked list
		{
				putHead(node);
		}
	}
	private void addBefore(int index, Node node)//When a node that was proposed is seen as Valid to add to the list
		// Use the index to find the next node its supposed to point to and add it into the linked list properly
		// setting its prev and next pointers appropriately
	{
		if(index >= length && index > 0) //In my implementation of the filter function the redundant(if any) nodes are removed
		// before the new combined node is added, so in cases where linked list may lose a lot of nodes all the up to the tail 
		// then the new Node should be the JUST the new tail if its index is length or higher
		{
			add(node,0);
			return;
		}
		if(index == 0) //When first this mean the head is to be replaced
		{
			add(node,1);
			return;
		}
		Node currNode = nodeAt(index); //Retrieve new Node's next Node to point to
		Node prevNode = currNode.getPrev(); // Retrieve what will be newNode's previous Node to point to
		prevNode.setNext(node); //Make the previous node point next to the new Node
		node.setPrev(prevNode); //Make new node refer to the previous
		currNode.setPrev(node); //Make the new node the currNode's prev neightbor
		node.setNext(currNode);//Make the new node the previous Node before the currNode
		length++;
	}
	private void popTail()//Remove and reet tail
	{
		if(this.tail.getPrev() == null) //If only one node value
		{
			this.tail = null;
			this.head = null; //Linked List is now empty
		}
		else if(this.tail == null && this.head == null)
			return; //Empty linked list, nothing left to remove
		else
		{
			Node node = this.tail.getPrev(); //Get tail's prev node
			Node prevNode2 = node.getPrev();//Get that node's prev node which points to the rest of the linked list
			if(prevNode2 == null)//Case of only two Nodes going to just 1 node
			{
				this.tail = node;
				this.tail.setPrev(null);//Mark as both beginning and end and head and tail. a
				this.tail.setNext(null);//Also have other pointers point to null because there is 
				this.head = node; // Other data beside the one Node and the linked list attr.
				this.head.setPrev(null);
				this.head.setNext(null);
			}
			else
			{
				this.tail = node; //tail is now equal's to previous pointed neighbor
				this.tail.setPrev(prevNode2); //tail's previous neighbor now changed to 2nd to last node's prev
				this.tail.setNext(null); //Set next as null to denote its status as tail
				prevNode2.setNext(this.tail); //3rd to last node that is now second to last node that tail will point to in prev
			}
		}
		length--;
	}
	private void popHead() //Remove the head
	{
		Node tempNode = this.head.getNext(); //Head will eventually be the Node the current Head's next points to
		if(tempNode == null) //Only one node
		{
			this.head = null; //Now an empty linked list
			this.tail = null;
			length--;
			return;
		}
		Node next = tempNode.getNext();//get the 3rd Node
		this.head.setNext(next);//Make it heads next node or the now second node
		this.head.setPrev(null);//Mark it as a head node
		this.head = tempNode; //Officially change head's node
		if(next == null) //THere were only 2 nodes in the linked, now there will be only 1
		{
			this.tail = this.head; //head and tail will be the same node
			this.tail.setPrev(null); //Since there is only one node both pointers will be set to null
			this.tail.setNext(null);
		}
		else
		{
			this.head.getNext().setPrev(this.head); //have the newly set second value (which has all the pointer rferences
			// to the rest of the list, point back to the head so a user can successfully iterate through this now modifief
			// linked list
		}
		length--;
	}
	private void remove(Node node)
	{
		if(this.head == null)//Empty list do nothing
			return;
		else if(node == this.head) //The node is the head Node
		{
			popHead();
		}
		else if(node.getNext() == null)//The node is the tail node
		{
			popTail();
		}
		else
		{
			Node pNode = node.getPrev();//Get the node's previous Node through get method
			Node nextNode = node.getNext(); //Get its next Node
			nextNode.setPrev(pNode);//Have that nextNode skip over the node to be removed by pointing in prev to pNode
			pNode.setNext(nextNode);//Have pNode skip node to be removed and point instead to the node right after it
			length--;
		}
	}
	
	private void filter(int index, int start,int end, int tailFlag) //Second part of the BIG FUNCTION
	//checkStart was checking which test case a proposed node may fall if 
	{
		if(tailFlag == -1)//When a column goes past the highest column in the linked list
		{
			Node newNode;
			if((start - this.tail.getEndCol()) >= 2) //if start is 2 or more higher than tail's endvol
			{
				newNode = new Node(start,end);//make a new node but keep the previous node
			}
			else
			{
				newNode = new Node(this.tail.getStartCol(), end); //If they overlap or intersect make a new larger combined node
				remove(this.tail); //and remove the old tail
			}
			add(newNode,0);//make newNode the new tail of the linked list
			return; //Since this happened at the end of the list and the newNode is set the function has done its requirements so it can end
		}
		int retrieve = index; 
		if(retrieve < 0)
			retrieve = 0;
		Node starterNode = nodeAt(retrieve);//Get the node to which the new node will be inserted before
		Node prevNode;
		if(retrieve == 0)
			prevNode = starterNode;
		else
			prevNode = starterNode.getPrev(); //If not the head, get a reference to the previous node of staterNode
		int base = prevNode.getEndCol();
		if((start - base)  == 1 || (start - base) == 0)
		//if in for some circumsstance the proposed's start column is equal or 1 greater than the prevNode's end
		//then they intersect and the iteration to check and eliminate redundancies or intersection should start at prevNode
		// and not the current startNode
		{
			base = prevNode.getStartCol(); //Since they intersect the newNode's range starts at prevNode's start
			starterNode = prevNode;
			if(retrieve > 0)
				retrieve--;//Useful for later on, basically since we moved backed one extra node we insert 1 index back to
						//ensure the linked list is sorted
		}
		//This if else basically picks the most inclusive range for the new node it can by trying to give it lowest startCol it can
		else
		{
			base = start; // In all other cases the default is the start listed in the parameter
		}
		Node node;
		while(starterNode != null && starterNode.getEndCol() <= end)
		//Obviously iterate to the end AND while the current node's end is <= to the proposed end
		// So start and the initial node mark where to see how long the intersection goes and towards how many nodes
		// Once a node's endcol is > end then its clear that the next node will have no intersections and the construction of the new node
		// to add to the list can begin
		{
			prevNode = starterNode;
			starterNode = starterNode.getNext();
			remove(prevNode); //Remove all intersecting nodes until the loop terminates
		}
		//BIG NOTE: once the loop terminates it returns the node that failed the while condition meaning its endCol is > end but 
		// the same might not be said for its startCol
		if(starterNode == null) //If the tail's endCol was LESS THAN AND NOT EQUAL TO the end
		{
			node = new Node(base,end); //COnstructing a new node and set it as the new this.tail
			add(node,0);
		}
		else if(end < starterNode.getStartCol())
		// the end was less than the last node's startCol()
		// this means none of starterNode's column's will be included the in the new Node object to be inserted before it and
		// and does not need to removed
		{
			if(starterNode.getStartCol() - end == 1) //One exception is if it is only higher by 1, meaning they in reality do intersect
			{
				node = new Node(base,starterNode.getEndCol()); //Make a new Node of max width or most inclusive
				remove(starterNode); //remove now redundant starterNode
			}
			else
				node = new Node(base,end);//Since end was less by a signficant margin, then its range will stop at 'end' since it 
							//does not intersect with starterNode, meaning it must be kept in the list
			addBefore(retrieve, node);//Put node in linked list
		}
		else if(end >= starterNode.getStartCol())
		//When end was less than the starterNode endCol (hence failing and exiting while loop) but is greater than its startCol
		// Meaning new Node intersects with starterNode as well and its columns can be included in the newNode range
		{
			int endCol = starterNode.getEndCol();
			remove(starterNode); //Remove node since it will be replaced by wider node
			node = new Node(base, endCol); //Go from base to the end of starterNode and add it to list
			addBefore(retrieve, node);
		}
	}
}
