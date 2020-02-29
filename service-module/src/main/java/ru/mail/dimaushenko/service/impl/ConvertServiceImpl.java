package ru.mail.dimaushenko.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemDetails;
import ru.mail.dimaushenko.repository.model.Shop;
import ru.mail.dimaushenko.repository.model.ShopWithItem;
import ru.mail.dimaushenko.service.ConvertService;
import ru.mail.dimaushenko.service.model.AddItemDTO;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemDetailsDTO;
import ru.mail.dimaushenko.service.model.ShopDTO;
import ru.mail.dimaushenko.service.model.ShopWithItemDTO;
import ru.mail.dimaushenko.service.model.addShopDTO;

@Service
public class ConvertServiceImpl implements ConvertService {

    @Override
    public Item convertAddItemDTOToItem(AddItemDTO addItemDTO) {
        Item item = new Item();
        item.setName(addItemDTO.getName());
        item.setDescription(addItemDTO.getDescription());
        return item;
    }

    @Override
    public Item convertItemDTOToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        return item;
    }

    @Override
    public ItemDTO convertItemToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        return itemDTO;
    }

    @Override
    public List<ItemDTO> convertItemToItemDTO(List<Item> items) {
        List<ItemDTO> itemsDTO = new ArrayList<>();
        for (Item item : items) {
            ItemDTO itemDTO = convertItemToItemDTO(item);
            itemsDTO.add(itemDTO);
        }
        return itemsDTO;
    }

    @Override
    public List<Item> convertItemDTOToItem(List<ItemDTO> itemsDTO) {
        List<Item> items = new ArrayList<>();
        for (ItemDTO itemDTO : itemsDTO) {
            Item item = convertItemDTOToItem(itemDTO);
            items.add(item);
        }
        return items;
    }

    @Override
    public Shop convertAddShopDTOToShop(addShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setName(shopDTO.getName());
        shop.setLocation(shopDTO.getLocation());
        return shop;
    }

    @Override
    public Shop convertShopDTOToShop(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setId(shopDTO.getId());
        shop.setName(shopDTO.getName());
        shop.setLocation(shopDTO.getLocation());
        return shop;
    }

    @Override
    public ShopDTO convertShopToShopDTO(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(shop.getId());
        shopDTO.setName(shop.getName());
        shopDTO.setLocation(shop.getLocation());
        return shopDTO;
    }

    @Override
    public List<ShopDTO> convertShopToShopDTO(List<Shop> shops) {
        List<ShopDTO> shopsDTO = new ArrayList<>();
        for (Shop shop : shops) {
            ShopDTO shopDTO = convertShopToShopDTO(shop);
            shopsDTO.add(shopDTO);
        }
        return shopsDTO;
    }

    @Override
    public List<Shop> convertShopDTOToShop(List<ShopDTO> shopsDTO) {
        List<Shop> shops = new ArrayList<>();
        for (ShopDTO shopDTO : shopsDTO) {
            Shop shop = convertShopDTOToShop(shopDTO);
            shops.add(shop);
        }
        return shops;
    }

    @Override
    public List<ShopWithItemDTO> convertShopWithItemToShopWithItemDTO(List<ShopWithItem> shops) {
        List<ShopWithItemDTO> shopWithItemDTOs = new ArrayList<>();
        for (ShopWithItem shop : shops) {
            ShopWithItemDTO shopWithItemDTO = convertShopWithItemToShopWithItemDTO(shop);
            shopWithItemDTOs.add(shopWithItemDTO);
        }
        return shopWithItemDTOs;
    }

    @Override
    public ShopWithItemDTO convertShopWithItemToShopWithItemDTO(ShopWithItem shop) {
        ShopWithItemDTO shopWithItemDTO = new ShopWithItemDTO();
        shopWithItemDTO.setId(shop.getId());
        shopWithItemDTO.setName(shop.getName());
        shopWithItemDTO.setLocation(shop.getLocation());
        List<ItemDTO> itemDTOs = convertItemToItemDTO(shop.getItems());
        shopWithItemDTO.setItemsDTO(itemDTOs);
        return shopWithItemDTO;
    }

    @Override
    public ItemDetails convertItemDetailsDTOToItemDetails(ItemDetailsDTO itemDetailsDTO) {
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setItemId(itemDetailsDTO.getItemId());
        itemDetails.setPrice(itemDetailsDTO.getPrice());
        return itemDetails;
    }
}
