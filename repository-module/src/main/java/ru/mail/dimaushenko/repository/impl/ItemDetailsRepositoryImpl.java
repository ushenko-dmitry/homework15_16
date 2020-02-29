package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import ru.mail.dimaushenko.repository.ItemDetailsRepository;
import ru.mail.dimaushenko.repository.model.ItemDetails;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDetailsRepositoryImpl extends GeneralRepositoryImpl<ItemDetails> implements ItemDetailsRepository {

    @Value("${sql.request.remove.item_details.by_id}")
    private String SQL_REQUEST_REMOVE_ITEM_DETAILS;
    @Value("${sql.request.insert.item_details}")
    private String SQL_REQUEST_INSERT_ITEM_DETAILS;

    @Override
    public Integer addEntity(Connection connection, ItemDetails itemDetails) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_INSERT_ITEM_DETAILS)) {
            preparedStatement.setInt(1, itemDetails.getItemId());
            preparedStatement.setDouble(2, itemDetails.getPrice());
            preparedStatement.executeUpdate();
        }
        return null;
    }

    @Override
    public List<ItemDetails> getAllEntities(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeEntityById(Connection connection, Integer id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(SQL_REQUEST_REMOVE_ITEM_DETAILS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

}
