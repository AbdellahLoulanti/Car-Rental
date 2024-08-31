package com.PjGl.pjgl.Observer;

import com.PjGl.pjgl.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ClientNotifier implements Observer {

    private MailService mailService;
    private String clientEmail;

    public ClientNotifier(MailService mailService, String clientEmail) {
        this.mailService = mailService;
        this.clientEmail = clientEmail;
    }

    @Override
    public void update(String message) {
        mailService.sendEmail(clientEmail, "Nouvelle Voiture Disponible", message);
    }
}
