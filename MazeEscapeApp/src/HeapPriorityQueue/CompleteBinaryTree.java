package HeapPriorityQueue;
import java.util.*;

public class CompleteBinaryTree<E> {
	protected ArrayList<BTPos<E>> T;
	
	public CompleteBinaryTree()
	{
		T=new ArrayList<BTPos<E>>();
		T.add(0, null);
	}
	
	public int size()
	{
		return T.size()-1;
	}
	
	public boolean isEmpty()
	{
		return (size() == 0);
	}
	
	public boolean isInternal(Position<E> v) throws InvalidPositionException
	{
		return hasLeft(v);
	}
	
	public boolean isExternal(Position<E> v) throws InvalidPositionException
	{
		return !isInternal(v);
	}
	
	public boolean isRoot(Position<E> v) throws InvalidPositionException
	{
		BTPos<E> vv = checkPosition(v);
		return vv.index()==1;
	}
	
	public boolean hasLeft(Position<E> v) throws InvalidPositionException
	{
		BTPos<E> vv = checkPosition(v);
		return 2*vv.index() <= size();
	}
	
	public boolean hasRight(Position<E> v) throws InvalidPositionException
	{
		BTPos<E> vv = checkPosition(v);
		return 2*vv.index()+1 <= size();
	}
	
	public Position<E> root() throws EmptyTreeException
	{
		if (isEmpty())
			throw new EmptyTreeException("Tree is Empty");
		
		return T.get(1);
	}
	
	public Position<E> left (Position<E> v) throws InvalidPositionException, BoundaryViolationException
	{
		if(!hasLeft(v))
			throw new BoundaryViolationException("No Left Child");
		
		BTPos<E> vv = checkPosition(v);
		return T.get(2*vv.index());
	}
	
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException
	{
		if(!hasRight(v))
			throw new BoundaryViolationException("No Right Child");
		BTPos<E> vv = checkPosition(v);
		return T.get(2*vv.index()+1);
	}
	
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException
	{
		if(isRoot(v))
			throw new BoundaryViolationException("No Parent");
		BTPos<E> vv = checkPosition(v);
		return T.get(vv.index/2);
	}
	
	public E replace(Position<E> v, E o) throws InvalidPositionException
	{
		BTPos<E> vv = checkPosition(v);
		return vv.setElement(o);
	}
	
	public Position<E> add(E e)
	{
		int i=size()+1;
		BTPos<E> p = new BTPos<E>(e,i);
		T.add(i, p);
		return p;
	}
	
	public E remove() throws EmptyTreeException
	{
		if (isEmpty()) throw new EmptyTreeException("Tree is empty");
		return T.remove(size()).element();
	}
	
	protected BTPos<E> checkPosition(Position<E> v) throws InvalidPositionException
	{
		if (v == null || !(v instanceof BTPos))
			throw new InvalidPositionException("Position is Invalid");
		return (BTPos<E>)v;
	}
	
	public Iterator<E> iterator()
	{
		ArrayList<E> list = new ArrayList<E>();
		Iterator<BTPos<E>> iter = T.iterator();
		iter.next();
		while (iter.hasNext())
			list.add(iter.next().element());
		return list.iterator();
	}
	
	protected E remove(int index)
	{
		if(isEmpty()) throw new EmptyTreeException("Tree is Empty");
		return T.remove(size()).element;
	}
	

}
