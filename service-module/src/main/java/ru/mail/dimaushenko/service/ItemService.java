package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.AddItemDTO;
import ru.mail.dimaushenko.service.model.ItemDTO;

public interface ItemService {

    ItemDTO addItem(AddItemDTO addItemDTO);

    List<ItemDTO> getAllItems();

    boolean isItemHasItemDetails(ItemDTO itemDTO);

    void removeItem(ItemDTO itemDTO);
    
}
