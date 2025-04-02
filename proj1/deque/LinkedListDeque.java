package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;

        private IntNode(T i, IntNode n, IntNode p) {
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

    @Override
    public void addFirst(T item) {
        IntNode node = new IntNode(item, sentinel.next, sentinel);
        sentinel.next.prev = node;
        sentinel.next = node;
        size++;
    }

    @Override
    public void addLast(T item) {
        IntNode node = new IntNode(item, sentinel, sentinel.prev);
        sentinel.prev.next = node;
        sentinel.prev = node;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        IntNode node = sentinel.next;
        while (node != sentinel) {
            System.out.println(node.item);
            node = node.next;
        }
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        T removedItem = sentinel.next.item;

        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;

        size--;

        return removedItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T removedItem = sentinel.prev.item;

        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;

        size--;

        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        IntNode node = sentinel.next;
        while (index > 0) {
            node = node.next;
            index--;
        }

        return node.item;
    }

    private T getRecursiveInternal(IntNode node, int index) {
        if (index == 0) {
            return node.item;
        }
        node = node.next;
        return getRecursiveInternal(node, index - 1);
    }

    public T getRecursive(int index) {
        IntNode node = sentinel.next;

        return getRecursiveInternal(node, index);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private IntNode current;

        private LinkedListDequeIterator() {
            current = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = current.item;
            current = current.next;
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> other = (Deque<T>) o;

        if (this.size() != other.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }

        return true;
    }
}
