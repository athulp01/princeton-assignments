import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private class Node<Item>{
        private Node<Item> next = null;
        private Node<Item> prev = null;
        private Item value;
    }

    public Deque(){
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() { return (size == 0); }

    public int size() { return size; }

    public void addFirst( Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node<Item> newFirst = new Node<>();
        newFirst.value = item;
        if(!isEmpty())                                         //Checks if empty
            first.prev = newFirst;
        newFirst.next = first;
        first = newFirst;
        size++;
        if(size() == 1) last = first;
    }

    public void addLast( Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node<Item> newLast = new Node<>();
        newLast.value = item;
        if(!isEmpty())
            last.next = newLast;
        newLast.prev = last;
        size++;
        last = newLast;
        if(size() == 1) first = last;
    }

    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException();
        Item value = first.value;
        first = first.next;
        if(first != null)
            first.prev = null;
        size--;
        return value;

    }

    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException();
        Item value = last.value;
        last = last.prev;
        if(last != null)
            last.next = null;
        size--;
        return value;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(current == null) throw new NoSuchElementException();
            Item item = current.value;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.size();
        deque.size();
        deque.addLast(3);
        deque.isEmpty();
        //deque.removeFirst();
        deque.addFirst(6);
        //deque.removeFirst();
        deque.addLast(8);
        //deque.removeFirst();
        deque.addFirst(10);
        //StdOut.println(deque.removeLast());
        for(int i : deque) {
            StdOut.println(i);
            for(int j : deque)
                StdOut.println(j);
        }

    }
}
