package jp.minecraftuser.ecoegg;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * EcoEggで使用するユーティリティ
 * @author ecolight
 */
public class EcoEggUtil {

    /**
     * モンスターエッグかどうかを判断する
     * @param itemStack 判定するアイテムを渡す
     * @return モンスターエッグかどうかの真偽値を返す
     */
    public static boolean isMonsterEgg(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (!itemStack.getType().getKey().toString().matches(".*_spawn_egg")) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        String item_name = itemMeta.getDisplayName();
        if (item_name.isEmpty()) return false;

        return item_name.startsWith("[EcoEgg]");
    }

    /**
     * モンスターエッグのあるMOBか判定する
     * @param e エンティティを渡す
     * @return モンスターエッグかどうかの真偽値を返す
     */
    public static boolean existMonsterEgg(Entity e){
        return Material.matchMaterial("minecraft:" + e.getType().getName() + "_spawn_egg") != null;
    }

}
