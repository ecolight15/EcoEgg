package jp.minecraftuser.ecoegg.mob;

import jp.minecraftuser.ecoegg.m;
import net.minecraft.server.v1_13_R2.EntityVillager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftVillager;
import org.bukkit.entity.*;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class InfoMob {
    private LivingEntity entity;
    private Player player;


    public InfoMob(LivingEntity ent, Player player, Plugin plg) {
        this.entity = ent;
        this.player = player;


    }

    public void show() {

        player.sendMessage(m.plg("===== " + entity.getType() + "ステータス表示 ====="));
        player.sendMessage(m.plg("EntityName:" + entity.getType()));
        player.sendMessage(m.plg("CustomName:" + entity.getName()));
        player.sendMessage(m.plg("MaxHealth:" + entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        player.sendMessage(m.plg("Health:" + entity.getHealth()));


        //ウマとかラバとかロバとかゾンビウマとかスケルトンウマとか
        //最初間違えてラマも登録してた()
        if (entity instanceof AbstractHorse) {
            showHorse();
        }
        if (entity instanceof Ocelot) {
            showOcelot();
        }
        if (entity instanceof Rabbit) {
            showRabbit();
        }
        if (entity instanceof Wolf) {
            showWolf();
        }
        if (entity instanceof Parrot) {
            showParrot();
        }
        if (entity instanceof TropicalFish) {
            showTropicalFish();
        }
        //テイムできるMOBならテイムできるよにする
        if (entity instanceof Tameable) {
            showOwner();
        }
        //動物なら年齢を登録
        if (entity instanceof Animals) {
            showAnimal();
        }
        if (entity instanceof Villager) {
            showVillager();
        }
        player.sendMessage(m.plg("===== " + entity.getName() + "ステータスここまで ====="));

    }

    private void showRabbit() {

        Rabbit rabbit = (Rabbit) entity;

        player.sendMessage(m.plg("RabbitType:" + rabbit.getRabbitType().name()));


    }

    private void showWolf() {

        Wolf wolf = (Wolf) entity;

        player.sendMessage(m.plg("Color:" + wolf.getCollarColor().name()));
        player.sendMessage(m.plg("Angry:" + wolf.isAngry()));
    }

    private void showOcelot() {

        Ocelot ocelot = (Ocelot) entity;
        player.sendMessage(m.plg("CatType:" + ocelot.getCatType().name()));


    }

    private void showParrot() {
        Parrot parrot = (Parrot) entity;
        player.sendMessage(m.plg("Variant:" + parrot.getVariant()));

    }

    public void showTropicalFish() {
        TropicalFish tropicalFish = (TropicalFish) entity;
        player.sendMessage(m.plg("Pattern:" + tropicalFish.getPattern()));
        player.sendMessage(m.plg("BodyColor:" + tropicalFish.getBodyColor()));
        player.sendMessage(m.plg("PatternColor:" + tropicalFish.getPatternColor()));

    }

    private void showHorse() {

        // 馬ステータス表示
        AbstractHorse horse = (AbstractHorse) entity;

        if (horse.getCustomName() != null) player.sendMessage(m.plg("Name:" + horse.getCustomName()));
        player.sendMessage(m.plg("MaxHealth:" + horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        player.sendMessage(m.plg("Health:" + horse.getHealth()));
        player.sendMessage(m.plg("MaxDomestication:" + horse.getMaxDomestication()));
        player.sendMessage(m.plg("Domestication:" + horse.getDomestication()));

        if (horse instanceof Horse) {
            Horse normal_Horse = (Horse) horse;
            player.sendMessage(m.plg("Style:" + normal_Horse.getStyle().name()));
            player.sendMessage(m.plg("Color:" + normal_Horse.getColor().name()));
        }
        player.sendMessage(m.plg("Variant:" + horse.getVariant().name()));
        player.sendMessage(m.plg("JumpStrength:" + horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue()));
        player.sendMessage(m.plg("Speed:" + horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()));


    }

    private void showVillager() {
        Villager villager = (Villager) entity;
        villager.getRecipes().forEach(merchantRecipe -> {
            StringBuilder torihiki = new StringBuilder();
            merchantRecipe.getIngredients().forEach(itemStack -> {
                torihiki.append(itemStack.getType()).append("*").append(itemStack.getAmount());
            });
            torihiki.append("->");
            ItemStack resultItem = merchantRecipe.getResult();
            torihiki.append(resultItem.getType()).append("*").append(resultItem.getAmount());
            torihiki.append("(").append(merchantRecipe.getUses()).append("/").append(merchantRecipe.getMaxUses()).append(")");
            player.sendMessage(m.plg("Trade:" + torihiki));
        });
        player.sendMessage(m.plg("Career:" + villager.getCareer()));
        player.sendMessage(m.plg("Profession:" + villager.getProfession()));
        player.sendMessage(m.plg("Riches" + villager.getRiches()));

        try {
            player.sendMessage("CareerLevel:" + getVillagerCareerLevel(villager));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    private void showOwner() {
        Tameable tame_entity = (Tameable) entity;
        if (tame_entity.getOwner() != null) player.sendMessage(m.plg("Owner:" + tame_entity.getOwner().getName()));

    }

    private void showAnimal() {
        Animals animals = (Animals) entity;
        player.sendMessage(m.plg("Age:" + animals.getAge()));
        player.sendMessage(m.plg("isChild:" + !animals.isAdult()));
    }

    private int getVillagerCareerLevel(Villager villager) throws NoSuchFieldException, IllegalAccessException {
        EntityVillager entityVillager = ((CraftVillager) villager).getHandle();
        Field careerLevelField = EntityVillager.class.getDeclaredField("careerLevel");
        careerLevelField.setAccessible(true);
        return careerLevelField.getInt(entityVillager);
    }


}
