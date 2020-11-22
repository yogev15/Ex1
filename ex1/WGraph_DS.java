package ex1;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph, Serializable{

	private int mc;
	private int NodeSize;
	private int EdgeSize;
	private HashMap<Integer , node_info> hashGraph;

	//standard constructor
	public WGraph_DS()
	{
		this.mc = 0;
		this.NodeSize = 0;
		this.EdgeSize = 0;
		this.hashGraph = new HashMap<Integer,node_info>();
	}

	//copy constructor
	public WGraph_DS(WGraph_DS g)
	{
		this.mc = g.getMC();
		this.NodeSize = g.nodeSize();
		this.EdgeSize = g.edgeSize();
		this.hashGraph = new HashMap<Integer,node_info>();
		for(node_info temp1 : g.getV())
		{
			this.hashGraph.put(temp1.getKey(), temp1);
		}

	}

	@Override
	public node_info getNode(int key) {
		// returns the vertex with the key value of key if exist in the graph
		if(!this.hashGraph.isEmpty())
			return this.hashGraph.get(key);
		return null;
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		// returns true or false there is an edge between 2 vertexes 
		if(this.hashGraph.containsKey(node1)  && this.hashGraph.containsKey(node2) && node1 != node2)
		{
			NodeInfo temp = (NodeInfo) this.hashGraph.get(node1);
			if(temp.getNi().containsKey(node2))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double getEdge(int node1, int node2) {
		// returns an edge weight between 2 vertexes
		if(hasEdge(node1,node2))
		{
			NodeInfo temp = (NodeInfo) this.hashGraph.get(node1);
			return temp.getNiEdges().get(node2);
		}

		return -1;
	}

	@Override
	public void addNode(int key) {
		// adds a new vertex to the graph 
		if(this.hashGraph.containsKey(key)) //if there's a vertex with the same key  
			return;
		NodeInfo temp = new NodeInfo();
		temp.setKey(key);
		this.hashGraph.put(key, temp);
		this.mc++;
		this.NodeSize++;
	}

	@Override
	public void connect(int node1, int node2, double w) {
		// a method that connects 2 vertexes with a weighted edge value of w
		if(node1 == node2 || w < 0) 
			return;
		if(this.hashGraph.containsKey(node1)  && this.hashGraph.containsKey(node2))//checks if both vertexes are in the graph
		{
			NodeInfo temp1,temp2;
			temp1 = (NodeInfo) this.getNode(node1);
			temp2 = (NodeInfo) this.getNode(node2);

			if(this.hasEdge(node1, node2)) // if there is an edge between the vertexes updates the weight of the edge to w
			{
				if(temp1.getNiEdges().get(temp2.getKey()) == w)
					return;
				temp1.hashNiWieght.replace(node2 , temp1.getNiEdges().get(node2) , w);	
				temp2.hashNiWieght.replace(node1 , temp2.getNiEdges().get(node1) , w);
			}
			else //connects the vertexes
			{
				temp1.hashNi.put(node2 , temp2);
				temp2.hashNi.put(node1 , temp1);
				temp1.hashNiWieght.put(node2 , w);
				temp2.hashNiWieght.put(node1 , w);
				this.EdgeSize++;
			}
			this.mc++;
		}

	}

	@Override
	public Collection<node_info> getV() {
		// returns a collection of the graph vertexes
		return this.hashGraph.values();
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		// returns a collection of the neighbours of node_id vertex
		NodeInfo temp = (NodeInfo)this.getNode(node_id);
		return temp.getNi().values();
	}

	@Override
	public node_info removeNode(int key) {
		// deletes a vertex from the graph
		if(this.hashGraph.containsKey(key))
		{
			for(node_info node : this.getV(key)) // deletes the edges of the vertex to his neighbours 
			{
				NodeInfo temp = (NodeInfo)node;
				temp.getNi().remove(key);
				temp.getNiEdges().remove(key);
				this.mc++;
				this.EdgeSize--;
			}

			this.NodeSize--;
			return this.hashGraph.remove(key);
		}
		return null;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		// deletes an edge between 2 vertexes
		if(this.hashGraph.containsKey(node1)  && this.hashGraph.containsKey(node2))
		{
			NodeInfo temp1 = (NodeInfo) this.getNode(node1);
			NodeInfo temp2 = (NodeInfo) this.getNode(node2);

			if(this.hasEdge(node1, node2))
			{
				temp1.getNi().remove(node2);
				temp1.getNiEdges().remove(node2);
				temp2.getNi().remove(node1);
				temp2.getNiEdges().remove(node1);		
			}
		}
	}

	@Override
	public int nodeSize() {
		// returns the amount of vertexes in the graph
		return this.NodeSize;
	}

	@Override
	public int edgeSize() {
		//  returns the amount of edges in the graph
		return this.EdgeSize;
	}

	@Override
	public int getMC() {
		//  returns the mode count of the graph
		return this.mc;
	}

	public HashMap<Integer, node_info> getHashGraph() {
		//returns the hashmap of the vertexes in the graph
		return hashGraph;
	}
	
	@Override
	public boolean equals(Object object)
	{
		//a method that compare 2 objects of weighted graph 
		if(!(object instanceof WGraph_DS))
			return false;
		
		WGraph_DS g = (WGraph_DS) object;
		
		for(node_info temp : g.getV())
		{
			if(!this.hashGraph.containsKey(temp.getKey()))
				return false;
			
			WGraph_DS.NodeInfo node = (NodeInfo) this.getNode(temp.getKey());
			if(!node.equals(temp)) //compares every node of the 2 graphs
				return false;	
		}
		return true;
	}


	//an inner class of WGraph_DS that implements node_info
	public class NodeInfo implements node_info,Serializable ,Comparable<NodeInfo>{

		private int key;
		private double tag;
		private String info;
		private NodeInfo parent;
		private HashMap<Integer , node_info> hashNi; 
		private HashMap<Integer , Double> hashNiWieght; 
		
		//constructor
		public NodeInfo()
		{
			this.key = 0;
			this.tag = Integer.MAX_VALUE;
			this.info = "white";
			this.parent = null;
			this.hashNi =  new HashMap<Integer , node_info>();
			this.hashNiWieght = new HashMap<Integer , Double>();
		}

		//copy constructor
		public NodeInfo(NodeInfo node)
		{
			this.key = node.getKey();
			this.tag = node.getTag();
			this.info = node.getInfo();
			this.parent = node.getParent();
			this.hashNi =  new HashMap<Integer , node_info>();
			this.hashNiWieght = new HashMap<Integer , Double>();
		}


		@Override
		public int getKey() {
			// returns this vertex key
			return this.key;
		}

		@Override
		public String getInfo() {
			// returns this vertex info
			return this.info;
		}

		@Override
		public void setInfo(String s) {
			// sets this vertex info with s
			this.info = s;
		}

		@Override
		public double getTag() {
			// returns this vertex tag
			return this.tag;
		}

		@Override
		public void setTag(double t) {
			// sets this vertex tag with t
			this.tag = t;
		}

		public  HashMap<Integer , node_info> getNi()
		{
			//returns this vertex hashmap neighbours
			return this.hashNi;
		}

		public  HashMap<Integer , Double> getNiEdges()
		{
			//returns this vertex hashmap for edges value;
			return this.hashNiWieght;
		}

		public void setKey(int key)
		{
			// sets this vertex key with key
			this.key = key;
		}

		public void setParent(NodeInfo parent)
		{
			// sets this vertex parent with parent
			this.parent = parent;
		}

		public NodeInfo getParent()
		{
			//returns this vertex parent
			return this.parent;
		}
		
		@Override
		public boolean equals(Object object)
		{
			//a method that compare 2 vertexes
			if(object == null)
				return false;
			
			if(object instanceof NodeInfo)
				return this.key == ((node_info) object).getKey();
			return false;
		}
		
		@Override
		public int compareTo(NodeInfo node)
		{
			// a method that compare 2 vertexes tags and returns a value accordingly
			if(this.tag > node.getTag())
				return 1;
			if(this.tag < node.getTag())
				return -1;
			return 0;
		}

	}


}
