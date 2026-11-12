package id.ac.umm.strukdat.struktur;

import java.util.Objects;
import java.util.function.Predicate;

public class SinglyLinkedList<T> implements StrukturData<T> {

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