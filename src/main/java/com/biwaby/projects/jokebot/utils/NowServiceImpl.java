package com.biwaby.projects.jokebot.utils;

import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class NowServiceImpl implements NowService{
    @Override
    public Date getCurrentDate() {
        return new Date();
    }
}
