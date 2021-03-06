package com.danielsolawa.storeauth.services;


import com.danielsolawa.storeauth.bootstrap.Bootstrap;
import com.danielsolawa.storeauth.domain.Inventory;
import com.danielsolawa.storeauth.domain.Role;
import com.danielsolawa.storeauth.domain.User;
import com.danielsolawa.storeauth.repositories.CategoryRepository;
import com.danielsolawa.storeauth.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;


    PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        passwordEncoder = new BCryptPasswordEncoder();
        Bootstrap bootstrap = new Bootstrap(passwordEncoder, userRepository, categoryRepository);
        bootstrap.run();
    }

    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("Thomas");
        user.setPassword("password");
        user.setRole(Role.USER);

        Inventory inventory = new Inventory();
        inventory.setUser(user);
        user.setInventory(inventory);

        User savedUser = userRepository.save(user);


        assertNotNull(savedUser);
        assertNotNull(savedUser.getInventory());

        Long id = savedUser.getId();

        User foundUser = userRepository.findOne(id);

        assertNotNull(foundUser);
        assertThat(user.getUsername(), equalTo(foundUser.getUsername()));
        assertThat(user.getPassword(), equalTo(foundUser.getPassword()));
        assertThat(user.getRole(), equalTo(foundUser.getRole()));


        //delete created user

        userRepository.delete(id);

        User deletedUser = userRepository.findOne(id);

        assertNull(deletedUser);

    }


    @Test
    @Transactional
    public void updateUser() {
        //create new user
        User user = new User();
        user.setUsername("John");
        user.setPassword("password");
        user.setRole(Role.USER);

        User returnedUser =  userRepository.save(user);

        assertNotNull(returnedUser);

        Long id = returnedUser.getId();
        String oldUsername = returnedUser.getUsername();
        String oldPassword = returnedUser.getPassword();

        returnedUser.setUsername("Samantha");
        returnedUser.setPassword("newPassword");

        userRepository.save(returnedUser);

        User updatedUser = userRepository.findOne(id);

        assertNotNull(updatedUser);
        assertThat(updatedUser.getUsername(), not(equalTo(oldUsername)));
        assertThat(updatedUser.getPassword(), not(equalTo(oldPassword)));

        userRepository.delete(id);

    }


    private Long getUserId(){
        return userRepository.findAll().get(0).getId();
    }
}
