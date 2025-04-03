package net.stockholm.bit.bititem;

import net.stockholm.bit.bititem.key.BitKeyManager;
import net.stockholm.bit.bititem.parameters.BitItemParameters;
import net.stockholm.bit.bititem.parameters.BitParameters;
import net.stockholm.item.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BitItem {
    private final String id;
    private final BitItemParameters bitItemParameters;
    private final BitParameters bitParameters;

    public BitItem(String id, BitItemParameters bitItemParameters, BitParameters bitParameters) {
        this.id = id;
        this.bitItemParameters = bitItemParameters;
        this.bitParameters = bitParameters;

        BitKeyManager.registerKey(id, this);
    }

    public String getId() {
        return id;
    }

    public BitItemParameters getBitItemParameters() {
        return bitItemParameters;
    }

    public BitParameters getBitParameters() {
        return bitParameters;
    }

    public ItemStack getItemStack() {
        return new ItemBuilder(bitItemParameters.getMaterial()).customModelData(bitItemParameters.getCustomModelData()).name(bitItemParameters.getName()).lore(bitItemParameters.getDescription().toArray(new String[0])).tag(BitKeyManager.getKey(this), 1, PersistentDataType.INTEGER).build();
    }

    public static boolean isBit(ItemStack item) {
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return BitKeyManager.getKeys().stream().anyMatch(key -> container.has(key, PersistentDataType.INTEGER));
    }

    public static BitItem getBitItem(ItemStack item) {
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return BitKeyManager.getBitItem(BitKeyManager.getKeys().stream()
                .filter(key -> container.has(key, PersistentDataType.INTEGER))
                .findFirst()
                .orElse(null).getKey());
    }
}