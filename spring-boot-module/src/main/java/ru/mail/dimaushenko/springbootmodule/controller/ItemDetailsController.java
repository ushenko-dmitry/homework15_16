package ru.mail.dimaushenko.springbootmodule.controller;

import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemDetailsDTO;

public interface ItemDetailsController {

    ItemDetailsDTO addItemDetails(ItemDTO itemDTO, Double price);

}
