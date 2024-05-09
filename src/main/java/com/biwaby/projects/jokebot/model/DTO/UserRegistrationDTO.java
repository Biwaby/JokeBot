package com.biwaby.projects.jokebot.model.DTO;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.biwaby.projects.jokebot.model.User}
 */

@Value
public class UserRegistrationDTO implements Serializable {
    Long id;
    String username;
    String password;
}