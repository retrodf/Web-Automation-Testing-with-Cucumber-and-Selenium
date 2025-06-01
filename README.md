# Web Automation Testing dengan Cucumber dan Selenium
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.22.2-green.svg)](https://cucumber.io/)
[![Selenium](https://img.shields.io/badge/Selenium-4.29.0-brightgreen.svg)](https://selenium.dev/)
[![Allure](https://img.shields.io/badge/Allure-2.29.1-yellow.svg)](https://github.com/allure-framework/allure2)

## 📋 Deskripsi Proyek
Proyek ini merupakan implementasi **web automation testing** menggunakan **Cucumber BDD Framework** dan **Selenium WebDriver** dengan **Page Factory Pattern** untuk menguji aplikasi web "Education Fund Payment Management System for Zaidan Educare School". 

Proyek ini dikembangkan sebagai bagian dari mata kuliah **Praktikum Pengujian Perangkat Lunak** Semester 6, dengan fokus pada:
- ✅ **Behavior-Driven Development (BDD)** menggunakan Cucumber
- ✅ **Page Object Model (POM)** dengan Page Factory Pattern
- ✅ **Modular Step Definitions** untuk maintainability yang lebih baik
- ✅ **Comprehensive Reporting** dengan Allure Framework
- ✅ **Automated WebDriver Management** menggunakan WebDriverManager

## 🛠️ Spesifikasi Teknis

### Bahasa Pemrograman dan Tools
- **Bahasa Pemrograman**: Java 17
- **Build System**: Maven 3.8+
- **Framework Testing**: 
  - Cucumber 7.22.2 (BDD Framework)
  - JUnit 5.12.2 (Unit Testing Framework)
  - Selenium 4.29.0 (Web Automation)
- **Design Pattern**: Page Factory Pattern & Page Object Model
- **Reporting Tool**: Allure 2.29.1
- **Web Driver Management**: WebDriverManager 5.6.3 dari Bonigarcia
- **Browser**: Microsoft Edge (Configurable)

### 📁 Struktur Proyek
Proyek ini mengikuti arsitektur modular dengan **Page Factory Pattern** dan pemisahan tanggung jawab yang jelas:

```
📦 Web-Automation-Testing-with-Cucumber-and-Selenium/
│
├── 📁 src/
│   ├── 📁 main/java/          # Source code aplikasi (tidak digunakan dalam proyek ini)
│   └── 📁 test/
│       ├── 📁 java/io/cucumber/zaidan/
│       │   ├── 📁 config/               # 🔧 Konfigurasi Framework
│       │   │   ├── AllureManager.java      # Allure reporting utilities
│       │   │   └── WebDriverManager.java   # WebDriver lifecycle management
│       │   │
│       │   ├── 📁 hooks/               # 🎣 Cucumber Hooks
│       │   │   └── Hooks.java              # Setup & teardown operations
│       │   │
│       │   ├── 📁 pages/               # 📄 Page Object Model
│       │   │   ├── BasePage.java           # Base class dengan Page Factory
│       │   │   ├── LoginPage.java          # Login page elements & actions
│       │   │   └── DashboardPage.java      # Dashboard page elements & actions
│       │   │
│       │   ├── 📁 runner/              # 🏃 Test Runner
│       │   │   └── RunCucumberTest.java    # JUnit 5 Platform runner
│       │   │
│       │   └── 📁 steps/               # 📋 Step Definitions (Modular)
│       │       ├── CommonSteps.java        # Common setup/teardown steps
│       │       ├── LoginSteps.java         # Login functionality steps
│       │       ├── DashboardSteps.java     # Dashboard functionality steps
│       │       └── LogoutSteps.java        # Logout functionality steps
│       │
│       └── 📁 resources/io/cucumber/zaidan/
│           ├── login.feature           # 🥒 Feature file untuk pengujian login
│           └── logout.feature          # 🥒 Feature file untuk pengujian logout
│
├── 📁 target/                     # 🎯 Build outputs
│   ├── 📁 allure-results/             # Raw Allure test results
│   ├── 📁 cucumber-reports/           # Cucumber HTML reports
│   └── 📁 test-classes/               # Compiled test classes
│
├── 📁 docs/                       # 📚 GitHub Pages documentation
├── 📄 pom.xml                     # Maven configuration
├── 📄 mvnw & mvnw.cmd            # Maven Wrapper scripts
└── 📄 README.md                  # Project documentation
```

## ✨ Keunggulan Arsitektur

### 🏗️ **Modular Step Definitions**
Berbeda dari pendekatan monolitik, proyek ini menggunakan **pemisahan step definitions** berdasarkan fungsionalitas:
- **CommonSteps**: Manajemen WebDriver dan setup/teardown umum
- **LoginSteps**: Semua step definitions terkait login functionality
- **DashboardSteps**: Step definitions untuk dashboard interactions
- **LogoutSteps**: Step definitions untuk logout functionality

### 🏭 **Page Factory Pattern**
Implementasi Page Object Model menggunakan **@FindBy annotations** untuk:
- ✅ Automatic element initialization
- ✅ Cleaner code organization
- ✅ Better element management
- ✅ Reduced boilerplate code

### 🔧 **Configuration Management**
- **WebDriverManager**: Centralized driver lifecycle management
- **AllureManager**: Comprehensive reporting utilities
- **Hooks**: Centralized test setup dan teardown
```

## 🧪 Fitur yang Diuji
Proyek ini menguji dua fitur utama **Education Fund Payment Management System for Zaidan Educare School**:

### 🔐 **1. Login Functionality** 
**File**: `login.feature`
- ✅ **Positive Test**: Login berhasil dengan kredensial valid sebagai "bendahara"
  - Verifikasi navigasi ke dashboard
  - Verifikasi visibility navigation bar untuk role bendahara
- ❌ **Negative Test**: Login gagal dengan kredensial tidak valid
  - Verifikasi error message untuk username tidak terdaftar

### 🚪 **2. Logout Functionality**
**File**: `logout.feature`  
- ✅ **Administrator Logout**: Logout dari dashboard administrator
  - Login sebagai administrator
  - Klik logout button
  - Konfirmasi logout dengan tombol "Ya"
  - Verifikasi redirect ke login page
  - Verifikasi login page elements ditampilkan dengan benar

## 🏗️ Detail Implementasi

### 🎯 **1. Behavior-Driven Development (BDD)**
Menggunakan **Cucumber Gherkin syntax** untuk test scenarios yang readable:

```gherkin
Feature: Login Functionality
  As a user of the Education Fund Payment Management System
  I want to be able to login with valid credentials
  So that I can access the system dashboard

  Background:
    Given User has opened the browser
    And User has navigated on the login page Education Fund Payment Management System for Zaidan Educare School app

  @positive @bendahara
  Scenario: Successful login with valid bendahara credentials
    When User enters username "bendahara" and password "admin123"
    And User clicks on login button
    Then User is navigated to the dashboard page
    And User should be able to see navigation bar for bendahara
```

### 📊 **2. Comprehensive Reporting dengan Allure**
Fitur reporting yang powerful:
- 📸 **Auto Screenshots**: Tangkapan layar otomatis pada setiap step penting
- 📝 **Test Data Recording**: Username, URL, page title, timestamps
- 🔍 **Step-by-Step Tracking**: Detail setiap langkah dengan status dan durasi
- 📈 **Visual Analytics**: Grafik dan diagram hasil pengujian
- 🌐 **Environment Info**: Browser, OS, test environment details
- ⏱️ **Timeline View**: Urutan kronologis eksekusi test

### 🔧 **3. Smart WebDriver Management**
- 🚀 **Automatic Setup**: WebDriverManager handles driver binaries
- 🔄 **Lifecycle Management**: Proper driver initialization dan cleanup
- ⚙️ **Configurable Options**: Browser options untuk stability
- 🖥️ **Cross-Platform**: Support untuk Windows, Linux, Mac

### 🏭 **4. Modular Architecture Benefits**
- **Maintainability**: Separated concerns untuk easier debugging
- **Reusability**: Common steps dapat digunakan across features
- **Scalability**: Easy untuk menambah test cases baru
- **Readability**: Cleaner code organization

## 🚀 Cara Menjalankan Pengujian

### 📋 **Prerequisites**
- ☕ **Java Development Kit (JDK) 17+** 
- 🌐 **Internet connection** (untuk download dependencies dan WebDriver)
- 🖥️ **Microsoft Edge Browser** (atau sesuaikan konfigurasi browser)
- 📦 **Maven** (opsional, sudah disediakan Maven Wrapper)

### 🔧 **Setup dan Eksekusi**

#### **1. Clone Repository**
```bash
git clone <repository-url>
cd Web-Automation-Testing-with-Cucumber-and-Selenium
```

#### **2. Jalankan Tests**

**Menggunakan Maven Wrapper (Recommended):**
```powershell
# Windows PowerShell
.\mvnw clean test

# Command Prompt  
mvnw.cmd clean test

# Linux/Mac
./mvnw clean test
```

**Menggunakan Maven (jika sudah terinstall):**
```bash
mvn clean test
```

#### **3. Generate Allure Reports**
```powershell
# Generate report
.\mvnw allure:report

# Serve report (opens browser automatically)
.\mvnw allure:serve
```

#### **4. Running Specific Tests**
```bash
# Run only login tests
mvn test -Dcucumber.filter.tags="@positive"

# Run only logout tests  
mvn test -Dcucumber.filter.tags="@logout"

# Run with specific browser (if configured)
mvn test -Dbrowser=chrome
```

### ⚡ **Quick Start Commands**
```bash
# Complete test run dengan reports
.\mvnw clean test allure:serve

# Run tests only
.\mvnw test

# Clean rebuild
.\mvnw clean compile test-compile
```

## 📊 Hasil Pengujian

### 🎯 **Multiple Report Formats**

#### **1. 🥒 Cucumber HTML Reports**
Laporan sederhana dan clean yang menampilkan:
- ✅ Feature-level test results
- 📝 Step-by-step execution details  
- ⏱️ Execution timestamps
- 📍 **Location**: `target/cucumber-reports/report.html`

#### **2. 🚀 Allure Interactive Reports**
Laporan comprehensive dan interactive dengan:

**📈 Dashboard Overview:**
- Test execution summary (Total, Passed, Failed, Broken, Skipped)
- Success rate percentage
- Execution time analysis
- Trend graphs dan analytics

**🔍 Detailed Features:**
- **Timeline View**: Chronological test execution
- **Test Cases**: Individual scenario details dengan screenshots
- **Categories**: Test grouping by functionality
- **Environment Info**: Browser, OS, Java version
- **History**: Test trends over time
- **Behaviors**: BDD stories mapping

**📸 Visual Documentation:**
- Automatic screenshots at key steps
- Error screenshots on failures
- Browser information capture
- Test data attachments

### 📱 **Sample Test Output**
```
🧪 Test Execution Summary:
├── 📋 Features: 2
├── 🎯 Scenarios: 3  
├── ✅ Passed: 3
├── ❌ Failed: 0
├── ⚠️ Pending: 0
└── ⏱️ Total Time: ~45 seconds

📄 Test Cases:
├── ✅ Login with valid bendahara credentials
├── ✅ Login with invalid credentials (error handling)
└── ✅ Administrator logout functionality
```

### 🌐 **GitHub Pages Integration**

#### **Deploy Reports Online**
```bash
# 1. Generate reports
.\mvnw clean test allure:report

# 2. Copy to docs folder for GitHub Pages
Copy-Item -Recurse "target/site/allure-maven-plugin/*" "docs/"

# 3. Commit and push
git add docs/
git commit -m "📊 Update Allure test reports"
git push origin main
```

#### **Access Online Reports**
- 🔗 **URL Pattern**: `https://[username].github.io/[repository-name]/`
- 📱 **Mobile Responsive**: Reports accessible on all devices
- 🔄 **Auto Updates**: Fresh reports on every push

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
