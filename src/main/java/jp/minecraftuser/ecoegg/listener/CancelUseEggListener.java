
package jp.minecraftuser.ecoegg.listener;

import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.ListenerFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * EcoEggドロップリスナクラス
 * @author ecolight
 */
public class CancelUseEggListener extends ListenerFrame  {
    /**
     * コンストラクタ
     * @param plg_ プラグインインスタンス
     * @param name_ 名前
     */
    public CancelUseEggListener(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * MOBスポーンイベントハンドラ
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
}
