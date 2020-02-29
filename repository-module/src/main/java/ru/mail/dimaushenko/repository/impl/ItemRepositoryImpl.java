package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.mail.dimaushenko.repository.ItemRepository;
import ru.mail.dimaushenko.repository.model.Item;

@Repository
public class ItemRepositoryImpl extends GeneralRepositoryImpl<Item> implements ItemRepository {

    @Value("${sql.request.insert.item}")
    private String SQL_REQUEST_INSERT_ITEM;
    @Value("${sql.request.select.all.item}")
    private String SQL_REQUEST_SELECT_ALL_ITEM;
    @Value("${sql.request.remove.item}")
    private String SQL_REQUEST_REMOVE_ITEM;
    @Value("${sql.request.select.item_details.by_id}")
    private String SQL_REQUEST_SELECT_ITEM_DETAILS_BY_ID;

    @Value("${sql.column.name.item.id}")
    private String SQL_COLUMN_NAME_ITEM_ID;
    @Value("${sql.column.name.item.name}")
    private String SQL_COLUMN_NAME_ITEM_NAME;
    @Value("${sql.column.name.item.description}")
    private String SQL_COLUMN_NAME_ITEM_DESCRIPTION;

    @Override
    public Integer addEntity(Connection connection, Item item) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_INSERT_ITEM)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
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
    public List<Item> getAllEntities(Connection connection) throws SQLException {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_SELECT_ALL_ITEM)) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Item item = getItem(result);
                    items.add(item);
                }
            }
        }
        return items;
    }

    @Override
    public void removeEntityById(Connection connection, Integer id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_REMOVE_ITEM)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean isItemHasItemDetails(Connection connection, Integer itemId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_SELECT_ITEM_DETAILS_BY_ID)) {
            preparedStatement.setInt(1, itemId);
            try (ResultSet result = preparedStatement.executeQuery()) {
                return result.next();
            }
        }        
    }

    private Item getItem(ResultSet result) throws SQLException {
        Item item = new Item();
        item.setId(result.getInt(SQL_COLUMN_NAME_ITEM_ID));
        item.setName(result.getString(SQL_COLUMN_NAME_ITEM_NAME));
        item.setDescription(result.getString(SQL_COLUMN_NAME_ITEM_DESCRIPTION));
        return item;
    }
}
