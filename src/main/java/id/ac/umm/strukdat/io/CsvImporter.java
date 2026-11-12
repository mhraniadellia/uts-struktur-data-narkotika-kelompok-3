
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

    private final SinglyLinkedList<String> errors =
            new SinglyLinkedList<>();

    public SinglyLinkedList<Putusan> impor(String path)
            throws IOException {

        SinglyLinkedList<Putusan> hasil =
                new SinglyLinkedList<>();

        errors.clear();

        List<String> baris = Files.readAllLines(
                Path.of(path),
                StandardCharsets.UTF_8
        );

        for (int i = 1; i < baris.size(); i++) {

            String teks = baris.get(i);

            if (teks.isBlank()) {
                continue;
            }

            int nomorBaris = i + 1;

            try {
                Putusan putusan = parseBaris(teks);
                hasil.add(putusan);

            } catch (IllegalArgumentException e) {
                errors.add(
                        "Baris " + nomorBaris + ": " + e.getMessage()
                );
            }
        }

        return hasil;
    }

    public Putusan parseBaris(String teks) {

        if (teks == null || teks.isBlank()) {
            throw new IllegalArgumentException(
                    "Baris CSV kosong"
            );
        }

        // 1. Pecah berdasarkan titik koma
        String[] k = teks.split(";", -1);

        // 2. Periksa jumlah kolom
        if (k.length != JUMLAH_KOLOM) {
            throw new IllegalArgumentException(
                    "Jumlah kolom " + k.length
                            + ", seharusnya " + JUMLAH_KOLOM
            );
        }

        // 3. Periksa caseId
        if (k[0].isBlank()) {
            throw new IllegalArgumentException(
                    "caseId tidak boleh kosong"
            );
        }

        try {

            // 4. Ubah teks menjadi tipe data yang sesuai
            LocalDate tanggal = LocalDate.parse(k[3].trim());

            double berat = Double.parseDouble(k[6].trim());

            int tuntutan = Integer.parseInt(k[9].trim());

            int vonis = Integer.parseInt(k[10].trim());

            long denda = Long.parseLong(k[11].trim());

            // 5. Periksa nilai negatif
            if (berat < 0 || tuntutan < 0
                    || vonis < 0 || denda < 0) {

                throw new IllegalArgumentException(
                        "Berat, tuntutan, vonis, dan denda "
                                + "tidak boleh negatif"
                );
            }

            // 6. Buat objek Putusan
            return new Putusan(
                    k[0],
                    k[1],
                    k[2],
                    tanggal,
                    k[4],
                    k[5],
                    berat,
                    k[7],
                    k[8],
                    tuntutan,
                    vonis,
                    denda,
                    k[12],
                    k[13],
                    k[14],
                    k[15]
            );

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Format angka tidak valid: " + e.getMessage()
            );

        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException(
                    "Format tanggal tidak valid: " + e.getMessage()
            );
        }
    }

    public SinglyLinkedList<String> getErrors() {
        return errors;
    }
}
