package id.ac.umm.strukdat.io;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CsvImporter {

    private static final int JUMLAH_KOLOM = 16;
    private final SinglyLinkedList<String> errors = new SinglyLinkedList<>();

    public SinglyLinkedList<Putusan> impor(String path) throws IOException {
        SinglyLinkedList<Putusan> hasil = new SinglyLinkedList<>();
        errors.clear();

        List<String> baris = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);

        for (int i = 1; i < baris.size(); i++) {      // i = 1: lewati header
            String teks = baris.get(i);
            if (teks.isBlank()) {
                continue;                              // abaikan baris kosong
            }
            int nomorBaris = i + 1;                    // nomor baris di file (header = 1)

            // TODO langkah 4: pecah, validasi, buat Putusan, hasil.add(...)
        }
        return hasil;
    }

    public SinglyLinkedList<String> getErrors() {
        return errors;
    }
}