package com.vak.oop.dao;

import com.vak.oop.model.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public record UserDao(EntityManager entityManager) {
  public boolean isUser(String username) {
    String jpql = "SELECT u FROM UserEntity u WHERE u.username = :username";
    TypedQuery<UserEntity> query = entityManager.createQuery(jpql, UserEntity.class);
    query.setParameter("username", username);
    query.setMaxResults(1);
    return !query.getResultList().isEmpty();
  }

  public boolean loginValidate(String username, String password) {
    String jpql = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password AND u.role = :role";
    TypedQuery<UserEntity> query = entityManager.createQuery(jpql, UserEntity.class);
    query.setParameter("username", username);
    query.setParameter("password", password);
    query.setParameter("role", "admin");
    List<UserEntity> users = query.getResultList();
    return !users.isEmpty();
  }

  public String getUserEmailByUsername(String username) {
    String jpql = "SELECT u.email FROM UserEntity u WHERE u.username = :username";
    TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
    query.setParameter("username", username);
    return query.getSingleResult();
  }

  public Long getUserIdByUsername(String username) {
    try {
      String jpql = "SELECT u.userid FROM UserEntity u WHERE u.username = :username";
      TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
      query.setParameter("username", username);
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  public void saveToken(String username, String token, LocalDateTime dateTime) {
    Long userId = getUserIdByUsername(username);
    if (userId == null) return;
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery("INSERT INTO token (userid, token, date) VALUES (?, ?, ?)").setParameter(1, userId).setParameter(2, token).setParameter(3, dateTime).executeUpdate();
    entityManager.getTransaction().commit();
  }

  public boolean isValidToken(Long userId, String token) {
    String sql = "SELECT COUNT(*) FROM token WHERE userid = :uid AND token = :token";
    Object result = entityManager.createNativeQuery(sql).setParameter("uid", userId).setParameter("token", token).getSingleResult();
    return ((Number) result).longValue() > 0;
  }

  public boolean updatePassword(Long userId, String newPassword) {
    entityManager.getTransaction().begin();
    entityManager.createQuery("UPDATE UserEntity u SET u.password = :pwd WHERE u.userid = :uid").setParameter("pwd", newPassword).setParameter("uid", userId).executeUpdate();
    entityManager.createNativeQuery("DELETE FROM token WHERE userid = :uid").setParameter("uid", userId).executeUpdate();
    entityManager.getTransaction().commit();
    return true;
  }

  public void deleteUser(UserEntity UserEntity) {
    entityManager.getTransaction().begin();
    UserEntity toRemove = entityManager.merge(UserEntity);
    entityManager.remove(toRemove);
    entityManager.getTransaction().commit();
  }

  public List<UserEntity> getUsersByPage(int page, int pageSize) {
    TypedQuery<UserEntity> query = entityManager.createQuery("SELECT p FROM UserEntity p WHERE p.role = :role", UserEntity.class);
    query.setParameter("role", "user");
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
  }

  public int getTotalUserCount() {
    return entityManager.createQuery("SELECT COUNT(p) FROM UserEntity p", Long.class).getSingleResult().intValue();
  }
}