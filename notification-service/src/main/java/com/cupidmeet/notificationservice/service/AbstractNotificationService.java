package com.cupidmeet.notificationservice.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * AbstractNotificationService - абстрактный класс для сервисов, которые создают и отправляют
 * email-уведомления.
 * <p>
 * Этот класс использует шаблонизатор для создания email и определяет общий процесс обработки
 * уведомлений, оставляя реализацию деталей подклассам.
 *
 * @param <T> тип данных для уведомлений
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractNotificationService<T> {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    /**
     * Обрабатывает уведомление: создает и отправляет email, используя данные уведомления.
     *
     * @param notification данные уведомления для заполнения содержимого и темы email
     */
    public void processNotification(T notification) {
        String emailAddress = getEmailAddress(notification);
        String subject = getSubject(notification);
        String body = generateEmailContent(notification);

        sendEmail(emailAddress, subject, body);
    }

    /**
     * Имя шаблона для создания email.
     *
     * @return имя шаблона
     */
    protected abstract String getTemplateName();

    /**
     * Тема email на основе данных notification.
     *
     * @param notification данные уведомления
     * @return тема email
     */
    protected abstract String getSubject(T notification);

    /**
     * Получает Email-адрес на основе данных notification.
     *
     * @param notification данные уведомления
     * @return email-адрес получателя
     */
    protected abstract String getEmailAddress(T notification);

    /**
     * Получает данные для шаблона email на основе данных notification.
     *
     * @param notification данные уведомления
     * @return модель данных для шаблона
     */
    protected abstract Map<String, Object> getTemplateModel(T notification);

    /**
     * Отправляет email на указанный адрес с темой и содержимым.
     *
     * @param to      email получателя
     * @param subject тема email
     * @param body    содержимое email в формате HTML
     */
    private void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            log.info("Email отправлен на адрес {}", to);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException("Ошибка при отправке email на " + to, e);
        }
    }

    /**
     * Создает содержимое email на основе шаблона и данных notification.
     *
     * @param notification данные уведомления для email
     * @return содержимое email в HTML формате
     */
    private String generateEmailContent(T notification) {
        Context context = new Context();
        context.setVariables(getTemplateModel(notification));
        return templateEngine.process(getTemplateName(), context);
    }
}