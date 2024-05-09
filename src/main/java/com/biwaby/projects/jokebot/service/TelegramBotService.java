package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.Joke;
import com.biwaby.projects.jokebot.service.interfaces.JokeService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;
    private final JokeService jokeService;
    private final HashMap<User, Integer> usersAndNumPage = new HashMap<>();

    private static final Keyboard keyboard = new ReplyKeyboardMarkup(
            new KeyboardButton("Показать все анекдоты"),
            new KeyboardButton("Случайный анекдот"),
            new KeyboardButton("Топ-5 популярных анекдотов"))
            .resizeKeyboard(true);

    private static final InlineKeyboardMarkup pagingKeyboard = new InlineKeyboardMarkup(
            new InlineKeyboardButton("⬅").callbackData("prevPage"),
            new InlineKeyboardButton("➡").callbackData("nextPage")
    );

    public TelegramBotService(@Autowired TelegramBot telegramBot, JokeService jokeService) {
        this.telegramBot = telegramBot;
        this.jokeService = jokeService;

        this.telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::handleUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, Throwable::printStackTrace);
    }

    private String buildPage(Page<Joke> page) {
        String answer = "";
        List<Joke> jokesFromPage = page.getContent();

        if (!jokesFromPage.isEmpty()) {
            answer = "Страница " + (page.getNumber() + 1) + " / " + page.getTotalPages() + "\n\n";
            for (Joke joke : jokesFromPage) {
                answer += joke.getJoke() + "\n\n" + "Дата создания: " + joke.getCreationDate() + "\n" + "Дата изменения: " + joke.getUpdatingDate() + "\n\n\n";
            }
        }
        else {
            answer = "Анекдоты закончились";
        }
        return answer;
    }

    private void handleUpdate(Update update) {
        String botAnswer = "";
        boolean isPagging = false;

        if (update.callbackQuery() == null) {
            String message = update.message().text();

            if (message.startsWith("/start")) {
                botAnswer = "Привет! Этот бот умеет шутить смешные (или не очень) анекдоты. Внизу есть кнопки, которые тебе помогут получить (или нет) дозу хохмы.";
            }
            else if (message.equals("Случайный анекдот")) {
                Joke randomJoke = jokeService.getRandomJoke(update.message().from().id());
                botAnswer = "<strong>" + randomJoke.getJoke() + "</strong>\n\n" + "Дата создания: " + randomJoke.getCreationDate() + "\n" + "Дата изменения: " + randomJoke.getUpdatingDate() + "\n\n\n";
            }
            else if (message.equals("Топ-5 популярных анекдотов")) {
                List<Joke> topList = jokeService.getTopFive();
                for (Joke joke : topList) {
                    botAnswer += "<strong>" + joke.getJoke() + "</strong>\n\n" + "Дата создания: " + joke.getCreationDate() + "\n" + "Дата изменения: " + joke.getUpdatingDate() + "\n\n\n";
                }
            }
            else if (message.equals("Показать все анекдоты")) {
                isPagging = true;
                Page<Joke> page = jokeService.getAllJokes(0);

                if (usersAndNumPage.containsKey(update.message().from())) {
                    int curPageNum = usersAndNumPage.get(update.message().from());

                    if (curPageNum + 1 > page.getTotalPages()) {
                        botAnswer = buildPage(page);
                    }
                    else {
                        usersAndNumPage.put(update.message().from(), curPageNum + 1);
                        botAnswer = buildPage(jokeService.getAllJokes(curPageNum + 1));
                    }
                }
                else {
                    usersAndNumPage.put(update.message().from(), 0);
                    botAnswer = buildPage(page);
                }
            }
            else {
                botAnswer = "Прости, я не понимаю что ты хочешь(";
            }

            if (isPagging) {
                SendMessage request = new SendMessage(update.message().chat().id(), botAnswer)
                        .parseMode(ParseMode.HTML)
                        .disableNotification(true)
                        .replyMarkup(pagingKeyboard);
                this.telegramBot.execute(request);
            }
            else {
                SendMessage request = new SendMessage(update.message().chat().id(), botAnswer)
                        .parseMode(ParseMode.HTML)
                        .disableNotification(true)
                        .replyMarkup(keyboard);
                this.telegramBot.execute(request);
            }
        }
        else {
            if (update.callbackQuery().data().equals("nextPage") || update.callbackQuery().data().equals("prevPage")) {
                Page<Joke> page = jokeService.getAllJokes(0);
                User user = update.callbackQuery().from();

                if (usersAndNumPage.containsKey(user)) {
                    int curPageNum = usersAndNumPage.get(update.callbackQuery().from());

                    if (update.callbackQuery().data().equals("nextPage")) {
                        if (curPageNum + 1 > page.getTotalPages()) {
                            usersAndNumPage.put(update.callbackQuery().from(), 0);
                            botAnswer = buildPage(page);
                        }
                        else {
                            usersAndNumPage.put(update.callbackQuery().from(), curPageNum + 1);
                            botAnswer = buildPage(jokeService.getAllJokes(curPageNum + 1));
                        }
                    }
                    else if (update.callbackQuery().data().equals("prevPage")) {
                        if (curPageNum - 1 < 0) {
                            usersAndNumPage.put(update.callbackQuery().from(), 0);
                            botAnswer = buildPage(page);
                        }
                        else {
                            usersAndNumPage.put(update.callbackQuery().from(), curPageNum - 1);
                            botAnswer = buildPage(jokeService.getAllJokes(curPageNum - 1));
                        }
                    }
                }
                else {
                    usersAndNumPage.put(update.callbackQuery().from(), 0);
                    botAnswer = buildPage(page);
                }
            }
            EditMessageText request = new EditMessageText(update.callbackQuery().message().chat().id(), update.callbackQuery().message().messageId(), botAnswer)
                    .replyMarkup(pagingKeyboard);
            this.telegramBot.execute(request);
        }
    }
}
