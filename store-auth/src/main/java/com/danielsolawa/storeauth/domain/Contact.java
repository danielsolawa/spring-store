package com.danielsolawa.storeauth.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "contact_user",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name= "user_id"))
    private List<User> users = new ArrayList<>();
    private Long userId;

    @Enumerated(EnumType.STRING)
    private MessageFrom messageFrom;

    private String conversationId;
    private String subject;
    private String content;
    private LocalDateTime date;


    public Contact addUser(User user){
        this.users.add(user);

        return this;
    }
}
