package jp.minecraftuser.ecoegg;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * EcoEggで使用するユーティリティ
 * @author ecolight
 */
public class EcoEggUtil {

    /**
     * モンスターエッグかどうかを判断する
     * @param item 判定するアイテムを渡す
     * @return モンスターエッグかどうかの真偽値を返す
     */
    public static boolean isMonsterEgg(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (!item.getType().getKey().toString().matches(".*_spawn_egg")) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        String item_name = meta.getDisplayName();
        if (item_name == null) return false;

        return item_name.startsWith("[EcoEgg]");
    }

    /**
     * モンスターエッグのあるMOBか判定する
     * @param e エンティティを渡す
     * @return モンスターエッグかどうかの真偽値を返す
     */
    public static boolean existMonsterEgg(Entity e) {
        return Material.matchMaterial("minecraft:" + e.getType().toString() + "_spawn_egg") != null;
    }

    /**
     * 文字列からエンティティタイプを取得する
     * @param name MOB名称を指定する
     * @return 対応するエンティティタイプを返却する
     */
    public static EntityType cnvSTR2ENTITY(String name) {
        if (EntityType.BAT.getName().equalsIgnoreCase(name)) {
            return EntityType.BAT;
        } else if (EntityType.BLAZE.getName().equalsIgnoreCase(name)) {
            return EntityType.BLAZE;
        } else if (EntityType.CAVE_SPIDER.getName().equalsIgnoreCase(name)) {
            return EntityType.CAVE_SPIDER;
        } else if (EntityType.CHICKEN.getName().equalsIgnoreCase(name)) {
            return EntityType.CHICKEN;
        } else if (EntityType.COW.getName().equalsIgnoreCase(name)) {
            return EntityType.COW;
        } else if (EntityType.CREEPER.getName().equalsIgnoreCase(name)) {
            return EntityType.CREEPER;
        } else if (EntityType.ENDERMAN.getName().equalsIgnoreCase(name)) {
            return EntityType.ENDERMAN;
        } else if (EntityType.ENDER_DRAGON.getName().equalsIgnoreCase(name)) {
            return EntityType.ENDER_DRAGON;
        } else if (EntityType.GHAST.getName().equalsIgnoreCase(name)) {
            return EntityType.GHAST;
        } else if (EntityType.GIANT.getName().equalsIgnoreCase(name)) {
            return EntityType.GIANT;
        } else if (EntityType.IRON_GOLEM.getName().equalsIgnoreCase(name)) {
            return EntityType.IRON_GOLEM;
        } else if (EntityType.MAGMA_CUBE.getName().equalsIgnoreCase(name)) {
            return EntityType.MAGMA_CUBE;
        } else if (EntityType.MUSHROOM_COW.getName().equalsIgnoreCase(name)) {
            return EntityType.MUSHROOM_COW;
        } else if (EntityType.OCELOT.getName().equalsIgnoreCase(name)) {
            return EntityType.OCELOT;
        } else if (EntityType.PIG.getName().equalsIgnoreCase(name)) {
            return EntityType.PIG;
        } else if (EntityType.PIG_ZOMBIE.getName().equalsIgnoreCase(name)) {
            return EntityType.PIG_ZOMBIE;
        } else if (EntityType.SHEEP.getName().equalsIgnoreCase(name)) {
            return EntityType.SHEEP;
        } else if (EntityType.SILVERFISH.getName().equalsIgnoreCase(name)) {
            return EntityType.SILVERFISH;
        } else if (EntityType.SKELETON.getName().equalsIgnoreCase(name)) {
            return EntityType.SKELETON;
        } else if (EntityType.SLIME.getName().equalsIgnoreCase(name)) {
            return EntityType.SLIME;
        } else if (EntityType.SNOWMAN.getName().equalsIgnoreCase(name)) {
            return EntityType.SNOWMAN;
        } else if (EntityType.SPIDER.getName().equalsIgnoreCase(name)) {
            return EntityType.SPIDER;
        } else if (EntityType.SQUID.getName().equalsIgnoreCase(name)) {
            return EntityType.SQUID;
        } else if (EntityType.VILLAGER.getName().equalsIgnoreCase(name)) {
            return EntityType.VILLAGER;
        } else if (EntityType.WITCH.getName().equalsIgnoreCase(name)) {
            return EntityType.WITCH;
        } else if (EntityType.WITHER.getName().equalsIgnoreCase(name)) {
            return EntityType.WITHER;
        } else if (EntityType.WOLF.getName().equalsIgnoreCase(name)) {
            return EntityType.WOLF;
        } else if (EntityType.ZOMBIE.getName().equalsIgnoreCase(name)) {
            return EntityType.ZOMBIE;
        }
        return null;
    }
}
