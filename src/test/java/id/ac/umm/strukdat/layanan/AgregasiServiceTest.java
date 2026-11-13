
package id.ac.umm.strukdat.layanan;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AgregasiServiceTest {

    private SinglyLinkedList<Putusan> daftar;
    private AgregasiService ag;

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
        ag = new AgregasiService();
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
    void jumlahPerkara() {
        assertEquals(5, ag.jumlahPerkara(daftar));
    }

    @Test
    void rataRataVonis() {
        assertEquals(67.2, ag.rataRataVonis(daftar), 0.001);
    }

    @Test
    void totalDenda() {
        assertEquals(1100000000L, ag.totalDenda(daftar));
    }

    @Test
    void distribusiJenis() {
        SinglyLinkedList<AgregasiService.JumlahJenis> hasil =
                ag.distribusiJenis(daftar);

        assertEquals(4, hasil.size());

        assertEquals("Metamfetamina", hasil.get(0).getJenis());
        assertEquals(3, hasil.get(0).getJumlah());

        assertEquals("MDMA", hasil.get(1).getJenis());
        assertEquals(1, hasil.get(1).getJumlah());

        assertEquals("MDMB-4en PINACA", hasil.get(2).getJenis());
        assertEquals(1, hasil.get(2).getJumlah());

        assertEquals("Ganja", hasil.get(3).getJenis());
        assertEquals(1, hasil.get(3).getJumlah());
    }

    @Test
    void listKosong() {
        SinglyLinkedList<Putusan> kosong = new SinglyLinkedList<>();

        assertEquals(0, ag.jumlahPerkara(kosong));
        assertEquals(0.0, ag.rataRataVonis(kosong), 0.001);
        assertEquals(0L, ag.totalDenda(kosong));
        assertTrue(ag.distribusiJenis(kosong).isEmpty());
    }
}
