package ru.mail.dimaushenko.service.model;

import java.util.List;

public class ShopWithItemDTO {

    private Integer id;
    private String name;
    private String location;
    private List<ItemDTO> itemsDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ItemDTO> getItemsDTO() {
        return itemsDTO;
    }

    public void setItemsDTO(List<ItemDTO> itemsDTO) {
        this.itemsDTO = itemsDTO;
    }

    @Override
    public String toString() {
        String itemsStr = "";
        for (ItemDTO itemDTO : itemsDTO) {
            itemsStr += "\n     " + itemDTO;
        }
        return "ShopWithItemDTO{" + "id=" + id + ", name=" + name + ", location=" + location + ", itemsDTO=" + itemsStr + '}';
    }

}
