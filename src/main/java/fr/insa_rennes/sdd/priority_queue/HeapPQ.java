package fr.insa_rennes.sdd.priority_queue;

import java.util.Arrays;
import java.util.Comparator;

import fr.insa_rennes.sdd.util.ArraySupport;

public class HeapPQ<T> implements PriorityQueue<T> {
	private static final int DEFAULT_INITIAL_CAPACITY = 8;
	private Comparator<? super T> comparator;
	private int size;
	T[] heap;

	public HeapPQ() {
		this(DEFAULT_INITIAL_CAPACITY, null);
	}	

	public HeapPQ(int initialCapacity) {
		this(initialCapacity, null);
	}

	public HeapPQ(Comparator<? super T> comparator) {
		this(DEFAULT_INITIAL_CAPACITY, comparator);
	}

	@SuppressWarnings("unchecked")
	public HeapPQ(int initialCapacity, Comparator<? super T> comparator) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		heap = (T[])new Object[initialCapacity];
		this.comparator = comparator == null ? (t1, t2) -> ((Comparable<? super T>)t1).compareTo(t2) : comparator;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(T e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (size >= heap.length) {
			grow();
		}
		siftUp(size, e);
		size++;
	}

	private void grow() {		        
		int oldLength = heap.length;
		heap = Arrays.copyOf(heap, ArraySupport.newLength(oldLength, oldLength + 1, oldLength << 1));
	}

	private void siftUp(int i, T x) {
		while (i > 0) {
			int parent = (i - 1) >>> 1;
			T e = heap[parent];
			if (comparator.compare(x, e) >= 0) {
				break;
			}
			heap[i] = e;
			i = parent;
		}
		heap[i] = x;		
	}

	@Override
	public T peek() {
		return size == 0 ? null : heap[0];
	}

	@Override
	public T poll() {
		T res = peek();
		if (res != null) { 
			T e = heap[--size];
			heap[size] = null;
			if (size > 0) siftDown(e);
		}
		return res;
	}

	private void siftDown(T x) {		
		int half = size >>> 1;
		int i = 0;
		while (i < half) {
			int child = (i << 1) + 1;
			T e = heap[child];
			int right = child + 1;
			if (right < size && comparator.compare(e, heap[right]) > 0) {
				e = heap[child = right];
			}
			if (comparator.compare(x, e) <= 0) {
				break;
			}
			heap[i] = e;
			i = child;
		}
		heap[i] = x;
	}
	
}
