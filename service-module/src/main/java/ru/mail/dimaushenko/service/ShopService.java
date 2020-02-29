package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ShopDTO;
import ru.mail.dimaushenko.service.model.ShopWithItemDTO;
import ru.mail.dimaushenko.service.model.addShopDTO;

public interface ShopService {

    ShopDTO addShop(addShopDTO shopDTO);

    List<ShopWithItemDTO> getShopWithItem();

    void putItemToShop(ShopDTO shopDTO, ItemDTO itemDTO);
    
    void removeItemInShop(ShopDTO shopDTO, ItemDTO itemDTO);

    List<ShopDTO> getAllShops();

    boolean isShopHaveItem(ShopDTO shopDTO, ItemDTO itemDTO);
}
