package ru.mail.dimaushenko.springbootmodule.controller;

import java.util.List;
import ru.mail.dimaushenko.service.model.ItemDTO;

public interface ItemController {

    ItemDTO addItem(String name, String description);

    List<ItemDTO> getAllItem();

    void remoweItemsInShop();

    void removeItem(ItemDTO itemDTO);

}
