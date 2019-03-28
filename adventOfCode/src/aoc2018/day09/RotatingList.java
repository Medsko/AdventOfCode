package aoc2018.day09;

import java.util.LinkedList;

public class RotatingList<T> {

	private LinkedList<T> list;
	
	public RotatingList() {
		list = new LinkedList<>();
	}
	
	public void add(T element) {
		list.addFirst(element);
	}
	
	public void rotate(int range) {
		if (range < 0) {
			while (range++ < 0) {
				list.addLast(list.removeFirst());
			}
		} else {
			while (range-- > 0) {
				list.addFirst(list.removeLast());
			}
		}
	}
	
	public T removeAtCursor() {
		return list.removeFirst();
	}
	
	@Override
	public String toString() {
		String toString = "";
		for (T t : list) {
			toString += t + " ";
		}
		return toString;
	}
}
