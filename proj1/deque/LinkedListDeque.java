package deque;

import java.util.Iterator;

public class LinkedListDeque<T> {
    private class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;

        public IntNode(T i, IntNode n, IntNode p) {
            item = i;
            next = n;
	        prev = p;
        }
    }

    private IntNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        IntNode node = new IntNode(item, sentinel.next, sentinel);
        sentinel.next.prev = node;
        sentinel.next = node;
        size = size + 1;
    }
    public void addLast(T item) {

    }
    public boolean isEmpty() {}
    public int size() {}

    public void printDeque() {}
    public T removeFirst() {}
    public T removeLast() {}
    public T get(int index) {}
    public T getRecursive(int index) {}
    public Iterator<T> iterator() {}
    public boolean equals(Object o) {
        return true;
    }
}
