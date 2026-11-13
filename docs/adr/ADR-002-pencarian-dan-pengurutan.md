# ADR-002: Pemilihan Algoritma Pencarian, Filter, dan Pengurutan

- Tanggal: 2026-11-13
- Status: Diterima
- Penulis: Nina (Anggota B)
- Reviewer: Maharani (Anggota A)

## Konteks

Aplikasi pengelolaan data putusan narkotika memiliki tiga fitur yang berkaitan dengan pencarian dan pengolahan data, yaitu Cari, Filter, dan Urutan. Fitur Cari digunakan untuk menemukan putusan berdasarkan nomor perkara atau `caseId` serta menampilkan jumlah langkah pencarian. Fitur Filter digunakan untuk memperoleh putusan berdasarkan beberapa kriteria sekaligus, sedangkan fitur Urutan digunakan untuk mengurutkan putusan berdasarkan lama vonis.

Data terdiri atas 50 objek `Putusan` yang disimpan dalam struktur utama `SinglyLinkedList`. Implementasi harus menggunakan struktur data buatan sendiri dan tidak menggunakan koleksi `java.util` sebagai penyimpanan data.

## Keputusan

Kelompok menetapkan pendekatan berikut:

1. **Pencarian:** menggunakan pencarian sekuensial melalui method `search(Predicate<T>)` pada `SinglyLinkedList`. Method ini memeriksa elemen satu per satu dan menyimpan jumlah pemeriksaan dalam penghitung `langkahTerakhir`.

2. **Filter:** menggunakan kombinasi beberapa kriteria dengan `Predicate` dan operasi `and()`. Hasil penyaringan disimpan dalam `SinglyLinkedList` baru sehingga data pada list utama tidak berubah.

3. **Pengurutan:** menggunakan algoritma *sorted insert* melalui method `urutkanVonis()`. Setiap putusan diambil dari list utama, kemudian disisipkan ke posisi yang sesuai dalam list hasil yang baru.

    - Urutan naik menggunakan kondisi `vonis > vonis putusan yang sedang disisipkan`.
    - Urutan turun menggunakan kondisi `vonis < vonis putusan yang sedang disisipkan`.

   Penggunaan operator `>` dan `<` membuat putusan dengan nilai vonis yang sama tetap mengikuti urutan asalnya. Dengan demikian, algoritma mempertahankan sifat *stable sort*.

## Alasan

1. **Pencarian sekuensial lebih sesuai dengan Singly Linked List.** Struktur linked list tidak menyediakan akses acak berdasarkan indeks dengan kompleksitas O(1). Operasi `get(i)` membutuhkan penelusuran dari `head`, sehingga binary search tidak efektif digunakan. Selain itu, data putusan tidak disusun berdasarkan nomor perkara atau `caseId` sebagai kunci pencarian.

2. **Satu method pencarian dapat digunakan kembali.** Method `search(Predicate<T>)` dapat digunakan untuk mencari nomor perkara atau `caseId` pada menu Cari, memeriksa duplikasi `caseId` pada menu Tambah, serta menemukan posisi penyisipan pada proses pengurutan. Pendekatan ini mengurangi kebutuhan membuat algoritma pencarian yang berbeda untuk setiap fitur.

3. **Filter tidak mengubah data utama.** Hasil filter ditempatkan dalam list baru agar data asli tetap tersedia untuk fitur lain. Pengguna dapat melakukan penyaringan tanpa kehilangan atau mengubah urutan data utama.

4. **Sorted insert mempertahankan urutan relatif data dengan vonis sama.** Perbandingan menggunakan `>` untuk urutan naik dan `<` untuk urutan turun membuat elemen baru dengan vonis sama disisipkan setelah elemen sebelumnya yang memiliki vonis sama. Hal ini menjaga urutan awal putusan.

5. **Penggunaan list hasil memisahkan data asli dan hasil pengolahan.** List utama tetap mempertahankan urutan awal, sedangkan hasil pencarian, filter, atau pengurutan dapat digunakan secara terpisah oleh fitur aplikasi lainnya.

## Konsekuensi

### Kelebihan

1. Pencarian sekuensial memiliki kompleksitas terbaik O(1) ketika elemen ditemukan pada posisi pertama dan terburuk O(n) ketika elemen berada di posisi terakhir atau tidak ditemukan.
2. Jumlah langkah pencarian dapat ditampilkan kepada pengguna melalui `langkahTerakhir`, sehingga proses pencarian dapat diamati dan diuji.
3. Filter mendukung penggabungan beberapa kriteria dalam satu proses, sehingga pengguna dapat mencari putusan berdasarkan jenis narkotika, berat barang bukti, pasal terbukti, dan rentang vonis secara bersamaan.
4. Hasil filter dan pengurutan disimpan dalam list baru sehingga list utama tidak berubah.
5. Pengurutan bersifat stabil karena putusan dengan vonis sama tetap mengikuti urutan asal.

### Kekurangan

1. Pencarian sekuensial tetap membutuhkan O(n) pada kondisi terburuk karena elemen harus diperiksa satu per satu.
2. Proses filter menggunakan perulangan yang mengakses elemen melalui `sumber.get(i)`. Karena setiap pemanggilan `get(i)` membutuhkan penelusuran dari `head`, keseluruhan proses filter memiliki kompleksitas O(n²).
3. Pengurutan dengan *sorted insert* memiliki kompleksitas O(n²). Untuk setiap putusan, algoritma melakukan pencarian posisi penyisipan dan penyisipan ke list hasil. Kedua operasi tersebut dapat membutuhkan penelusuran O(n).
4. Filter dan pengurutan membutuhkan memori tambahan untuk menyimpan list hasil.
5. Untuk jumlah data yang jauh lebih besar, pendekatan filter dan pengurutan ini dapat menjadi kurang efisien dibandingkan algoritma yang memiliki kompleksitas lebih rendah.

Untuk jumlah 50 putusan sesuai kebutuhan proyek, kompleksitas O(n²) pada filter dan pengurutan masih dapat diterima karena implementasinya lebih sederhana dan mudah ditelusuri melalui pengujian.

## Alternatif yang Dipertimbangkan

1. **Binary Search**

   Binary search memiliki kompleksitas O(log n), tetapi membutuhkan data yang sudah terurut berdasarkan kunci pencarian dan akses indeks yang efisien. Singly Linked List tidak menyediakan akses indeks O(1), sehingga metode ini tidak dipilih.

2. **Indeks Hash berdasarkan Nomor Perkara atau caseId**

   Indeks hash dapat mempercepat pencarian, tetapi penggunaan `HashMap` termasuk koleksi `java.util` yang tidak diperbolehkan dalam ketentuan proyek. Membuat implementasi hash table sendiri juga berada di luar cakupan pekerjaan kelompok.

3. **Merge Sort pada Linked List**

   Merge sort dapat mengurutkan linked list dengan kompleksitas O(n log n). Namun, implementasinya lebih rumit dibandingkan *sorted insert*. Karena jumlah data proyek hanya 50 putusan, peningkatan efisiensinya belum dianggap sebanding dengan tambahan kompleksitas implementasi.

4. **Selection Sort atau Bubble Sort di Tempat**

   Kedua algoritma dapat digunakan untuk mengurutkan data, tetapi pengurutan langsung pada list utama akan mengubah urutan data asli. Pertukaran node pada linked list juga membutuhkan penanganan pointer yang lebih rumit. Selain itu, selection sort tidak menjamin kestabilan urutan untuk elemen dengan nilai sama. Oleh karena itu, pendekatan ini tidak dipilih.

## Referensi

- Panduan UTS Struktur Data Semester Ganjil 2026/2027, bagian fitur aplikasi dan skenario pengujian.
- ADR-001: Pemilihan Struktur Data Utama dan Sekunder.
- Review Pull Request fitur Urutan, khususnya pembahasan kompleksitas algoritma O(n²) bersama Anggota A.
- Implementasi `SinglyLinkedList.java`, khususnya method `search(Predicate<T>)` dan `get(int)`.
- Implementasi `FilterService.java` dan `UrutanService.java` pada package `layanan`.
- Pengujian `UrutanServiceTest.java` dan `FilterServiceTest.java` pada folder `src/test/java`.
