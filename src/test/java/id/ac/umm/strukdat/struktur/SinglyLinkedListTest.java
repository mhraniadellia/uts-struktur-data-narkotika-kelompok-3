
package id.ac.umm.strukdat.struktur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {

    private SinglyLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new SinglyLinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
    }

    @Test
    void listKosongMemenuhiInvariant() {
        SinglyLinkedList<String> kosong = new SinglyLinkedList<>();

        assertEquals(0, kosong.size());
        assertTrue(kosong.isEmpty());
        assertTrue(kosong.validateInvariant());
    }

    @Test
    void hapusElemenTerakhirMemindahkanTail() {
        assertEquals("E", list.remove(4));
        assertEquals(4, list.size());
        assertEquals("D", list.get(3));

        list.add("F");

        assertEquals("F", list.get(4));
        assertTrue(list.validateInvariant());
    }

    @Test
    void hapusElemenPertamaMemindahkanHead() {
        assertEquals("A", list.remove(0));
        assertEquals("B", list.get(0));
        assertEquals(4, list.size());
        assertTrue(list.validateInvariant());
    }

    @Test
    void hapusElemenTengah() {
        assertEquals("C", list.remove(2));

        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("D", list.get(2));
        assertEquals("E", list.get(3));

        assertEquals(4, list.size());
        assertTrue(list.validateInvariant());
    }

    @Test
    void hapusSampaiKosong() {
        assertEquals("A", list.remove(0));
        assertEquals("B", list.remove(0));
        assertEquals("C", list.remove(0));
        assertEquals("D", list.remove(0));
        assertEquals("E", list.remove(0));

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertTrue(list.validateInvariant());
    }

    @Test
    void tambahKembaliTigaElemen() {
        list.remove(0);
        list.remove(1);
        list.remove(2);

        list.insert(0, "X");
        list.insert(2, "Y");
        list.add("Z");

        assertEquals("X", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("Y", list.get(2));
        assertEquals("D", list.get(3));
        assertEquals("Z", list.get(4));

        assertEquals(5, list.size());
        assertTrue(list.validateInvariant());
    }

    @Test
    void cariYangAda() {
        int posisi = list.search(x -> x.equals("D"));

        assertEquals(3, posisi);
        assertEquals(4, list.getLangkahTerakhir());
        assertTrue(list.validateInvariant());
    }

    @Test
    void cariYangTidakAda() {
        int posisi = list.search(x -> x.equals("Z"));

        assertEquals(-1, posisi);
        assertEquals(5, list.getLangkahTerakhir());
        assertTrue(list.validateInvariant());
    }

    @Test
    void cariPosisiBatas() {
        assertEquals(0, list.search(x -> x.equals("A")));
        assertEquals(1, list.getLangkahTerakhir());

        assertEquals(4, list.search(x -> x.equals("E")));
        assertEquals(5, list.getLangkahTerakhir());

        assertTrue(list.validateInvariant());
    }

    @Test
    void indexTidakValidMelemparException() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(5));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(-1));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.remove(5));

        assertTrue(list.validateInvariant());
    }
}
