package server.player.character.inventory.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.common.dto.Location;
import server.common.dto.Location2D;
import server.items.dropped.model.DroppedItem;
import server.items.helper.ItemTestHelper;
import server.items.service.ItemService;
import server.items.types.ItemType;
import server.items.weapons.Weapon;
import server.player.character.inventory.model.CharacterItem;
import server.player.character.inventory.model.Inventory;
import server.player.character.inventory.model.exceptions.InventoryException;

@MicronautTest
public class InventoryServiceTest {

    @Inject InventoryService inventoryService;

    @Inject ItemService itemService;

    @Inject ItemTestHelper itemTestHelper;

    private static final String CHARACTER_NAME = "test_character";

    @BeforeEach
    void cleanDb() {
        itemTestHelper.deleteAllItemData();
        itemTestHelper.prepareInventory(CHARACTER_NAME);
    }

    @Test
    void testPickupItemWillPickUpItemAndSetToInventory() {
        // Given
        Location location = new Location("map", 1, 1, 1);
        Weapon weapon = (Weapon) itemTestHelper.createAndInsertItem(ItemType.WEAPON.getType());
        DroppedItem droppedItem = itemTestHelper.createAndInsertDroppedItem(location, weapon);

        // When
        inventoryService.pickupItem(CHARACTER_NAME, droppedItem.getDroppedItemId());

        // Then
        // TODO: Make test not rely on service call
        Inventory inventory = inventoryService.getInventory(CHARACTER_NAME);
        List<CharacterItem> items = inventory.getCharacterItems();
        Assertions.assertThat(items.size()).isEqualTo(1);

        String actualInstanceId = items.get(0).getItemInstance().getItemInstanceId();

        Assertions.assertThat(actualInstanceId)
                .isEqualTo(droppedItem.getItemInstance().getItemInstanceId());
    }

    @Test
    void dropItemWillRemoveItFromInventoryAndAddDroppedItemObject() {
        // Given
        Weapon weapon = (Weapon) itemTestHelper.createAndInsertItem(ItemType.WEAPON.getType());
        Location location = new Location("map", 1, 1, 1);
        DroppedItem droppedItem = itemTestHelper.createAndInsertDroppedItem(location, weapon);
        // TODO: make test not rely on service call
        inventoryService.pickupItem(CHARACTER_NAME, droppedItem.getDroppedItemId());

        // When
        inventoryService.dropItem(CHARACTER_NAME, new Location2D(0, 0), location);

        // Then
        List<DroppedItem> itemList = itemService.getItemsInMap(location);

        Assertions.assertThat(itemList.size()).isEqualTo(1);
        Assertions.assertThat(itemList.get(0).getItemInstance())
                .isEqualTo(droppedItem.getItemInstance());
    }

    @Test
    void getAvailableSlotWillReturnCorrectValuesForInventorySize() {
        // Given
        Inventory inventory = inventoryService.getInventory(CHARACTER_NAME);
        // max size of 2x2 would allow 4 slots. ensure we can add 4 items and anything else will
        // throw.
        inventory.setMaxSize(new Location2D(2, 2));
        inventoryService.updateInventoryMaxSize(inventory);

        Weapon weapon = (Weapon) itemTestHelper.createAndInsertItem(ItemType.WEAPON.getType());

        // When
        for (int i = 0; i < 4; i++) {
            Location location = new Location("map", 1, 1, 1);
            DroppedItem droppedItem = itemTestHelper.createAndInsertDroppedItem(location, weapon);
            inventoryService.pickupItem(CHARACTER_NAME, droppedItem.getDroppedItemId());
        }

        // Then
        Location location = new Location("map", 1, 1, 1);
        DroppedItem droppedItem = itemTestHelper.createAndInsertDroppedItem(location, weapon);
        // next `pickup` will error out
        org.junit.jupiter.api.Assertions.assertThrows(
                InventoryException.class,
                () -> inventoryService.pickupItem(CHARACTER_NAME, droppedItem.getDroppedItemId()));
    }
}
