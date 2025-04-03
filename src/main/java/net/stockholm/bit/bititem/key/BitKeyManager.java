package net.stockholm.bit.bititem.key;

import net.stockholm.bit.Bit;
import net.stockholm.bit.bititem.BitItem;
import org.bukkit.NamespacedKey;

import java.util.Collection;
import java.util.Map;

public class BitKeyManager {
    public static void registerKey(String key, BitItem bitItem) {
        BitKeyData.getBitKeyData().put(new NamespacedKey(Bit.getInstance(), key), bitItem);
    }
    public static NamespacedKey getKey(BitItem bitItem) {
        return BitKeyData.getBitKeyData().entrySet().stream()
                .filter(entry -> entry.getValue().equals(bitItem))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
    public static BitItem getBitItem(String key) {
        return BitKeyData.getBitKeyData().keySet().stream()
                .filter(namespacedKey -> namespacedKey.getKey().equals(key))
                .findFirst()
                .map(BitKeyData.getBitKeyData()::get)
                .orElse(null);
    }
    public static Collection<NamespacedKey> getKeys() {
        return BitKeyData.getBitKeyData().keySet();
    }
}