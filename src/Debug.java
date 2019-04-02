import java.util.Iterator;

import static java.lang.System.out;

/**
 * Test client
 * 
 * IMPORTANT:
 * Before you run, make sure the assert's enabled otherwise assertions below will be useless.
 * To enable assert, add "-ea" (without quotes) to VM arguments.
 */
public class Debug {
	public static void main(String[] args) {
		/* Forest test 1 */
		out.println("Forest test 1:");
		Forest<Integer> test1 = new Forest<>();
		test1.addEdge(0, 1);
		test1.addEdge(1, 2);
		test1.addEdge(3, 4);
		test1.addEdge(4, 5);
		test1.addEdge(6, 7);
		test1.addEdge(7, 8);
		assert(test1.vertexCount() == 9);//vertexes (0 -> 8)
		assert(test1.edgeCount() == 6);
		assert test1.isBipartite() : "The graph is bipartite";//new syntax: assert bool : msg
		assert(test1.getConnectedComponents().size() == 3);
		out.println("bfs iter:");
		Iterator<Integer> bfsiter1 = test1.breadthFirstIterator(0);
		while (bfsiter1.hasNext())
			out.println(bfsiter1.next());
		out.println(test1);
		out.println("end of Forest test 1\n");
		
		/* Forest test 2 */
		out.println("Forest test 2:");
		Forest<Integer> test2 = new Forest<>();//tree
		test2.addEdge(0, 1);
		test2.addEdge(0, 2);
		test2.addEdge(1, 3);
		test2.addEdge(1, 4);
		test2.addEdge(2, 5);
		test2.addEdge(2, 6);
		assert(test2.vertexCount() == 7);
		assert(test2.edgeCount() == 6);
		assert(test2.degree(0) == 2);
		assert(test2.getConnectedComponents().size() == 1);
		out.println("dfs iter:");
		Iterator<Integer> dfsIter2 = test2.depthFirstIterator(0);
		while (dfsIter2.hasNext())
			out.println(dfsIter2.next());
		out.println("bfs iter:");
		Iterator<Integer> bfsIter2 = test2.breadthFirstIterator(0);
		while (bfsIter2.hasNext())
			out.println(bfsIter2.next());
		out.println(test2);
		out.println("end of Forest test 2\n");
		
		/* Cyclic forest test passed, java.lang.IllegalArgumentException thrown */
		/*
		Forest<Integer> bad = new Forest<>();
		bad.addEdge(0, 1);
		bad.addEdge(1, 2);
		bad.addEdge(2, 3);
		bad.addEdge(3, 0);//cycle!
		*/
		
		/* DirectedGraph test 1 */
		out.println("DirectedGraph test 3");
		DirectedGraph<Integer> test3 = new DirectedGraph<>();
		test3.addEdge(0, 1);
		test3.addEdge(1, 2);
		test3.addEdge(2, 0);
		assert(test3.vertexCount() == 3);
		assert(test3.edgeCount() == 3);
		assert(test3.isCyclic());
		assert(test3.getStronglyConnectedComponents().size() == 1);
		out.println(test3);
		out.println(test3.transpose());
		out.println("bfs iter:");
		Iterator<Integer> bfsIter3 = test3.breadthFirstIterator(0);
		while (bfsIter3.hasNext())
			out.println(bfsIter3.next());
		out.println("end of DirectedGraph test 3\n");
		
		/* DirectedGraph test 2 */
		out.println("DirectedGraph test 4");
		DirectedGraph<Integer> test4 = new DirectedGraph<>();
		test4.addEdge(1, 0);
		test4.addEdge(0, 2);
		test4.addEdge(2, 1);
		test4.addEdge(0, 3);
		test4.addEdge(3, 4);
		assert(test4.vertexCount() == 5);
		assert(test4.edgeCount() == 5);
		assert(test4.isCyclic());
		assert(test4.getStronglyConnectedComponents().size() == 3);
		assert(test4.shortestPath(0, 4) == 2);
		out.println(test4);
		out.println(test4.transpose());
		out.println("dfs iter:");
		Iterator<Integer> dfsIter4 = test3.depthFirstIterator(0);
		while (dfsIter4.hasNext())
			out.println(dfsIter4.next());
		out.println("end of DirectedGraph test 4\n");
		
		/* DirectedGraph test 3 */
		out.println("DirectedGraph test 5");
		DirectedGraph<Integer> test5 = new DirectedGraph<>();
		test5.addEdge(5, 2);
		test5.addEdge(5, 0);
		test5.addEdge(4, 0);
		test5.addEdge(4, 1);
		test5.addEdge(2, 3);
		test5.addEdge(3, 1);
		assert(test5.vertexCount() == 6);
		assert(test5.edgeCount() == 6);
		assert(test5.topologicalSort() != null);
		assert(test5.getStronglyConnectedComponents().size() == 6);
		out.println(test5);
		out.println(test5.transpose());
		out.println("dfs iter:");
		Iterator<Integer> dfsIter5 = test3.depthFirstIterator(0);
		while (dfsIter5.hasNext())
			out.println(dfsIter5.next());
		out.println("end of DirectedGraph test 5\n");
		
		/* Network test 1 */
		out.println("Network test 6");
		Network<Integer> test6 = new Network<>();
		test6.addEdge(0, 1, 4);
		test6.addEdge(0, 7, 8);
		test6.addEdge(1, 2, 8);
		test6.addEdge(1, 7, 11);
		test6.addEdge(2, 3, 7);
		test6.addEdge(2, 8, 2);
		test6.addEdge(2, 5, 4);
		test6.addEdge(3, 4, 9);
		test6.addEdge(3, 5, 14);
		test6.addEdge(4, 5, 10);
		test6.addEdge(5, 6, 2);
		test6.addEdge(6, 7, 1);
		test6.addEdge(6, 8, 6);
		test6.addEdge(7, 8, 7);
		assert(test6.vertexCount() == 9);
		assert(test6.edgeCount() == 14);
		assert(test6.edgeSum() == 93);
		assert(test6.spanningTree().edgeSum() == 37);//MST
		assert(test6.shortestPath(0, 2) == 12);
		assert(test6.shortestPath(0, 4) == 21);
		assert(test6.shortestPath(0, 5) == 11);
		assert(test6.shortestPath(0, 7) == 8);
		out.println(test6);
		out.println("end of Network test 6\n");
		
		/* Network test 2 */
		out.println("Network test 7");
		Network<Integer> test7 = new Network<>(true);//flow network
		test7.addEdge(0, 1, 2);
		test7.addEdge(1, 2, 3);
		test7.addEdge(2, 3, 4);
		test7.addEdge(3, 4, 5);
		test7.addEdge(4, 5, 6);
		test7.addEdge(5, 6, 7);
		assert(test7.vertexCount() == 7);
		assert(test7.edgeCount() == 6);
		assert(test7.edgeSum() == 27);
		out.println(test7);
		out.println("end of Network test 7\n");
		
		/* Vertex add/remove test 1 */
		out.println("test 8:");
		Forest<Integer> test8 = new Forest<>();//tree
		for (int i = 0; i < 7; ++i)
			test8.addVertex(i);
		test8.addEdge(0, 1);
		test8.addEdge(0, 2);
		test8.addEdge(1, 3);
		test8.addEdge(1, 4);
		test8.addEdge(2, 5);
		test8.addEdge(2, 6);
		assert(test8.vertexCount() == 7);//0->6
		test8.removeVertex(4);
		test8.removeVertex(6);
		assert(test8.vertexCount() == 5);//we removed 4 and 6
		assert(test8.edgeCount() == 4);//previously, we had 6 edges and we removed 2 edges
		out.println("bfs iter:");
		Iterator<Integer> bfsIter8 = test8.breadthFirstIterator(0);
		while (bfsIter8.hasNext())
			out.println(bfsIter8.next());
		test8.removeVertex(0);//after we remove the root, we will have 2 subtrees
		assert(test8.vertexCount() == 4);//subtree1 have vertexes 1 and 3, subtree2 vertexes have 2 and 5
		assert(test8.edgeCount() == 2);
		test8.addVertex(100);
		assert(test8.vertexCount() == 5);
		out.println("end of test 8\n");
		
		/* Vertex add/remove test 2 */
		out.println("test 9:");
		Network<Integer> test9 = new Network<>();
		test9.addEdge(0, 1, 10);
		test9.addEdge(0, 2, 10);
		assert(test9.vertexCount() == 3);
		assert(test9.edgeSum() == 20);
		test9.removeVertex(0);
		assert(test9.vertexCount() == 2);
		assert(test9.edgeSum() == 0);
		out.println("end of test 9\n");
		
		/* Edge remove test 1 */
		out.println("test 10:");
		Network<Integer> test10 = new Network<>(true);//flow network
		test10.addEdge(0, 1, 2);
		test10.addEdge(1, 2, 3);
		test10.addEdge(2, 3, 4);
		test10.addEdge(3, 4, 5);
		test10.addEdge(4, 5, 6);
		test10.addEdge(5, 6, 7);
		test10.removeEdge(0, 1, 2);
		test10.removeEdge(1, 2, 3);
		test10.removeEdge(2, 3, 4);
		test10.removeEdge(3, 4, 5);
		test10.removeEdge(4, 5, 6);
		test10.removeEdge(5, 6, 7);
		assert(test10.edgeCount() == 0);
		out.println("end of test 10\n");
		
		/* Max flow test 1 */
		out.println("test 11:");
		Network<Integer> test11 = new Network<>(true);//flow network
		test11.addEdge(0, 1, 16);
		test11.addEdge(0, 2, 13);
		test11.addEdge(1, 2, 10);
		test11.addEdge(1, 3, 12);
		test11.addEdge(2, 1, 4);
		test11.addEdge(2, 4, 14);
		test11.addEdge(3, 2, 9);
		test11.addEdge(3, 5, 20);
		test11.addEdge(4, 3, 7);
		test11.addEdge(4, 5, 4);
		assert(test11.maxFlow(0, 5) == 23);
		out.println("end of test 11\n");
		
		/* Max flow test 2 */
		out.println("test 12:");
		Network<Integer> test12 = new Network<>(true);//flow network
		test12.addEdge(0, 1, 3);
		test12.addEdge(0, 2, 7);
		test12.addEdge(1, 3, 9);
		test12.addEdge(1, 4, 9);
		test12.addEdge(2, 1, 9);
		test12.addEdge(2, 4, 9);
		test12.addEdge(2, 5, 4);
		test12.addEdge(3, 5, 3);
		test12.addEdge(4, 5, 7);
		test12.addEdge(0, 4, 10);
		assert(test12.maxFlow(0, 5) == 14);
		out.println("end of test 12\n");
		
		/* Max flow test 3 */
		out.println("test 13:");
		Network<Integer> test13 = new Network<>(true);//flow network
		test13.addEdge(0, 1, 10);
		test13.addEdge(0, 2, 10);
		test13.addEdge(1, 3, 4);
		test13.addEdge(1, 4, 8);
		test13.addEdge(1, 2, 2);
		test13.addEdge(2, 4, 9);
		test13.addEdge(3, 5, 10);
		test13.addEdge(4, 3, 6);
		test13.addEdge(4, 5, 10);
		assert(test13.maxFlow(0, 5) == 19);
		out.println("end of test 13\n");
		
		assert false : "assert's working";//make sure assert's enabled
	}
}
