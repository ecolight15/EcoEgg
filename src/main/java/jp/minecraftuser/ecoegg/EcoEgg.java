
package jp.minecraftuser.ecoegg;

import java.util.HashMap;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import java.util.logging.Level;
import jp.minecraftuser.ecoegg.command.*;
import jp.minecraftuser.ecoframework.CommandFrame;
import jp.minecraftuser.ecoegg.config.EcoEggConfig;
import jp.minecraftuser.ecoegg.listener.CancelUseEggListener;
import jp.minecraftuser.ecoegg.listener.EcoEggDropListener;
import jp.minecraftuser.ecoegg.listener.CommandListener;
import jp.minecraftuser.ecoegg.listener.DamageCancelListener;
import jp.minecraftuser.ecoegg.listener.HatchingListener;
import jp.minecraftuser.ecoframework.ConfigFrame;
import jp.minecraftuser.ecoframework.PluginFrame;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/**
 * EcoEggプラグインメインクラス
 * @author ecolight
 */
public class EcoEgg extends PluginFrame {
    private static EcoEggConfig eceConf = null;
    private static Player getter = null;
    HashMap<Player, InfoParam> infoList = null;
    public static Object USE_ECO_EGG_FLAG;

    @Override
    public void onLoad() {
        // 設定ファイルからWorldGuard連携のフラグを読み込み、有効時のみフラグ登録する
        ConfigFrame c = new ConfigFrame(this) {
            @Override
            public void reloadNotify() {};
        };
        c.registerBoolean("WorldGuard.Enabled");
        if (c.getBoolean("WorldGuard.Enabled")) {
            getLogger().log(Level.INFO, "WorldGuard flag enabled.");
            USE_ECO_EGG_FLAG = new StateFlag("use-ecoegg", true);
            FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
            try {
                registry.register((Flag)USE_ECO_EGG_FLAG);
            } catch (Exception e) { // FlagConflictException
                e.printStackTrace();
            }
        } else {
            getLogger().log(Level.INFO, "WorldGuard flag disabled.");
        }
    }

    @Override
    public void onEnable() {
        initialize();
        eceConf = (EcoEggConfig) getDefaultConfig();
        infoList = new HashMap<>();
        ConfigurationSerialization.registerClass(SimpleTradeRecipe.class);
    }

    @Override
    public void onDisable() {
        disable();
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
        registerPluginListener(new CancelUseEggListener(this, "cancel"));
        registerPluginListener(new CommandListener(this, "command"));
        registerPluginListener(new DamageCancelListener(this, "damage"));
        registerPluginListener(new EcoEggDropListener(this, "drop"));
        registerPluginListener(new HatchingListener(this, "hatching"));
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
        getter = pl;
    }

    public Player getGetter() {
        return getter;
    }

    public void setParamUser(InfoParam param) {
        infoList.remove(param.getPlayer());
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

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (!(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }

}
