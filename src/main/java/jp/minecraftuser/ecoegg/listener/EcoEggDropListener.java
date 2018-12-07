
package jp.minecraftuser.ecoegg.listener;

import java.util.Random;
import java.util.logging.Level;
import jp.minecraftuser.ecoegg.DropParam;
import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoegg.config.EcoEggConfig;
import jp.minecraftuser.ecoframework.ListenerFrame;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * EcoEggドロップリスナクラス
 * @author ecolight
 */
public class EcoEggDropListener extends ListenerFrame  {
    private static EcoEggConfig eceConf = null;
    /**
     * コンストラクタ
     * @param plg_ プラグインインスタンス
     * @param name_ 名前
     */
    public EcoEggDropListener(PluginFrame plg_, String name_) {
        super(plg_, name_);
        eceConf = (EcoEggConfig)conf;
    }

    /**
     * エンティティ死亡イベントハンドラ
     * @param event 
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void EntityDeath(EntityDeathEvent event) {
        //----------------------------------------------------------------------
        // MOBが死んだとき
        //----------------------------------------------------------------------
        // DropTableに登録されているか
        DropParam param = eceConf.getDropParam(event.getEntityType());
        if (param == null) return;

        //----------------------------------------------------------------------
        // 魔道書を落とす
        //----------------------------------------------------------------------
        Player pl = event.getEntity().getKiller();
        int loopcnt = 1;
        if (pl != null) {
            ItemStack i = pl.getInventory().getItemInMainHand();
            if (i != null) {
                if (i.getEnchantments().containsKey(Enchantment.LOOT_BONUS_MOBS)) {
                    switch (i.getEnchantments().get(Enchantment.LOOT_BONUS_MOBS)) {
                        case 3:
                            loopcnt++;
                        case 2:
                            loopcnt++;
                        case 1:
                            loopcnt++;
                    }
                }
            }
        }

        // アイテム情報生成
        for (int cnt = 0; cnt < loopcnt; cnt++) {
            //------------------------------------------------------------------
            // 指定した確立でドロップする(configで変更可能)
            //------------------------------------------------------------------
            Random rnd = new Random();
            if (rnd.nextInt(param.getRate()) != 0) return;
            if ((loopcnt > 1) && (param.getRate() == 1)) {
                param.setAmount(param.getAmount() + loopcnt - 1);
                loopcnt = 1;
            }
            for (int i = 0; i < param.getAmount(); i++) {
                // ドロップ処理
                LivingEntity ent = event.getEntity();
                Location loc = ent.getLocation();
                if (ent.getType() == EntityType.ENDER_DRAGON) loc.setY(loc.getY()+20);
                ent.getWorld().dropItem(loc, ((EcoEgg)plg).makeBook());
                log.log(Level.INFO, "EggBookDrop:{0}", ent.getLocation().toString());
            }
        }
    }



}
