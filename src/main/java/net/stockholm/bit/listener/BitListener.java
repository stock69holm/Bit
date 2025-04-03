package net.stockholm.bit.listener;

import net.stockholm.bit.bititem.BitItem;
import net.stockholm.bit.bititem.processor.BitBlockBreakProcessor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BitListener implements Listener {
    @EventHandler
    private void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null || itemInHand.getType() == Material.AIR) return;

        if (!BitItem.isBit(itemInHand)) return;
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();

        event.setCancelled(true);

        BitBlockBreakProcessor.processBlockBreak(BitItem.getBitItem(itemInHand), blockLocation);
    }
}