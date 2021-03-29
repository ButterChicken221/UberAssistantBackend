package com.butterchicken.UberAssistant.controller;

import com.butterchicken.UberAssistant.dto.Note;
import com.butterchicken.UberAssistant.entity.User;
import com.butterchicken.UberAssistant.service.FirebaseMessagingService;
import com.butterchicken.UberAssistant.service.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/send-notification", method = RequestMethod.POST)
    public String sendNotification(@RequestBody Note note,
                                   @RequestParam String token) throws FirebaseMessagingException {
        return firebaseMessagingService.sendNotification(note, token);
    }

}
