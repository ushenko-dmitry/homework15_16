package ru.mail.dimaushenko.springbootmodule.runner;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.mail.dimaushenko.springbootmodule.controller.ItemController;
import ru.mail.dimaushenko.springbootmodule.controller.ItemDetailsController;
import ru.mail.dimaushenko.springbootmodule.controller.ShopController;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemDetailsDTO;
import ru.mail.dimaushenko.service.model.ShopDTO;

@Component
public class App implements ApplicationRunner {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final Scanner reader = new Scanner(System.in);
    private final ItemController itemController;
    private final ShopController shopController;
    private final ItemDetailsController itemDetailsController;

    public App(
            ItemController itemController,
            ShopController shopController,
            ItemDetailsController itemDetailsController
    ) {
        this.itemController = itemController;
        this.shopController = shopController;
        this.itemDetailsController = itemDetailsController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        menu();
    }

    private void menu() {
        while (true) {
            logger.info("\n"
                    + "-- MAIN MENU --\n"
                    + "1) Work with item\n"
                    + "2) Work with shop\n"
                    + "3) Print all items\n"
                    + "4) Print all shops\n"
                    + "0) exit\n");
            switch (reader.nextLine()) {
                case "1":
                    workWithItem();
                    break;
                case "2":
                    workWithShop();
                    break;
                case "3":
                    printItems();
                    break;
                case "4":
                    printShops();
                    break;
                case "0":
                    return;
                default:
                    break;
            }
        }
    }

    private void workWithItem() {
        while (true) {
            logger.info("\n"
                    + "-- WORK WITH ITEM --\n"
                    + "1) Add new item\n"
                    + "2) Add new item with price\n"
                    + "3) Add price to item\n"
                    + "4) Remove items in shops\n"
                    + "0) exit\n");
            switch (reader.nextLine()) {
                case "1":
                    addNewItem();
                    break;
                case "2":
                    addNewItemWithPrice();
                    break;
                case "3":
                    addPriceToItem();
                    break;
                case "4":
                    removeItemsInShops();
                    break;
                case "0":
                    return;
                default:
                    break;
            }
        }
    }

    private void workWithShop() {
        while (true) {
            logger.info("\n"
                    + "-- WORK WITH SHOP --\n"
                    + "1) Add new shop\n"
                    + "2) Add item to shop\n"
                    + "0) exit\n");
            switch (reader.nextLine()) {
                case "1":
                    addNewShop();
                    break;
                case "2":
                    addItemToShop();
                    break;
                case "0":
                    return;
                default:
                    break;
            }
        }
    }

    private void addItemToShop() {
        List<ShopDTO> shops = printShops();
        List<ItemDTO> items = printItems();
        logger.info("\nchoose shop");
        int shopIter;
        try {
            shopIter = Integer.parseInt(reader.nextLine());
        } catch (NumberFormatException nfe) {
            logger.error("Wrong input");
            return;
        }
        if (shopIter > shops.size() && shopIter < 0) {
            logger.info("wrong select");
            return;
        }
        logger.info("\nchoose item");
        int itemIter;
        try {
            itemIter = Integer.parseInt(reader.nextLine());
        } catch (NumberFormatException nfe) {
            logger.error("Wrong input");
            return;
        }
        if (itemIter > items.size() && itemIter < 0) {
            logger.info("wrong select");
            return;
        }
        shopController.putItemToShop(shops.get(shopIter), items.get(itemIter));
    }

    private void addNewShop() {
        logger.info("\n"
                + "-- ADD NEW SHOP --\n"
                + "Write name: ");
        String name = reader.nextLine();
        logger.info("\nWrite location: ");
        String location = reader.nextLine();
        shopController.addShop(name, location);
    }

    private void addNewItem() {
        logger.info("\n"
                + "-- ADD NEW ITEM --\n"
                + "Write name: ");
        String name = reader.nextLine();
        logger.info("\nWrite description: ");
        String description = reader.nextLine();
        itemController.addItem(name, description);
    }

    private void removeItemsInShops() {
        itemController.remoweItemsInShop();
    }

    private void addNewItemWithPrice() {
        logger.info("\n"
                + "-- ADD NEW ITEM --\n"
                + "Write name: ");
        String name = reader.nextLine();
        logger.info("\nWrite description: ");
        String description = reader.nextLine();
        ItemDTO itemDTO = itemController.addItem(name, description);
        if (itemDTO == null) {
            return;
        }
        logger.info("\nWrite price: ");
        String priceStr = reader.nextLine();
        try {
            Double price = Double.parseDouble(priceStr);
            ItemDetailsDTO itemDetailsDTO = itemDetailsController.addItemDetails(itemDTO, price);
            if (itemDetailsDTO == null) {
                itemController.removeItem(itemDTO);
            }
        } catch (NumberFormatException nfe) {
            logger.error("Price is not correct");
            itemController.removeItem(itemDTO);
        }
    }

    private void addPriceToItem() {
        List<ItemDTO> items = printItems();
        logger.info("\nchoose item");
        int itemIter;
        try {
            itemIter = Integer.parseInt(reader.nextLine());
        } catch (NumberFormatException nfe) {
            logger.error("Wrong input");
            return;
        }
        if (itemIter > items.size() && itemIter < 0) {
            logger.info("wrong select");
            return;
        }
        ItemDTO itemDTO = items.get(itemIter);
        logger.info("\nWrite price: ");
        String priceStr = reader.nextLine();
        try {
            Double price = Double.parseDouble(priceStr);
            itemDetailsController.addItemDetails(itemDTO, price);
        } catch (NumberFormatException nfe) {
            logger.error("Price is not correct");
        }
    }

    private List<ItemDTO> printItems() {
        List<ItemDTO> items = itemController.getAllItem();
        if (items.isEmpty()) {
            logger.error("items are not found");
            return Collections.emptyList();
        }
        for (int i = 0; i < items.size(); i++) {
            logger.info(i + ") " + items.get(i));
        }
        return items;
    }

    private List<ShopDTO> printShops() {
        List<ShopDTO> shops = shopController.getAllShop();
        if (shops.isEmpty()) {
            logger.error("shops are not found");
            return Collections.emptyList();
        }
        for (int i = 0; i < shops.size(); i++) {
            logger.info(i + ") " + shops.get(i));
        }
        return shops;
    }

}
