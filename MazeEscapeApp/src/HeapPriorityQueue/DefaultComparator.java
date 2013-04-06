package HeapPriorityQueue;

public class DefaultComparator<E> {
	public int compare (E a, E b) throws ClassCastException
	{
		return ((Comparable<E>) a).compareTo(b);
	}
}
