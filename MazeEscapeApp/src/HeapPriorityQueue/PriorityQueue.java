package HeapPriorityQueue;

public interface PriorityQueue<K,V> {
	public int size();
	public boolean isEmpty();
	public Entry<K,V> min() throws EmptyPriorityQueueException;
	public Entry<K,V> insert(K k, V x) throws InvalidKeyException;
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException;

}
