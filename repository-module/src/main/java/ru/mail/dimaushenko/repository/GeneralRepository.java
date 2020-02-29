package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    Integer addEntity(Connection connection, T t) throws SQLException;

    List<T> getAllEntities(Connection connection) throws SQLException;

    void removeEntityById(Connection connection, Integer id) throws SQLException;
}
