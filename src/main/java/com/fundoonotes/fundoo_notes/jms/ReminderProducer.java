package com.fundoonotes.fundoo_notes.jms;

import com.fundoonotes.fundoo_notes.config.RabbitMQConfig;
import com.fundoonotes.fundoo_notes.service.EmailService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ReminderProducer {

    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmailService emailService;

    @Async("taskExecutor")
    public void sendReminder(String email, String noteTitle) {
        boolean sentViaRabbit = false;
        try {
            if (rabbitTemplate != null) {
                String message = email + "|" + noteTitle;
                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.REMINDER_EXCHANGE,
                        RabbitMQConfig.REMINDER_ROUTING_KEY,
                        message
                );
                System.out.println("Reminder sent to RabbitMQ: " + message);
                sentViaRabbit = true;
            }
        } catch (Exception e) {
            System.err.println("RabbitMQ dispatch failed, falling back to direct email: " + e.getMessage());
        }

        if (!sentViaRabbit) {
            System.out.println("Sending reminder email directly to: " + email);
            emailService.sendReminderEmail(email, noteTitle);
        }
    }
}