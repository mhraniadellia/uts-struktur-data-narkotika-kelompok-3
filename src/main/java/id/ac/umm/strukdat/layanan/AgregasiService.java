
package id.ac.umm.strukdat.layanan;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

public class AgregasiService {


    public int jumlahPerkara(SinglyLinkedList<Putusan> list) {
        return list.size();
    }


    public double rataRataVonis(SinglyLinkedList<Putusan> list) {

        if (list.isEmpty()) {
            return 0.0;
        }

        double total = 0;

        for (int i = 0; i < list.size(); i++) {
            Putusan p = list.get(i);
            total += p.getVonisBulan();
        }

        return total / list.size();
    }


    public long totalDenda(SinglyLinkedList<Putusan> list) {

        long total = 0;

        for (int i = 0; i < list.size(); i++) {
            Putusan p = list.get(i);
            total += p.getDendaRupiah();
        }

        return total;
    }


    public SinglyLinkedList<JumlahJenis> distribusiJenis(
            SinglyLinkedList<Putusan> list) {

        SinglyLinkedList<JumlahJenis> hasil =
                new SinglyLinkedList<>();

        for (int i = 0; i < list.size(); i++) {

            Putusan p = list.get(i);

            String jenisNarkotika = p.getJenisNarkotika();


            if (jenisNarkotika == null || jenisNarkotika.trim().isEmpty()) {
                continue;
            }


            String[] jenisArray = jenisNarkotika.split("/");

            for (String zatMentah : jenisArray) {


                String zat = zatMentah.trim();

                if (zat.isEmpty()) {
                    continue;
                }


                int posisi = hasil.search(
                        j -> j.getJenis().equalsIgnoreCase(zat)
                );

                if (posisi >= 0) {


                    hasil.get(posisi).tambah();

                } else {


                    hasil.add(new JumlahJenis(zat));
                }
            }
        }

        return hasil;
    }


    public static class JumlahJenis {

        private final String jenis;
        private int jumlah;

        public JumlahJenis(String jenis) {
            this.jenis = jenis;
            this.jumlah = 1;
        }

        public String getJenis() {
            return jenis;
        }

        public int getJumlah() {
            return jumlah;
        }

        public void tambah() {
            jumlah++;
        }

        @Override
        public String toString() {
            return jenis + ": " + jumlah;
        }
    }
}
