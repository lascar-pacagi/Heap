package fr.insa_rennes.sdd.dijkstra;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import fr.insa_rennes.sdd.graph.Graph;
import fr.insa_rennes.sdd.graph.VertexAndWeight;
import fr.insa_rennes.sdd.priority_queue.PriorityQueue;

public class Dijkstra<T> {
	private final PriorityQueue<DijkstraNode<T>> pq;	
	private final Map<T, Double> cost = new HashMap<>();
	private final Map<T, T> prev = new HashMap<>();

	public Dijkstra(Graph<T> graph, T source) {
		this(graph, source, FactoryPQ.newInstance("HeapPQ"));
	}	

	public Dijkstra(Graph<T> graph, T source, PriorityQueue<DijkstraNode<T>> pq) {
		this.pq = pq; 
		solve(graph, source);
	}

	private void solve(Graph<T> graph, T source) {
		pq.add(new DijkstraNode<>(0.0, source));
		while (!pq.isEmpty()) {
			DijkstraNode<T> node = pq.poll();			
			if (cost.containsKey(node.vertex)) {
				continue;
			}								
			cost.put(node.vertex, node.cost);
			prev.put(node.vertex, node.prev);
			for (VertexAndWeight<T> next : graph.neighbors(node.vertex)) {
				pq.add(new DijkstraNode<T>(node.cost + next.weight, next.vertex, node.vertex));								
			}
		}
	}

	public Deque<T> getPathTo(T v) {
		Deque<T> path = new ArrayDeque<>();
		if (hasPathTo(v)) {			
			T current = v;
			do {
				path.addFirst(current);
				current = prev.get(current);
			} while (current != null);
		}
		return path;
	}

	public double getCost(T v) {
		return cost.getOrDefault(v, Double.POSITIVE_INFINITY);
	}
	
	public boolean hasPathTo(T v) {
		return getCost(v) != Double.POSITIVE_INFINITY;
	}

}
