package HeapPriorityQueue;

public interface Entry <K,V> {
	public K getKey();
	public V getValue();
	public void setValue(V v);
	public void setKey(K k);

}
