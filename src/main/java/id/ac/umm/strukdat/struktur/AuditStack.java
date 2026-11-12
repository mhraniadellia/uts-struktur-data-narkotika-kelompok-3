package id.ac.umm.strukdat.struktur;

import java.util.EmptyStackException;
import java.util.function.Predicate;

public class AuditStack<T> {

    private static final int KAPASITAS_AWAL = 4;

    private Object[] elemen = new Object[KAPASITAS_AWAL];
    private int top = 0;
    private int langkahTerakhir;

    public int size() {
        return top;
    }

    public boolean isEmpty() {
        return top == 0;
    }


}