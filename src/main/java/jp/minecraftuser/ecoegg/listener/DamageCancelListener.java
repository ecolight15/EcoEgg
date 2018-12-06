
package jp.minecraftuser.ecoegg.listener;

import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.ListenerFrame;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * ダメージキャンセルリスナクラス
 *
 * @author ecolight
 */
public class DamageCancelListener extends ListenerFrame {

    /**
     * コンストラクタ
     *
     * @param plg_  プラグインインスタンス
     * @param name_ 名前
     */
    public DamageCancelListener(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * OP騎乗の落下ダメージはキャンセル
     *
     * @param event
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void EntityDamage(EntityDamageEvent event) {
        // OP騎乗の落下ダメージはキャンセル
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        Entity ent = event.getEntity();
        if (ent == null) return;
        for (Entity e : ent.getPassengers()) {
            if (e.isOp()) {
                event.setCancelled(true);
                break;
            }
        }
    }
}
