package lv.side.commands;

import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lv.side.Main;

import java.util.Set;

import static lv.side.Main.color;

public class MainCommand implements CommandExecutor {

    private final Main plugin;

    public MainCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender);
        if (sender != null) {

            if (!player.hasPermission("side.admin")) {
                sender.sendMessage("You don't have permission");
                return false;
            }
            if (args.length > 1) {
                if (args[0].equals("Buy") || args[0].equals("Sell") || args[0].equals("Spawner")) {
                    if (player.getTargetBlock((Set) null, 5).getState() instanceof Sign) {
                        if (args[1] != null && args[2] != null) {
                            Sign sign = (Sign) player.getTargetBlock((Set) null, 5).getState();
                            sign.setLine(0, color("&1[" + args[0] + "]"));
                            sign.setLine(1, args[1]);
                            sign.setLine(2, args[2]);
                            sign.setLine(3, "$" + plugin.getConfig().getString("Prices." + args[0] + "." + args[1] + "." + args[2]));
                            sign.update();
                            if (args[0].equals("Sell")) {
                                Main.sellSignList.addSign(sign.getLines(), sign.getBlock());
                            } else if (args[0].equals("Buy")) {
                                Main.buySignList.addSign(sign.getLines(), sign.getBlock());
                            }
                            return true;
                        }
                        sender.sendMessage(color("&4[!] &cUsage: /sign <Buy/Sell/Spawner/nobuy/nosell> <Amount> <Item/Mob>"));
                        return false;
                    }
                    sender.sendMessage(color("&4[!] &cPlease look at sign when use this command."));
                    return false;
                }
                if (args[0].equals("nobuy")) {
                    Sign sign = (Sign) player.getTargetBlock((Set) null, 5).getState();
                    sign.setLine(0, color("&4&lNOT"));
                    sign.setLine(1, color("&4&lAVAILABLE"));
                    sign.setLine(2, color("&4&lTO"));
                    sign.setLine(3, color("&4&lBUY"));
                    sign.update();
                    sender.sendMessage(color("&6[!] &eNot available to buy sign set."));
                    return true;
                }
                if (args[0].equals("nosell")) {
                    Sign sign = (Sign) player.getTargetBlock((Set) null, 5).getState();
                    sign.setLine(0, color("&4&lNOT"));
                    sign.setLine(1, color("&4&lAVAILABLE"));
                    sign.setLine(2, color("&4&lFOR"));
                    sign.setLine(3, color("&4&lSALE"));
                    sign.update();
                    sender.sendMessage(color("&6[!] &eNot available for sale sign set."));
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    sender.sendMessage(color("&6[!] &ePlugin reloaded successfully."));
                    return true;
                }
                if (args[0].equalsIgnoreCase("update")) {
                    Main.readSigns();
                    Main.updateSigns();
                    sender.sendMessage(color("&6[!] &eSigns updated successfully."));
                    return true;
                }
            } else {
                sender.sendMessage(color("&6&lSignSave &7Commands"));
                sender.sendMessage(color("&8* &e/sign <Buy/Sell/Spawner> <Amount> <Item/Mob>"));
                sender.sendMessage(color("&8* &e/sign <nobuy/nosell> &8- &fSet no buy or no sell sign."));
                sender.sendMessage(color("&8* &e/sign reload &8- &fReloads the plugin."));
                return true;
            }
            return false;
        }
        return false;
    }
}