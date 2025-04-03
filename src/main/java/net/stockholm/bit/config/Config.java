package net.stockholm.bit.config;

import net.stockholm.bit.Bit;
import net.stockholm.bit.bititem.BitItem;
import net.stockholm.bit.bititem.parameters.BitItemParameters;
import net.stockholm.bit.bititem.parameters.BitParameters;
import org.bukkit.Material;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;
import org.tomlj.TomlArray;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Config {
    public static List<Material> blockedBlocks;
    public static String RECEIVED_BIT;
    public static String USAGE;
    public static String PLAYER_OFFLINE;
    public static String BIT_NULL;

    static {
        blockedBlocks = new ArrayList<>();
    }

    public static void load() {
        File config = new File(Bit.getInstance().getDataFolder(), "config.toml");

        if (!config.exists()) {
            try (InputStream input = Bit.getInstance().getResource("config.toml")) {
                assert input != null;
                Files.copy(input, config.toPath());
            } catch (IOException e) {
                Bit.getInstance().getLogger().severe(String.valueOf(e));
            }
        }

        Path path = config.toPath();

        try {
            TomlParseResult toml = Toml.parse(path);

            TomlTable bits = toml.getTable("bits");
            if (bits != null) {
                bits.keySet().forEach(key -> {
                    TomlTable bitDetails = bits.getTable(key);
                    String name = bitDetails.getString("name");
                    TomlArray loreArray = bitDetails.getArray("description");
                    List<String> lore = (loreArray != null) ? loreArray.toList().stream().map(Object::toString).collect(Collectors.toList()) : new ArrayList<>();
                    Material material = Material.valueOf(bitDetails.getString("material"));
                    int customModelData = bitDetails.getLong("custommodeldata").intValue();
                    int x = bitDetails.getLong("x").intValue();
                    int y = bitDetails.getLong("y").intValue();
                    int z = bitDetails.getLong("z").intValue();

                    BitItem bitItem = new BitItem(key,
                            new BitItemParameters(name, lore, material, customModelData),
                            new BitParameters(x, y, z));
                });
            }

            TomlTable blockedBlocksTable = toml.getTable("blockedBlocks");
            if (blockedBlocksTable != null) {
                List<String> blocked = blockedBlocksTable.keySet().stream()
                        .map(blockedBlocksTable::getString)
                        .collect(Collectors.toList());
                blockedBlocks.addAll(blocked.stream().map(Material::valueOf).collect(Collectors.toList()));
            }

            TomlTable messagesTable = toml.getTable("messages");
            RECEIVED_BIT = messagesTable.getString("received-bit");
            USAGE = messagesTable.getString("usage");
            PLAYER_OFFLINE = messagesTable.getString("player-offline");
            BIT_NULL = messagesTable.getString("bit-null");

        } catch (IOException e) {
            Bit.getInstance().getLogger().severe(String.valueOf(e));
        }
    }
}