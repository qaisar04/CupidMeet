package com.cupidmeet.notificationservice.service.impl;

import com.cupidmeet.notificationservice.dto.Message;
import com.cupidmeet.notificationservice.service.AbstractNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageServiceImpl extends AbstractNotificationService<Message> {

    public MessageServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        super(mailSender, templateEngine);
    }

    @Override
    protected String getTemplateName() {
        return "email-message.html";
    }

    @KafkaListener(topics = "notification-topic")
    public void sendMessage(Message message) {
        System.out.println(message);
       this.processNotification(message);
    }

    @Override
    protected String getSubject(Message notification) {
        return "You have received a new message from user: %s".formatted(notification.getSenderName());
    }

    @Override
    protected String getEmailAddress(Message notification) {
        return  notification.getReceiverEmail().toString();
    }

    @Override
    protected Map<String, Object> getTemplateModel(Message notification) {
        Map<String, Object> model = new HashMap<>();

        model.put("message", notification.getMessage());
        model.put("receiverName", notification.getReceiverName().toString());
        model.put("senderName", notification.getSenderName().toString());
        model.put("senderUrl", notification.getSenderProfileUrl().toString());

        return model;
    }
}
