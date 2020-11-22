package ex1.tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import ex1.*;
public class WGraph_AlgoMyTest {

	@Test
	void test_isConnected()
	{
		weighted_graph g = WGraph_DSMyTest.graph_creator(25, 35, 1);
		weighted_graph_algorithms AlgoGraph = new WGraph_Algo();
		AlgoGraph.init(g);
		assertTrue(AlgoGraph.isConnected());
		
		
		g = WGraph_DSMyTest.graph_creator(45, 90, 1);
		AlgoGraph = new WGraph_Algo();
		AlgoGraph.init(g);
		assertTrue(AlgoGraph.isConnected());

		g = WGraph_DSMyTest.graph_creator(3, 1, 1);
		AlgoGraph = new WGraph_Algo();
		AlgoGraph.init(g);
		assertFalse(AlgoGraph.isConnected());
		
		g = WGraph_DSMyTest.graph_creator(100, 300, 1);
		AlgoGraph = new WGraph_Algo();
		AlgoGraph.init(g);
		assertTrue(AlgoGraph.isConnected());
		
	}


	@Test
	void test_shortestPathDist()
	{
		weighted_graph g = WGraph_DSMyTest.graph_creator(15, 0, 1);
		g.connect(0, 1, 2.5);
		g.connect(0, 2, 5);
		g.connect(1, 4, 3);
		g.connect(3, 2, 1);
		g.connect(3, 13, 6);
		g.connect(4, 7, 10);
		g.connect(7, 9, 12);
		g.connect(8, 12, 3.1);
		g.connect(12, 13, 4.2);
		g.connect(9, 13, 6);
		
		weighted_graph_algorithms AlgoGraph = new WGraph_Algo();
		AlgoGraph.init(g);
		double dis = AlgoGraph.shortestPathDist(4, 13);
		assertEquals(17.5 , dis);
		
	}
	
	@Test
	void test_shortestPath()
	{
		weighted_graph g = WGraph_DSMyTest.graph_creator(15, 0, 1);
		g.connect(0, 1, 2.5);
		g.connect(0, 2, 5);
		g.connect(1, 4, 3);
		g.connect(3, 2, 1);
		g.connect(3, 13, 6);
		g.connect(4, 7, 10);
		g.connect(7, 9, 12);
		g.connect(8, 12, 3.1);
		g.connect(12, 13, 4.2);
		g.connect(9, 13, 6);
		
		weighted_graph_algorithms AlgoGraph = new WGraph_Algo();
		AlgoGraph.init(g);
		List<node_info> path = AlgoGraph.shortestPath(4,13);
		
		int[] keyPath = {4, 1, 0, 2, 3, 13};
		int i = 0;
		for(node_info n: path) {
			
			assertEquals(n.getKey(), keyPath[i]);
			i++;
		}
		
	}
	
	@Test
	void test_save_load()
	{
		weighted_graph g = WGraph_DSMyTest.graph_creator(10,30,1);
		weighted_graph_algorithms AlgoGraph = new WGraph_Algo();
		AlgoGraph.init(g);
		String str = "newGraph.txt";
		AlgoGraph.save(str);
		weighted_graph g1 = WGraph_DSMyTest.graph_creator(10,30,1);
		AlgoGraph.load(str);
		assertEquals(g,g1);
		g.removeNode(0);
		assertNotEquals(g,g1);
	}
}
