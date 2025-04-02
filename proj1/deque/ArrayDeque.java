package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private final int arrayInitSize = 8;
    private int arraySize;

    public ArrayDeque() {
        items = (T[]) new Object[arrayInitSize];
        arraySize = arrayInitSize;
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private void resizeLarger() {
        int newArraySize = arraySize * 2;
        T[] newArray = (T[]) new Object[newArraySize];
        int p = (nextFirst + 1) % arraySize;
        for (int i = 0; i < size; i++) {
            newArray[i] = items[p];
            p = (p + 1) % arraySize;
        }
        nextFirst = newArraySize - 1;
        nextLast = size;
        items = newArray;
        arraySize = newArraySize;
    }

    private void resizeSmaller() {
        int newArraySize = arraySize / 2;
        T[] newArray = (T[]) new Object[newArraySize];
        int p = (nextFirst + 1) % arraySize;
        for (int i = 0; i < size; i++) {
            newArray[i] = items[p];
            p = (p + 1) % arraySize;
        }
        nextFirst = newArraySize - 1;
        nextLast = size;
        items = newArray;
        arraySize = newArraySize;
    }


    @Override
    public void addFirst(T item) {
        if (size == arraySize) {
            resizeLarger();
        }

        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + arraySize) % arraySize;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == arraySize) {
            resizeLarger();
        }

        items[nextLast] = item;
        nextLast = (nextLast + 1) % arraySize;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int p = (nextFirst + 1) % arraySize;
        for (int i = 0; i < size; i++) {
            System.out.println(items[p]);
            p = (p + 1) % arraySize;
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        nextFirst = (nextFirst + 1) % arraySize;
        T removeItem = items[nextFirst];
        items[nextFirst] = null;

        size--;

        if (arraySize > arrayInitSize && size < arraySize / 4) {
            resizeSmaller();
        }

        return removeItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        nextLast = (nextLast - 1 + arraySize) % arraySize;
        T removeItem = items[nextLast];
        items[nextLast] = null;

        size--;

        if (arraySize > arrayInitSize && size < arraySize / 4) {
            resizeSmaller();
        }

        return removeItem;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }

        // Calculate the actual position in the circular array
        int actualIndex = (nextFirst + 1 + index) % arraySize;
        return items[actualIndex];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int position;

        private ArrayDequeIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = get(position);
            position++;
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
