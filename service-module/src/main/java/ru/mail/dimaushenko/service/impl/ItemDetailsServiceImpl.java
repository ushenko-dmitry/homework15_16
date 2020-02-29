package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.dimaushenko.repository.ConnectionPoolRepository;
import ru.mail.dimaushenko.repository.ItemDetailsRepository;
import ru.mail.dimaushenko.repository.model.ItemDetails;
import ru.mail.dimaushenko.service.ConvertService;
import ru.mail.dimaushenko.service.ItemDetailsService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemDetailsDTO;

@Service
public class ItemDetailsServiceImpl implements ItemDetailsService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionPoolRepository connectionPool;
    private final ItemDetailsRepository itemDetailsRepository;
    private final ConvertService convertService;

    public ItemDetailsServiceImpl(
            ConnectionPoolRepository connectionPool,
            ItemDetailsRepository itemDetailsRepository,
            ConvertService convertService) {
        this.connectionPool = connectionPool;
        this.itemDetailsRepository = itemDetailsRepository;
        this.convertService = convertService;
    }

    @Override
    public void removeItemDetails(ItemDTO itemDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                itemDetailsRepository.removeEntityById(connection, itemDTO.getId());
                connection.commit();
            } catch (SQLException ex) {
                logger.error("Error with remove item_details in database");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
    }

    @Override
    public ItemDetailsDTO addItemDetails(ItemDetailsDTO itemDetailsDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                ItemDetails itemDetails = convertService.convertItemDetailsDTOToItemDetails(itemDetailsDTO);
                itemDetailsRepository.addEntity(connection, itemDetails);
                connection.commit();
                return itemDetailsDTO;
            } catch (SQLException ex) {
                logger.error("Error with adding item_details into database");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
        return null;
    }

}
