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
import ru.mail.dimaushenko.repository.ShopRepository;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.Shop;
import ru.mail.dimaushenko.repository.model.ShopWithItem;
import ru.mail.dimaushenko.service.ConvertService;
import ru.mail.dimaushenko.service.ShopService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ShopDTO;
import ru.mail.dimaushenko.service.model.ShopWithItemDTO;
import ru.mail.dimaushenko.service.model.addShopDTO;

@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionPoolRepository connectionPool;
    private final ShopRepository shopRepository;
    private final ConvertService convertService;

    public ShopServiceImpl(
            ConnectionPoolRepository connectionPool,
            ShopRepository shopRepository,
            ConvertService convertService) {
        this.connectionPool = connectionPool;
        this.shopRepository = shopRepository;
        this.convertService = convertService;
    }

    @Override
    public ShopDTO addShop(addShopDTO addShopDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Shop shop = convertService.convertAddShopDTOToShop(addShopDTO);
                Integer id = shopRepository.addEntity(connection, shop);
                shop.setId(id);
                ShopDTO shopDTO = convertService.convertShopToShopDTO(shop);
                connection.commit();
                if (shopDTO.getId() != null) {
                    return shopDTO;
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
    public List<ShopDTO> getAllShops() {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Shop> shops = shopRepository.getAllEntities(connection);
                List<ShopDTO> shopsDTO = convertService.convertShopToShopDTO(shops);
                connection.commit();
                return shopsDTO;
            } catch (SQLException ex) {
                logger.error("Error with get all shops from database");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
        return Collections.emptyList();
    }

    @Override
    public List<ShopWithItemDTO> getShopWithItem() {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<ShopWithItem> shops = shopRepository.getShopWithItem(connection);
                List<ShopWithItemDTO> shopWithItemDTO = convertService.convertShopWithItemToShopWithItemDTO(shops);
                connection.commit();
                return shopWithItemDTO;
            } catch (SQLException ex) {
                logger.error("Error with get shops with items");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
        return Collections.emptyList();
    }

    @Override
    public void putItemToShop(ShopDTO shopDTO, ItemDTO itemDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Shop shop = convertService.convertShopDTOToShop(shopDTO);
                Item item = convertService.convertItemDTOToItem(itemDTO);
                shopRepository.putItemToShop(connection, shop.getId(), item.getId());
                connection.commit();
            } catch (SQLException ex) {
                logger.error("Error with put item to shop");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
    }

    @Override
    public boolean isShopHaveItem(ShopDTO shopDTO, ItemDTO itemDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Shop shop = convertService.convertShopDTOToShop(shopDTO);
                Item item = convertService.convertItemDTOToItem(itemDTO);
                boolean isShopHaveItem = shopRepository.isShopHaveItem(connection, shop.getId(), item.getId());
                connection.commit();
                return isShopHaveItem;
            } catch (SQLException ex) {
                logger.error("Error with get is shop have item");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
        return false;
    }

    @Override
    public void removeItemInShop(ShopDTO shopDTO, ItemDTO itemDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Shop shop = convertService.convertShopDTOToShop(shopDTO);
                Item item = convertService.convertItemDTOToItem(itemDTO);
                shopRepository.removeItemInShop(connection, shop.getId(), item.getId());
                connection.commit();
            } catch (SQLException ex) {
                logger.error("Error with remove item in shop");
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error("Error with get connection");
        }
    }

}
