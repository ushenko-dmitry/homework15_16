package ru.mail.dimaushenko.springbootmodule.controller;

import java.util.List;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ShopDTO;

public interface ShopController {

    ShopDTO addShop(String name, String location);

    List<ShopDTO> getAllShop();

    public void putItemToShop(ShopDTO get, ItemDTO get0);

}
