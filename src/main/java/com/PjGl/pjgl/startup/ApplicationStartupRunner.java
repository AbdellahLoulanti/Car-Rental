package com.PjGl.pjgl.startup;

import com.PjGl.pjgl.Observer.ClientNotifier;
import com.PjGl.pjgl.Observer.ReservationManager;
import com.PjGl.pjgl.Repository.ClientRepo;
import com.PjGl.pjgl.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    private ReservationManager reservationManager;

    @Autowired
    private ClientRepo clientRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        MailService mailService = applicationContext.getBean(MailService.class);
        clientRepository.findAll().forEach(client -> {
            ClientNotifier notifier = new ClientNotifier(mailService, client.getEmail());
            reservationManager.attach(notifier);
        });
    }
}
