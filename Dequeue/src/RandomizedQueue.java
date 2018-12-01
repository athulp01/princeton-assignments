import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private int last;
    private Item[] queue;
    private int nulled;

    public RandomizedQueue() {
        size = 0;
        last = -1;
        queue = (Item[]) new Object[2];
        nulled = 0;
    }

    public boolean isEmpty() { return (size == 0);}

    public int size() { return size; }

    public void enqueue(Item item) {
        if(item == null) throw new IllegalArgumentException();
        if(last == queue.length-1) {
            Item[] arr = (Item[]) new Object[2*queue.length];
            for(int i =0;i<queue.length;i++)
                arr[i] = queue[i];
            queue = arr;
        }
        size++;
        queue[++last] = item;
    }

    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        if(size == queue.length/4) {
            Item[] arr = (Item[]) new Object[size/2];
            int j = 0;
            for(Item i : queue) {
                if(i!=null) {
                    arr[j] = i;
                    j++;
                }
            }
            last = j;
            queue = arr;
        }
        int idx = 0;
        if(size() > 1)
        do{
            idx = StdRandom.uniform(0,last);
        }while(queue[idx]==null);

        Item re = queue[idx];
        queue[idx] = null;
        nulled++;
        size--;
        return re;
    }

    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        int idx=0;
        if(size() > 1)
        do {
            idx = StdRandom.uniform(0,last);
        }while(queue[idx]==null);
        return queue[idx];
    }

    public Iterator<Item> iterator() {
        return new RqIterator();
    }

    private class RqIterator implements Iterator<Item> {
        Item[] arr;
        int current;

        public RqIterator() {
            arr = (Item[]) new Object[size];
            int j = 0;
            for(Item i : queue) {
                if(i!=null) {
                    arr[j] = i;
                    j++;
                }
            }
            StdRandom.shuffle(arr);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return (current < arr.length);
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            return arr[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String args[]) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.dequeue();
        rq.dequeue();
        rq.enqueue(161);
    }
}
