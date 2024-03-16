package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.Joke;
import com.biwaby.projects.jokebot.model.PhotoMemes;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;
import java.io.File;

@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;
    private final JokeService jokeService;
    private final PhotoMemesService photoMemesService;

    public TelegramBotService(@Autowired TelegramBot telegramBot, JokeService jokeService, PhotoMemesService photoMemesService) {
        this.telegramBot = telegramBot;
        this.jokeService = jokeService;
        this.photoMemesService = photoMemesService;

        this.telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::handleUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, Throwable::printStackTrace);
    }

    private static final Keyboard keyboard = new ReplyKeyboardMarkup("")
            .addRow("Показать все анекдоты", "Показать все фото мемы")
            .addRow("Случайный анекдот", "Случайный фото мем")
            .resizeKeyboard(true);

    private void handleUpdate(Update update) {
        String botAnswer = "";
        String message = update.message().text();
        List<Joke> jokeList = jokeService.getAllJokes();
        List<PhotoMemes> photoMemesList = photoMemesService.getAllPhotoMemes();

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
                botAnswer += "<strong>" + joke.getJoke() + "</strong>\n\n" + "Дата создания: " + joke.getCreationDate() + "\n" + "Дата изменения: " + joke.getUpdatingDate() + "\n\n\n";
            }
        }
        else if (message.equals("Показать все фото мемы")) {
            for (PhotoMemes photoMeme : photoMemesList) {
                File photoPath = new File(photoMeme.getPhotoPath());
                String photoCaption = "Дата создания: " + photoMeme.getCreationDate() + "\n" + "Дата изменения: " + photoMeme.getUpdatingDate() + "\n";

                SendPhoto request = new SendPhoto(update.message().chat().id(), photoPath)
                        .caption(photoCaption)
                        .parseMode(ParseMode.HTML)
                        .disableNotification(true)
                        .replyMarkup(keyboard);
                this.telegramBot.execute(request);
            }
        }
        else if (message.equals("Случайный фото мем")) {
            Random random = new Random();
            PhotoMemes randPhotoMeme = photoMemesList.get(random.nextInt(photoMemesList.size()));
            File randPhotoPath = new File(randPhotoMeme.getPhotoPath());
            String randPhotoCaption = "Дата создания: " + randPhotoMeme.getCreationDate() + "\n" + "Дата изменения: " + randPhotoMeme.getUpdatingDate() + "\n";

            SendPhoto request = new SendPhoto(update.message().chat().id(), randPhotoPath)
                    .caption(randPhotoCaption)
                    .parseMode(ParseMode.HTML)
                    .disableNotification(true)
                    .replyMarkup(keyboard);
            this.telegramBot.execute(request);
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
