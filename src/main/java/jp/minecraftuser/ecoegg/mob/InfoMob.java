package jp.minecraftuser.ecoegg.mob;

import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoegg.m;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

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
            saveOcelot();
        }
        if (entity instanceof Rabbit) {
            saveRabbit();
        }
        if (entity instanceof Wolf) {
            saveWolf();
        }
        //テイムできるMOBならテイムできるよにする
        if (entity instanceof Tameable) {
            showOwner();
        }
        //動物なら年齢を登録
        if (entity instanceof Animals) {
            showAge();
        }
        player.sendMessage(m.plg("===== " + entity.getName() + "ステータスここまで ====="));

    }

    private void saveRabbit() {

        Rabbit rabbit = (Rabbit) entity;

        player.sendMessage(m.plg("RabbitType:" + rabbit.getRabbitType().name()));


    }

    private void saveWolf() {

        Wolf wolf = (Wolf) entity;

        player.sendMessage(m.plg("Color:" + wolf.getCollarColor().name()));
        player.sendMessage(m.plg("Angry:" + wolf.isAngry()));
    }

    private void saveOcelot() {

        Ocelot ocelot = (Ocelot) entity;
        player.sendMessage(m.plg("CatType:" + ocelot.getCatType().name()));


    }


    private void showHorse() {

        // 馬ステータス表示
        AbstractHorse horse = (AbstractHorse) entity;

        if (horse.getCustomName() != null) player.sendMessage(m.plg("Name:" + horse.getCustomName()));
        player.sendMessage(m.plg("MaxHealth:" + horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        player.sendMessage(m.plg("Health:" + horse.getHealth()));
        if (horse.getOwner() != null) player.sendMessage(m.plg("Owner:" + horse.getOwner().getName()));
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

    private void showOwner() {
        Tameable tame_entity = (Tameable) entity;
        if (tame_entity.getOwner() != null) player.sendMessage(m.plg("Owner:" + tame_entity.getOwner().getName()));

    }

    private void showAge() {
        Animals animals = (Animals) entity;
        player.sendMessage(m.plg("Age:" + animals.getAge()));


    }
}
