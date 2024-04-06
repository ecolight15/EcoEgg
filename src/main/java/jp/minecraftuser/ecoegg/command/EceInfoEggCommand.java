
package jp.minecraftuser.ecoegg.command;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.EcoEggUtil;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoegg.mob.CreateMob;
import jp.minecraftuser.ecoegg.mob.InfoMob;
import jp.minecraftuser.ecoframework.CommandFrame;
import jp.minecraftuser.ecoframework.PluginFrame;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import jp.minecraftuser.ecoframework.Utl;

/**
 * ece infoコマンドクラス
 *
 * @author ecolight
 */
public class EceInfoEggCommand extends CommandFrame {

    /**
     * コンストラクタ
     *
     * @param plg_  プラグインインスタンス
     * @param name_ コマンド名
     */
    public EceInfoEggCommand(PluginFrame plg_, String name_) {
        super(plg_, name_);
    }

    /**
     * コマンド権限文字列設定
     *
     * @return 権限文字列
     */
    @Override
    public String getPermissionString() {
        return "ecoegg.infoegg";
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
        // モンスターエッグ以外は処理しない
        Player player = ((Player) sender).getPlayer();
        ItemStack item = player.getItemInHand();
        if (!EcoEggUtil.isMonsterEgg(item)) {
            Utl.sendPluginMessage(plg, sender, "モンスターエッグを持った状態でコマンドを入力してください");
            return true;
        }

        String item_name = item.getItemMeta().getDisplayName();
        String[] token = item_name.split(",");
        String most = token[token.length - 2];
        String least = token[token.length - 1];

        // MOBのyaml情報を取得
        LoaderMob load = new LoaderMob((EcoEgg) plg, new UUID(Long.parseLong(most), Long.parseLong(least)));

        CreateMob createMob = new CreateMob(player, Material.STONE, player.getLocation(), player.getLocation(), load, plg);
        LivingEntity entity = createMob.create();

        InfoMob infoMob = new InfoMob(entity, player, plg);
        infoMob.show();

        entity.remove();

        return true;
    }


}
