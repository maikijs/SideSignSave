package lv.side.Utils;

import org.bukkit.block.Block;

public class LocationUtils {
    private String[] text;
    private int X;
    private int Y;
    private int Z;
    private String World;

    public LocationUtils(String[] text, Block block) {
        this.text = text;
        this.X = block.getX();
        this.Y = block.getY();
        this.Z = block.getZ();
        this.World = block.getWorld().getName();
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) { X = x; }

    public int getY() {
        return Y;
    }

    public void setY(int y) { Y = y; }

    public int getZ() {
        return Z;
    }

    public void setZ(int z) { Z = z; }

    public String getWorld() {
        return World;
    }

    public void setWorld(String world) {
        World = world;
    }
}
