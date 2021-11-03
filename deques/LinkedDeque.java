package deques;

public class LinkedDeque<T> implements Deque<T> {
    private int size;
    private Node first;
    private Node last;

    public LinkedDeque() {
        this.first = new Node(null, null, null);
        this.last = new Node(null, first, null);
        first.next = last;
        size = 0;
    }

    private class Node {
        private T value;
        private Node next;
        private Node prev;
        Node(T value) {
            this.value = value;
        }
        Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public void addFirst(T item) {
        Node newNode = new Node(item);
        newNode.next = this.first.next;
        newNode.next.prev = newNode;
        newNode.prev = this.first;
        this.first.next = newNode;
        size += 1;
    }

    public void addLast(T item) {
        Node newNode = new Node(item, last.prev, last);
        newNode.prev.next = newNode;
        last.prev = newNode;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T top = this.first.next.value;
        this.first.next = this.first.next.next;
        this.first.next.prev = this.first;
        size -= 1;
        return top;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T back = this.last.prev.value;
        this.last.prev = this.last.prev.prev;
        this.last.prev.next = this.last;
        size -= 1;
        return back;
    }

    public T get(int index) {
        if ((index > size) || (index < 0)) {
            return null;
        }
        Node cur = this.first.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        T result = cur.value;
        return result;
    }

    public int size() {
        return size;
    }
}
