
package jp.minecraftuser.ecoegg.command;

import jp.minecraftuser.ecoegg.CommandType;
import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.InfoParam;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoegg.m;
import jp.minecraftuser.ecoframework.CommandFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ece infoコマンドクラス
 * @author ecolight
 */
public class EceInfoCommand extends CommandFrame {

    /**
     * コンストラクタ
     * @param plg_ プラグインインスタンス
     * @param name_ コマンド名
     */
    public EceInfoCommand(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * コマンド権限文字列設定
     * @return 権限文字列
     */
    @Override
    public String getPermissionString() {
        return "ecoegg.info";
    }

    /**
     * 処理実行部
     * @param sender コマンド送信者
     * @param args パラメタ
     * @return コマンド処理成否
     */
    @Override
    public boolean worker(CommandSender sender, String[] args) {
        ((EcoEgg)plg).setParamUser(new InfoParam((Player) sender, CommandType.INFO));
        sender.sendMessage(m.plg("対象を右クリックして下さい"));
        return true;
    }
    
}
