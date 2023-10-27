# SpringBoot-Humidity-Tracking-MQTT-Api
# Uzaktan Yönetim ve Veri Gözlemleme Projesi

Bu proje, Arduino cihazları ile iletişim kurarak çeşitli verileri gözlemlemek ve cihazları uzaktan yönetmek amacı taşımaktadır. Proje, MQTT protokolü kullanarak cihazlarla haberleşmektedir ve Spring Framework ile geliştirilen bir API üzerinden yönetilmektedir. Projede aynı zamanda nemlendirme ve verimlilik izleme özellikleri de bulunmaktadır.

## Özellikler

- **Uzaktan Yönetim**: Arduino cihazlarıyla MQTT üzerinden iletişim kurarak uzaktan yönetim imkanı.
- **Veri Gözlemleme**: Cihazlardan gelen verileri gözlemleme ve analiz etme.
- **Nemlendirme**: Ortamın nem durumunu kontrol etme ve gerekirse nemlendirme işlemi yapma.
- **Verimlilik İzleme**: Sistem üzerinden verimliliği izleme ve raporlama.

## Teknolojiler

- **Spring Boot**: API geliştirme.
- **MQTT**: Cihazlarla haberleşme protokolü.
- **Amazon Web Services (AWS)**: Uygulamanın deploy edildiği sunucu.

## Kurulum

1. Projeyi klonlayın:
2.  [git clone](https://github.com/farukaydogan/SPRING-BOOT-WATERING-AND-MANAGMENT-API.git)

markdown
Copy code
2. Gerekli bağımlılıkları yükleyin:
mvn install

markdown
Copy code
3. Uygulamayı çalıştırın:
mvn spring-boot:run

markdown
Copy code

## API Kullanımı

API üzerinden çeşitli işlemler yapabilirsiniz. Aşağıda bazı örnekler bulunmaktadır:

### Cihaz Durumu Getirme

`GET /api/devices/{deviceId}`

Bu endpoint, belirtilen ID'ye sahip cihazın durumunu getirir.

### Cihazı Güncelleme

`PUT /api/devices/{deviceId}`

Bu endpoint, belirtilen ID'ye sahip cihazın durumunu güncellemeye yarar.

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Daha fazla bilgi için `LICENSE` dosyasına bakınız.

## İletişim

Herhangi bir sorunuz veya öneriniz varsa, lütfen [issues](https://github.com/farukaydogan/SPRING-BOOT-WATERING-AND-MANAGMENT-API/issues) sayfasını kullanarak bize ulaşın.



It is an API written to determine the moisture content of the crops.

<img width="1118" alt="Screenshot 2023-10-28 at 02 00 13" src="https://github.com/farukaydogan/ARGE-WATERING/assets/57232389/f5a1ca67-a4af-4ff2-b5b8-f4681f595d26">
<img width="1135" alt="Screenshot 2023-10-28 at 02 00 27" src="https://github.com/farukaydogan/ARGE-WATERING/assets/57232389/6c315655-e005-4356-8933-28e615532130">


https://github.com/farukaydogan/SpringBoot-Humidity-Tracking-Web-Socket-Api/assets/57232389/40a8a0e5-97b2-49d3-a088-2c0ee940ef55
<img width="1182" alt="Screenshot 2023-10-28 at 02 00 45" src="https://github.com/farukaydogan/ARGE-WATERING/assets/57232389/546b4543-d1cb-4c62-8a55-4b23ce4f7c15">
