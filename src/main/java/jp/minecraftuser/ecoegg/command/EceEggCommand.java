
package jp.minecraftuser.ecoegg.command;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.m;
import jp.minecraftuser.ecoframework.CommandFrame;
import jp.minecraftuser.ecoframework.PluginFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ece eggコマンドクラス
 *
 * @author ecolight
 */
public class EceEggCommand extends CommandFrame {

    /**
     * コンストラクタ
     *
     * @param plg_  プラグインインスタンス
     * @param name_ コマンド名
     */
    public EceEggCommand(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * コマンド権限文字列設定
     *
     * @return 権限文字列
     */
    @Override
    public String getPermissionString() {
        return "ecoegg.egg";
    }

    /**
     * 処理実行部
     *
     * @param sender コマンド送信者
     * @param args   パラメタ
     * @return コマンド処理成否
     */
    @Override
    public boolean worker(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(m.plg("引数が足りません"));
            return true;
        }
        String id = args[0];
        id = id.replace("_", ",");


        ((Player) sender).getInventory().setItemInMainHand(((EcoEgg) plg).makeEgg(id));
        sender.sendMessage(m.plg("モンスターエッグ入手"));
        return true;
    }

}
