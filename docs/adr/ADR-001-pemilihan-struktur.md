# ADR-001: Pemilihan Struktur Data Utama dan Sekunder

- Tanggal: 2026-11-05
- Direvisi: 2026-11-13 (melengkapi kompleksitas dan implementasi sesuai kode)
- Status: Diterima
- Penulis: Maharani (Anggota A)
- Reviewer: Nina (Anggota B)

## Konteks
Aplikasi harus menyimpan 50 objek Putusan dalam satu struktur utama buatan sendiri
(generik, tanpa koleksi java.util) dan satu struktur sekunder untuk riwayat operasi.
Data perlu dapat ditambah dan dihapus pada posisi awal, tengah, dan akhir, dicari,
difilter, dan diurutkan.

## Keputusan
- Struktur utama: Singly Linked List generik `SinglyLinkedList<T>` dengan pointer `head`,
  `tail`, dan penghitung `size`.
- Struktur sekunder: Stack generik `AuditStack<T>` untuk merekam riwayat operasi (audit).
  AuditStack diimplementasikan dengan array `Object[]` dan penunjuk `top`; kapasitas
  diperbesar dua kali lipat saat penuh.

## Alasan
1. Singly Linked List dipilih karena mendukung penambahan dan penghapusan data melalui
   perubahan pointer antar-node tanpa harus menggeser elemen lainnya.
2. Struktur ini memungkinkan pengelolaan 50 objek Putusan secara berurutan serta mendukung
   operasi penambahan, penghapusan, pencarian, dan penampilan data.
3. Penyimpanan pointer `tail` membuat penambahan di akhir (yang paling sering dipakai saat
   impor) dapat dilakukan tanpa menelusuri list.
4. Stack dipilih untuk menyimpan riwayat operasi karena menggunakan prinsip LIFO
   (Last In, First Out), sehingga aktivitas terbaru dapat ditampilkan terlebih dahulu.

## Konsekuensi
- Kelebihan:
  1. Penambahan dan penghapusan elemen di awal Linked List dapat dilakukan dengan
     kompleksitas O(1).
  2. Penambahan di akhir juga O(1) karena list menyimpan pointer `tail`.
  3. Perubahan hubungan antar-node dapat dilakukan tanpa menggeser elemen lainnya,
     walaupun mencari posisi node sebelumnya tetap membutuhkan penelusuran O(n).
  4. Stack memudahkan pencatatan dan penampilan riwayat operasi terbaru; `push` rata-rata
     O(1) karena kapasitas array diperbesar dua kali lipat saat penuh.
  5. Kedua struktur dapat dibuat secara generik sehingga dapat digunakan kembali untuk
     tipe data lain.
- Kekurangan:
  1. Pengaksesan data berdasarkan indeks (`get`) membutuhkan penelusuran node dengan
     kompleksitas O(n).
  2. Menghapus elemen terakhir tetap O(n), karena harus menelusuri sampai node sebelum
     `tail` untuk memindahkan `tail`.
  3. Pencarian data berdasarkan nomor perkara atau caseId membutuhkan pemeriksaan elemen
     secara berurutan dengan kompleksitas O(n).
  4. Menampilkan semua data dengan `get(i)` dalam perulangan menjadi O(n²); untuk 50 data
     masih dapat diterima.
  5. Setiap node membutuhkan memori tambahan untuk menyimpan pointer ke node berikutnya.
  6. Stack hanya dapat mengakses elemen paling atas secara langsung. Mencari isi riwayat
     (`searchPreserve`) membutuhkan pop ke stack sementara lalu dikembalikan, dengan
     kompleksitas O(n) dan memori tambahan untuk stack sementara.

## Alternatif yang dipertimbangkan
- Array dinamis:
  Dipertimbangkan karena memiliki akses indeks dengan kompleksitas O(1). Namun, penambahan
  dan penghapusan elemen di awal atau tengah memerlukan pergeseran elemen. Oleh karena itu,
  kelompok memilih Singly Linked List karena lebih mudah menunjukkan perubahan hubungan
  antar-node.
- Doubly Linked List:
  Dipertimbangkan karena penghapusan elemen terakhir bisa O(1) dengan pointer `prev`.
  Namun setiap node membutuhkan pointer tambahan dan penyambungan pointer lebih rumit.
  Untuk 50 data, keuntungan tersebut tidak sebanding, sehingga kelompok memilih Singly
  Linked List.
- Queue:
  Dipertimbangkan sebagai struktur sekunder untuk menyimpan riwayat operasi. Namun, Queue
  menggunakan prinsip FIFO (First In, First Out), sehingga aktivitas yang paling awal
  dimasukkan akan dikeluarkan terlebih dahulu. Kelompok memilih Stack karena lebih sesuai
  untuk menampilkan aktivitas terbaru terlebih dahulu.

## Referensi
- Panduan UTS Struktur Data Semester Ganjil 2026/2027, Bagian 3 (Pilihan Struktur Data).
- Contoh implementasi struktur data yang diberikan dosen pada perkuliahan melalui Zoom.
- `docs/trace/trace-singly-linked-list.md` (bukti perpindahan `tail` saat menghapus elemen terakhir).
