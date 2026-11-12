
package id.ac.umm.strukdat;

import id.ac.umm.strukdat.io.CsvImporter;
import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

import java.io.IOException;
import java.util.Scanner;

public class App {

    private static final String FILE_DATA = "data/putusan-50.csv";

    private final Scanner input = new Scanner(System.in);
    private SinglyLinkedList<Putusan> daftar = new SinglyLinkedList<>();

    public static void main(String[] args) {
        new App().jalankan();
    }

    private void jalankan() {
        boolean lanjut = true;

        while (lanjut) {
            System.out.println();
            System.out.println("=== Data Putusan Narkotika PN Bandung ===");
            System.out.println("1. Impor data");
            System.out.println("2. Tampil semua putusan");
            System.out.println("3. Tambah putusan");
            System.out.println("4. Hapus putusan");
            System.out.println("5. Cari putusan");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            String pilihan = input.nextLine().trim();

            switch (pilihan) {
                case "1" -> menuImpor();
                case "2" -> menuTampil();
                case "3" -> menuTambah();
                case "4" -> menuHapus();
                case "5" -> menuCari();
                case "0" -> lanjut = false;
                default -> System.out.println("Pilihan tidak dikenal.");
            }
        }

        System.out.println("Program selesai.");
    }

    private void menuImpor() {
        CsvImporter importer = new CsvImporter();

        try {
            daftar = importer.impor(FILE_DATA);

            System.out.println("\nImpor data selesai.");
            System.out.println("Jumlah putusan berhasil diimpor: "
                    + daftar.size());

            if (!importer.getErrors().isEmpty()) {
                System.out.println("Jumlah data bermasalah: "
                        + importer.getErrors().size());

                for (int i = 0; i < importer.getErrors().size(); i++) {
                    System.out.println(importer.getErrors().get(i));
                }
            } else {
                System.out.println("Tidak ada kesalahan parsing.");
            }

            System.out.println("Validasi struktur: "
                    + daftar.validateInvariant());

        } catch (IOException e) {
            System.out.println("Gagal membaca file CSV: "
                    + e.getMessage());
        }
    }

    // Menu 2: Tampilkan semua putusan
    private void menuTampil() {
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data putusan. Silakan impor data terlebih dahulu.");
            return;
        }

        System.out.println("\n=== DAFTAR PUTUSAN ===");

        for (int i = 0; i < daftar.size(); i++) {
            Putusan p = daftar.get(i);

            System.out.println(
                    i + ". " +
                            p.getCaseId() + " | " +
                            p.getNomorPerkara() + " | " +
                            p.getJenisNarkotika() + " | " +
                            "Vonis " + p.getVonisBulan() + " bulan"
            );
        }

        System.out.println("--------------------------------------");
        System.out.println("Total putusan: " + daftar.size());
    }

    private void menuTambah() {
        System.out.println("(belum dibuat)");
    }

    private void menuHapus() {
        if (daftar.isEmpty()) {
            System.out.println("Tidak ada data yang dapat dihapus.");
            return;
        }

        menuTampil();

        System.out.print("\nMasukkan posisi putusan yang ingin dihapus: ");
        String teks = input.nextLine().trim();

        try {
            int posisi = Integer.parseInt(teks);

            Putusan terhapus = daftar.remove(posisi);

            System.out.println("Putusan berhasil dihapus.");
            System.out.println("Nomor perkara: "
                    + terhapus.getNomorPerkara());

            System.out.println("Validasi struktur: "
                    + daftar.validateInvariant());

        } catch (NumberFormatException e) {
            System.out.println("Posisi harus berupa angka.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Posisi tidak valid: " + e.getMessage());
        }
    }

    private void menuCari() {
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data untuk dicari.");
            return;
        }

        System.out.print("Masukkan nomor perkara atau caseId: ");
        String kunci = input.nextLine().trim();

        int posisi = daftar.search(p ->
                p.getNomorPerkara().equalsIgnoreCase(kunci)
                        || p.getCaseId().equalsIgnoreCase(kunci)
        );

        if (posisi >= 0) {
            Putusan p = daftar.get(posisi);

            System.out.println("\n=== PUTUSAN DITEMUKAN ===");
            System.out.println("Posisi: " + posisi);

            cetakDetail(p);

        } else {
            System.out.println("Putusan tidak ditemukan.");
        }

        System.out.println("Jumlah langkah pencarian: "
                + daftar.getLangkahTerakhir());
    }

    private void cetakDetail(Putusan p) {
        System.out.println("Case ID             : " + p.getCaseId());
        System.out.println("Nomor Perkara       : " + p.getNomorPerkara());
        System.out.println("Pengadilan          : " + p.getPengadilan());
        System.out.println("Tanggal Putusan     : " + p.getTanggalPutusan());
        System.out.println("Terdakwa Kode       : " + p.getTerdakwaKode());
        System.out.println("Jenis Narkotika     : " + p.getJenisNarkotika());
        System.out.println("Berat Barang Bukti  : "
                + p.getBeratBarangBuktiGram() + " gram");
        System.out.println("Pasal Dakwaan       : " + p.getPasalDakwaan());
        System.out.println("Pasal Terbukti      : " + p.getPasalTerbukti());
        System.out.println("Tuntutan            : " + p.getTuntutanBulan() + " bulan");
        System.out.println("Vonis               : " + p.getVonisBulan() + " bulan");
        System.out.println("Denda               : Rp" + p.getDendaRupiah());
        System.out.println("Barang Bukti        : " + p.getBarangBukti());
        System.out.println("Keadaan Memberatkan : " + p.getKeadaanMemberatkan());
        System.out.println("Keadaan Meringankan : " + p.getKeadaanMeringankan());
        System.out.println("Source Pages        : " + p.getSourcePages());
    }
}
