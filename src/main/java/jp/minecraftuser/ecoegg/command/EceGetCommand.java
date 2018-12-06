
package jp.minecraftuser.ecoegg.command;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoframework.CommandFrame;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.Utl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ece getコマンドクラス
 * @author ecolight
 */
public class EceGetCommand extends CommandFrame {

    /**
     * コンストラクタ
     * @param plg_ プラグインインスタンス
     * @param name_ コマンド名
     */
    public EceGetCommand(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * コマンド権限文字列設定
     * @return 権限文字列
     */
    @Override
    public String getPermissionString() {
        return "ecoegg.get";
    }

    /**
     * 処理実行部
     * @param sender コマンド送信者
     * @param args パラメタ
     * @return コマンド処理成否
     */
    @Override
    public boolean worker(CommandSender sender, String[] args) {
        Utl.sendPluginMessage(plg, sender, "対象を右クリックして下さい");
        ((EcoEgg)plg).setGetter((Player) sender);
        return true;
    }
    
}
