# AplikasiMobile

`AplikasiMobile` merupakan repository yang menyimpan tugas dan praktek mata kuliah `Aplikasi Mobile`.

## Menduplikat/Fork `AplikasiMobile`

Jika Anda ingin berkolaborasi dengan Saya, Anda sebaiknya jangan langsung `git clone <url>` repostiory ini. Sebaiknya, Anda `fork` terlebih dahulu ke `repository github` milik Anda sendiri. Lalu Anda dapat melakukan `git clone <url-repository-github-Anda>` dengan begitu, Anda dapat melakukan `Pull requests` ke repository [lyrihkaesa/AplikasiMobile](https://github.com/lyrihkaesa/AplikasiMobile/pulls).

## Memperbarui/Update repository lokal

Untuk melakukan pembaruan/update `repository lokal` dari `repository remote/origin` gunakanlah perintah berikut:

```bash
git pull
```
or 
```bash
git fetch
```

## Membuat Branch Baru

Untuk membuat branch baru pada Git dan masuk ke branch tersebut, Anda dapat mengikuti langkah-langkah berikut:

1. Klik kanan pada folder proyek Anda, kemudian pilih ‘Git Bash Here’.
2. Ketikkan perintah:  
```bash
git branch <nama-branch-yang-dibuat>
```
3. Masuk ke branch yang baru saja dibuat dengan perintah:  
```bash
git checkout <nama-branch-yang-dibuat>
```
4. Jika Anda ingin membuat branch baru dan langsung pindah ke branch bersangkutan, gunakan perintah:  
```bash
git checkout -b <nama-branch-yang-dibuat>
```

## Aturan Penamaan Branch

Berikut adalah beberapa best practice penamaan branch pada Git:

1. Gunakan nama yang deskriptif dan mudah dipahami oleh tim Anda.
2. Gunakan tAnda `-` atau `_` untuk memisahkan kata dalam nama branch.
3. Gunakan huruf kecil untuk semua karakter dalam nama branch.
4. Gunakan awalan yang jelas untuk menunjukkan jenis branch tersebut, seperti `feature/`, `bugfix/`, atau `hotfix/`.
5. Hindari menggunakan spasi dalam nama branch. Jika perlu, gunakan tAnda `-` atau `_`.
6. Hindari menggunakan karakter khusus seperti `~`, `^`, atau `:` dalam nama branch.

Berikut contoh penamaan branch:

- NIM-Nama/tugas/<nama-tugas>
  - 09999-Kaesa/tugas/lingkaran
- NIM-Nama/latihan/<nama-latihan>
  - 09999-Kaesa/latihan/kubus
- NIM-Nama/praktek/<nama-praktek>
  - 09999-Kaesa/praktek/<nama-praktek>