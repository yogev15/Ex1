package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.junit.jupiter.api.Test;
import ex1.*;

public class WGraph_DSMyTest {

	private static Random _rnd = null;
	@Test
	void test_nodeSize() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(2);
		g.addNode(6);

		int ns = g.nodeSize();
		assertEquals(6,ns);

		g.removeNode(2);
		g.removeNode(1);
		g.removeNode(1);
		g.removeNode(4);

		ns = g.nodeSize();
		assertEquals(3,ns);
	}

	@Test
	void test_edgeSize() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(6);

		g.connect(0,1,21);
		g.connect(0,6,9.6);
		g.connect(1,3,2);
		g.connect(2,3,7.7);
		g.connect(2,4,3);

		int e_size =  g.edgeSize();
		assertEquals(5, e_size);
		double v1 = g.getEdge(2,4);
		double v2 = g.getEdge(4,2);
		assertEquals(v1, v2);
		assertEquals(v1, 3);
	}

	@Test
	void test_vertexes() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(6);

		g.connect(0,1,21);
		g.connect(0,6,9.6);
		g.connect(1,3,2);
		g.connect(2,3,7.7);
		g.connect(2,4,3);

		Collection<node_info> vertexes = g.getV();
		Iterator<node_info> it = vertexes.iterator();
		while (it.hasNext()) {
			node_info node = it.next();
			assertNotNull(node);
		}
	}

	@Test
	void test_removeNode() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(6);

		g.connect(0,1,21);
		g.connect(0,6,9.6);
		g.connect(1,3,2);
		g.connect(2,3,7.7);
		g.connect(2,4,3);

		g.removeNode(3);
		g.removeNode(0);
		assertFalse(g.hasEdge(3,1));

		assertEquals(1,g.edgeSize());
		assertEquals(4,g.nodeSize());
	}

	@Test
	void test_removeEdge() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(6);

		g.connect(0,1,21);
		g.connect(0,6,9.6);
		g.connect(1,3,2);
		g.connect(2,3,7.7);
		g.connect(2,4,3);

		g.removeEdge(0,1);
		assertEquals(g.getEdge(0,1),-1);
	}

	@Test
	void test_hasEdge() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(6);

		g.connect(0,1,21);
		g.connect(0,6,9.6);
		g.connect(1,3,2);
		g.connect(2,3,7.7);
		g.connect(2,4,3);

		assertTrue(g.hasEdge(0, 6));
		g.removeEdge(0, 6);
		assertFalse(g.hasEdge(0, 6));

	}
	@Test
	void test_connect() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(6);

		g.connect(0,1,21);
		g.connect(0,6,9.6);
		g.connect(1,3,2);
		g.connect(2,3,7.7);
		g.connect(2,4,3);

		g.removeEdge(3, 2);
		assertFalse(g.hasEdge(3,2));
		g.connect(3, 2, 0.4);
		assertEquals(g.getEdge(3, 2),0.4);
	}

	//a function that create a random graph with v_size nodes and e_size edges
	public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
		weighted_graph g = new WGraph_DS();
		_rnd = new Random(seed);
		for(int i=0;i<v_size;i++) {
			g.addNode(i);
		}
		// Iterator<node_data> itr = V.iterator(); 
		int[] nodes = nodes(g);
		while(g.edgeSize() < e_size) {
			int a = nextRnd(0,v_size);
			int b = nextRnd(0,v_size);
			int i = nodes[a];
			int j = nodes[b];
			double w = _rnd.nextDouble();
			g.connect(i,j, w);
		}
		return g;
	}
	private static int nextRnd(int min, int max) {
		double v = nextRnd(0.0+min, (double)max);
		int ans = (int)v;
		return ans;
	}
	private static double nextRnd(double min, double max) {
		double d = _rnd.nextDouble();
		double dx = max-min;
		double ans = d*dx+min;
		return ans;
	}
	
	// a method that create an array of vertexes of graph g
	private static int[] nodes(weighted_graph g) {
		int size = g.nodeSize();
		Collection<node_info> V = g.getV();
		node_info[] nodes = new node_info[size];
		V.toArray(nodes); // 
		int[] ans = new int[size];
		for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
		Arrays.sort(ans);
		return ans;
	}

}
