package com.duc.project.service;

import com.duc.project.model.Role;
import com.duc.project.model.User;
import com.duc.project.model.UserRole;
import com.duc.project.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User saveUser(User user, String url) {
        String password = passwordEncoder.encode(user.getPassWord());
        user.setPassWord(password);
        Role role = new Role();
        role.setName("USER");
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setUserRoles(userRoles);
        user.setEnabled(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        User newUser =userRepository.save(user);
        if(newUser != null) {
            sendEmail(newUser, url);
        }
        return newUser;
    }

    @Override
    public void removeSessionMessage() {
        HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).
                getRequest().getSession();
        session.removeAttribute("msg");
    }

    @Override
    public void sendEmail(User user, String url) {
        String from = "leanhduc04032003@gmail.com";
        String to = user.getEmail();
        String subject = "Account Verfication";
        String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Duc";

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Duc");
            helper.setTo(to);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getFullName());
            String siteUrl = url + "/verify?code=" + user.getVerificationCode();

            System.out.println(siteUrl);

            content = content.replace("[[URL]]", siteUrl);

            helper.setText(content, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verifyAccount(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null) {
            return false;
        } else {

            user.setEnabled(true);
            user.setVerificationCode(null);

            userRepository.save(user);

            return true;
        }
    }


}
