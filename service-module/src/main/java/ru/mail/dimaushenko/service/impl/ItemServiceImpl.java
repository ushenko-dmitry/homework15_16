package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.dimaushenko.repository.ConnectionPoolRepository;
import ru.mail.dimaushenko.repository.ItemRepository;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.service.ConvertService;
import ru.mail.dimaushenko.service.ItemService;
import ru.mail.dimaushenko.service.model.AddItemDTO;
import ru.mail.dimaushenko.service.model.ItemDTO;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionPoolRepository connectionPool;
    private final ItemRepository itemRepository;
    private final ConvertService convertService;

    public ItemServiceImpl(
            ConnectionPoolRepository connectionPool,
            ItemRepository itemRepository1,
            ConvertService convertService
    ) {
        this.connectionPool = connectionPool;
        this.itemRepository = itemRepository1;
        this.convertService = convertService;
    }

    @Override
    public ItemDTO addItem(AddItemDTO addItemDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertService.convertAddItemDTOToItem(addItemDTO);
                Integer id = itemRepository.addEntity(connection, item);
                item.setId(id);
                ItemDTO itemDTO = convertService.convertItemToItemDTO(item);
                connection.commit();
                if (itemDTO.getId() != null) {
                    return itemDTO;
                }
            } catch (SQLException ex) {
                logger.error("Error with adding item into database");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
        return null;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.getAllEntities(connection);
                List<ItemDTO> itemsDTO = convertService.convertItemToItemDTO(items);
                connection.commit();
                return itemsDTO;
            } catch (SQLException ex) {
                logger.error("Error with get all items from database");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isItemHasItemDetails(ItemDTO itemDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertService.convertItemDTOToItem(itemDTO);
                boolean isItemHasItemDetails = itemRepository.isItemHasItemDetails(connection, item.getId());
                connection.commit();
                return isItemHasItemDetails;
            } catch (SQLException ex) {
                logger.error("Error with get `is item have item_details`");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
        return false;
    }

    @Override
    public void removeItem(ItemDTO itemDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertService.convertItemDTOToItem(itemDTO);
                itemRepository.removeEntityById(connection, item.getId());
                connection.commit();
            } catch (SQLException ex) {
                logger.error("Error with remove item");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
    }

}
