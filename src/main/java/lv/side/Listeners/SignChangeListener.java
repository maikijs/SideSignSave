package lv.side.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import lv.side.Main;

public class SignChangeListener implements Listener
{
  @EventHandler
  public void SignChangeEvent(SignChangeEvent event){
    if (event.getLine(0).contains("[Buy]")) {
      Main.buySignList.addSign(event.getLines(), event.getBlock());
    }
    if (event.getLine(0).contains("[Sell]")) {
      Main.sellSignList.addSign(event.getLines(), event.getBlock());
    }

  }
}
