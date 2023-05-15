package server.player.character.equippable.model.types;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import server.items.model.ItemInstance;
import server.items.types.ItemType;
import server.player.character.equippable.model.EquippedItems;

@Data
@NoArgsConstructor
@JsonTypeName("CAPE")
@EqualsAndHashCode(callSuper=false)
public class CapeSlot extends EquippedItems {

    public CapeSlot(String characterName, ItemInstance itemInstance) {
        super(characterName, itemInstance, ItemType.CAPE.getType());
    }
}
