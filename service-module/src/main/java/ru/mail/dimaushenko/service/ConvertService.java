package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemDetails;
import ru.mail.dimaushenko.repository.model.Shop;
import ru.mail.dimaushenko.repository.model.ShopWithItem;
import ru.mail.dimaushenko.service.model.AddItemDTO;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemDetailsDTO;
import ru.mail.dimaushenko.service.model.ShopDTO;
import ru.mail.dimaushenko.service.model.ShopWithItemDTO;
import ru.mail.dimaushenko.service.model.addShopDTO;

public interface ConvertService {

    ItemDTO convertItemToItemDTO(Item item);

    Item convertItemDTOToItem(ItemDTO itemDTO);

    Item convertAddItemDTOToItem(AddItemDTO addItemDTO);

    List<Item> convertItemDTOToItem(List<ItemDTO> itemsDTO);

    List<ItemDTO> convertItemToItemDTO(List<Item> items);

    Shop convertShopDTOToShop(ShopDTO shopDTO);

    List<Shop> convertShopDTOToShop(List<ShopDTO> shopsDTO);

    Shop convertAddShopDTOToShop(addShopDTO shopDTO);

    ShopDTO convertShopToShopDTO(Shop shop);

    List<ShopDTO> convertShopToShopDTO(List<Shop> shops);

    List<ShopWithItemDTO> convertShopWithItemToShopWithItemDTO(List<ShopWithItem> shops);
    
    ShopWithItemDTO convertShopWithItemToShopWithItemDTO(ShopWithItem shop);

    ItemDetails convertItemDetailsDTOToItemDetails(ItemDetailsDTO itemDetailsDTO);

}
