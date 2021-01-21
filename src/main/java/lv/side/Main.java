package lv.side;

import lv.side.Listeners.SignChangeListener;
import lv.side.Utils.SignUtils;
import lv.side.Utils.FileUtils;
import lv.side.commands.MainCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {
    public static SignUtils sellSignList;
    public static SignUtils buySignList;
    private static Main instance;
    private File sellFile;
    private File buyFile;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting!");

        getCommand("SignSave").setExecutor(new MainCommand(this));
        getServer().getPluginManager().registerEvents(new SignChangeListener(), this);

        sellSignList = new SignUtils("sellSigns");
        buySignList = new SignUtils("buySigns");
        createSignFiles();
        saveConfig();

    }

    @Override
    public void onDisable() {
        getLogger().info("Stopped!");
    }

    public static Main getInstance() {
        return instance;
    }

    public static void updateSigns() {
        Main.sellSignList.UpdateSings();
        Main.buySignList.UpdateSings();
    }

    public static void readSigns() {
        Main.sellSignList = FileUtils.readSigns("sellSigns");
        Main.buySignList = FileUtils.readSigns("buySigns");
    }

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private void createSignFiles() {
        try {
            sellFile = new File(getDataFolder(), "sellSigns.xml");
            buyFile = new File(getDataFolder(), "buySigns.xml");
            if (!sellFile.exists()) {
                sellFile.createNewFile();
            }
            if (!buyFile.exists()) {
                buyFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

