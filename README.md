# Анекдотический телеграм-бот на Java+Spring Boot+JPA
`Проект по лабораторным работам по дисциплине "Технологии программирования"(Java)`

Реализован чат-бот для Telegram с функционалом выдачи анекдотов. Имеется HTTP-интерфейс для добавления/изменения/удаления/просмотра анекдотов.

Для создания бота был использован [Java Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api/tree/master).

## **Авторизация:**

### Роли:
```
- Неавторизованный пользователь - может получать шутки (все/по ID/ТОП-5)
- USER - зарегистрированный пользователь, может добавлять шутки
- MODERATOR - права USER, может изменять/удалять шутки
- ADMIN - права USER и MODERATOR, может выдавать и отзывать роли у пользователей, просматривать/удалять пользователей, просматривать роли
```
### API методы
- Все пользователи
```
POST /users/register - регистрация нового пользователя
GET /jokes?page={page_num} - выдача всех шуткок (пагинация)
GET /jokes/{id} - выдача шутки по ID
GET /jokes/topFiveJokes - выдача ТОП-5 шуток по кол-ву вызовов
```
- Только зарегистрированные пользователи (только `USER`, `MODERATOR` и `ADMIN`)
```
POST /jokes - содание новой шутки
```
- Только для `MODERATOR` и `ADMIN`
```
PUT /jokes/{id} - изменение шутки
DELETE /jokes/{id} - удаление шутки
```
- Только для `ADMIN`
```
GET /actuator - Actuator (health, beans, info)
GET /users/getAll?page={page_num} - выдача всех пользователей (пагинация)
GET /users/getAllRoles - выдача всех ролей
PUT /users/grant?userId={userId}&roleId={roleId} - выдать роль пользователю
PUT /users/revoke?userId={userId}&roleId={roleId} - отнять роль у пользователя
DELETE /users/{id} - удаление пользователя
```
---
- Система сборки: Gradle
- Java: 21
- Spring Boot: 3.2.3
