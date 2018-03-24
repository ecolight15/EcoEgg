
package jp.minecraftuser.ecoegg.command;

import jp.minecraftuser.ecoegg.CommandType;
import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.InfoParam;
import jp.minecraftuser.ecoegg.OptionType;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoegg.m;
import jp.minecraftuser.ecoframework.CommandFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/**
 * ece setコマンドクラス
 * @author ecolight
 */
public class EceSetCommand extends CommandFrame {

    /**
     * コンストラクタ
     * @param plg_ プラグインインスタンス
     * @param name_ コマンド名
     */
    public EceSetCommand(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * コマンド権限文字列設定
     * @return 権限文字列
     */
    @Override
    public String getPermissionString() {
        return "ecoegg.set";
    }

    /**
     * 処理実行部
     * @param sender コマンド送信者
     * @param args パラメタ
     * @return コマンド処理成否
     */
    @Override
    public boolean worker(CommandSender sender, String[] args) {
        InfoParam param = new InfoParam((Player) sender, CommandType.SET);

        if (args[0].equalsIgnoreCase("jump")) {
            param.setOpt(OptionType.JUMP);
            param.setVal(Double.parseDouble(args[1]));
        } else if (args[0].equalsIgnoreCase("speed")) {
            param.setOpt(OptionType.SPEED);
            param.setVal(Double.parseDouble(args[1]));
        } else if (args[0].equalsIgnoreCase("color")) {
            param.setOpt(OptionType.COLOR);
            if (Horse.Color.valueOf(args[1]) == null) return false;
            param.setHorseColor(Horse.Color.valueOf(args[1]));
        } else if (args[0].equalsIgnoreCase("health")) {
            param.setOpt(OptionType.HEALTH);
            param.setVal(Double.parseDouble(args[1]));
        } else if (args[0].equalsIgnoreCase("owner")) {
            param.setOpt(OptionType.OWNER);
            param.setStr(args[1]);
        } else if (args[0].equalsIgnoreCase("style")) {
            param.setOpt(OptionType.STYLE);
            if (Horse.Style.valueOf(args[1]) == null) return false;
            param.setHorseStyle(Horse.Style.valueOf(args[1]));
        } else if (args[0].equalsIgnoreCase("variant")) {
            param.setOpt(OptionType.VARIANT);
            if (Horse.Variant.valueOf(args[1]) == null) return false;
            param.setHorseVariant(Horse.Variant.valueOf(args[1]));
        } else {
            return false;
        }
        ((EcoEgg)plg).setParamUser(param);

        sender.sendMessage(m.plg("対象の動物(馬)を右クリックして下さい"));
        return true;
    }
    
}
