package com.duc.project.service;

import com.duc.project.model.User;

public interface UserService {
    User findByUserName(String userName);

    User saveUser(User user, String url);
    public void removeSessionMessage();
    public void sendEmail(User user, String url);
    public boolean verifyAccount(String verificationCode);
}
