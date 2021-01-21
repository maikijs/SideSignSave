package lv.side.Utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.ArrayList;

public class SignUtils {
    private ArrayList<LocationUtils> signs;
    private String fileName;

    public SignUtils(String fileName) {
        signs = new ArrayList<>();
        this.fileName = fileName;
    }

    public void UpdateSings() {
        ArrayList<Block> notExistingSigns = new ArrayList<>();
        for (LocationUtils e : signs) {
            Block block = Bukkit.getWorld(e.getWorld())
                    .getBlockAt(e.getX(), e.getY(), e.getZ());
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                if (fileName.equals("sellSigns")) {
                    sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&1[Sell]"));
                } else if (fileName.equals("buySigns")) {
                    sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&1[Buy]"));
                }
                sign.setLine(1, e.getText()[1]);
                sign.setLine(2, e.getText()[2]);
                sign.setLine(3, e.getText()[3]);
                sign.update();
            } else {
                notExistingSigns.add(block);
            }
        }
        for (Block blockss : notExistingSigns) {
            removeSignByCords(blockss);
        }
    }

    public void addSign(String[] text, Block block) {
        signs.add(new LocationUtils(text, block));
        FileUtils.saveSignToXML(this);
    }

    public void removeSignByCords(Block block) {
        LocationUtils tempV = null;
        for (LocationUtils e : signs) {
            if (e.getX() == block.getX() && e.getY() == block.getY()
                    && e.getZ() == block.getZ()) {
                tempV = e;
            }
        }
        signs.remove(tempV);
        FileUtils.saveSignToXML(this);
    }

    public ArrayList<LocationUtils> getSigns() {
        return signs;
    }

    public void setSigns(ArrayList<LocationUtils> signs) {
        this.signs = signs;
    }

    public String getFileName() {
        return fileName;
    }
}
