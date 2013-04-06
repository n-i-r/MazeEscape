package HeapPriorityQueue;

public class BTPos<E> implements Position<E>{
	E element;
	int index;
	
	public BTPos(E elmt, int i)
	{
		element=elmt;
		index=i;
	}
	
	public E element()
	{
		return element;
	}
	
	public int index()
	{
		return index;
	}
	
	public E setElement(E elmt)
	{
		E temp=element;
		element=elmt;
		return temp;
	}
}
