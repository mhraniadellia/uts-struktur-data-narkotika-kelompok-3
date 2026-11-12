package id.ac.umm.strukdat.struktur;

import java.util.Objects;
import java.util.function.Predicate;

public class SinglyLinkedList<T> implements StrukturData<T> {

    @Override
    public void add(T value) {

    }

    @Override
    public T get(int position) {
        return null;
    }

    @Override
    public T remove(int position) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(T value) {
        return false;
    }

    @Override
    public void clear() {

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

}