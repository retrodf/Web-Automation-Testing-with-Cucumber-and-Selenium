# Web Automation Testing dengan Cucumber dan Selenium

## Deskripsi Proyek
Proyek ini merupakan implementasi web automation testing menggunakan Cucumber dan Selenium untuk menguji aplikasi "Education Fund Payment Management System for Zaidan Educare School". Proyek ini dibuat sebagai bagian dari mata kuliah Praktikum Pengujian Perangkat Lunak, Semester 6.

## Spesifikasi Teknis

### Bahasa Pemrograman dan Tools
- **Bahasa Pemrograman**: Java 17
- **Build System**: Maven
- **Framework Testing**: 
  - Cucumber 7.22.2 (BDD Framework)
  - JUnit 5.12.2 (Unit Testing Framework)
  - Selenium 4.29.0 (Web Automation)
- **Reporting Tool**: Allure 2.29.1
- **Web Driver Management**: WebDriverManager 5.6.3 dari Bonigarcia
- **Browser**: Microsoft Edge

### Struktur Proyek
Proyek ini mengikuti struktur standar Maven dengan penambahan direktori khusus untuk keperluan testing:

```
WebAutomation/
│
├── src/
│   ├── main/java/       # Source code aplikasi (tidak digunakan dalam proyek ini)
│   └── test/
│       ├── java/io/cucumber/zaidan/
│       │   ├── config/         # Konfigurasi untuk Allure dan WebDriver
│       │   │   ├── AllureManager.java
│       │   │   └── WebDriverManager.java
│       │   ├── hooks/          # Cucumber hooks
│       │   │   └── Hooks.java
│       │   ├── pages/          # Page Objects
│       │   │   └── Pages.java
│       │   ├── runner/         # Runner untuk menjalankan test
│       │   │   └── RunCucumberTest.java
│       │   └── steps/          # Step definitions untuk Cucumber
│       │       └── StepDefinitions.java
│       └── resources/io/cucumber/zaidan/
│           ├── login.feature   # Feature file untuk pengujian login
│           └── logout.feature  # Feature file untuk pengujian logout
│
├── drivers/              # WebDriver binaries (bila diperlukan)
├── target/               # Output dari build dan hasil test
│   ├── allure-results/   # Hasil reporting Allure
│   └── cucumber-reports/ # Hasil reporting Cucumber
│
├── pom.xml               # Konfigurasi Maven
├── mvnw                  # Maven Wrapper script (Linux/Mac)
└── mvnw.cmd              # Maven Wrapper script (Windows)
```

## Fitur yang Diuji
Proyek ini menguji dua fitur utama aplikasi Education Fund Payment Management System:

### 1. Login Functionality
Pengujian ini mencakup dua skenario:
- **Skenario 1**: Login berhasil menggunakan kredensial valid sebagai "bendahara"
- **Skenario 2**: Login gagal menggunakan kredensial tidak valid (username tidak terdaftar)

### 2. Logout Functionality
Pengujian ini mencakup satu skenario:
- **Skenario 1**: Verifikasi halaman login setelah logout dari akun "administrator"

## Detail Implementasi

### 1. Behavior-Driven Development (BDD)
Proyek ini menggunakan BDD dengan Cucumber untuk mendefinisikan skenario pengujian dalam bahasa Gherkin yang mudah dibaca, baik oleh tim teknis maupun non-teknis:

```gherkin
Feature: Login Functionality

  Scenario: Check login is successful with valid credentials as role "bendahara"
    Given User has opened the browser
    And User has navigated on the login page Education Fund Payment Management System for Zaidan Educare School app
    When User enters username "bendahara" and password "admin123"
    And User clicks on login button
    Then User is navigated to the dashboard page
    And User should be able to see navigation bar for bendahara
```

### 2. Reporting dengan Allure
Proyek ini menggunakan Allure untuk menghasilkan laporan pengujian yang komprehensif dan interaktif dengan fitur:
- Screenshot otomatis pada setiap langkah penting
- Perekaman data test seperti username, URL, judul halaman
- Pelacakan langkah-langkah (step) dengan detail
- Informasi status pengujian (passed/failed)

### 3. Pengelolaan WebDriver
WebDriverManager digunakan untuk otomatisasi pengaturan dan manajemen WebDriver, sehingga tidak perlu mengunduh dan mengkonfigurasi WebDriver secara manual.

### 4. Struktur Kode yang Terorganisir
- **Page Object Model (POM)** untuk memisahkan kode pengujian dari interaksi dengan UI
- **Config Package** untuk mengelola konfigurasi dan dependensi
- **Hooks** untuk menangani setup dan teardown pengujian
- **Step Definitions** untuk implementasi langkah-langkah yang didefinisikan dalam feature files

## Cara Menjalankan Pengujian

### Prasyarat
- Java Development Kit (JDK) 17 atau lebih tinggi
- Maven (opsional, karena sudah disediakan Maven Wrapper)
- Internet untuk mengunduh dependensi Maven dan WebDriver

### Langkah Menjalankan Pengujian

1. **Clone repositori** (jika menggunakan Git):
   ```powershell
   git clone [URL_REPOSITORY]
   cd WebAutomation
   ```

2. **Jalankan pengujian** menggunakan Maven Wrapper:
   ```powershell
   ./mvnw clean test
   ```
   Atau jika menggunakan Maven yang sudah terinstal:
   ```powershell
   mvn clean test
   ```

3. **Menghasilkan laporan Allure**:
   ```powershell
   ./mvnw allure:report
   ```
   Atau:
   ```powershell
   mvn allure:report
   ```

4. **Melihat laporan Allure**:
   ```powershell
   ./mvnw allure:serve
   ```
   Atau:
   ```powershell
   mvn allure:serve
   ```
   Perintah ini akan membuka browser secara otomatis dengan laporan Allure.

## Hasil Pengujian

Setelah menjalankan pengujian, Anda akan mendapatkan hasil sebagai berikut:

### 1. Laporan Cucumber
Laporan HTML sederhana yang menunjukkan ringkasan eksekusi setiap skenario pengujian, tersedia di:
```
target/cucumber-reports/report.html
```

### 2. Laporan Allure
Laporan interaktif dan detail yang menampilkan:
- **Ringkasan pengujian**: jumlah test yang dijalankan, berhasil, gagal, dan dilewati
- **Grafik dan diagram**: visualisasi hasil pengujian
- **Timeline**: urutan kronologis eksekusi test
- **Detail skenario**: langkah-langkah yang dijalankan dengan status dan durasi
- **Screenshot**: tangkapan layar yang diambil otomatis selama eksekusi
- **Informasi lingkungan**: browser, OS, dan informasi lainnya

### Tampilan Hasil Pengujian
Laporan Allure menampilkan:

1. **Tampilan Feature Level**:
   - Login Functionality (2 test case)
   - Logout Functionality (1 test case)

2. **Tampilan Test Case Detail**:
   - Login Functionality - Check login is successful with valid credentials as role "bendahara"
   - Login Functionality - Check login is un-successful with invalid credentials...
   - Logout Functionality - Check login page after login using valid credentials as role "administrator"

Catatan: Laporan Allure menampilkan skenario pengujian dalam dua tampilan berbeda (grouped by feature dan individual test cases) yang meskipun terlihat seperti duplikat, sebenarnya adalah tampilan berbeda dari test yang sama untuk kemudahan analisis.

## Catatan Tambahan

1. **Konfigurasi Browser**:
   Proyek ini menggunakan Microsoft Edge sebagai browser default. Untuk mengubah browser, modifikasi kelas WebDriverManager.java.

2. **URL Aplikasi**:
   URL aplikasi yang diuji adalah `http://ptbsp.ddns.net:6882`. Pastikan aplikasi ini dapat diakses sebelum menjalankan pengujian.

3. **Pengelolaan Kredensial**:
   Proyek ini menggunakan kredensial hardcoded untuk tujuan demonstrasi. Pada implementasi nyata, disarankan untuk menggunakan environment variables atau file konfigurasi eksternal.

4. **Tangkapan Layar**:
   Tangkapan layar otomatis diambil pada:
   - Halaman login
   - Input kredensial
   - Keadaan akhir test
   - Jika terjadi kegagalan test

## Kesimpulan

Proyek Web Automation ini mendemonstrasikan implementasi pengujian otomatis untuk aplikasi web menggunakan pendekatan BDD dengan Cucumber, Selenium, dan pelaporan yang komprehensif menggunakan Allure. Proyek ini dapat dijadikan sebagai dasar untuk pengembangan framework pengujian otomatis yang lebih lengkap untuk aplikasi web lainnya.
