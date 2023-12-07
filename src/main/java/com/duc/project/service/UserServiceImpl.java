package com.duc.project.service;

import com.duc.project.model.Role;
import com.duc.project.model.User;
import com.duc.project.model.UserRole;
import com.duc.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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
        User newUser =userRepository.save(user);
        return newUser;
    }


}
