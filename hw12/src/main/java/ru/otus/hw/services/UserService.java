package ru.otus.hw.services;

import ru.otus.hw.models.User;

public interface UserService {

    User findByUsername(String username);
}
