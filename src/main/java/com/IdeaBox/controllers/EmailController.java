package com.IdeaBox.controllers;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {
	
	@Autowired private JavaMailSender mailSender;

    @RequestMapping(path = "/email-send", method = RequestMethod.GET)
    public String sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Hello from Spring Boot Application");
        message.setTo("ideainboxapp@gmail.com");
        message.setFrom("ideainboxapp@gmail.com");

        try {
            mailSender.send(message);
            return "redirect:/timeline";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }
    
    @RequestMapping(path = "/emailsenha", method = RequestMethod.GET)
    public String emailRecuperacao(@RequestParam String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Sua nova senha é 123.");
        message.setTo(email);
        message.setFrom("ideainboxapp@gmail.com");

        try {
            mailSender.send(message);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }
    
    @RequestMapping(path = "/enviarEmail1", method = RequestMethod.GET)
    public String email1(@RequestParam String email, @RequestParam String text, HttpSession session) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(text);
        message.setTo(email);
        message.setFrom("ideainboxapp@gmail.com");

        try {
            mailSender.send(message);
            if(session.getAttribute("gerenteLogado") != null) {
            	return "redirect:/pendentes";
            }
            return "redirect:/sugestaoADM";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }

    @RequestMapping(path = "/email-send1", method = RequestMethod.GET)
    public String sendMail1(@RequestParam String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(text);
        message.setTo("ideainboxapp@gmail.com");
        message.setFrom("ideainboxapp@gmail.com");

        try {
            mailSender.send(message);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }

//"ideainboxapp@gmail.com"
}
