package fr.insa_rennes.sdd.priority_queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Supplier;

public class PriorityQueueTestCommon {
	Supplier<PriorityQueue<Integer>> factory;	
	
	PriorityQueueTestCommon(Supplier<PriorityQueue<Integer>> factory) {
		this.factory = factory;
	}	

	public void testAddPeekPoll() {		
		PriorityQueue<Integer> pq = factory.get();
		assertNull(pq.peek());
		assertNull(pq.poll());
		assertThrows(NullPointerException.class, () -> pq.add(null));
		pq.add(3);
		assertEquals(3, pq.peek());
		pq.add(-100);
		assertEquals(-100, pq.peek());
		pq.add(200);
		assertEquals(-100, pq.peek());
		pq.add(5);
		assertEquals(-100, pq.peek());
		pq.add(-2019);
		assertEquals(-2019, pq.peek());
		assertEquals(-2019, pq.poll());
		assertEquals(-100, pq.peek());
		assertEquals(-100, pq.poll());
		pq.add(-99);
		assertEquals(-99, pq.peek());
		assertEquals(-99, pq.poll());
		assertEquals(3, pq.poll());
		assertEquals(5, pq.poll());
		assertEquals(200, pq.poll());
	}

	public void testSize() {
		PriorityQueue<Integer> pq = factory.get();
		assertEquals(0, pq.size());
		pq.add(1);
		assertEquals(1, pq.size());
		pq.add(2);
		assertEquals(2, pq.size());
		pq.add(3);
		assertEquals(3, pq.size());
		pq.add(4);
		assertEquals(4, pq.size());
		pq.add(5);
		assertEquals(5, pq.size());
		pq.add(6);
		assertEquals(6, pq.size());
		pq.add(7);		
		assertEquals(7, pq.size());
		pq.add(8);
		assertEquals(8, pq.size());
		pq.add(9);
		assertEquals(9, pq.size());
		pq.poll();
		assertEquals(8, pq.size());
		pq.poll();
		pq.poll();
		pq.poll();
		pq.poll();
		assertEquals(4, pq.size());
		pq.poll();
		pq.poll();
		pq.poll();
		pq.poll();
		assertEquals(0, pq.size());		
		pq.poll();
		assertEquals(0, pq.size());
	}

	public void testIsEmpty() {
		PriorityQueue<Integer> pq = factory.get();
		assertTrue(pq.isEmpty());
		pq.add(1);
		assertFalse(pq.isEmpty());
		pq.poll();
		assertTrue(pq.isEmpty());
		pq.poll();
		pq.poll();
		pq.poll();
		assertTrue(pq.isEmpty());
		pq.add(1);
		assertFalse(pq.isEmpty());
		pq.add(2);
		assertFalse(pq.isEmpty());
		pq.add(3);
		assertFalse(pq.isEmpty());
		pq.poll();
		assertFalse(pq.isEmpty());
		pq.poll();
		assertFalse(pq.isEmpty());
		pq.poll();
		assertTrue(pq.isEmpty());
	}

}
