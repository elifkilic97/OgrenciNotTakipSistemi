# Öğrenci Not Takip Sistemi

Java ve SQL Server kullanılarak geliştirilmiş, rol tabanlı bir masaüstü öğrenci not takip uygulamasıdır.

Bu proje, Marmara Üniversitesi Bilgisayar Programcılığı programında **Nesne Yönelimli Programlama II** dersi kapsamında final projesi olarak geliştirilmiştir.

## Proje Raporu

Projenin tasarımı, veritabanı yapısı, sınıf mimarisi ve uygulama ekranları hakkında daha ayrıntılı bilgi için [Proje Raporu](docs/ProjeRaporu.pdf) dosyasını inceleyebilirsiniz.
## Proje Hakkında

Uygulama; öğrenci, öğretmen ve yönetici (admin) olmak üzere üç farklı kullanıcı rolünü desteklemektedir. Kullanıcılar giriş yaptıktan sonra rollerine göre farklı işlemlere erişebilir.

### Öğrenci

- Kayıtlı olduğu dersleri görüntüleyebilir.
- Vize ve final notlarını görüntüleyebilir.
- Not ortalamasını ve başarı durumunu görebilir.

### Öğretmen

- Sorumlu olduğu dersleri görüntüleyebilir.
- Derslerine kayıtlı öğrencileri listeleyebilir.
- Öğrencilerin vize ve final notlarını girebilir.
- Mevcut notları güncelleyebilir.

### Admin

- Öğrenci ve öğretmen ekleyebilir.
- Kullanıcı bilgilerini görüntüleyebilir, güncelleyebilir ve silebilir.
- Ders oluşturabilir ve silebilir.
- Öğrencileri derslere atayabilir.
- Ders ve öğretmen eşleştirmelerini yönetebilir.
- Sistemle ilgili temel raporları görüntüleyebilir.

## Kullanılan Teknolojiler

- Java
- Java Swing
- JDBC
- Microsoft SQL Server
- IntelliJ IDEA

## Proje Mimarisi

Proje, uygulamanın farklı sorumluluklarını birbirinden ayırmak amacıyla katmanlı bir yapıda geliştirilmiştir.

```text
src/
└── com.notTakipSistemi/
    ├── controller/
    ├── dao/
    ├── model/
    ├── service/
    ├── ui/
    └── utils/
```

### Model

Uygulamanın temel veri nesnelerini içerir.

Başlıca model sınıfları:

- User
- Student
- Teacher
- Admin
- Course
- Grade

### DAO

Veritabanı erişim işlemlerinden sorumludur. JDBC ve SQL sorguları kullanılarak SQL Server üzerindeki verilerle CRUD işlemleri gerçekleştirilir.

### Service

Uygulamanın iş kurallarını içerir ve kullanıcı arayüzü ile DAO katmanı arasında bağlantı sağlar.

### UI

Java Swing kullanılarak geliştirilen kullanıcı arayüzlerini içerir. Kullanıcının rolüne göre öğrenci, öğretmen veya admin paneli açılır.

## Nesne Yönelimli Programlama

Projede temel nesne yönelimli programlama prensipleri uygulanmıştır:

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction

`User` sınıfı temel kullanıcı yapısını temsil eder. `Student`, `Teacher` ve `Admin` sınıfları kullanıcı rollerine göre özelleştirilmiştir.

## Veritabanı

Uygulama Microsoft SQL Server kullanmaktadır. Java ile SQL Server arasındaki bağlantı JDBC üzerinden gerçekleştirilmektedir.

Temel tablolar:

- Users
- Students
- Teachers
- Admins
- Courses
- Grades
- Course_Student

## Veritabanı Bağlantısı

Veritabanı bağlantı bilgileri güvenlik amacıyla kaynak kod içerisinde tutulmamaktadır.

Uygulama aşağıdaki environment variable'ları kullanır:

```text
DB_URL
DB_USER
DB_PASSWORD
```

Örnek yapılandırma:

```text
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=NotTakipSistemiDB;encrypt=true;trustServerCertificate=true
DB_USER=your_database_user
DB_PASSWORD=your_database_password
```

IntelliJ IDEA kullanılıyorsa bu değerler aşağıdaki bölümden tanımlanabilir:

```text
Run → Edit Configurations → Environment variables
```

## Gereksinimler

Projeyi çalıştırmak için:

- JDK 17 veya üzeri
- Microsoft SQL Server
- Microsoft SQL Server JDBC Driver

gereklidir.

## Uygulamayı Çalıştırma

Projeyi klonladıktan sonra gerekli SQL Server veritabanı oluşturulmalı ve veritabanı bağlantısı için gerekli environment variable'lar tanımlanmalıdır.

Uygulamanın başlangıç sınıfı:

```text
com.notTakipSistemi.ui.LoginFrame
```

Uygulama çalıştırıldığında giriş ekranı açılır. Kullanıcı, kullanıcı adı ve şifresi ile giriş yaptıktan sonra rolüne uygun panele yönlendirilir.

## Projede Kazanılan Deneyimler

Bu proje kapsamında:

- Java ile nesne yönelimli uygulama geliştirme
- Nesne yönelimli programlama prensiplerini uygulama
- Katmanlı yazılım mimarisi kullanma
- JDBC ile veritabanı bağlantısı kurma
- SQL sorguları ve CRUD işlemleri gerçekleştirme
- Rol tabanlı kullanıcı işlemleri geliştirme
- Java Swing ile masaüstü kullanıcı arayüzü oluşturma

konularında uygulamalı deneyim kazanılmıştır.

## Not

Bu proje eğitim amacıyla geliştirilmiştir ve mevcut haliyle yerel bir Microsoft SQL Server veritabanı ile çalışmaktadır.