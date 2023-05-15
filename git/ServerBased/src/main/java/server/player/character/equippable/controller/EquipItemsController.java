package server.player.character.equippable.controller;

import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import java.util.List;
import server.player.character.equippable.model.EquippedItems;
import server.player.character.equippable.model.GenericEquipData;
import server.player.character.equippable.service.EquipItemService;
import server.player.character.inventory.model.Inventory;

@Controller("/v1/equipped")
public class EquipItemsController {

    @Inject EquipItemService equipItemService;

    @Post("/equip")
    public GenericEquipData equip(@Body GenericEquipData equipData, @Header String characterName) {
        EquippedItems equippedItems =
                equipItemService.equipItem(equipData.getItemInstanceId(), characterName);

        return GenericEquipData.builder().equippedItems(equippedItems).build();
    }

    @Post("/unequip")
    public GenericEquipData unequip(
            @Body GenericEquipData equipData, @Header String characterName) {
        Inventory i = equipItemService.unequipItem(equipData.getItemInstanceId(), characterName);

        GenericEquipData genericEquipData = new GenericEquipData();
        genericEquipData.setInventory(i);

        return genericEquipData;
    }

    @Get()
    public GenericEquipData getCharacterEquippedItems(@Header String characterName) {
        List<EquippedItems> equippedItemsList = equipItemService.getEquippedItems(characterName);
        return GenericEquipData.builder().equippedItemsList(equippedItemsList).build();
    }
}
