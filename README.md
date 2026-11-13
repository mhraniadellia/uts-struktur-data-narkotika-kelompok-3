# UTS Struktur Data – Putusan Pengadilan Narkotika (Kelompok 3)

Aplikasi Java berorientasi objek untuk menyimpan dan mengolah 50 putusan pengadilan
narkotika PN Bandung menggunakan struktur data buatan sendiri (tanpa koleksi `java.util`).

Mata kuliah : Struktur Data (D) Semester Ganjil 2026/2027
Dosen pengampu: Galih W. Wicaksono

## Identitas Kelompok

| Nama                   | NIM             | Akun GitHub | Peran |
|------------------------|-----------------|-------------|-------|
| Maharani Adellia Putri | 202510370110228 | [@mhraniadellia](https://github.com/mhraniadellia) | Anggota A – struktur utama, impor CSV, menu tampil/tambah/hapus/cari, trace, data P001–P025 |
| Nina Lathu Hanifa      | 202510370110248 | [@ninalathuhanifa-debug](https://github.com/ninalathuhanifa-debug) | Anggota B – struktur sekunder, audit, filter, agregasi, urutan, ekspor, data P026–P050 |

## Struktur Data yang Dipilih

| Peran | Struktur | Class | Keterangan |
|-------|----------|-------|------------|
| Utama | Singly Linked List generik | `SinglyLinkedList<T>` | Menyimpan seluruh 50 objek `Putusan` |
| Sekunder | Stack generik berbasis array | `AuditStack<T>` | Merekam riwayat operasi pengguna (audit) |

Alasan pemilihan: lihat [ADR-001-pemilihan-struktur.md](docs/adr/ADR-001-pemilihan-struktur.md) 
Strategi pencarian, filter, dan pengurutan: lihat  [ADR-002-pencarian-dan-pengurutan.md](docs/adr/ADR-002-pencarian-dan-pengurutan.md)

### Kompleksitas operasi struktur utama

| Operasi | Kompleksitas | Keterangan |
|---------|--------------|------------|
| `addFirst` | O(1) | Hanya mengubah `head` |
| `addLast` / `add` | O(1) | Memakai pointer `tail` |
| `get(i)` | O(n) | Menelusuri dari `head` |
| `insert(i, x)` | O(n) | Mencari node sebelum posisi i |
| `remove(i)` | O(n) | Mencari node sebelum posisi i, memperbarui `tail` bila perlu |
| `search` | O(n) | Sequential search dengan pencacah langkah |
| Tampil semua (menu 2) | O(n²) | `get(i)` di dalam perulangan; cukup untuk 50 data |
| Filter (menu 6) | O(n²) | `sumber.get(i)` di dalam perulangan, hasil ke list baru |
| Urutan vonis (menu 8) | O(n²) | Sorted insert stabil ke list baru (`search` + `insert` per elemen) |

### Kompleksitas operasi struktur sekunder (`AuditStack<T>`)

| Operasi | Kompleksitas | Keterangan |
|---------|--------------|------------|
| `push` | O(1) rata-rata | O(n) saat array diperbesar 2× |
| `pop`, `peek`, `lihat` | O(1) | Akses langsung ke indeks `top - 1` |
| `searchPreserve` | O(n) | Pop ke stack sementara lalu `restore` |

Pemetaan operasi `AuditStack` ke kontrak `StrukturData` (panduan 3.2): `add` ↔ `push`,
`remove` ↔ `pop`, `get` ↔ `peek`/`lihat`, `contains` ↔ `searchPreserve`; `size`, `isEmpty`, `clear` sama.

## Struktur Repository

```
uts-struktur-data-narkotika-kelompok-3/
|-- src/main/java/id/ac/umm/strukdat/
|   |-- model/Putusan.java
|   |-- struktur/StrukturData.java, SinglyLinkedList.java, AuditStack.java
|   |-- io/CsvImporter.java (impor CSV dan validasi)
|   |-- layanan/       (FilterService, AgregasiService, UrutanService, EksporService)
|   `-- App.java       (menu konsol)
|-- src/test/java/     (unit test JUnit 5)
|-- data/putusan-50.csv
|-- docs/data-provenance.csv
|-- docs/adr/          (keputusan desain)
|-- docs/trace/        (trace manual operasi struktur)
|-- docs/demo/         (bukti demo skenario uji)
|-- AI_USAGE.md
|-- CONTRIBUTIONS.md
|-- README.md
`-- pom.xml
```

## Cara Build, Test, dan Menjalankan

Kebutuhan: JDK 17 atau lebih baru, Maven 3.9+ (atau Maven bawaan IntelliJ IDEA).

```bash
# build dan jalankan seluruh test
mvn clean test

# kompilasi lalu jalankan aplikasi dari folder root project
mvn clean compile
java -cp target/classes id.ac.umm.strukdat.App
```

Lewat IntelliJ: panel Maven → Lifecycle → `clean` lalu `test`; jalankan `App.java` dengan tombol Run.

Aplikasi dijalankan dari folder root project karena membaca `data/putusan-50.csv` dengan path relatif.

### Menu aplikasi

| Menu | Fungsi | Operasi struktur |
|------|--------|------------------|
| 1 | Impor `data/putusan-50.csv`, menampilkan jumlah berhasil dan pesan error | `add` |
| 2 | Tampil ringkasan semua putusan sesuai urutan list | `get` |
| 3 | Tambah putusan dari satu baris CSV, di akhir atau posisi tertentu; caseId duplikat ditolak | `add` / `insert` |
| 4 | Hapus putusan berdasarkan posisi | `remove` |
| 5 | Cari berdasarkan nomor perkara atau caseId, menampilkan jumlah langkah | `search` |
| 6 | Filter: jenis, rentang berat, pasal terbukti, rentang vonis (dapat digabung) | `FilterService` |
| 7 | Agregasi: jumlah perkara, rata-rata vonis, total denda, distribusi jenis | `AgregasiService` |
| 8 | Urutkan berdasarkan vonis (naik/turun) ke list baru; list utama tidak berubah | `UrutanService` (`search` + `insert`) |
| 9 | Riwayat audit (terbaru di atas) dan pencarian kata kunci tanpa mengubah isi stack | `AuditStack.lihat`, `searchPreserve` |
| 10 | Ekspor hasil filter/urutan terakhir ke `docs/demo/ekspor-hasil.csv` atau laporan agregasi ke `docs/demo/laporan-agregasi.txt` | `EksporService` |
| 0 | Keluar | |

### Contoh penggunaan

```
Pilih menu: 1
Impor data selesai.
Jumlah putusan berhasil diimpor: 50
Tidak ada kesalahan parsing.
Validasi struktur: true

Pilih menu: 5
Masukkan nomor perkara atau caseId: P050
=== PUTUSAN DITEMUKAN ===
=== PUTUSAN DITEMUKAN ===
Posisi: 49
Case ID             : P050
Nomor Perkara       : 761/Pid.Sus/2025/PN Bdg
Pengadilan          : PN Bandung
Tanggal Putusan     : 2025-11-20
Terdakwa Kode       : T050
Jenis Narkotika     : Metamfetamina
Berat Barang Bukti  : 15.0153 gram
Pasal Dakwaan       : 114(2) UU 35/2009 / 112(2) UU 35/2009
Pasal Terbukti      : 114(2) UU 35/2009
Tuntutan            : 96 bulan
Vonis               : 90 bulan
Denda               : Rp1000000000
Barang Bukti        : 29 bungkus plastik bening berisi sabu yang terdiri dari 5 bungkus berlakban warna kuning 18 bungkus berlakban warna merah dan 6 bungkus berlakban warna hitam
Keadaan Memberatkan : Perbuatan terdakwa tidak mendukung program pemerintah dalam memberantas peredaran narkotika
Keadaan Meringankan : Terdakwa mengakui dan menyesali perbuatan yang dilakukannya serta berjanji tidak akan mengulanginya lagi dan terdakwa belum pernah dihukum
Source Pages        : identitas=1|dakwaan=3-8|bb=2-3,32-34|berat=5-8,29-30,32-33|tuntutan=2|pertimbangan=25-32|amar=33-34|tanggal=34
Jumlah langkah pencarian: 50
```

Menu Tambah menerima satu baris dengan format yang sama seperti `data/putusan-50.csv` (16 kolom, pemisah `;`).

Setiap operasi yang berhasil (impor, tambah, hapus, cari, filter, agregasi, urutan, ekspor) dicatat ke
`AuditStack` beserta jamnya dan dapat dilihat lewat menu 9.

### Ekspor

- Menu 10 hanya aktif setelah menu 6 (Filter) atau menu 8 (Urutan) dijalankan.
- File CSV hasil ekspor memakai header 16 kolom dan pemisah `;` yang sama dengan `data/putusan-50.csv`,
  sehingga dapat diimpor ulang (diuji oleh `EksporServiceTest.roundTripCsv`).
- Laporan teks menyebut jumlah putusan sumbernya, karena dihitung dari hasil terakhir, bukan dari seluruh 50 data.
- Batasan: nilai tidak di-*quote*. Hal ini aman karena aturan data melarang `;` dan baris baru di semua kolom,
  dan baris yang kolomnya bukan 16 ditolak oleh `CsvImporter.parseBaris`.

## Dataset

### Sumber dan kriteria

- Sumber: Direktori Putusan Mahkamah Agung, https://putusan3.mahkamahagung.go.id
- Pengadilan: **PN Bandung** (`PN Bdg`) untuk seluruh 50 putusan
- Kriteria: putusan tingkat pertama perkara narkotika, satu terdakwa per putusan, memuat dakwaan, tuntutan, pertimbangan, dan amar
- Berkas PDF sumber tidak diunggah ke repository. Identitas sumber dicatat melalui nomor perkara dan `docs/data-provenance.csv`
- Nama terdakwa disamarkan dengan kode `T001`–`T050`

### Format file

- `data/putusan-50.csv` memakai pemisah titik koma (`;`) sesuai arahan dosen, karena beberapa atribut teks mengandung koma
- `docs/data-provenance.csv` memakai pemisah koma sesuai contoh panduan
- Encoding UTF-8, baris pertama adalah header

### Aturan pengisian atribut

| Atribut | Aturan |
|---------|--------|
| `tanggalPutusan` | Tanggal putusan diucapkan, format `YYYY-MM-DD` |
| `jenisNarkotika` | Nama zat dari hasil laboratorium; lebih dari satu zat dipisah `/` (mis. `Metamfetamina/MDMA`) |
| `beratBarangBuktiGram` | (1) total berat netto awal hasil laboratorium; (2) jika tidak tersedia, berat netto lain yang tertulis di putusan (sisa sampel, berat penyidik, atau amar); (3) jika tidak ada sama sekali, `0`. Semua zat dijumlahkan, dalam gram |
| `pasalDakwaan` | Seluruh pasal dakwaan. Alternatif/subsidair dipisah ` / `, kumulatif dipisah ` + ` dengan akhiran `(kumulatif)`. Akhiran penyesuaian (`jo UU 1/2023`, `jo UU 1/2026`) dan peraturan penggolongan tidak ditulis |
| `pasalTerbukti` | Pasal yang dinyatakan terbukti dalam amar, format sama dengan `pasalDakwaan` |
| `tuntutanBulan`, `vonisBulan` | Pidana penjara dalam bulan (tahun × 12 + bulan); pidana denda subsider tidak dihitung |
| `dendaRupiah` | Pidana denda dalam amar, tanpa pemisah ribuan; tanpa denda = `0` |
| `barangBukti` | Ringkasan narkotika yang disita, ditambah alat komunikasi/timbangan bila ada |
| `keadaanMemberatkan`, `keadaanMeringankan` | Ringkasan pertimbangan hakim dengan kata sendiri |
| `sourcePages` | Halaman sumber per kelompok atribut: `identitas`, `dakwaan`, `bb`, `berat`, `tuntutan`, `pertimbangan`, `amar`, `tanggal`; beberapa lokasi dipisah koma |

Nilai yang tidak ditemukan di putusan ditulis `0` (angka) atau kosong (teks), tidak diperkirakan.

### Catatan khusus data

- P001: berat memakai hasil laboratorium (9.5481 gram); amar hanya menyebut "8 gram" (angka bulat penyidik)
- P008: berat memakai berat sisa sampel karena berat netto awal tidak dicantumkan di putusan
- P009, P024: dua jenis zat; berat adalah jumlah berat netto semua zat
- P024: dakwaan kumulatif, kedua pasal dinyatakan terbukti
- P030: vonis (90 bulan) lebih berat dari tuntutan (84 bulan); sudah dicek ulang ke amar dan tuntutan
- Distribusi jenis pada menu Agregasi menghitung setiap zat: putusan `Metamfetamina/MDMA` dihitung untuk Metamfetamina dan MDMA, sehingga total distribusi dapat lebih dari 50
- P009, P013: nilai berat sempat tertukar dengan P010/P014 saat penyalinan; diperbaiki menjadi 31.0198 dan 0.3488 gram
- Header `data/putusan-50.csv` awalnya berisi `baris_csv` (sisa kolom bantu Google Sheet); diperbaiki menjadi 16 nama kolom

### Provenance

`docs/data-provenance.csv` memuat 9 atribut per putusan (450 baris): `nomorPerkara`, `tanggalPutusan`,
`jenisNarkotika`, `beratBarangBuktiGram`, `pasalDakwaan`, `pasalTerbukti`, `tuntutanBulan`, `vonisBulan`,
`dendaRupiah`. Setiap baris mencatat halaman sumber, anggota pengekstrak (`extractor`), dan anggota
pemeriksa (`reviewer`). Status `verified` berarti nilai sudah dicocokkan reviewer dengan PDF.

## Skenario Uji

| No | Skenario | Test / bukti |
|----|----------|--------------|
| 1 | Muat 50 record, size = 50 | `CsvImporterTest.memuat50Record` |
| 2 | Cari perkara yang ada, tidak ada, dan di posisi batas | `SinglyLinkedListTest.cariYangAda`, `cariYangTidakAda`, `cariPosisiBatas` |
| 3 | Hapus elemen pertama, tengah, terakhir dan tunjukkan perubahan pointer | `SinglyLinkedListTest.hapusElemenPertamaMemindahkanHead`, `hapusElemenTengah`, `hapusElemenTerakhirMemindahkanTail`; [docs/trace/trace-singly-linked-list.md](docs/trace/trace-singly-linked-list.md) |
| 4 | Tambah kembali tiga record, cek invariant dan urutan | `SinglyLinkedListTest.tambahKembaliTigaElemen`; trace yang sama |
| 5 | Filter gabungan dicocokkan dengan provenance manual | `FilterServiceTest.filterGabungan`; [docs/trace/filter-gabungan.md](docs/trace/filter-gabungan.md) |
| 6 | Pencarian pada Stack tidak mengubah urutan akhir | `AuditStackTest.searchPreserveTidakMengubahUrutan`, `searchPreserveTidakDitemukan` |
| 7 | Record tidak valid ditolak, struktur tetap konsisten | `CsvImporterTest.recordTidakValidDitolak`, `SinglyLinkedListTest.indexTidakValidMelemparException` |

### Test tambahan

| Komponen | Test |
|----------|------|
| Agregasi | `AgregasiServiceTest` |
| Urutan | `UrutanServiceTest`: naik, turun, list utama tidak berubah, vonis sama tetap urutan asli, list kosong |
| Ekspor | `EksporServiceTest`: round trip CSV, header sesuai skema, isi laporan teks |

Bug yang ditemukan melalui test: `memuat50Record` awalnya gagal (48/50) karena dua baris berisi berat dengan satuan "gram"; data diperbaiki di commit terpisah.

## Kolaborasi dan Transparansi

- Alur kerja: Issue → branch → commit kecil → Pull Request → review anggota lain → merge
- Kontribusi tiap anggota: [CONTRIBUTIONS.md](CONTRIBUTIONS.md)
- Penggunaan AI: [AI_USAGE.md](AI_USAGE.md)
- Versi final: tag `uts-v1.0`
