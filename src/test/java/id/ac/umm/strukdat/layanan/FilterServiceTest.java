
package id.ac.umm.strukdat.layanan;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilterServiceTest {

    private SinglyLinkedList<Putusan> daftar;
    private FilterService fs;

    private Putusan buat(String id, String jenis, double berat,
                         String pasal, int vonis, long denda) {
        return new Putusan(
                id, "1/Pid.Sus/2026/PN Bdg", "PN Bandung",
                LocalDate.of(2026, 1, 1),
                "T" + id.substring(1), jenis, berat,
                pasal, pasal, 60, vonis, denda,
                "-", "-", "-", "identitas=1"
        );
    }

    @BeforeEach
    void setUp() {
        fs = new FilterService();
        daftar = new SinglyLinkedList<>();

        daftar.add(buat("P901", "Metamfetamina", 1.5,
                "114(1) UU 35/2009", 60, 200000000L));
        daftar.add(buat("P902", "Metamfetamina/MDMA", 30.0,
                "114(2) UU 35/2009", 90, 300000000L));
        daftar.add(buat("P903", "MDMB-4en PINACA", 9.5,
                "609(1) huruf a KUHP", 54, 200000000L));
        daftar.add(buat("P904", "Ganja", 0.5,
                "111(1) UU 35/2009", 48, 100000000L));
        daftar.add(buat("P905", "Metamfetamina", 8.0,
                "114(2) jo 132(1) UU 35/2009", 84, 300000000L));
    }

    @Test
    void filterJenisMetamfetamina() {
        SinglyLinkedList<Putusan> hasil =
                fs.filter(daftar, fs.jenis("Metamfetamina"));

        assertEquals(3, hasil.size());
        assertEquals("P901", hasil.get(0).getCaseId());
        assertEquals("P902", hasil.get(1).getCaseId());
        assertEquals("P905", hasil.get(2).getCaseId());
    }

    @Test
    void filterJenisMDMAIkutKombinasi() {
        SinglyLinkedList<Putusan> hasil =
                fs.filter(daftar, fs.jenis("MDMA"));

        assertEquals(1, hasil.size());
        assertEquals("P902", hasil.get(0).getCaseId());
    }

    @Test
    void filterRentangBerat() {
        SinglyLinkedList<Putusan> hasil =
                fs.filter(daftar, fs.rentangBerat(1, 10));

        assertEquals(3, hasil.size());
        assertEquals("P901", hasil.get(0).getCaseId());
        assertEquals("P903", hasil.get(1).getCaseId());
        assertEquals("P905", hasil.get(2).getCaseId());
    }

    @Test
    void filterPasal114Ayat2() {
        SinglyLinkedList<Putusan> hasil =
                fs.filter(daftar, fs.pasalTerbukti("114(2)"));

        assertEquals(2, hasil.size());
        assertEquals("P902", hasil.get(0).getCaseId());
        assertEquals("P905", hasil.get(1).getCaseId());
    }

    @Test
    void filterRentangVonis() {
        SinglyLinkedList<Putusan> hasil =
                fs.filter(daftar, fs.rentangVonis(54, 84));

        assertEquals(3, hasil.size());
        assertEquals("P901", hasil.get(0).getCaseId());
        assertEquals("P903", hasil.get(1).getCaseId());
        assertEquals("P905", hasil.get(2).getCaseId());
    }

    @Test
    void filterGabungan() {
        SinglyLinkedList<Putusan> hasil =
                fs.filter(daftar,
                        fs.jenis("Metamfetamina")
                                .and(fs.rentangVonis(60, 84)));

        assertEquals(2, hasil.size());
        assertEquals("P901", hasil.get(0).getCaseId());
        assertEquals("P905", hasil.get(1).getCaseId());
        assertTrue(hasil.validateInvariant());
    }

    @Test
    void filterTidakMengubahListUtama() {
        fs.filter(daftar,
                fs.jenis("Metamfetamina")
                        .and(fs.rentangVonis(60, 84)));

        assertEquals(5, daftar.size());
        assertTrue(daftar.validateInvariant());
    }
}
