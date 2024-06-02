package com.biwaby.projects.jokebot.aop;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class TelegramBotServiceLogAspect {

    @Before("execution(public * com.biwaby.projects.jokebot.service.TelegramBotService.handleUpdate(..)) && args(update)")
    public void beforeCallTelegramBotService(JoinPoint joinPoint, Update update) {
        System.out.println("tg bot call");

        if (update.message() != null) {
            log.info("TG BOT received update: ID={}, chatID={}, username={}",
                    update.updateId(),
                    update.message().chat().id(),
                    update.message().from().username());
        }
        else {
            log.info("TG BOT received update: ID={}", update.updateId());
        }
    }
}
