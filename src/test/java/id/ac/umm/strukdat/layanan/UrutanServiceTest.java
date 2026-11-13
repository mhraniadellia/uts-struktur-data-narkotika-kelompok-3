
package id.ac.umm.strukdat.layanan;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UrutanServiceTest {

    private SinglyLinkedList<Putusan> daftar;
    private UrutanService us;

    // Helper untuk membuat data putusan
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
        us = new UrutanService();
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
    void urutanNaik() {
        SinglyLinkedList<Putusan> hasil =
                us.urutkanVonis(daftar, true);

        assertEquals(5, hasil.size());

        assertEquals("P904", hasil.get(0).getCaseId());
        assertEquals("P903", hasil.get(1).getCaseId());
        assertEquals("P901", hasil.get(2).getCaseId());
        assertEquals("P905", hasil.get(3).getCaseId());
        assertEquals("P902", hasil.get(4).getCaseId());

        assertTrue(hasil.validateInvariant());
    }

    @Test
    void urutanTurun() {
        SinglyLinkedList<Putusan> hasil =
                us.urutkanVonis(daftar, false);

        assertEquals(5, hasil.size());

        assertEquals("P902", hasil.get(0).getCaseId());
        assertEquals("P905", hasil.get(1).getCaseId());
        assertEquals("P901", hasil.get(2).getCaseId());
        assertEquals("P903", hasil.get(3).getCaseId());
        assertEquals("P904", hasil.get(4).getCaseId());

        assertTrue(hasil.validateInvariant());
    }

    @Test
    void listUtamaTidakBerubah() {
        SinglyLinkedList<Putusan> hasil =
                us.urutkanVonis(daftar, true);

        assertEquals(5, daftar.size());
        assertEquals("P901", daftar.get(0).getCaseId());

        assertTrue(daftar.validateInvariant());
        assertTrue(hasil.validateInvariant());
    }

    @Test
    void vonisSamaTetapUrutanAsli() {
        daftar.add(buat("P906", "Metamfetamina", 2.0,
                "114(1) UU 35/2009", 60, 200000000L));

        SinglyLinkedList<Putusan> hasil =
                us.urutkanVonis(daftar, true);

        int posisiP901 = hasil.search(
                p -> p.getCaseId().equals("P901")
        );

        int posisiP906 = hasil.search(
                p -> p.getCaseId().equals("P906")
        );

        assertTrue(posisiP901 < posisiP906);
        assertEquals(60, hasil.get(posisiP901).getVonisBulan());
        assertEquals(60, hasil.get(posisiP906).getVonisBulan());

        assertTrue(hasil.validateInvariant());
    }

    @Test
    void listKosong() {
        SinglyLinkedList<Putusan> kosong =
                new SinglyLinkedList<>();

        SinglyLinkedList<Putusan> hasil =
                us.urutkanVonis(kosong, true);

        assertTrue(hasil.isEmpty());
        assertTrue(hasil.validateInvariant());
    }
}
