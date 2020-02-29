package ru.mail.dimaushenko.springbootmodule.controller.impl;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ru.mail.dimaushenko.springbootmodule.controller.ItemDetailsController;
import ru.mail.dimaushenko.service.ItemDetailsService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemDetailsDTO;

@Controller
public class ItemDetailsControllerImpl implements ItemDetailsController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemDetailsService itemDetailsService;

    public ItemDetailsControllerImpl(
            ItemDetailsService itemDetailsService
    ) {
        this.itemDetailsService = itemDetailsService;
    }

    @Override
    public ItemDetailsDTO addItemDetails(ItemDTO itemDTO, Double price) {
        ItemDetailsDTO itemDetailsDTO = new ItemDetailsDTO();
        itemDetailsDTO.setItemId(itemDTO.getId());
        itemDetailsDTO.setPrice(price);
        return itemDetailsService.addItemDetails(itemDetailsDTO);
    }

}
