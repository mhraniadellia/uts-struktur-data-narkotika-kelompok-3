
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


    public void push(T value) {

        if (value == null) {
            throw new IllegalArgumentException(
                    "Nilai tidak boleh null"
            );
        }

        if (top == elemen.length) {
            perbesar();
        }

        elemen[top] = value;
        top++;
    }


    private void perbesar() {

        Object[] baru = new Object[elemen.length * 2];

        for (int i = 0; i < top; i++) {
            baru[i] = elemen[i];
        }

        elemen = baru;
    }


    @SuppressWarnings("unchecked")
    public T peek() {

        if (isEmpty()) {
            throw new EmptyStackException();
        }

        return (T) elemen[top - 1];
    }


    @SuppressWarnings("unchecked")
    public T pop() {

        if (isEmpty()) {
            throw new EmptyStackException();
        }

        T data = (T) elemen[top - 1];

        elemen[top - 1] = null;
        top--;

        return data;
    }


    public void clear() {

        for (int i = 0; i < top; i++) {
            elemen[i] = null;
        }

        top = 0;
    }


    @SuppressWarnings("unchecked")
    public T lihat(int dariAtas) {

        if (dariAtas < 0 || dariAtas >= top) {
            throw new IndexOutOfBoundsException(
                    "Posisi elemen tidak valid"
            );
        }

        return (T) elemen[top - 1 - dariAtas];
    }

    public void restore(AuditStack<T> sementara) {

        while (!sementara.isEmpty()) {
            push(sementara.pop());
        }
    }


    public int searchPreserve(Predicate<T> kondisi) {

        if (kondisi == null) {
            throw new IllegalArgumentException(
                    "Kondisi pencarian tidak boleh null"
            );
        }

        langkahTerakhir = 0;

        AuditStack<T> sementara = new AuditStack<>();
        int posisi = -1;

        while (!isEmpty()) {

            T x = pop();
            langkahTerakhir++;

            sementara.push(x);

            if (kondisi.test(x)) {
                posisi = langkahTerakhir - 1;
                break;
            }
        }

        restore(sementara);

        return posisi;
    }


    public boolean validateInvariant() {

        if (top < 0 || top > elemen.length) {
            return false;
        }


        for (int i = 0; i < top; i++) {
            if (elemen[i] == null) {
                return false;
            }
        }


        for (int i = top; i < elemen.length; i++) {
            if (elemen[i] != null) {
                return false;
            }
        }

        return true;
    }


    public int getLangkahTerakhir() {
        return langkahTerakhir;
    }
}
