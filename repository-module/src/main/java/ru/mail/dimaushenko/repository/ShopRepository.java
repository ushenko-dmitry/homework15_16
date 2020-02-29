package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import ru.mail.dimaushenko.repository.model.Shop;
import ru.mail.dimaushenko.repository.model.ShopWithItem;

public interface ShopRepository extends GeneralRepository<Shop> {

    List<ShopWithItem> getShopWithItem(Connection connection) throws SQLException;

    void putItemToShop(Connection connection, Integer shopId, Integer itemId) throws SQLException;

    boolean isShopHaveItem(Connection connection, Integer shopId, Integer itemId) throws SQLException;

    void removeItemInShop(Connection connection, Integer shopId, Integer itemId) throws SQLException;

}
