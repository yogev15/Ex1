package ex1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


import ex1.WGraph_DS.NodeInfo;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable{

	private WGraph_DS graph;

	//constructor
	public WGraph_Algo()
	{
		this.graph = new WGraph_DS();
	}

	@Override
	public void init(weighted_graph g) {
		// initiate graph to g;
		this.graph = (WGraph_DS) g;
	}

	@Override
	public weighted_graph getGraph() {
		// returns the graph of the class
		weighted_graph g = this.graph;
		return g;
	}

	@Override
	public weighted_graph copy() {
		// returns a copied graph of graph 
		return new WGraph_DS(this.graph);
	}

	@Override
	public boolean isConnected() {
		//a function that checks if this graph is connected

		if(this.graph.nodeSize() <= 1) //if the number of nodes is 0 or 1 then it is connected
			return true;	

		if(this.graph.edgeSize() == 0 && this.graph.nodeSize() > 1) //if the number of the nodes in the graph is bigger then 1 and there are no edges in it then the graph isn't connected
			return false;	

		WGraph_DS g = (WGraph_DS) this.copy(); //g is the copied graph the function will check
		Iterator<node_info> it = g.getV().iterator();
		WGraph_DS.NodeInfo next = (NodeInfo) it.next();
		Dijkstra(next);

		for(node_info temp : g.getV()) // checks if every node has a connected tag
		{
			if(temp.getTag() == Integer.MAX_VALUE)
				return false;
		}

		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// a function that returns the value of the shortest path from source vertex to destination vertex
		WGraph_DS g = (WGraph_DS) this.copy();

		if(g.getHashGraph().containsKey(src) && g.getHashGraph().containsKey(dest))
		{
			Dijkstra((NodeInfo) g.getNode(src));
			return g.getNode(dest).getTag();
		}
		return -1;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		//  a function that returns a list of the shortest path from source vertex to destination vertex

		WGraph_DS g = (WGraph_DS) this.copy();
		LinkedList<node_info> pathList = new LinkedList<node_info>();

		if(g.getHashGraph().containsKey(src) && g.getHashGraph().containsKey(dest))
		{
			Dijkstra((WGraph_DS.NodeInfo) g.getNode(src));

			if(g.getNode(dest).getTag() == Integer.MAX_VALUE) //if destination vertex tag value is max value returns null
				return null;
			else //if not the path is built
			{	
				WGraph_DS.NodeInfo node = (WGraph_DS.NodeInfo) g.getNode(dest);
				while(node.getParent() != null)
				{
					pathList.addFirst(node);
					node = node.getParent();
				}
				pathList.addFirst(node);
			}
		}
		return pathList;
	}

	@Override
	public boolean save(String file) 
	{//a function that saves this class graph on a document with file location
		try{
			FileOutputStream filename= new FileOutputStream(file);
			ObjectOutputStream object =new ObjectOutputStream(filename);
			object.writeObject(this.graph);
			object.close();
			filename.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean load(String file) 
	{//a function that loads this class graph on a document with file location
		try{
			FileInputStream filename = new FileInputStream(file);
			ObjectInputStream object = new ObjectInputStream(filename);
			this.graph=(WGraph_DS)object.readObject();
			object.close();
			filename.close();
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}


	
	public void Dijkstra (WGraph_DS.NodeInfo node)
	{
		//An algorithm that goes through the vertexes of the graph/
		//after the algorithm is done, we can check this graph features
		if(this.graph == null || this.graph.nodeSize() == 0)
			return;

		PriorityQueue<WGraph_DS.NodeInfo> q = new PriorityQueue<WGraph_DS.NodeInfo>(); // the queue of nodes 
		node.setTag(0); 
		q.add(node);

		while(!q.isEmpty()) 
		{
			WGraph_DS.NodeInfo next = q.poll(); // the next node
			for(node_info neighbour : this.graph.getV(next.getKey())) //changes the nodes neighboures data and adds them to the queue
			{
				WGraph_DS.NodeInfo tempNei = (NodeInfo) neighbour;				
				if(tempNei.getTag() > next.getTag() + next.getNiEdges().get(tempNei.getKey())) 
				{
					tempNei.setTag(next.getTag() + next.getNiEdges().get(tempNei.getKey()));
					tempNei.setParent(next);
					
				}
				if(tempNei.getInfo() == "white")
				{
					q.add(tempNei);
				}

				next.setInfo("black"); //when finished with a node he's info changes to black 
			}

		}

	}
	
	@Override
	public boolean equals(Object o)
	{
		//a method that compare 2 WGraph_Algo objects
		if(this == o) 
			return true;
		if(o == null || getClass()!= o.getClass()) 
			return false;
		
		WGraph_Algo g =(WGraph_Algo)o;
		for(node_info node : this.graph.getV())
		{
			if(!g.graph.getV().contains(node))
				return false;
		}
		return true;
	}
}
