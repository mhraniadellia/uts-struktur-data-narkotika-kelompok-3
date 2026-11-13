# Kontribusi Anggota

## Anggota A – Maharani (@mhraniadellia)

### Tanggung jawab
- Kerangka project, pom.xml, .gitignore
- Model Putusan dan kontrak StrukturData
- SinglyLinkedList<T> beserta unit test
- CsvImporter beserta unit test
- Menu App: impor, tampil, tambah, hapus, cari
- Ekstraksi data P001–P025 dan provenance-nya
- Trace manual SinglyLinkedList, ADR-001, README
- Demo skenario uji 1–4

### Pull request yang dibuat

| PR | Judul |
|----|-------|
| #2 | Kerangka project dan struktur folder |
| #4 | catat penggunaan AI sesi 4 November 2026 |
| #6 | Model Putusan, interface StrukturData, dan ADR-001 |
| #8 | Data putusan P001–P025 dan provenance |
| #12 | Implementasi SinglyLinkedList<T> sebagai struktur utama |
| #14 | Impor CSV ke SinglyLinkedList dengan validasi record |
| #16 | Menu konsol App: impor, tampil, tambah, hapus, dan cari putusan |
| #21 | Perbaiki berat barang bukti P009 dan P013 yang tertukar |
| #25 | Perbarui AI_USAGE.md dengan sesi 4–13 November |
| #28 | Perbaiki header data/putusan-50.csv menjadi 16 nama kolom |
| #29 | Trace manual SinglyLinkedList untuk skenario uji 3 dan 4 |
| #34 | Revisi ADR-001: lengkapi kompleksitas dan implementasi sesuai kode |
| #37 | README final: menu 8–10, ekspor, catatan data, dan test tambahan |
| #40 | Lengkapi AI_USAGE bagian Anggota A |
| #45 | Demo skenario uji 1–4 dan hasil mvn clean test |

### Pull request yang direview

| PR | Judul |
|----|-------|
| #10 | Data putusan P026–P050 dan provenance |
| #18 | Implementasi AuditStack<T> sebagai struktur sekunder |
| #22 | Fitur filter dan agregasi data putusan |
| #30 | Fitur urutan vonis, riwayat audit, dan ekspor hasil |
| #31 | Tambahkan isi test UrutanService dan EksporService |
| #35 | ADR-002: keputusan pencarian sekuensial dan pengurutan sorted insert |
| #41 | Lengkapi AI_USAGE bagian Anggota B |
| #44 | Demo skenario uji 5–7 dan hasil ekspor |

## Anggota B – Nina (@ninalathuhanifa-debug)

### Tanggung jawab
- AuditStack<T> beserta unit test
- FilterService dan AgregasiService beserta unit test
- UrutanService dan EksporService beserta unit test
- Menu App: filter, agregasi, urutan, riwayat audit, ekspor
- Ekstraksi data P026–P050 dan provenance-nya
- Trace filter gabungan, ADR-002
- Demo skenario uji 5–7

### Pull request yang dibuat

| PR | Judul |
|----|-------|
| #10 | Data putusan P026–P050 dan provenance |
| #18 | Implementasi AuditStack<T> sebagai struktur sekunder |
| #22 | Fitur filter dan agregasi data putusan |
| #30 | Fitur urutan vonis, riwayat audit, dan ekspor hasil |
| #31 | Tambahkan isi test UrutanService dan EksporService |
| #35 | ADR-002: keputusan pencarian sekuensial dan pengurutan sorted insert |
| #41 | Lengkapi AI_USAGE bagian Anggota B |
| #44 | Demo skenario uji 5–7 dan hasil ekspor |

### Pull request yang direview

| PR | Judul |
|----|-------|
| #2 | Kerangka project dan struktur folder |
| #4 | catat penggunaan AI sesi 4 November 2026 |
| #6 | Model Putusan, interface StrukturData, dan ADR-001 |
| #8 | Data putusan P001–P025 dan provenance |
| #12 | Implementasi SinglyLinkedList<T> sebagai struktur utama |
| #14 | Impor CSV ke SinglyLinkedList dengan validasi record |
| #16 | Menu konsol App: impor, tampil, tambah, hapus, dan cari putusan |
| #21 | Perbaiki berat barang bukti P009 dan P013 yang tertukar |
| #25 | Perbarui AI_USAGE.md dengan sesi 4–13 November |
| #28 | Perbaiki header data/putusan-50.csv menjadi 16 nama kolom |
| #29 | Trace manual SinglyLinkedList untuk skenario uji 3 dan 4 |
| #34 | Revisi ADR-001: lengkapi kompleksitas dan implementasi sesuai kode |
| #37 | README final: menu 8–10, ekspor, catatan data, dan test tambahan |
| #40 | Lengkapi AI_USAGE bagian Anggota A |
| #45 | Demo skenario uji 1–4 dan hasil mvn clean test |

## Kerja bersama
- Aturan pengisian data disepakati bersama
- Verifikasi silang data: setiap anggota memeriksa 25 putusan milik anggota lain (status verified di provenance)
- AI_USAGE.md diisi oleh masing-masing anggota untuk bagiannya

## Ringkasan

| Anggota | PR dibuat | PR direview |
|---------|-----------|-------------|
| Maharani | 15 | 8 |
| Nina | 8 | 15 |