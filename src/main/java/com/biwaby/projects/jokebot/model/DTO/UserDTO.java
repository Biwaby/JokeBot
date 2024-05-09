package com.biwaby.projects.jokebot.model.DTO;

import com.biwaby.projects.jokebot.model.Role;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.biwaby.projects.jokebot.model.User}
 */

@Value
public class UserDTO implements Serializable {
    Long id;
    String username;
    boolean enabled;
    Set<Role> roles;
}