
package id.ac.umm.strukdat;

import id.ac.umm.strukdat.io.CsvImporter;
import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;
import id.ac.umm.strukdat.layanan.FilterService;
import id.ac.umm.strukdat.layanan.AgregasiService;
import java.util.function.Predicate;
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
            System.out.println("6. Filter putusan");
            System.out.println("7. Agregasi");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            String pilihan = input.nextLine().trim();

            switch (pilihan) {
                case "1" -> menuImpor();
                case "2" -> menuTampil();
                case "3" -> menuTambah();
                case "4" -> menuHapus();
                case "5" -> menuCari();
                case "6" -> menuFilter();
                case "7" -> menuAgregasi();
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
        System.out.println("\n=== TAMBAH PUTUSAN ===");
        System.out.println("Tempel satu baris CSV (16 kolom, pemisah ;):");

        String teks = input.nextLine().trim();

        try {
            Putusan p = new CsvImporter().parseBaris(teks);

            if (daftar.search(x ->
                    x.getCaseId().equalsIgnoreCase(p.getCaseId())) >= 0) {

                System.out.println("caseId " + p.getCaseId()
                        + " sudah ada, tidak ditambahkan.");
                return;
            }

            System.out.print("Posisi sisip (kosongkan untuk tambah di akhir, 0.."
                    + daftar.size() + "): ");

            String teksPosisi = input.nextLine().trim();

            if (teksPosisi.isEmpty()) {
                daftar.add(p);
            } else {
                int posisi = Integer.parseInt(teksPosisi);
                daftar.insert(posisi, p);
            }

            System.out.println("Putusan " + p.getCaseId()
                    + " berhasil ditambahkan.");

            System.out.println("Jumlah data sekarang: " + daftar.size());

            System.out.println("Validasi struktur: "
                    + daftar.validateInvariant());

        } catch (NumberFormatException e) {
            System.out.println("Posisi harus berupa angka.");

        } catch (IllegalArgumentException e) {
            System.out.println("Data tidak valid: " + e.getMessage());

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Posisi tidak valid: " + e.getMessage());
        }
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

    private void menuFilter() {
        if (daftar.isEmpty()) {
            System.out.println("Data putusan masih kosong.");
            return;
        }

        FilterService fs = new FilterService();
        Predicate<Putusan> kondisi = p -> true;

        System.out.println("\n=== MENU FILTER PUTUSAN ===");

        try {
            System.out.print("Jenis narkotika (kosongkan jika tidak difilter): ");
            String jenis = input.nextLine().trim();

            if (!jenis.isEmpty()) {
                kondisi = kondisi.and(fs.jenis(jenis));
            }

            System.out.print("Berat minimum (gram): ");
            String minBerat = input.nextLine().trim();

            System.out.print("Berat maksimum (gram): ");
            String maxBerat = input.nextLine().trim();

            if (!minBerat.isEmpty() && !maxBerat.isEmpty()) {
                double min = Double.parseDouble(minBerat);
                double max = Double.parseDouble(maxBerat);

                if (min > max) {
                    System.out.println("Peringatan: berat minimum tidak boleh lebih besar dari maksimum.");
                    return;
                }

                kondisi = kondisi.and(fs.rentangBerat(min, max));
            } else if (!minBerat.isEmpty() || !maxBerat.isEmpty()) {
                System.out.println("Rentang berat diabaikan karena min/max tidak lengkap.");
            }

            System.out.print("Pasal terbukti (kosongkan jika tidak difilter): ");
            String pasal = input.nextLine().trim();

            if (!pasal.isEmpty()) {
                kondisi = kondisi.and(fs.pasalTerbukti(pasal));
            }

            System.out.print("Vonis minimum (bulan): ");
            String minVonis = input.nextLine().trim();

            System.out.print("Vonis maksimum (bulan): ");
            String maxVonis = input.nextLine().trim();

            if (!minVonis.isEmpty() && !maxVonis.isEmpty()) {
                int min = Integer.parseInt(minVonis);
                int max = Integer.parseInt(maxVonis);

                if (min > max) {
                    System.out.println("Peringatan: vonis minimum tidak boleh lebih besar dari maksimum.");
                    return;
                }

                kondisi = kondisi.and(fs.rentangVonis(min, max));
            } else if (!minVonis.isEmpty() || !maxVonis.isEmpty()) {
                System.out.println("Rentang vonis diabaikan karena min/max tidak lengkap.");
            }

            SinglyLinkedList<Putusan> hasil = fs.filter(daftar, kondisi);

            System.out.println("\n=== HASIL FILTER ===");

            if (hasil.isEmpty()) {
                System.out.println("Tidak ada putusan yang sesuai dengan kriteria.");
            } else {
                for (int i = 0; i < hasil.size(); i++) {
                    Putusan p = hasil.get(i);

                    System.out.println((i + 1) + ". " + p.getCaseId()
                            + " | " + p.getNomorPerkara()
                            + " | " + p.getJenisNarkotika()
                            + " | " + p.getBeratBarangBuktiGram() + " gram"
                            + " | " + p.getPasalTerbukti()
                            + " | " + p.getVonisBulan() + " bulan");
                }
            }

            System.out.println("Jumlah hasil filter: " + hasil.size());

        } catch (NumberFormatException e) {
            System.out.println("Berat dan vonis harus berupa angka.");
        }
    }

    private void menuAgregasi() {
        if (daftar.isEmpty()) {
            System.out.println("Data putusan masih kosong.");
            return;
        }

        AgregasiService ag = new AgregasiService();

        System.out.println("\n=== MENU AGREGASI PUTUSAN ===");

        System.out.println("Jumlah perkara       : "
                + ag.jumlahPerkara(daftar));

        System.out.printf("Rata-rata vonis      : %.2f bulan%n",
                ag.rataRataVonis(daftar));

        System.out.println("Total denda          : Rp"
                + ag.totalDenda(daftar));

        System.out.println("\nDistribusi jenis narkotika:");

        SinglyLinkedList<AgregasiService.JumlahJenis> distribusi =
                ag.distribusiJenis(daftar);

        if (distribusi.isEmpty()) {
            System.out.println("Tidak ada data distribusi jenis narkotika.");
        } else {
            for (int i = 0; i < distribusi.size(); i++) {
                System.out.println((i + 1) + ". " + distribusi.get(i));
            }
        }
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
