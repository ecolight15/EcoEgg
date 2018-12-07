
package jp.minecraftuser.ecoegg.listener;

import jp.minecraftuser.ecoegg.EcoEggUtil;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.ListenerFrame;
import jp.minecraftuser.ecoframework.Utl;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * EcoEggドロップリスナクラス
 *
 * @author ecolight
 */
public class CancelUseEggListener extends ListenerFrame {
    /**
     * コンストラクタ
     *
     * @param plg_  プラグインインスタンス
     * @param name_ 名前
     */
    public CancelUseEggListener(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * MOBスポーンイベントハンドラ
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void CreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity ent = event.getEntity();
        if (ent == null) return;
        String custom_name = ent.getCustomName();
        if (custom_name == null) return;
        if (custom_name.startsWith("[EcoEgg]")) {
            event.setCancelled(true);
        }
    }

    /**
     * エンエィティ右クリックイベントハンドラ
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void CreatureRightClick(PlayerInteractEntityEvent event) {
        Player plyaer = event.getPlayer();
        ItemStack item = plyaer.getItemInHand();
        if(EcoEggUtil.isMonsterEgg(item)){
            Utl.sendPluginMessage(plg,plyaer,"エンティティに対してモンスターエッグが使用されたので抑止しました");
            Utl.sendPluginMessage(plg,plyaer,"おかしな子供を生みたい場合はオフハンドにもって使用してください");
            event.setCancelled(true);
        }

    }
}
