#### ДЗ-16: MessageServer

Задачи:
- Сервер из ДЗ-15 разделить на три приложения:
  - MessageServer
  - Frontend
  - DBServer
- Запускать Frontend и DBServer из MessageServer
- Сделать MessageServer сокет-сервером, Frontend и DBServer клиентами
- Пересылать сообщения с Frontend на DBService через MessageServer

Как запустить:
```
cd C:\jetty-distribution-9.4.18.v20190429
java -jar start.jar
cd C:\Git\java\otus-java\otus-java-2018-12-rem\task16
java -jar messageserver\target\messageserver.jar
```