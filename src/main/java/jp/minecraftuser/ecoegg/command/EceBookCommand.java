
package jp.minecraftuser.ecoegg.command;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoegg.m;
import jp.minecraftuser.ecoframework.CommandFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ece eggコマンドクラス
 * @author ecolight
 */
public class EceBookCommand extends CommandFrame {

    /**
     * コンストラクタ
     * @param plg_ プラグインインスタンス
     * @param name_ コマンド名
     */
    public EceBookCommand(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * コマンド権限文字列設定
     * @return 権限文字列
     */
    @Override
    public String getPermissionString() {
        return "ecoegg.book";
    }

    /**
     * 処理実行部
     * @param sender コマンド送信者
     * @param args パラメタ
     * @return コマンド処理成否
     */
    @Override
    public boolean worker(CommandSender sender, String[] args) {
        
        ((Player)sender).getInventory().setItemInMainHand(((EcoEgg)plg).makeBook());
        sender.sendMessage(m.plg("えこたまご入手"));
        return true;
    }
    
}
