# Анекдотический телеграм-бот на Java+Spring Boot+JPA
`Задание по 1-ой лабораторной работе по дисциплине "Технологии программирования"(Java)`

Реализован чат-бот для Telegram с функционалом выдачи анекдотов и фото мемов. Имеется HTTP-интерфейс для добавления/изменения/удаления/просмотра анекдотов и фото мемов.

Для создания бота был использован [Java Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api/tree/master).

HTTP-интерфейс работает через JSON:
```
GET /jokes - выдача всех шуток
GET /jokes/id - выдача шутки с определенным ID
POST /jokes - создание новой шутки
PUT /jokes/id - изменение шутки
DELETE /jokes/id - удаление шутки
------------------------
GET /photoMemes - выдача всех фото мемов
GET /photoMemes/id - выдача фото мемам с определенным ID
POST /photoMemes - создание нового фото мема
PUT /photoMemes/id - изменение фото мема
DELETE /photoMemes/id - удаление фото мема
```
- Система сборки: Gradle
- Java: 21
- Spring Boot: 3.2.3
