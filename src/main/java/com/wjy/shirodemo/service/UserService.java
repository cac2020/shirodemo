package com.wjy.shirodemo.service;

import com.wjy.shirodemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<User,Long> {

    User findByUsername(String username);

    //void saveUser(User user);
}
