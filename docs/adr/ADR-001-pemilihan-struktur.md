# ADR-001: Pemilihan Struktur Data Utama dan Sekunder

- Tanggal: 2026-11-05
- Status: Diterima
- Penulis: Maharani (Anggota A), direview oleh Anggota B (Nina)

## Konteks
Aplikasi harus menyimpan 50 objek Putusan dalam satu struktur utama buatan sendiri
(generik, tanpa koleksi java.util) dan satu struktur sekunder untuk riwayat operasi.

## Keputusan
- Struktur utama: Singly Linked List generik `SinglyLinkedList<T>`
- Struktur sekunder: Stack generik `AuditStack<T>` untuk merekam riwayat operasi (audit)

## Alasan
1. Singly Linked List dipilih karena mendukung penambahan dan penghapusan data melalui perubahan pointer antar-node tanpa harus menggeser elemen lainnya.
2. Struktur ini memungkinkan pengelolaan 50 objek Putusan secara berurutan serta mendukung operasi penambahan, penghapusan, pencarian, dan penampilan data.
3. Stack dipilih untuk menyimpan riwayat operasi karena menggunakan prinsip LIFO (Last In, First Out), sehingga aktivitas terbaru dapat ditampilkan terlebih dahulu.

## Konsekuensi
- Kelebihan:
  1. Penambahan dan penghapusan elemen di awal Linked List dapat dilakukan dengan kompleksitas O(1).
  2. Perubahan hubungan antar-node dapat dilakukan tanpa menggeser elemen lainnya.
  3. Stack memudahkan pencatatan dan penampilan riwayat operasi terbaru.
  4. Kedua struktur dapat dibuat secara generik sehingga dapat digunakan kembali untuk tipe data lain.
- Kekurangan:
  1. Pengaksesan data berdasarkan indeks membutuhkan penelusuran node dengan kompleksitas O(n).
  2. Pencarian data berdasarkan nomor perkara atau caseId membutuhkan pemeriksaan elemen secara berurutan dengan kompleksitas O(n).
  3. Setiap node membutuhkan memori tambahan untuk menyimpan pointer ke node berikutnya. 
  4. Stack hanya dapat mengakses elemen paling atas secara langsung.


## Alternatif yang dipertimbangkan
- Array dinamis:
Dipertimbangkan karena memiliki akses indeks dengan kompleksitas O(1). Namun, penambahan dan penghapusan elemen di awal atau tengah memerlukan pergeseran elemen. Oleh karena itu, kelompok memilih Singly Linked List karena lebih mudah menunjukkan perubahan hubungan antar-node.
- Queue:
Dipertimbangkan sebagai struktur sekunder untuk menyimpan riwayat operasi. Namun, Queue menggunakan prinsip FIFO (First In, First Out), sehingga aktivitas yang paling awal dimasukkan akan dikeluarkan terlebih dahulu. Kelompok memilih Stack karena lebih sesuai untuk menampilkan aktivitas terbaru terlebih dahulu.

## Referensi
- Panduan UTS Struktur Data Semester Ganjil 2026/2027, Bagian 3 (Pilihan Struktur Data).
- Contoh implementasi struktur data yang diberikan dosen pada perkuliahan melalui Zoom.