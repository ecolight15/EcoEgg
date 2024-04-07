
package jp.minecraftuser.ecoegg.command;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.CommandFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * ece eggコマンドクラス
 * @author ecolight
 */
public class EceBookUpdateCommand extends CommandFrame {

    /**
     * コンストラクタ
     * @param plg_ プラグインインスタンス
     * @param name_ コマンド名
     */
    public EceBookUpdateCommand(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * コマンド権限文字列設定
     * @return 権限文字列
     */
    @Override
    public String getPermissionString() {
        return "ecoegg.bookupdate";
    }

    /**
     * 処理実行部
     * @param sender コマンド送信者
     * @param args パラメタ
     * @return コマンド処理成否
     */
    @Override
    public boolean worker(CommandSender sender, String[] args) {
        Player player = ((Player)sender);
        int eceBookCount = 0;
        for (ItemStack itemStack : player.getInventory()) {
            if (((EcoEgg) plg).isBook(itemStack)) {
                eceBookCount += itemStack.getAmount();
                itemStack.setAmount(0);
            }
        }
        for (int i = 0; i < eceBookCount; i++){
            ItemStack itemStack = ((EcoEgg) plg).makeBook();
            player.getInventory().addItem(itemStack);
        }
        return true;
    }

}
