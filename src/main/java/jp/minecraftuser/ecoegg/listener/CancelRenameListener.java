
package jp.minecraftuser.ecoegg.listener;

import jp.minecraftuser.ecoegg.EcoEggUtil;
import jp.minecraftuser.ecoframework.ListenerFrame;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.Utl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * 偽卵作成キャンセルイベントリスナクラス
 *
 * @author ecolight
 */
public class CancelRenameListener extends ListenerFrame {

    /**
     * コンストラクタ
     *
     * @param plg_  プラグインインスタンス
     * @param name_ 名前
     */
    public CancelRenameListener(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * プレイヤーエンティティ作用イベント
     * 金床のリザルトアイテムをクリックした際に、作成されるのがえこたまご([EcoEgg])であれば、イベントをキャンセル
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void AnvilInventoryClickEvent(InventoryClickEvent event) {

        if (event.getInventory().getType() == InventoryType.ANVIL) {
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack resultItem = event.getCurrentItem();
                if(EcoEggUtil.isMonsterEgg(resultItem)){
                    event.setCancelled(true);
                    Player player = (Player) event.getWhoClicked();
                    player.closeInventory();
                    Utl.sendPluginMessage(plg, player, "ニセたまごの作成は禁止されています。");
                }
            }
        }

    }
}
