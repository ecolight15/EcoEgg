
package jp.minecraftuser.ecoegg;

import java.util.HashMap;
import jp.minecraftuser.ecoframework.CommandFrame;
import jp.minecraftuser.ecoegg.command.EceBookCommand;
import jp.minecraftuser.ecoegg.command.EceCommand;
import jp.minecraftuser.ecoegg.command.EceGetCommand;
import jp.minecraftuser.ecoegg.command.EceInfoCommand;
import jp.minecraftuser.ecoegg.command.EceReloadCommand;
import jp.minecraftuser.ecoegg.command.EceSetCommand;
import jp.minecraftuser.ecoegg.config.EcoEggConfig;
import jp.minecraftuser.ecoegg.listener.CreatureListener;
import jp.minecraftuser.ecoegg.listener.PlayerListener;
import jp.minecraftuser.ecoframework.PluginFrame;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 *
 * @author ecolight
 */
public class EcoEgg extends PluginFrame {
    private static EcoEggConfig eceConf = null;
    private static Player getter = null;
    HashMap<Player, InfoParam> infoList = null;

    @Override
    public void onEnable() {
        m.msgLoad(null, this);
        initialize();
        eceConf = (EcoEggConfig)getDefaultConfig();
        infoList = new HashMap<>(); 
        getLogger().info(getName()+" Enable");
    }

    @Override
    public void onDisable()
    {
        disable();
        getLogger().info(getName()+" Disable");
    }

    @Override
    public void initializeConfig() {
        registerPluginConfig(new EcoEggConfig(this));
    }

    @Override
    public void initializeCommand() {
        CommandFrame cmd = new EceCommand(this, "ece");
        cmd.addCommand(new EceReloadCommand(this, "reload"));
        cmd.addCommand(new EceInfoCommand(this, "info"));
        cmd.addCommand(new EceGetCommand(this, "get"));
        cmd.addCommand(new EceSetCommand(this, "set"));
        cmd.addCommand(new EceBookCommand(this, "book"));
        registerPluginCommand(cmd);
    }

    @Override
    public void initializeListener() {
        registerPluginListener(new CreatureListener(this, "creature"));
        registerPluginListener(new PlayerListener(this, "player"));
    }
    
    public ItemStack makeBook() {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)item.getItemMeta();
        meta.setAuthor(eceConf.getAuthor());
        meta.setDisplayName(eceConf.getDispName());
        meta.setTitle(eceConf.getTitle());
        for (String page: eceConf.getPages()) {
            meta.addPage(page);
        }
        item.setItemMeta(meta);
        return item;
    }

    public void setGetter(Player pl) {
        this.getter = pl;
    }
    public Player getGetter() {
        return this.getter;
    }
    public void setParamUser(InfoParam param) {
        if (infoList.containsKey(param.getPlayer())) {
            infoList.remove(param.getPlayer());
        }
        infoList.put(param.getPlayer(), param);
    }
    public InfoParam getParamUser(Player pl) {
        InfoParam param = infoList.get(pl);
        if (param != null) infoList.remove(pl);
        return param;
    }
    public boolean chkInfoUser(Player pl) {
        if (!infoList.containsKey(pl)) return false;
        return (infoList.get(pl).getType() == CommandType.INFO);
    }
    public boolean chkSetUser(Player pl) {
        if (!infoList.containsKey(pl)) return false;
        return (infoList.get(pl).getType() == CommandType.SET);
    }

}
