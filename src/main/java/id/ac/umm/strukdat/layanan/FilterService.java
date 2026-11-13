
package id.ac.umm.strukdat.layanan;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

import java.util.function.Predicate;

public class FilterService {


    public SinglyLinkedList<Putusan> filter(
            SinglyLinkedList<Putusan> sumber,
            Predicate<Putusan> kondisi) {

        SinglyLinkedList<Putusan> hasil = new SinglyLinkedList<>();

        for (int i = 0; i < sumber.size(); i++) {
            Putusan p = sumber.get(i);

            if (kondisi.test(p)) {
                hasil.add(p);
            }
        }

        return hasil;
    }


    public Predicate<Putusan> jenis(String kata) {
        return p -> p.getJenisNarkotika() != null
                && p.getJenisNarkotika().toLowerCase()
                .contains(kata.toLowerCase());
    }


    public Predicate<Putusan> rentangBerat(double min, double max) {
        return p -> p.getBeratBarangBuktiGram() >= min
                && p.getBeratBarangBuktiGram() <= max;
    }


    public Predicate<Putusan> pasalTerbukti(String kata) {
        return p -> p.getPasalTerbukti() != null
                && p.getPasalTerbukti().toLowerCase()
                .contains(kata.toLowerCase());
    }


    public Predicate<Putusan> rentangVonis(int min, int max) {
        return p -> p.getVonisBulan() >= min
                && p.getVonisBulan() <= max;
    }
}
