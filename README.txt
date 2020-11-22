assignment ex1 OOP Ariel University

this assignment asked us to bulid an unweighted and undirectional graph and cheks his features like 
conctivity and paths between vertexes.

the src folder contains 3 interfaces of node_id, weighted_graph and weighted_graph_algorithms and 2 
classes that implements them, WGraph_DS who implements weighted_graph and contains an inner class NodeInfo 
that implements node_info, and WGraph_Algo that implements weighted_graph_algorithms.
the tests folder contains 2 tests, onr for WGraph_DS  and one for WGraph_Algo.

the structor of the classes: 
NodeInfo - is the class that illustrate a vertex, every vertex have a uniqe key , tag , info and a vertex parent.
in addition every vertex have also 2 hash maps: one for its neighbours and one for the neighbours edges.

WGraph_DS - every graph have a mode count,and a number of vertexes,edges it. 
like NodeInfo this class use a hash map, in this case for saving the vertexes of the graph.

WGraph_Algo - contains only a WGraph_DS  graph.

the Dijkstra method is An algorithm that goes through the vertices of the graph and changes their values 
so we can able to check if a graph is connected or not and the shortest path between 2 vertexe.