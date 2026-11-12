package id.ac.umm.strukdat.struktur;

import java.util.Objects;
import java.util.function.Predicate;

public class SinglyLinkedList<T> implements StrukturData<T> {

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public T get(int position) {
        cekIndex(position, size - 1);

        Node<T> cur = head;

        for (int i = 0; i < position; i++) {
            cur = cur.next;
        }

        return cur.data;
    }

    @Override
    public T remove(int position) {
        cekIndex(position, size - 1);

        if (position == 0) {
            T data = head.data;

            head = head.next;
            size--;

            if (size == 0) {
                tail = null;
            }

            return data;
        }

        Node<T> prev = head;

        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }

        Node<T> target = prev.next;

        prev.next = target.next;

        if (target == tail) {
            tail = prev;
        }

        size--;

        return target.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T value) {
        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int langkahTerakhir;

    public void addFirst(T value) {
        Node<T> baru = new Node<>(value);
        baru.next = head;
        head = baru;
        if (size == 0) {
            tail = baru;
        }
        size++;
    }

    public void addLast(T value) {
        Node<T> baru = new Node<>(value);
        if (size == 0) {
            head = baru;
            tail = baru;
        } else {
            tail.next = baru;
            tail = baru;
        }
        size++;
    }

    public void insert(int position, T value) {
        cekIndex(position, size);

        if (position == 0) {
            addFirst(value);
            return;
        }

        if (position == size) {
            addLast(value);
            return;
        }

        Node<T> prev = head;

        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }

        Node<T> baru = new Node<>(value);

        baru.next = prev.next;
        prev.next = baru;

        size++;
    }

    private void cekIndex(int position, int batasAtas) {
        if (position < 0 || position > batasAtas) {
            throw new IndexOutOfBoundsException(
                    "Posisi " + position + " di luar rentang 0.." + batasAtas);
        }
    }
}