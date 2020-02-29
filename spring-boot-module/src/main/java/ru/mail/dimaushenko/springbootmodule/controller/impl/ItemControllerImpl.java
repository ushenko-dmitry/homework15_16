package ru.mail.dimaushenko.springbootmodule.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ru.mail.dimaushenko.springbootmodule.controller.ItemController;
import ru.mail.dimaushenko.service.ItemDetailsService;
import ru.mail.dimaushenko.service.ItemService;
import ru.mail.dimaushenko.service.ShopService;
import ru.mail.dimaushenko.service.model.AddItemDTO;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ShopDTO;
import ru.mail.dimaushenko.service.model.ShopWithItemDTO;

@Controller
public class ItemControllerImpl implements ItemController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemService itemService;
    private final ShopService shopService;
    private final ItemDetailsService itemDetailsService;

    public ItemControllerImpl(
            ItemService itemService,
            ShopService shopService,
            ItemDetailsService itemDetailsService
    ) {
        this.itemService = itemService;
        this.shopService = shopService;
        this.itemDetailsService = itemDetailsService;
    }

    @Override
    public ItemDTO addItem(String name, String description) {
        AddItemDTO addItemDTO = new AddItemDTO();
        addItemDTO.setName(name);
        addItemDTO.setDescription(description);
        ItemDTO itemDTO = itemService.addItem(addItemDTO);
        if (itemDTO != null) {
            logger.info("Item was successfully created");
        } else {
            logger.error("ERROR: some problem with create item");
        }
        return itemDTO;
    }

    @Override
    public List<ItemDTO> getAllItem() {
        return itemService.getAllItems();
    }

    @Override
    public void remoweItemsInShop() {
        List<ShopWithItemDTO> shopWithItem = shopService.getShopWithItem();
        System.out.println(shopWithItem);
        for (ShopWithItemDTO shopWithItemDTO : shopWithItem) {
            ShopDTO shopDTO = new ShopDTO();
            shopDTO.setId(shopWithItemDTO.getId());
            shopDTO.setName(shopWithItemDTO.getName());
            shopDTO.setLocation(shopWithItemDTO.getLocation());
            for (ItemDTO itemDTO : shopWithItemDTO.getItemsDTO()) {
                shopService.removeItemInShop(shopDTO, itemDTO);
                if (itemService.isItemHasItemDetails(itemDTO)) {
                    itemDetailsService.removeItemDetails(itemDTO);
                }
                removeItem(itemDTO);
            }
        }

    }

    @Override
    public void removeItem(ItemDTO itemDTO) {
        itemService.removeItem(itemDTO);
    }

}
