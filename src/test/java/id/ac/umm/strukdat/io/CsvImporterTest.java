
package id.ac.umm.strukdat.io;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CsvImporterTest {

    private static final String HEADER =
            "caseId;nomorPerkara;pengadilan;tanggalPutusan;terdakwaKode;jenisNarkotika;"
                    + "beratBarangBuktiGram;pasalDakwaan;pasalTerbukti;tuntutanBulan;vonisBulan;"
                    + "dendaRupiah;barangBukti;keadaanMemberatkan;keadaanMeringankan;sourcePages";

    @Test
    void memuat50Record() throws Exception {
        CsvImporter importer = new CsvImporter();

        SinglyLinkedList<Putusan> hasil =
                importer.impor("data/putusan-50.csv");

        assertEquals(50, hasil.size());
        assertTrue(importer.getErrors().isEmpty());
        assertTrue(hasil.validateInvariant());
    }

    @Test
    void recordTidakValidDitolak(@TempDir Path folder) throws Exception {

        String valid1 = "P901;1/Pid.Sus/2026/PN Bdg;PN Bandung;2026-01-01;T901;Metamfetamina;"
                + "1.5;114(1) UU 35/2009;114(1) UU 35/2009;60;48;100000000;1 paket sabu;-;-;identitas=1";

        String valid2 = valid1.replace("P901", "P902")
                .replace("T901", "T902");

        String vonisBukanAngka = valid1.replace("P901", "P903")
                .replace(";48;", ";abc;");

        String kolomKurang =
                "P904;2/Pid.Sus/2026/PN Bdg;PN Bandung";

        Path file = folder.resolve("uji.csv");

        Files.writeString(
                file,
                String.join("\n", HEADER, valid1, valid2,
                        vonisBukanAngka, kolomKurang),
                StandardCharsets.UTF_8
        );

        CsvImporter importer = new CsvImporter();

        SinglyLinkedList<Putusan> hasil =
                importer.impor(file.toString());

        assertEquals(2, hasil.size());
        assertEquals(2, importer.getErrors().size());
        assertTrue(hasil.validateInvariant());
    }
}
