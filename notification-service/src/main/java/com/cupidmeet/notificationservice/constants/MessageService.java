package com.cupidmeet.notificationservice.constants;

/**
 * Класс MessageService предоставляет статические константы,
 * которые используются в классе MessageServiceImpl для формирования
 * и отправки сообщений по электронной почте.
 */
public class MessageService {

    public static final String MAIL_MESSAGE = "message";
    public static final String MAIL_SENDER = "senderName";
    public static final String MAIL_SENDER_URL = "senderUrl";
    public static final String MAIL_RECEIVER = "receiverName";
    public static final String MAIL_HTML = "email-message.html";
    public static final String TOPIC_NAME = "notification-topic";
    public static final String MAIL_SUBJECT_MESSAGE = "You have received a new message from user: %s";
}
