package net.stockholm.bit.bititem.parameters;

import org.bukkit.Material;

import java.util.List;

public class BitItemParameters {
    private final String name;
    private final List<String> description;
    private final Material material;
    private final int customModelData;
    public BitItemParameters(String name, List<String> description, Material material, int customModelData) {
        this.name = name;
        this.description = description;
        this.material = material;
        this.customModelData = customModelData;
    }
    public String getName() {
        return name;
    }
    public List<String> getDescription() {
        return description;
    }
    public Material getMaterial() {
        return material;
    }
    public int getCustomModelData() {
        return customModelData;
    }
}
