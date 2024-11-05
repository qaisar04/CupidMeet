package com.cupidmeet.notificationservice.service.impl;

import com.cupidmeet.notificationservice.dto.Message;
import com.cupidmeet.notificationservice.service.AbstractNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.cupidmeet.notificationservice.constants.MessageService.MAIL_HTML;
import static com.cupidmeet.notificationservice.constants.MessageService.MAIL_MESSAGE;
import static com.cupidmeet.notificationservice.constants.MessageService.MAIL_RECEIVER;
import static com.cupidmeet.notificationservice.constants.MessageService.MAIL_SENDER;
import static com.cupidmeet.notificationservice.constants.MessageService.MAIL_SENDER_URL;
import static com.cupidmeet.notificationservice.constants.MessageService.MAIL_SUBJECT_MESSAGE;
import static com.cupidmeet.notificationservice.constants.MessageService.TOPIC_NAME;

@Service
public class MessageServiceImpl extends AbstractNotificationService<Message> {

    public MessageServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        super(mailSender, templateEngine);
    }

    @Override
    protected String getTemplateName() {
        return MAIL_HTML;
    }

    @KafkaListener(topics = TOPIC_NAME)
    public void sendMessage(Message message) {
       this.processNotification(message);
    }

    @Override
    protected String getSubject(Message notification) {
        return MAIL_SUBJECT_MESSAGE.formatted(notification.getSenderName());
    }

    @Override
    protected String getEmailAddress(Message notification) {
        return  notification.getReceiverEmail().toString();
    }

    @Override
    protected Map<String, Object> getTemplateModel(Message notification) {
        Map<String, Object> model = new HashMap<>();

        model.put(MAIL_MESSAGE, notification.getMessage());
        model.put(MAIL_RECEIVER, notification.getReceiverName().toString());
        model.put(MAIL_SENDER, notification.getSenderName().toString());
        model.put(MAIL_SENDER_URL, notification.getSenderProfileUrl().toString());

        return model;
    }
}
