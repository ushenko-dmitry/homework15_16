package ru.mail.dimaushenko.springbootmodule.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ru.mail.dimaushenko.springbootmodule.controller.ShopController;
import ru.mail.dimaushenko.service.ShopService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ShopDTO;
import ru.mail.dimaushenko.service.model.addShopDTO;

@Controller
public class ShopControllerImpl implements ShopController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ShopService shopService;

    public ShopControllerImpl(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public ShopDTO addShop(String name, String location) {
        addShopDTO shopDTO = new addShopDTO();
        shopDTO.setName(name);
        shopDTO.setLocation(location);
        ShopDTO ShopDTO = shopService.addShop(shopDTO);
        if (shopDTO != null){
            logger.info("Shop was successfully created");
        } else {
            logger.error("ERROR: some problem with create shop");
        }
        return ShopDTO;
    }

    @Override
    public List<ShopDTO> getAllShop() {
        return shopService.getAllShops();
    }

    @Override
    public void putItemToShop(ShopDTO shopDTO, ItemDTO itemDTO) {
        if (!shopService.isShopHaveItem(shopDTO, itemDTO)) {
            shopService.putItemToShop(shopDTO, itemDTO);
        }else{
            logger.error("Shop is already has this item");
        }
    }

}
