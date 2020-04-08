package jp.minecraftuser.ecoegg;

import org.apache.commons.lang.StringUtils;
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
        if (StringUtils.isEmpty(item_name)) return false;

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

    /**
     * 文字列からエンティティタイプを取得する
     * @param name MOB名称を指定する
     * @return 対応するエンティティタイプを返却する
     */
    public static EntityType cnvSTR2ENTITY(String name) {
        if (EntityType.BAT.name().equalsIgnoreCase(name)) {
            return EntityType.BAT;
        } else if (EntityType.BLAZE.name().equalsIgnoreCase(name)) {
            return EntityType.BLAZE;
        } else if (EntityType.CAVE_SPIDER.name().equalsIgnoreCase(name)) {
            return EntityType.CAVE_SPIDER;
        } else if (EntityType.CHICKEN.name().equalsIgnoreCase(name)) {
            return EntityType.CHICKEN;
        } else if (EntityType.COW.name().equalsIgnoreCase(name)) {
            return EntityType.COD;
        } else if (EntityType.COD.name().equalsIgnoreCase(name)) {
            return EntityType.COW;
        } else if (EntityType.CREEPER.name().equalsIgnoreCase(name)) {
            return EntityType.CREEPER;
        } else if (EntityType.DOLPHIN.name().equalsIgnoreCase(name)) {
            return EntityType.DOLPHIN;
        } else if (EntityType.DONKEY.name().equalsIgnoreCase(name)) {
            return EntityType.DONKEY;
        } else if (EntityType.DROWNED.name().equalsIgnoreCase(name)) {
            return EntityType.DROWNED;
        } else if (EntityType.ELDER_GUARDIAN.name().equalsIgnoreCase(name)) {
            return EntityType.ELDER_GUARDIAN;
        } else if (EntityType.ENDERMAN.name().equalsIgnoreCase(name)) {
            return EntityType.ENDERMAN;
        } else if (EntityType.ENDER_DRAGON.name().equalsIgnoreCase(name)) {
            return EntityType.ENDER_DRAGON;
        } else if (EntityType.EVOKER.name().equalsIgnoreCase(name)) {
            return EntityType.EVOKER;
        } else if (EntityType.GHAST.name().equalsIgnoreCase(name)) {
            return EntityType.GHAST;
        } else if (EntityType.GIANT.name().equalsIgnoreCase(name)) {
            return EntityType.GIANT;
        } else if (EntityType.GUARDIAN.name().equalsIgnoreCase(name)) {
            return EntityType.GUARDIAN;
        } else if (EntityType.HORSE.name().equalsIgnoreCase(name)) {
            return EntityType.HORSE;
        } else if (EntityType.HUSK.name().equalsIgnoreCase(name)) {
            return EntityType.HUSK;
        } else if (EntityType.ILLUSIONER.name().equalsIgnoreCase(name)) {
            return EntityType.ILLUSIONER;
        } else if (EntityType.IRON_GOLEM.name().equalsIgnoreCase(name)) {
            return EntityType.IRON_GOLEM;
        } else if (EntityType.LLAMA.name().equalsIgnoreCase(name)) {
            return EntityType.LLAMA;
        } else if (EntityType.MAGMA_CUBE.name().equalsIgnoreCase(name)) {
            return EntityType.MAGMA_CUBE;
        } else if (EntityType.MULE.name().equalsIgnoreCase(name)) {
            return EntityType.MULE;
        } else if (EntityType.MUSHROOM_COW.name().equalsIgnoreCase(name)) {
            return EntityType.MUSHROOM_COW;
        } else if (EntityType.OCELOT.name().equalsIgnoreCase(name)) {
            return EntityType.OCELOT;
        } else if (EntityType.PARROT.name().equalsIgnoreCase(name)) {
            return EntityType.PARROT;
        } else if (EntityType.PHANTOM.name().equalsIgnoreCase(name)) {
            return EntityType.PHANTOM;
        } else if (EntityType.PIG.name().equalsIgnoreCase(name)) {
            return EntityType.PIG;
        } else if (EntityType.PIG_ZOMBIE.name().equalsIgnoreCase(name)) {
            return EntityType.PIG_ZOMBIE;
        } else if (EntityType.POLAR_BEAR.name().equalsIgnoreCase(name)) {
            return EntityType.POLAR_BEAR;
        } else if (EntityType.PUFFERFISH.name().equalsIgnoreCase(name)) {
            return EntityType.PUFFERFISH;
        } else if (EntityType.RABBIT.name().equalsIgnoreCase(name)) {
            return EntityType.RABBIT;
        } else if (EntityType.SALMON.name().equalsIgnoreCase(name)) {
            return EntityType.SALMON;
        } else if (EntityType.SHEEP.name().equalsIgnoreCase(name)) {
            return EntityType.SHEEP;
        } else if (EntityType.SHULKER.name().equalsIgnoreCase(name)) {
            return EntityType.SHULKER;
        } else if (EntityType.SILVERFISH.name().equalsIgnoreCase(name)) {
            return EntityType.SILVERFISH;
        } else if (EntityType.SKELETON.name().equalsIgnoreCase(name)) {
            return EntityType.SKELETON;
        } else if (EntityType.SKELETON_HORSE.name().equalsIgnoreCase(name)) {
            return EntityType.SKELETON_HORSE;
        } else if (EntityType.SLIME.name().equalsIgnoreCase(name)) {
            return EntityType.SLIME;
        } else if (EntityType.SNOWMAN.name().equalsIgnoreCase(name)) {
            return EntityType.SNOWMAN;
        } else if (EntityType.SPIDER.name().equalsIgnoreCase(name)) {
            return EntityType.SPIDER;
        } else if (EntityType.SQUID.name().equalsIgnoreCase(name)) {
            return EntityType.SQUID;
        } else if (EntityType.STRAY.name().equalsIgnoreCase(name)) {
            return EntityType.STRAY;
        } else if (EntityType.TROPICAL_FISH.name().equalsIgnoreCase(name)) {
            return EntityType.TROPICAL_FISH;
        } else if (EntityType.TURTLE.name().equalsIgnoreCase(name)) {
            return EntityType.TURTLE;
        } else if (EntityType.VEX.name().equalsIgnoreCase(name)) {
            return EntityType.VEX;
        } else if (EntityType.VILLAGER.name().equalsIgnoreCase(name)) {
            return EntityType.VILLAGER;
        } else if (EntityType.VINDICATOR.name().equalsIgnoreCase(name)) {
            return EntityType.VINDICATOR;
        } else if (EntityType.WITCH.name().equalsIgnoreCase(name)) {
            return EntityType.WITCH;
        } else if (EntityType.WITHER.name().equalsIgnoreCase(name)) {
            return EntityType.WITHER;
        } else if (EntityType.WITHER_SKELETON.name().equalsIgnoreCase(name)) {
            return EntityType.WITHER_SKELETON;
        } else if (EntityType.WOLF.name().equalsIgnoreCase(name)) {
            return EntityType.WOLF;
        } else if (EntityType.ZOMBIE.name().equalsIgnoreCase(name)) {
            return EntityType.ZOMBIE;
        } else if (EntityType.ZOMBIE_HORSE.name().equalsIgnoreCase(name)) {
            return EntityType.ZOMBIE_HORSE;
        } else if (EntityType.ZOMBIE_VILLAGER.name().equalsIgnoreCase(name)) {
            return EntityType.ZOMBIE_VILLAGER;
        }
        return null;
    }
}
