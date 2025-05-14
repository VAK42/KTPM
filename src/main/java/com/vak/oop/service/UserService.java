package com.vak.oop.service;

import com.vak.oop.dao.UserDao;
import com.vak.oop.model.UserEntity;
import com.vak.oop.util.ConfigLoader;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class UserService {
  private final UserDao userDao;

  public UserService(EntityManager entityManager) {
    this.userDao = new UserDao(entityManager);
  }

  public boolean login(String username, String password) {
    if (userDao.isUser(username)) {
      return userDao.loginValidate(username, password);
    }
    return false;
  }

  public boolean checkExistence(String username) {
    return userDao.isUser(username);
  }

  public String getUserEmail(String username) {
    return userDao.getUserEmailByUsername(username);
  }

  public String generateToken(String username) {
    String token = UUID.randomUUID().toString();
    userDao.saveToken(username, token, LocalDateTime.now());
    return token;
  }

  public void sendResetEmail(String to, String token) throws MessagingException {
    String body = "Your Password Reset Token Is: " + token;
    Properties props = new Properties();
    props.put("mail.smtp.auth", ConfigLoader.get("mail.smtp.auth"));
    props.put("mail.smtp.starttls.enable", ConfigLoader.get("mail.smtp.starttls.enable"));
    props.put("mail.smtp.host", ConfigLoader.get("mail.smtp.host"));
    props.put("mail.smtp.port", ConfigLoader.get("mail.smtp.port"));
    final String username = ConfigLoader.get("mail.username");
    final String password = ConfigLoader.get("mail.password");
    Session session = Session.getInstance(props, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(username));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
    message.setSubject("Password Reset");
    message.setText(body);
    Transport.send(message);
  }

  public boolean resetPassword(String username, String token, String newPassword) {
    Long userId = userDao.getUserIdByUsername(username);
    if (userId == null || !userDao.isValidToken(userId, token)) return false;
    return userDao.updatePassword(userId, newPassword);
  }

  public void deleteUser(UserEntity UserEntity) {
    userDao.deleteUser(UserEntity);
  }

  public List<UserEntity> getUsersByPage(int page, int size) {
    return userDao.getUsersByPage(page, size);
  }

  public int getTotalUserCount() {
    return userDao.getTotalUserCount();
  }
}