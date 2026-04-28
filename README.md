# E-Commerce Sales Analysis with Hadoop MapReduce

Proyek ini mengimplementasikan pemrosesan Big Data menggunakan Apache Hadoop MapReduce di dalam ekosistem Google Cloud Dataproc. Fokus utama proyek ini adalah melakukan agregasi data (SUM) untuk menghitung total pendapatan (revenue) berdasarkan kategori produk menggunakan dataset *E-Commerce Sales* dari Kaggle.

## Teknologi yang Digunakan
* **Google Cloud Platform (GCP):** Dataproc, Cloud Shell
* **Framework:** Apache Hadoop (HDFS & MapReduce)
* **Bahasa Pemrograman:** Java (IntelliJ IDEA)

## Langkah-langkah Eksekusi

### 1. Akses Master Node (CLI)
Akses terminal Master Node menggunakan Google Cloud Shell tanpa memerlukan konfigurasi SSH manual dari perangkat lokal. Buka Google Cloud Console, aktifkan Cloud Shell, dan jalankan perintah berikut:
```bash
gcloud compute ssh safeband-hadoop-cluster-m --zone=asia-southeast1-c --tunnel-through-iap
```
Catatan: Jika muncul konfirmasi Do you want to continue (Y/n)?, ketik Y. Kosongkan bagian passphrase dengan menekan Enter dua kali.

### 2. Persiapan Data di HDFS
Data mentah dibagi menjadi beberapa bagian (split -l 1000) untuk mensimulasikan pemrosesan terdistribusi pada banyak direktori. Setelah data diunggah ke cluster, pastikan data telah didistribusikan ke dalam folder HDFS yang sesuai (misal: x001, x002, dst). Untuk memverifikasi struktur direktori data di HDFS, gunakan perintah berikut:
```bash
hdfs dfs -ls -R /user/hadoop/data/
```
![Membagi Data](assets/Membagi%20data%20menjadi%201000%20per%20file.png)
![Letak Data di Cluster](assets/Letak%20data%20setelah%20dipindah%20ke%20cluster.png)

### 3. Menjalankan Job MapReduce
Eksekusi file .jar MapReduce yang telah di-build. Pastikan untuk menentukan direktori output yang belum ada di dalam HDFS untuk menghindari konflik (error bentrok).
```bash
hadoop jar MapReduce09-1.0-SNAPSHOT.jar id.ac.polinema.App /user/hadoop/data/x* /user/hadoop/output_amazon_09
```
![Menjalankan JAR di Cluster](assets/Menjalankan%20jar%20di%20cluster.png)

### 4. Memeriksa Hasil Output
Setelah proses MapReduce menampilkan status completed successfully, hasil agregasi penjualan dapat dilihat langsung melalui terminal dengan perintah berikut:
```bash
hdfs dfs -cat /user/hadoop/output_amazon_09/part-r-00000
```
![Hasil JAR](assets/hasil%20jar.png)
![Screenshot Hasil Output](assets/Screenshot%202026-04-28%20173702.png)

