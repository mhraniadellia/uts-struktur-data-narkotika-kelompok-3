# Trace Manual: SinglyLinkedList (Skenario Uji 3 dan 4)

Dokumen ini menelusuri perubahan pointer `head`, `tail`, `next`, dan nilai `size`
pada operasi hapus dan tambah kembali. Setiap langkah sesuai dengan test
`SinglyLinkedListTest.tambahKembaliTigaElemen` dan dapat diulang lewat menu App.

Notasi: `[X]` adalah node berisi data X, `→` adalah pointer `next`, `∅` adalah `null`.

## Keadaan awal

Setelah `add("A")`, `add("B")`, `add("C")`, `add("D")`, `add("E")`:

```
head                              tail
 ↓                                 ↓
[A] → [B] → [C] → [D] → [E] → ∅

size = 5
```

## Skenario 3: hapus pertama, tengah, terakhir

### Langkah 1: `remove(0)` (hapus elemen pertama)

Kasus `position == 0`: data `head` disimpan, lalu `head` dipindah ke node berikutnya.

```
Sebelum:  head → [A] → [B] → [C] → [D] → [E] → ∅      size = 5
                  ↑ dihapus

Proses:   data = head.data      (= "A")
          head = head.next      (head pindah ke [B])
          size--                (size = 4)
          size != 0 → tail tetap [E]

Sesudah:  head → [B] → [C] → [D] → [E] → ∅            size = 4
                                    ↑ tail
```

Nilai kembali: `"A"`. `validateInvariant()` = `true`.

### Langkah 2: `remove(1)` (hapus elemen tengah)

Kasus `position > 0`: cari `prev` (node di posisi `position - 1`), lalu lewati `target`.

```
Sebelum:  head → [B] → [C] → [D] → [E] → ∅            size = 4
                 prev  target

Proses:   prev = head                     (maju 0 kali, prev = [B])
          target = prev.next              (= [C])
          prev.next = target.next         ([B] sekarang menunjuk [D])
          target == tail? tidak           (tail tetap [E])
          size--                          (size = 3)

Sesudah:  head → [B] → [D] → [E] → ∅                  size = 3
                              ↑ tail
```

Nilai kembali: `"C"`. `validateInvariant()` = `true`.

### Langkah 3: `remove(2)` (hapus elemen terakhir)

Kasus `target == tail`: setelah dilewati, `tail` harus dipindah ke `prev`.

```
Sebelum:  head → [B] → [D] → [E] → ∅                  size = 3
                       prev  target = tail

Proses:   prev = head, maju 1 kali        (prev = [D])
          target = prev.next              (= [E])
          prev.next = target.next         ([D] sekarang menunjuk ∅)
          target == tail? YA → tail = prev (tail pindah ke [D])
          size--                          (size = 2)

Sesudah:  head → [B] → [D] → ∅                        size = 2
                        ↑ tail
```

Nilai kembali: `"E"`. `validateInvariant()` = `true`.

Jika langkah `tail = prev` tidak ada, `tail` masih menunjuk node `[E]` yang sudah dilepas.
Akibatnya `addLast` berikutnya akan menyambung ke `[E]` dan elemen baru tidak pernah
masuk ke list. Kondisi ini dideteksi oleh `validateInvariant()` (node terakhir yang
ditelusuri ≠ `tail`).

## Skenario 4: tambah kembali tiga elemen

Setelah tiga elemen (A, C, E) dihapus, list ditambah kembali tiga elemen di tiga posisi berbeda:

| Langkah | Operasi | Elemen | Posisi |
|---------|---------|--------|--------|
| 4 | `insert(0, "X")` | X | awal (indeks 0) |
| 5 | `insert(2, "Y")` | Y | tengah (indeks 2) |
| 6 | `add("Z")` | Z | akhir |

Elemen yang ditambahkan sengaja memakai data baru (X, Y, Z), bukan A, C, E, agar node hasil
penambahan mudah dibedakan dari node lama pada diagram dan pada asersi test.

### Langkah 4: `insert(0, "X")` (tambah di awal)

`position == 0` diteruskan ke `addFirst`.

```
Sebelum:  head → [B] → [D] → ∅                        size = 2

Proses:   baru = [X]
          baru.next = head                ([X] menunjuk [B])
          head = baru                     (head pindah ke [X])
          size != 0 → tail tetap [D]
          size++                          (size = 3)

Sesudah:  head → [X] → [B] → [D] → ∅                  size = 3
                              ↑ tail
```

`validateInvariant()` = `true`.

### Langkah 5: `insert(2, "Y")` (tambah di tengah)

`0 < position < size`: cari `prev` di posisi 1, sisipkan setelahnya.

```
Sebelum:  head → [X] → [B] → [D] → ∅                  size = 3
                       prev

Proses:   prev = head, maju 1 kali        (prev = [B])
          baru = [Y]
          baru.next = prev.next           ([Y] menunjuk [D])
          prev.next = baru                ([B] menunjuk [Y])
          size++                          (size = 4)

Sesudah:  head → [X] → [B] → [Y] → [D] → ∅            size = 4
                                    ↑ tail
```

`validateInvariant()` = `true`.

Urutan dua baris sambungan tidak boleh terbalik. Jika `prev.next = baru` dijalankan
lebih dulu, pointer ke `[D]` hilang dan sisa list terputus.

### Langkah 6: `add("Z")` (tambah di akhir)

`add` memanggil `addLast`, yang memakai `tail` sehingga tidak perlu menelusuri list.

```
Sebelum:  head → [X] → [B] → [Y] → [D] → ∅            size = 4
                                    ↑ tail

Proses:   baru = [Z]
          tail.next = baru                ([D] menunjuk [Z])
          tail = baru                     (tail pindah ke [Z])
          size++                          (size = 5)

Sesudah:  head → [X] → [B] → [Y] → [D] → [Z] → ∅      size = 5
                                          ↑ tail
```

`validateInvariant()` = `true`.

## Ringkasan

| Langkah | Operasi | Isi list | head | tail | size | Invariant |
|---------|---------|----------|------|------|------|-----------|
| 0 | awal | A B C D E | A | E | 5 | ✓ |
| 1 | remove(0) | B C D E | B | E | 4 | ✓ |
| 2 | remove(1) | B D E | B | E | 3 | ✓ |
| 3 | remove(2) | B D | B | D | 2 | ✓ |
| 4 | insert(0, X) | X B D | X | D | 3 | ✓ |
| 5 | insert(2, Y) | X B Y D | X | D | 4 | ✓ |
| 6 | add(Z) | X B Y D Z | X | Z | 5 | ✓ |

`validateInvariant()` diperiksa setelah setiap operasi (kolom Invariant) dan selalu bernilai
`true`, sehingga struktur list terbukti konsisten di seluruh tahapan. Hasil akhir sesuai dengan
asersi pada `SinglyLinkedListTest.tambahKembaliTigaElemen`.

## Demo dengan data putusan

Langkah yang sama dapat diulang pada aplikasi dengan data asli:

1. Menu 1: impor 50 putusan
2. Menu 4: hapus posisi 0 (pertama), lalu posisi tengah, lalu posisi terakhir
3. Menu 3: tambahkan tiga putusan (boleh baris CSV yang tadi dihapus) di posisi awal, tengah, dan akhir
4. Menu 2: periksa urutan hasil; setiap operasi hapus dan tambah menampilkan `Validasi struktur: true`

Tangkapan layar demo disimpan di `docs/demo/`.
