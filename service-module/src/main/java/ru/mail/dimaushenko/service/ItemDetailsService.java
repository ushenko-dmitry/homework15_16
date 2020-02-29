package ru.mail.dimaushenko.service;

import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemDetailsDTO;

public interface ItemDetailsService {

    ItemDetailsDTO addItemDetails(ItemDetailsDTO DetailsDTO);
    
    void removeItemDetails(ItemDTO itemDTO);

}
