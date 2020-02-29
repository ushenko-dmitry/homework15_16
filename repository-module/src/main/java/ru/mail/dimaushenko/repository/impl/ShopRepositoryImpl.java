package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.mail.dimaushenko.repository.ShopRepository;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.Shop;
import ru.mail.dimaushenko.repository.model.ShopWithItem;

@Repository
public class ShopRepositoryImpl extends GeneralRepositoryImpl<Shop> implements ShopRepository {

    @Value("${sql.request.insert.shop}")
    private String SQL_REQUEST_INSERT_ITEM;
    @Value("${sql.request.insert.item_shop}")
    private String SQL_REQUEST_INSERT_ITEM_SHOP;
    @Value("${sql.request.select.all.shop}")
    private String SQL_REQUEST_SELECT_ALL_SHOP;
    @Value("${sql.request.select.item_shop.by_ids}")
    private String SQL_REQUEST_SELECT_ITEM_SHOP_BY_IDS;
    @Value("${sql.request.select.shop_with_items}")
    private String SQL_REQUEST_SELECT_SHOP_WITH_ITEM;
    @Value("${sql.request.remove.item_shop}")
    private String SQL_REQUEST_REMOVE_ITEM_SHOP;

    @Value("${sql.column.name.shop.id}")
    private String SQL_COLUMN_NAME_SHOP_ID;
    @Value("${sql.column.name.shop.name}")
    private String SQL_COLUMN_NAME_SHOP_NAME;
    @Value("${sql.column.name.shop.location}")
    private String SQL_COLUMN_NAME_SHOP_LOCATION;

    @Value("${sql.column.name.view_shop_with_items.item_id}")
    private String SQL_COLUMN_NAME_SHOP_WITH_ITEM_ITEM_ID;
    @Value("${sql.column.name.view_shop_with_items.item_name}")
    private String SQL_COLUMN_NAME_SHOP_WITH_ITEM_ITEM_NAME;
    @Value("${sql.column.name.view_shop_with_items.item_description}")
    private String SQL_COLUMN_NAME_SHOP_WITH_ITEM_ITEM_DESCRIPTION;
    @Value("${sql.column.name.view_shop_with_items.shop_id}")
    private String SQL_COLUMN_NAME_SHOP_WITH_ITEM_SHOP_ID;
    @Value("${sql.column.name.view_shop_with_items.shop_name}")
    private String SQL_COLUMN_NAME_SHOP_WITH_ITEM_SHOP_NAME;
    @Value("${sql.column.name.view_shop_with_items.shop_location}")
    private String SQL_COLUMN_NAME_SHOP_WITH_ITEM_SHOP_LOCATION;

    @Override
    public Integer addEntity(Connection connection, Shop shop) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_INSERT_ITEM)) {
            preparedStatement.setString(1, shop.getName());
            preparedStatement.setString(2, shop.getLocation());
            if (preparedStatement.executeUpdate() > 0) {
                try (ResultSet result = preparedStatement.getGeneratedKeys()) {
                    if (result.next()) {
                        return result.getInt(1);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Shop> getAllEntities(Connection connection) throws SQLException {
        List<Shop> shops = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_SELECT_ALL_SHOP)) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Shop shop = getShop(result);
                    shops.add(shop);
                }
            }
        }
        return shops;
    }

    @Override
    public List<ShopWithItem> getShopWithItem(Connection connection) throws SQLException {
        List<ShopWithItem> shopsWithItem = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_SELECT_SHOP_WITH_ITEM)) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    getShopWithItem(result, shopsWithItem);
                }
            }
        }
        return shopsWithItem;
    }

    @Override
    public void putItemToShop(Connection connection, Integer shopId, Integer itemId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_INSERT_ITEM_SHOP)) {
            preparedStatement.setInt(1, shopId);
            preparedStatement.setInt(2, itemId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean isShopHaveItem(Connection connection, Integer shopId, Integer itemId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_SELECT_ITEM_SHOP_BY_IDS)) {
            preparedStatement.setInt(1, shopId);
            preparedStatement.setInt(2, itemId);
            try (ResultSet result = preparedStatement.executeQuery()) {
                return result.next();
            }
        }
    }

    @Override
    public void removeItemInShop(Connection connection, Integer shopId, Integer itemId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_REMOVE_ITEM_SHOP)) {
            preparedStatement.setInt(1, itemId);
            preparedStatement.setInt(2, shopId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void removeEntityById(Connection connection, Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Shop getShop(final ResultSet result) throws SQLException {
        Shop shop = new Shop();
        shop.setId(result.getInt(SQL_COLUMN_NAME_SHOP_ID));
        shop.setName(result.getString(SQL_COLUMN_NAME_SHOP_NAME));
        shop.setLocation(result.getString(SQL_COLUMN_NAME_SHOP_LOCATION));
        return shop;
    }

    private void getShopWithItem(final ResultSet result, List<ShopWithItem> shopsWithItem) throws SQLException {
        Item item = new Item();
        item.setId(result.getInt(SQL_COLUMN_NAME_SHOP_WITH_ITEM_ITEM_ID));
        item.setName(result.getString(SQL_COLUMN_NAME_SHOP_WITH_ITEM_ITEM_NAME));
        item.setDescription(result.getString(SQL_COLUMN_NAME_SHOP_WITH_ITEM_ITEM_DESCRIPTION));
        if (!shopsWithItem.isEmpty()) {
            ShopWithItem shop = shopsWithItem.get(shopsWithItem.size() - 1);
            if (shopsWithItem.get(shopsWithItem.size() - 1).getId() == result.getInt(SQL_COLUMN_NAME_SHOP_WITH_ITEM_SHOP_ID)) {
                shop.getItems().add(item);
                return;
            }
        }
        ShopWithItem shop = new ShopWithItem();
        shop.setId(result.getInt(SQL_COLUMN_NAME_SHOP_WITH_ITEM_SHOP_ID));
        shop.setName(result.getString(SQL_COLUMN_NAME_SHOP_WITH_ITEM_SHOP_NAME));
        shop.setLocation(result.getString(SQL_COLUMN_NAME_SHOP_WITH_ITEM_SHOP_LOCATION));
        shop.setItems(new ArrayList<>());
        shop.getItems().add(item);
        shopsWithItem.add(shop);
    }

}
