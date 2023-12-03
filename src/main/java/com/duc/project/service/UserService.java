package com.duc.project.service;

import com.duc.project.model.User;

public interface UserService {
    User findByUserName(String userName);
}
