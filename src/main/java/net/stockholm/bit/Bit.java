package net.stockholm.bit;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.stockholm.SDApi;
import net.stockholm.bit.command.BitCommand;
import net.stockholm.bit.config.Config;
import net.stockholm.bit.listener.BitListener;
import net.stockholm.command.consolecommand.manager.ConsoleCommandManager;
import net.stockholm.command.playercommand.manager.PlayerCommandManager;
import net.stockholm.util.Color;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class Bit extends JavaPlugin {
    private static Bit instance;
    private Logger logger;
    private PluginManager pluginManager;
    private WorldGuardPlugin worldGuardPlugin;
    @Override
    public void onEnable() {
        instance = this;
        this.logger = getLogger();
        this.pluginManager = getServer().getPluginManager();
        this.worldGuardPlugin = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");

        SDApi.init(this);

        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdirs();

        Config.load();

        ConsoleCommandManager.register(new BitCommand(), "bit");
        pluginManager.registerEvents(new BitListener(), this);

        logger.info(Color.apply("&fУспешная " + Color.toLegacy("#719450") + "загрузка&f!"));
        logger.info(Color.apply(worldGuardPlugin == null ?
                "&fWorldGuard " + Color.toLegacy("#531f1f") + "не обнаружен&f. Некоторые функции " + Color.toLegacy("#531f1f") + "недоступны&f."
                :
                "&fWorldGuard " + Color.toLegacy("#719450") + "обнаружен&f. Поддержка регионов " + Color.toLegacy("#719450") + "доступна&f."
        ));
        logger.info(Color.apply("&fПлагин разработан для " + Color.toLegacy("#488186") + "бесплатного &fиспользования. &fИнформация на " + Color.toLegacy("#488186") + "&nstock69holm.github.io/stockholm-dev"));
    }

    @Override
    public void onDisable() {
    }

    public static Bit getInstance() {
        return instance;
    }

    public WorldGuardPlugin getWorldGuardPlugin() {
        return worldGuardPlugin;
    }
}