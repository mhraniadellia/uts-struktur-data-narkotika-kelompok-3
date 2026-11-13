
package id.ac.umm.strukdat.layanan;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

public class UrutanService {


    public SinglyLinkedList<Putusan> urutkanVonis(
            SinglyLinkedList<Putusan> sumber, boolean naik) {

        SinglyLinkedList<Putusan> hasil = new SinglyLinkedList<>();

        for (int i = 0; i < sumber.size(); i++) {
            Putusan p = sumber.get(i);


            int posisi;

            if (naik) {
                posisi = hasil.search(
                        x -> x.getVonisBulan() > p.getVonisBulan()
                );
            } else {
                posisi = hasil.search(
                        x -> x.getVonisBulan() < p.getVonisBulan()
                );
            }


            if (posisi == -1) {
                hasil.add(p);
            } else {
                hasil.insert(posisi, p);
            }
        }

        return hasil;
    }
}
