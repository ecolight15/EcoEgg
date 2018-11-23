
package jp.minecraftuser.ecoegg.command;

import javafx.scene.effect.Effect;
import jp.minecraftuser.ecoegg.CommandType;
import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.InfoParam;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoegg.m;
import jp.minecraftuser.ecoegg.mob.CreateMob;
import jp.minecraftuser.ecoegg.mob.InfoMob;
import jp.minecraftuser.ecoframework.CommandFrame;
import jp.minecraftuser.ecoframework.PluginFrame;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

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
        return "ecoegg.egginfo";
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
        ((EcoEgg) plg).setParamUser(new InfoParam((Player) sender, CommandType.INFO));

        Player player = ((Player) sender).getPlayer();
        ItemStack item = player.getItemInHand();

        if (item == null) return false;

        // モンスターエッグ以外は処理しない
        if (!isMonsterEgg(item)) {
            return false;
        }


        // メタ取得
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        String itemname = meta.getDisplayName();
        if (itemname == null) return false;

        // エコエッグの表記で始まるか
        if (!itemname.startsWith("[EcoEgg]")) return false;
        String[] token = itemname.split(",");
        String most = token[token.length - 2];
        String least = token[token.length - 1];

        // MOBのyaml情報を取得
        LoaderMob load = new LoaderMob((EcoEgg) plg, new UUID(Long.parseLong(most), Long.parseLong(least)));

        CreateMob createMob = new CreateMob( player, Material.STONE, player.getLocation(), load, plg);
        LivingEntity entity = createMob.create();

        InfoMob infoMob = new InfoMob(entity, player, plg);
        infoMob.show();

        entity.remove();


        return true;
    }

    public boolean isMonsterEgg(ItemStack item) {
        return item.getType().getKey().toString().matches(".*_spawn_egg");
    }

}
