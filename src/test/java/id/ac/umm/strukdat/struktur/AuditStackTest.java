
package id.ac.umm.strukdat.struktur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class AuditStackTest {

    private AuditStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new AuditStack<>();

        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");
    }


    @Test
    void stackKosongMemenuhiInvariant() {

        AuditStack<String> stackBaru = new AuditStack<>();

        assertEquals(0, stackBaru.size());
        assertTrue(stackBaru.isEmpty());
        assertTrue(stackBaru.validateInvariant());
    }


    @Test
    void pushPopMengikutiLifo() {

        assertEquals("D", stack.pop());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertTrue(stack.validateInvariant());
    }


    @Test
    void peekTidakMenghapus() {

        assertEquals("D", stack.peek());
        assertEquals("D", stack.peek());

        assertEquals(4, stack.size());
        assertEquals("D", stack.pop());

        assertTrue(stack.validateInvariant());
    }


    @Test
    void popKosongMelemparException() {

        AuditStack<String> stackBaru = new AuditStack<>();

        assertThrows(
                EmptyStackException.class,
                () -> stackBaru.pop()
        );

        assertTrue(stackBaru.isEmpty());
        assertTrue(stackBaru.validateInvariant());
    }


    @Test
    void pushNullDitolak() {

        assertThrows(
                IllegalArgumentException.class,
                () -> stack.push(null)
        );

        assertEquals(4, stack.size());
        assertTrue(stack.validateInvariant());
    }


    @Test
    void resizeOtomatis() {

        stack.push("E");
        stack.push("F");
        stack.push("G");
        stack.push("H");
        stack.push("I");
        stack.push("J");

        assertEquals(10, stack.size());
        assertEquals("J", stack.peek());

        assertTrue(stack.validateInvariant());
    }


    @Test
    void lihatTidakMengubahStack() {

        assertEquals("D", stack.lihat(0));
        assertEquals("A", stack.lihat(3));

        assertEquals(4, stack.size());
        assertEquals("D", stack.peek());

        assertTrue(stack.validateInvariant());
    }


    @Test
    void searchPreserveTidakMengubahUrutan() {

        assertEquals(
                2,
                stack.searchPreserve(x -> x.equals("B"))
        );

        assertEquals(3, stack.getLangkahTerakhir());

        assertEquals(4, stack.size());

        assertEquals("D", stack.pop());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());

        assertTrue(stack.validateInvariant());
    }


    @Test
    void searchPreserveTidakDitemukan() {

        assertEquals(
                -1,
                stack.searchPreserve(x -> x.equals("Z"))
        );

        assertEquals(4, stack.getLangkahTerakhir());

        assertEquals(4, stack.size());

        assertEquals("D", stack.pop());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());

        assertTrue(stack.validateInvariant());
    }


    @Test
    void clearMengosongkanStack() {

        stack.clear();

        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
        assertTrue(stack.validateInvariant());
    }


    @Test
    void restoreMengembalikanUrutan() {

        AuditStack<String> sementara = new AuditStack<>();

        sementara.push(stack.pop());
        sementara.push(stack.pop());

        stack.restore(sementara);

        assertTrue(sementara.isEmpty());
        assertEquals(4, stack.size());

        assertEquals("D", stack.pop());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());

        assertTrue(stack.validateInvariant());
    }


    @Test
    void peekDanLihatDiLuarBatas() {

        AuditStack<String> stackKosong = new AuditStack<>();


        assertThrows(
                EmptyStackException.class,
                () -> stackKosong.peek()
        );


        assertThrows(
                IndexOutOfBoundsException.class,
                () -> stack.lihat(4)
        );


        assertThrows(
                IndexOutOfBoundsException.class,
                () -> stack.lihat(-1)
        );


        assertEquals(4, stack.size());
        assertEquals("D", stack.peek());
        assertTrue(stack.validateInvariant());
    }
}
