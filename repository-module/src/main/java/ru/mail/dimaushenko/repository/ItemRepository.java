package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import ru.mail.dimaushenko.repository.model.Item;

public interface ItemRepository extends GeneralRepository<Item> {

    boolean isItemHasItemDetails(Connection connection, Integer id) throws SQLException;

}
