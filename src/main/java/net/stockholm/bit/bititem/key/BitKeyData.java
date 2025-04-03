package net.stockholm.bit.bititem.key;

import net.stockholm.bit.bititem.BitItem;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public class BitKeyData {
    private static final Map<NamespacedKey, BitItem> bitKeyData = new HashMap<>();
    public static Map<NamespacedKey, BitItem> getBitKeyData() {
        return bitKeyData;
    }
}