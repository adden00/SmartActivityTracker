# SmartActivityTracker 
Приложение для взаимодействия с микроконтроллером для получения данных о состоянии здоровья (пульс, температура тела, количество шагов) по Bluetooth. 

### Используемые технологии
- исполльзование thread для постоянного получения данных в отдельном потоке
- использование Room для сохранения полученных данных в базу данных SQLite
- для отображения данных на графике применяется библиотека MPAndroidChart
- в пользовательском интерфейсе применен подход MVVM (ViewModel + LiveData)

### Демонстрация работы приложения
видеоролик, демонстрирующий работу приложения https://disk.yandex.ru/i/1_Ra2aAZXqIpfQ
