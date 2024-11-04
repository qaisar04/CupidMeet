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
 * AbstractNotificationService is an abstract base class for notification services
 * that handle generating and sending emails based on notification data of a specified type.
 * <p>
 * This class uses a template engine for email body generation and provides a generic
 * workflow for processing notifications, delegating specifics to subclasses.
 *
 * @param <T> the type of notification data to be processed
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractNotificationService<T> {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    /**
     * Processes a notification by generating and sending an email with content and details
     * derived from the given notification data.
     *
     * @param notification the notification data used to populate the email content and details
     */
    public void processNotification(T notification) {
        String emailAddress = getEmailAddress(notification);
        String subject = getSubject(notification);
        String body = generateEmailContent(notification);

        sendEmail(emailAddress, subject, body);
    }

    /**
     * Returns the name of the FreeMarker template to use for generating email content.
     * Each subclass should specify the template name relevant to the specific notification type.
     *
     * @return the name of the FreeMarker template
     */
    protected abstract String getTemplateName();

    /**
     * Returns the subject line of the email based on the notification data.
     *
     * @param notification the notification data
     * @return the subject of the email
     */
    protected abstract String getSubject(T notification);

    /**
     * Returns the email address of the recipient based on the notification data.
     *
     * @param notification the notification data
     * @return the recipient's email address
     */
    protected abstract String getEmailAddress(T notification);

    /**
     * Returns the model data to be used in the FreeMarker template for generating the email body.
     *
     * @param notification the notification data
     * @return a map of model data for the template
     */
    protected abstract Map<String, Object> getTemplateModel(T notification);

    /**
     * Sends an email with the specified recipient address, subject, and HTML content.
     *
     * @param to      the recipient's email address
     * @param subject the subject of the email
     * @param body    the body content of the email in HTML format
     */
    private void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
            log.info("Sent email to {}", to);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException("Failed to send email to " + to, e);
        }
    }

    /**
     * Generates the email content using the FreeMarker template specified by {@link #getTemplateName()}
     * and populates it with data from {@link #getTemplateModel(Object)}.
     *
     * @param notification the notification data used to populate the email content
     * @return the generated email content in HTML format
     */
    private String generateEmailContent(T notification) {
        Context context = new Context();
        context.setVariables(getTemplateModel(notification));

        return templateEngine.process(getTemplateName(), context);
    }
}
