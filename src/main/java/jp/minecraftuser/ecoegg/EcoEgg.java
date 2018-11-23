
package jp.minecraftuser.ecoegg;

import java.util.HashMap;

import jp.minecraftuser.ecoegg.command.*;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoegg.mob.SaveMob;
import jp.minecraftuser.ecoframework.CommandFrame;
import jp.minecraftuser.ecoegg.config.EcoEggConfig;
import jp.minecraftuser.ecoegg.listener.CreatureListener;
import jp.minecraftuser.ecoegg.listener.PlayerListener;
import jp.minecraftuser.ecoframework.PluginFrame;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
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
        eceConf = (EcoEggConfig) getDefaultConfig();
        infoList = new HashMap<>();
        getLogger().info(getName() + " Enable");
        ConfigurationSerialization.registerClass(SimpleTradeRecipe.class);

    }

    @Override
    public void onDisable() {
        disable();
        getLogger().info(getName() + " Disable");
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
        cmd.addCommand(new EceInfoEggCommand(this, "infoegg"));
        cmd.addCommand(new EceGetCommand(this, "get"));
        cmd.addCommand(new EceSetCommand(this, "set"));
        cmd.addCommand(new EceBookCommand(this, "book"));
        cmd.addCommand(new EceEggCommand(this, "egg"));
        registerPluginCommand(cmd);
    }

    @Override
    public void initializeListener() {
        registerPluginListener(new CreatureListener(this, "creature"));
        registerPluginListener(new PlayerListener(this, "player"));
    }

    public ItemStack makeBook() {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) item.getItemMeta();
        meta.setAuthor(eceConf.getAuthor());
        meta.setDisplayName(eceConf.getDispName());
        meta.setTitle(eceConf.getTitle());
        for (String page : eceConf.getPages()) {
            meta.addPage(page);
        }
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack makeEgg(String title) {
        ItemStack egg = new ItemStack(Material.PIG_SPAWN_EGG);//雑い
        ItemMeta im = egg.getItemMeta();
        im.setDisplayName("[EcoEgg]," + title);
        egg.setItemMeta(im);
        return egg;

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
