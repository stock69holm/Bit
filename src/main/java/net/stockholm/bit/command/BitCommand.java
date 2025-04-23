package net.stockholm.bit.command;

import net.stockholm.bit.bititem.BitItem;
import net.stockholm.bit.bititem.key.BitKeyManager;
import net.stockholm.bit.config.Config;
import net.stockholm.command.consolecommand.ConsoleCommand;
import net.stockholm.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BitCommand implements ConsoleCommand {
    @Override
    public void execute(ConsoleCommandSender sender, List<String> args) {
        int argsAmount = args.size();

        if (argsAmount < 2) {
            sender.sendMessage(Color.apply(Config.USAGE));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args.get(0));
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            sender.sendMessage(Color.apply(Config.PLAYER_OFFLINE));
            return;
        }

        BitItem bitItem = BitKeyManager.getBitItem(args.get(1));
        if (bitItem == null) {
            sender.sendMessage(Color.apply(Config.BIT_NULL));
            return;
        }

        if (!args.contains("-s")) targetPlayer.sendMessage(Color.apply(Config.RECEIVED_BIT));

        targetPlayer.getInventory().addItem(bitItem.getItemStack());
    }
}