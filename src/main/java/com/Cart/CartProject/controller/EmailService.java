package com.Cart.CartProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // This sends an email to the user after successful login
    public void sendLoginConfirmationToUser(String userEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);  // ✅ Send to the logged-in user
        message.setSubject("WELCOME TO CART-WEBSITE");
  message.setText(
    "🍏 Hello and Welcome!\n\n" +
    "We’re absolutely thrilled to have you join the **FreshBasket** family! 🧺\n\n" +
    "🌿 At FreshBasket, we believe that *freshness is not just a promise, it’s a way of life.*\n" +
    "Our mission is to deliver top-quality fruits and produce directly to your doorstep — handpicked, farm-fresh, and bursting with natural goodness.\n\n" +
    "💡 Why choose FreshBasket?\n" +
    "✅ Carefully sourced from trusted local farmers\n" +
    "✅ Thoroughly quality-checked for your health and safety\n" +
    "✅ Eco-friendly packaging to protect your produce and the planet 🌍\n" +
    "✅ Fast & hygienic delivery across your city 🚚\n\n" +
    "📦 Whether you’re stocking up for the week or grabbing a quick healthy bite, our online store is always ready for you — anytime, anywhere.\n\n" +
    "🛒 Start shopping now: https://www.freshbasket.com\n\n" +
    "🙋 Need assistance? Our support team is always just a message away.\n" +
    "We’re here to ensure your journey with us is smooth, satisfying, and full of flavor.\n\n" +
    "💬 Questions, feedback, or just want to say hi? Feel free to reach out — we love hearing from you!\n\n" +
    "Thank you for choosing **FreshBasket**, where every bite brings a smile! 😄🍃\n\n" +
    "With love,\n" +
    "— The FreshBasket Team 🍀\n" +
    "🌐 Stay connected: www.freshbasket.com\n" +
    "📧 support@freshbasket.com\n" +
    "📞 +91-9660800684"
);
        mailSender.send(message);
    }
}