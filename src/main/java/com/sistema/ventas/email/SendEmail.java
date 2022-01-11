package com.sistema.ventas.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    @Autowired
    private JavaMailSender mailSender;

    //Pasamos por parametro: destinatario, asunto y el mensaje
    public void envioEmail(String strEmailReceptor, String strAsunto, String strContenido) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(strEmailReceptor);
        email.setSubject(strAsunto);
        email.setText(strContenido);

        mailSender.send(email);
    }
}