
package id.ac.umm.strukdat.layanan;

import id.ac.umm.strukdat.model.Putusan;
import id.ac.umm.strukdat.struktur.SinglyLinkedList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EksporService {

    public void eksporCsv(
            SinglyLinkedList<Putusan> data, String path)
            throws IOException {

        StringBuilder sb = new StringBuilder();

        sb.append("caseId;nomorPerkara;pengadilan;tanggalPutusan;terdakwaKode;jenisNarkotika;beratBarangBuktiGram;pasalDakwaan;pasalTerbukti;tuntutanBulan;vonisBulan;dendaRupiah;barangBukti;keadaanMemberatkan;keadaanMeringankan;sourcePages").append("\n");

        for (int i = 0; i < data.size(); i++) {
            Putusan p = data.get(i);

            sb.append(p.getCaseId()).append(";")
                    .append(p.getNomorPerkara()).append(";")
                    .append(p.getPengadilan()).append(";")
                    .append(p.getTanggalPutusan()).append(";")
                    .append(p.getTerdakwaKode()).append(";")
                    .append(p.getJenisNarkotika()).append(";")
                    .append(p.getBeratBarangBuktiGram()).append(";")
                    .append(p.getPasalDakwaan()).append(";")
                    .append(p.getPasalTerbukti()).append(";")
                    .append(p.getTuntutanBulan()).append(";")
                    .append(p.getVonisBulan()).append(";")
                    .append(p.getDendaRupiah()).append(";")
                    .append(p.getBarangBukti()).append(";")
                    .append(p.getKeadaanMemberatkan()).append(";")
                    .append(p.getKeadaanMeringankan()).append(";")
                    .append(p.getSourcePages())
                    .append("\n");
        }

        Path lokasi = Path.of(path);

        if (lokasi.getParent() != null) {
            Files.createDirectories(lokasi.getParent());
        }

        Files.writeString(
                lokasi,
                sb.toString(),
                StandardCharsets.UTF_8
        );
    }

    public void eksporTeks(
            SinglyLinkedList<Putusan> data, String path)
            throws IOException {

        AgregasiService ag = new AgregasiService();

        StringBuilder sb = new StringBuilder();

        sb.append("LAPORAN AGREGASI PUTUSAN NARKOTIKA\n");
        sb.append("====================================\n\n");
        sb.append("Sumber data: hasil filter/urutan terakhir (")
                .append(data.size()).append(" putusan)\n\n");

        sb.append("Jumlah perkara: ")
                .append(ag.jumlahPerkara(data)).append("\n");

        sb.append("Rata-rata vonis: ")
                .append(String.format(java.util.Locale.ROOT, "%.2f", ag.rataRataVonis(data)))
                .append(" bulan\n");

        sb.append("Total denda: Rp")
                .append(ag.totalDenda(data)).append("\n\n");

        sb.append("Distribusi jenis narkotika:\n");

        SinglyLinkedList<AgregasiService.JumlahJenis> distribusi =
                ag.distribusiJenis(data);

        for (int i = 0; i < distribusi.size(); i++) {
            sb.append(i + 1).append(". ")
                    .append(distribusi.get(i)).append("\n");
        }

        Path lokasi = Path.of(path);

        if (lokasi.getParent() != null) {
            Files.createDirectories(lokasi.getParent());
        }

        Files.writeString(
                lokasi,
                sb.toString(),
                StandardCharsets.UTF_8
        );
    }
}
