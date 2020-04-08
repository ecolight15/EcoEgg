
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
        DropParam dropParam = eceConf.getDropParam(event.getEntityType());
        if (dropParam == null) return;

        //----------------------------------------------------------------------
        // 魔道書を落とす
        //----------------------------------------------------------------------
        Player player = event.getEntity().getKiller();

        // 所定のドロップ数を設定
        double dropAmount = dropParam.getAmount();
        // ルートボーナスの係数を掛ける
        if (player != null) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (itemStack != null) {
                if (itemStack.getEnchantments().containsKey(Enchantment.LOOT_BONUS_MOBS)) {
                    dropAmount *= dropParam.getLootBonus(itemStack.getEnchantments().get(Enchantment.LOOT_BONUS_MOBS));
                }
            }
        }

        // アイテム情報生成
        //------------------------------------------------------------------
        // 指定した確立でドロップする(configで変更可能)
        //------------------------------------------------------------------
        Random rnd = new Random();
        // 設定値を上限にランダム値を取得し、0以外の場合にはハズレとする
        if (rnd.nextInt(dropParam.getRate()) != 0) return;
        // ドロップ数分ループしてドロップする
        for (int i = 0; i < dropAmount; i++) {
            // ドロップ処理
            LivingEntity livingEntity = event.getEntity();
            Location loc = livingEntity.getLocation();
            if (livingEntity.getType() == EntityType.ENDER_DRAGON) loc.setY(loc.getY()+20);
            livingEntity.getWorld().dropItem(loc, ((EcoEgg)plg).makeBook());
            log.log(Level.INFO, "EggBookDrop:{0}", livingEntity.getLocation().toString());
        }
    }
}
