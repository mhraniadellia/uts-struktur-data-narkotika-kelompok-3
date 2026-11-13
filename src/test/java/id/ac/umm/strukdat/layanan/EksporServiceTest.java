
package id.ac.umm.strukdat.layanan;

import id.ac.umm.strukdat.io.CsvImporter;
import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EksporServiceTest {

    @TempDir
    Path tempDir;

    private SinglyLinkedList<Putusan> daftar;
    private EksporService ekspor;

    private Putusan buat(String id, String jenis, double berat,
                         String pasal, int vonis, long denda) {

        return new Putusan(
                id,
                "1/Pid.Sus/2026/PN Bdg",
                "PN Bandung",
                LocalDate.of(2026, 1, 1),
                "T" + id.substring(1),
                jenis,
                berat,
                pasal,
                pasal,
                60,
                vonis,
                denda,
                "-",
                "-",
                "-",
                "identitas=1"
        );
    }

    @BeforeEach
    void setUp() {
        ekspor = new EksporService();
        daftar = new SinglyLinkedList<>();

        daftar.add(buat("P901", "Metamfetamina", 1.5,
                "114(1) UU 35/2009", 60, 200000000L));

        daftar.add(buat("P902", "Metamfetamina/MDMA", 30.0,
                "114(2) UU 35/2009", 90, 300000000L));

        daftar.add(buat("P903", "MDMB-4en PINACA", 9.5,
                "609(1) huruf a KUHP", 54, 200000000L));
    }

    @Test
    void roundTripCsv() throws IOException {
        Path file = tempDir.resolve("hasil.csv");

        ekspor.eksporCsv(daftar, file.toString());

        CsvImporter importer = new CsvImporter();
        SinglyLinkedList<Putusan> hasil =
                importer.impor(file.toString());

        assertEquals(3, hasil.size());
        assertTrue(importer.getErrors().isEmpty());

        assertEquals(
                daftar.get(0).getCaseId(),
                hasil.get(0).getCaseId()
        );

        assertEquals(
                daftar.get(0).getVonisBulan(),
                hasil.get(0).getVonisBulan()
        );

        assertEquals(
                daftar.get(0).getDendaRupiah(),
                hasil.get(0).getDendaRupiah()
        );

        assertEquals(
                1.5,
                hasil.get(0).getBeratBarangBuktiGram(),
                0.0001
        );

        assertTrue(hasil.validateInvariant());
    }

    @Test
    void headerSesuaiSkema() throws IOException {
        Path file = tempDir.resolve("header.csv");

        ekspor.eksporCsv(daftar, file.toString());

        String barisPertama =
                Files.readAllLines(file).get(0);

        assertEquals(
                "caseId;nomorPerkara;pengadilan;tanggalPutusan;terdakwaKode;jenisNarkotika;"
                        + "beratBarangBuktiGram;pasalDakwaan;pasalTerbukti;tuntutanBulan;vonisBulan;"
                        + "dendaRupiah;barangBukti;keadaanMemberatkan;keadaanMeringankan;sourcePages",
                barisPertama
        );
    }

    @Test
    void eksporTeksBerisiRingkasan() throws IOException {
        Path file = tempDir.resolve("laporan.txt");

        ekspor.eksporTeks(daftar, file.toString());

        String isi = Files.readString(file);

        assertTrue(isi.contains("LAPORAN AGREGASI PUTUSAN NARKOTIKA"));
        assertTrue(isi.contains("Jumlah perkara: 3"));
        assertTrue(isi.contains("Rata-rata vonis: 68.00 bulan"));
        assertTrue(isi.contains("Total denda: Rp700000000"));
    }
}
