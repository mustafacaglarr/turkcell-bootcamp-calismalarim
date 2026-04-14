# Kotlin OOP Temelleri

Bu README, referans aldığın repodaki mantığa benzer şekilde ilerler:

- kapsülleme için `BankAccount`
- kalıtım için `Animal` ve `Dog`
- çok biçimlilik için `Shape`, `Circle`, `Square`
- soyutlama için `Vehicle`, `Car`, `Bike`

* * *

## 1. Abstraction (Soyutlama)

Soyutlama, bir yapının tüm detaylarını göstermek yerine sadece gerekli kuralları ve davranışları öne çıkarmaktır.
Böylece sistemin nasıl çalıştığını değil, ne yapması gerektiğini tanımlarız.

Bu projede "araç" fikri doğrudan tek bir somut nesne olarak değil, ortak bir yapı olarak ele alınmıştır.
Yani önce genel bir araç davranışı tanımlanır, sonra bu davranış alt sınıflarda tamamlanır.

- Referans: [`AbstractionExample.kt`](AbstractionExample.kt)
- Referans satırları: 1, 2, 5, 11
- Uygulama:
- `Vehicle` sınıfı `abstract` olarak tanımlanmıştır.
- `drive()` metodu soyut bir metottur; yani burada sadece zorunlu bir davranış tanımlanır.
- `Car` ve `Bike` sınıfları bu davranışı kendi içlerinde `override` ederek tamamlar.
- Böylece `Vehicle`, doğrudan üretilen bir nesneden çok, alt sınıflara yol gösteren bir şablon görevi görür.

* * *

## 2. Encapsulation (Kapsülleme)

Kapsülleme, veriyi ve o veri üzerinde işlem yapan metotları aynı sınıf içinde toplama prensibidir.
Asıl amaç, veriye dışarıdan kontrolsüz müdahaleyi engelleyip daha güvenli bir yapı kurmaktır.

Bu projede banka hesabı örneği seçilmiştir.
Çünkü bakiye gibi bir bilgiye herkesin doğrudan erişmesi mantıklı değildir; bunun kontrollü şekilde yönetilmesi gerekir.

- Referans: [`EncapsulationExample.kt`](EncapsulationExample.kt)
- Referans satırları: 1, 2, 11, 20
- Uygulama:
- `BankAccount` sınıfı içindeki `balance` değişkeni `private` olarak tanımlanmıştır.
- Bu sayede sınıf dışından `balance` değerine doğrudan müdahale edilemez.
- Para ekleme işlemi `deposit()` ile, para çekme işlemi `withdraw()` ile yapılır.
- `showBalance()` metodu ise mevcut veriyi kontrollü şekilde gösterir.
- Böylece nesnenin iç durumu rastgele değil, kurallara uygun şekilde değişir.

* * *

## 3. Inheritance (Kalıtım)

Kalıtım, bir sınıfın başka bir sınıftan özellik ve davranış devralmasıdır.
Bu sayede ortak yapılar tek bir yerde toplanır ve tekrar tekrar aynı kodu yazmaya gerek kalmaz.

Bu projede temel örnek olarak hayvanlar kullanılmıştır.
`Dog`, temel özellikleri `Animal` sınıfından alırken kendi özel davranışını da ekler.

- Referans: [`InheritanceExample.kt`](InheritanceExample.kt)
- Referans satırları: 1, 2, 7, 8
- Uygulama:
- `Animal` sınıfı ortak bir temel sınıf olarak tanımlanmıştır.
- `Dog` sınıfı, `Animal(name)` yapısı ile bu sınıftan miras alır.
- Böylece `Dog`, `eat()` metodunu yeniden yazmadan kullanabilir.
- Ek olarak kendi sınıfına özel olan `bark()` davranışını da ekler.
- Bu yapı, üst sınıftaki ortak özelliklerin alt sınıfta tekrar edilmeden kullanılmasını sağlar.

* * *

## 4. Polymorphism (Çok Biçimlilik)

Çok biçimlilik, aynı isimli bir metodun farklı sınıflarda farklı davranış gösterebilmesidir.
Bu prensip sayesinde nesneleri ortak bir üst türde tutup, her birinden kendi davranışını almaya devam ederiz.

Bu projede şekiller üzerinden bir örnek kurulmuştur.
Hepsi `Shape` olarak ele alınabilir, ama çizim davranışları birbirinden farklıdır.

- Referans: [`PolymorphismExample.kt`](PolymorphismExample.kt)
- Referans satırları: 1, 2, 7, 13, 20
- Uygulama:
- `Shape` sınıfı içinde `draw()` adında ortak bir metot tanımlanmıştır.
- `Circle` ve `Square`, bu metodu kendi ihtiyaçlarına göre `override` eder.
- `main()` içinde nesneler `List<Shape>` olarak tutulur.
- Döngü içinde her nesneye aynı `draw()` çağrısı yapılsa da ekrana farklı sonuç basılır.
- İşte bu, çok biçimliliğin en temel kullanımlarından biridir.

* * *

## Konular Arasındaki Bağlantı

Bu projede konular birbirinden kopuk değil, birbiriyle bağlantılı şekilde düşünülebilir:

- `Inheritance`, alt sınıfın üst sınıftan özellik almasını sağlar.
- `Polymorphism`, bu alt sınıfların aynı metodu farklı şekilde çalıştırmasını sağlar.
- `Abstraction`, hangi davranışın zorunlu olduğunu belirleyen genel bir çerçeve kurar.
- `Encapsulation`, nesnenin iç verisini koruyarak yapının daha güvenli olmasını sağlar.

Kısa hâliyle:

- `Animal -> Dog` kalıtımı gösterir.
- `Shape -> Circle / Square` kalıtım + çok biçimliliği birlikte gösterir.
- `Vehicle -> Car / Bike` soyut yapının alt sınıflarla tamamlanmasını gösterir.
- `BankAccount` ise verinin nasıl korunduğunu gösterir.

* * *

## Nasıl Çalıştırılır?

Her dosya ayrı bir örnek olduğu için tek tek derleyip çalıştırabilirsin.

Örnek:

```bash
kotlinc EncapsulationExample.kt -include-runtime -d EncapsulationExample.jar
java -jar EncapsulationExample.jar
```

Aynı yapıyla diğer dosyaları da çalıştırabilirsin:

```bash
kotlinc InheritanceExample.kt -include-runtime -d InheritanceExample.jar
java -jar InheritanceExample.jar
```

```bash
kotlinc PolymorphismExample.kt -include-runtime -d PolymorphismExample.jar
java -jar PolymorphismExample.jar
```

```bash
kotlinc AbstractionExample.kt -include-runtime -d AbstractionExample.jar
java -jar AbstractionExample.jar
```

* * *
