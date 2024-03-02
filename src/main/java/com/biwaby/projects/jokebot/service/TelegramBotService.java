package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.Joke;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;
    private final JokeService jokeService;

    public TelegramBotService(@Autowired TelegramBot telegramBot, JokeService jokeService) {
        this.telegramBot = telegramBot;
        this.jokeService = jokeService;

        this.telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::handleUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, Throwable::printStackTrace);
    }

    private static final Keyboard keyboard = new ReplyKeyboardMarkup(
            new KeyboardButton("Показать все анекдоты"),
            new KeyboardButton("Случайный анекдот")
    );

    private void handleUpdate(Update update) {
        String botAnswer = "";
        String message = update.message().text();
        List<Joke> jokeList = jokeService.getAllJokes();

        if (message.startsWith("/start")) {
            botAnswer = "Привет! Этот бот умеет шутить смешные (или не очень) анекдоты. Внизу есть кнопки, которые тебе помогут получить (или нет) дозу хохмы.";
        }
        else if (message.equals("Случайный анекдот")) {
            Random random = new Random();
            Joke randJoke = jokeList.get(random.nextInt(jokeList.size()));
            botAnswer = "<strong>" + randJoke.getJoke() + "</strong>\n\n" + "Дата создания: " + randJoke.getCreationDate() + "\n" + "Дата изменения: " + randJoke.getUpdatingDate() + "\n\n\n";
        }
        else if (message.equals("Показать все анекдоты")) {
            for (Joke joke : jokeList) {
                botAnswer += joke.getId() + ".\n<strong>" + joke.getJoke() + "</strong>\n\n" + "Дата создания: " + joke.getCreationDate() + "\n" + "Дата изменения: " + joke.getUpdatingDate() + "\n\n\n";
            }
        }
        else {
            botAnswer = "Прости, я не понимаю что ты хочешь(";
        }

        SendMessage request = new SendMessage(update.message().chat().id(), botAnswer)
                .parseMode(ParseMode.HTML)
                .disableNotification(true)
                .replyMarkup(keyboard);
        this.telegramBot.execute(request);
    }
}
