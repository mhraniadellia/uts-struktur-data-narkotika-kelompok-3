# Penggunaan AI

Dokumen ini mencatat setiap penggunaan AI generatif dalam pengerjaan UTS,
sesuai Kebijakan Penggunaan AI pada panduan bagian 6.

| No | Tanggal | Anggota | Alat | Tujuan | Prompt / ringkasan                                                | Bagian keluaran yang digunakan | Perubahan yang dilakukan | Cara verifikasi |
|----|---------|---------|------|--------|-------------------------------------------------------------------|-------------------------------|--------------------------|-----------------|
| 1 | 2026-11-04 | Maharani | Claude (Anthropic) | Menyusun roadmap dan pembagian tugas kelompok | "Pandu kerjakan UTS berdasarkan panduan, buat roadmap per anggota" | Urutan fase, pembagian peran A/B, daftar Issue/PR | Disesuaikan dengan kesepakatan kelompok | Dicocokkan dengan panduan bagian 3–8 |
| 2 | 2026-11-04 | Maharani | Claude (Anthropic) | Setup Git, GitHub, dan Maven | "Bagaimana langkah push pertama, .gitignore, proteksi branch, PR"  | Perintah git, isi .gitignore, properti maven.compiler.release dan plugin surefire di pom.xml | Path dan nama package disesuaikan dengan project | `mvn clean test` BUILD SUCCESS di laptop kedua anggota; PR #2 di-review anggota B |

## Catatan
- Kode struktur data, data putusan, dan isi ADR ditulis sendiri oleh anggota kelompok.
- Entri baru ditambahkan setiap kali AI digunakan.