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
@JsonTypeName("LEGS")
@EqualsAndHashCode(callSuper=false)
public class LegsSlot extends EquippedItems {

    public LegsSlot(String characterName, ItemInstance itemInstance) {
        super(characterName, itemInstance, ItemType.LEGS.getType());
    }
}
