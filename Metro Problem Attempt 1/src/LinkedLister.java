
public class LinkedLister {
	private int length;
	private Node head;
	private Node tail;
	private class Node
	{
		private int[] data = new int[2];
		private Node next;
		private Node prev;
		public Node()
		{
			this.next = this.prev = null;
		}
		public Node(int[] vals)
		{
			for(int i = 0; i < 2; i++)
			{
				this.data[i] = vals[i];
			}
			this.prev = null;
			this.next = null;
		}
		public Node(int start, int end)
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
		public String toString()
		{
			String result = "";
			result = "This node has data of\n";
			result = result + String.format("A starting column of: %d\n", data[0]);
			result = result + String.format("An ending column of: %d\n", data[1]);
			return result;
		}
	};
	public LinkedLister()
	{
		length = 0;
		this.head = this.tail = null;
	}
	public int getLength()
	{
		return length;
	}
	public void add(int[] vals,int flag)
	{
		Node node = new Node(vals);
		add(node,flag);
	}
	public void add(int start, int end, int flag)
	{
		Node node = new Node(start,end);
		add(node,flag);
	}
	public void iterate()
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
	public void remove(int index)
	{
		if(index >= length || index < 0)
			return;
		Node rNode = nodeAt(index);
		remove(rNode);
	}
	public int checkStart(int start, int end)
	{
		if(this.head == null)
		{
			Node newNode = new Node(start,end);
			add(newNode,1);
			return 0;
		}
		Node currNode = this.head;
		Node prevNode = currNode;
		int index = 0;
		int check = -1;
		while(currNode != null)
		{
			if(currNode.getStartCol() <= start && currNode.getEndCol() >= end)
			{
				System.out.println("No Match :(");
				return -1;
			}
			else if(currNode.getStartCol() > start && prevNode.getEndCol() <= start)
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if(currNode.getStartCol() > start)
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if(currNode.getStartCol() <= start && prevNode.getEndCol() >= start && currNode != this.head)
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if(currNode.getStartCol() >= end && prevNode.getEndCol() <= start)
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if(currNode.getStartCol() == start && currNode.getEndCol() < end)
			{
				check = index;
				filter(index,start,end,index);
				return check;
			}
			else if((currNode.getNext() == null && (end > currNode.getEndCol())))
			{
				check = index;
				filter(index,start,end,-1);
				return check;
			}
			prevNode = currNode;
			currNode = currNode.getNext();
			index++;
		}
		if(check == -1)
		{
			System.out.println("NO MATCHES :(");
		}
		return check;
	}
	public int getSpaceOccupation()
	{
		int occupations = 0;
		if(length == 0)
			return occupations;
		Node currNode = this.head;
		int trackOccupation;
		while(currNode != null)
		{
			trackOccupation = currNode.getEndCol() - currNode.getStartCol();
			trackOccupation++;
			occupations += trackOccupation;
			currNode = currNode.getNext();
		}
		return occupations;
	}
	public String toString()
	{
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
		this.head.setNext(node);
		this.tail = node;
		this.tail.setPrev(this.head);
		this.tail.setNext(null);
		length++;
	}
	private void resetTail(Node node)
	{
		if(this.tail == null && this.head == null)
		{
			addFromEmpty(node);
		}
		else if(this.head.getNext() == null && this.tail.getPrev() == null)
		{
			separate(node);
		}
		else
		{
			Node movedNode = this.tail;
			movedNode.setPrev(this.tail.getPrev());
			movedNode.setNext(node);
			this.tail = node;
			this.tail.setPrev(movedNode);
			this.tail.setNext(null);
			length++;
		}
	}
	private void putHead(Node node)
	{
		if(this.tail == null && this.head == null)
		{
			addFromEmpty(node);
		}
		else if(this.tail.getPrev() == null &&  this.head.getNext() == null)
		{
			separate(node);
		}
		else
		{
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
			return null;
		int ticker = 0;
		if(index == ticker)
		{
			return this.head;
		}
		Node node = this.head;
		while(ticker < index)
		{
			node = node.getNext();
			ticker++;
		}
		return node;
	}
	private void add(Node node, int flag)
	{
		if(this.head == null && this.tail == null)
		{
			addFromEmpty(node);
			return;
		}
		else if(flag == 0)
		{
				resetTail(node);
		}
		else if(flag != 0)
		{
				putHead(node);
		}
	}
	private void addBefore(int index, Node node)
	{
		if(index >= length && index > 0)
		{
			add(node,0);
			return;
		}
		if(index == 0)
		{
			add(node,1);
			return;
		}
		Node currNode = nodeAt(index);
		Node prevNode = currNode.getPrev();
		prevNode.setNext(node);
		node.setPrev(prevNode);
		currNode.setPrev(node);
		node.setNext(currNode);
		length++;
	}
	private void popTail()
	{
		if(this.tail.getPrev() == null)
		{
			this.tail = null;
			this.head = null;
		}
		else if(this.tail == null && this.head == null)
			return;
		else
		{
			Node node = this.tail.getPrev();
			Node prevNode2 = node.getPrev();
			if(prevNode2 == null)
			{
				this.tail = node;
				this.tail.setPrev(null);
				this.tail.setNext(null);
				this.head = node;
				this.head.setPrev(null);
				this.head.setNext(null);
			}
			else
			{
				this.tail = node;
				this.tail.setPrev(prevNode2);
				this.tail.setNext(null);
				prevNode2.setNext(this.tail);
			}
		}
		length--;
	}
	private void popHead()
	{
		Node tempNode = this.head.getNext();
		if(tempNode == null)
		{
			this.head = null;
			this.tail = null;
			length--;
			return;
		}
		Node next = tempNode.getNext();
		this.head.setNext(next);
		this.head.setPrev(null);
		this.head = tempNode;
		if(next == null)
		{
			this.tail = this.head;
			this.tail.setPrev(null);
			this.tail.setNext(null);
		}
		else
		{
			this.head.getNext().setPrev(this.head);
		}
		length--;
	}
	private void remove(Node node)
	{
		if(this.head == null)
			return;
		else if(node == this.head)
		{
			popHead();
		}
		else if(node.getNext() == null)
		{
			popTail();
		}
		else
		{
			Node pNode = node.getPrev();
			Node nextNode = node.getNext();
			nextNode.setPrev(pNode);
			pNode.setNext(nextNode);
			length--;
		}
	}
	
	private void filter(int index, int start,int end, int tailFlag)
	{
		if(tailFlag == -1)
		{
			Node newNode;
			if((start - this.tail.getEndCol()) >= 2)
			{
				newNode = new Node(start,end);
			}
			else
			{
				newNode = new Node(this.tail.getStartCol(), end);
				remove(this.tail);
			}
			add(newNode,0);
			return;
		}
		int retrieve = index;
		if(retrieve < 0)
			retrieve = 0;
		Node starterNode = nodeAt(retrieve);
		Node prevNode;
		if(retrieve == 0)
			prevNode = starterNode;
		else
			prevNode = starterNode.getPrev();
		int base = prevNode.getEndCol();
		if((start - base)  == 1 || (start - base) == 0)
		{
			base = prevNode.getStartCol();
			starterNode = prevNode;
			if(retrieve > 0)
				retrieve--;
		}
		else
		{
			base = start;
		}
		Node node;
		while(starterNode != null && starterNode.getEndCol() <= end)
		{
			prevNode = starterNode;
			starterNode = starterNode.getNext();
			remove(prevNode);
		}
		if(starterNode == null)
		{
			node = new Node(base,end);
			add(node,0);
		}
		else if(end < starterNode.getStartCol())
	
		{
			if(starterNode.getStartCol() - end == 1)
			{
				node = new Node(base,starterNode.getEndCol());
				remove(starterNode);
			}
			else
				node = new Node(base,end);
			addBefore(retrieve, node);
		}
		else if(end >= starterNode.getStartCol())
		{
			int endCol = starterNode.getEndCol();
			remove(starterNode);
			node = new Node(base, endCol);
			addBefore(retrieve, node);
		}
	}
}
