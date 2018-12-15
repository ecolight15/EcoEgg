
package jp.minecraftuser.ecoegg.listener;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.EcoEggUtil;
import jp.minecraftuser.ecoegg.InfoParam;
import jp.minecraftuser.ecoegg.mob.InfoMob;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.ListenerFrame;
import jp.minecraftuser.ecoframework.Utl;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * コマンド処理系イベントリスナクラス
 *
 * @author ecolight
 */
public class CommandListener extends ListenerFrame {

    /**
     * コンストラクタ
     *
     * @param plg_  プラグインインスタンス
     * @param name_ 名前
     */
    public CommandListener(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * プレイヤーエンティティ作用イベント
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerInteractEntity(PlayerInteractEntityEvent event) {

        //----------------------------------------------------------------------
        // たまごのあるMOBの場合
        //----------------------------------------------------------------------
        Player pl = event.getPlayer();
        Entity ent = event.getRightClicked();

        if (((EcoEgg) plg).chkInfoUser(pl)) {
            if (!EcoEggUtil.existMonsterEgg(ent) && !(ent instanceof Player)) {
                Utl.sendPluginMessage(plg, pl, "infoコマンドの対象外のエンティティです");
                return;
            }

            // MOB個別処理
            InfoParam param = ((EcoEgg) plg).getParamUser(pl); //何もしない

            InfoMob infoMob = new InfoMob((LivingEntity) ent, pl, plg);
            infoMob.show();

            event.setCancelled(true);
        } else if (((EcoEgg) plg).chkSetUser(pl)) {
            switch (ent.getType()) {
                case HORSE:
                    break;
                default:
                    Utl.sendPluginMessage(plg, pl, "setコマンドの対象外のエンティティです");
                    return;
            }
            InfoParam param;
            switch (ent.getType()) {
                case HORSE:
                    Horse horse = (Horse) ent;
                    param = ((EcoEgg) plg).getParamUser(pl);
                    switch (param.getOpt()) {
                        case JUMP:
                            horse.setJumpStrength(param.getVal());
                            Utl.sendPluginMessage(plg, pl, "ジャンプ力[" + horse.getJumpStrength() + "]を設定しました");
                            break;
                        case SPEED:
                            horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(param.getVal());
                            Utl.sendPluginMessage(plg, pl, "スピード[" + horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() + "]を設定しました");
                            break;
                        case HEALTH:
                            horse.setMaxHealth(param.getVal());
                            Utl.sendPluginMessage(plg, pl, "HP最大値[" + horse.getMaxHealth() + "]を設定しました");
                            break;
                        case OWNER:
                            if (plg.getServer().getPlayerExact(param.getStr()) == null) return;
                            horse.setOwner(plg.getServer().getPlayerExact(param.getStr()));
                            Utl.sendPluginMessage(plg, pl, "飼い主[" + horse.getOwner().getName() + "]を設定しました");
                            break;
                        case STYLE:
                            horse.setStyle(param.getHorseStyle());
                            Utl.sendPluginMessage(plg, pl, "スタイル[" + horse.getStyle().name() + "]を設定しました");
                            break;
                        case COLOR:
                            horse.setColor(param.getHorseColor());
                            Utl.sendPluginMessage(plg, pl, "色[" + horse.getColor().name() + "]を設定しました");
                            break;
                    }
                    break;
            }
            event.setCancelled(true);
        }
    }
}
