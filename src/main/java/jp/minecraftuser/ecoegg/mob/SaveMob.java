package jp.minecraftuser.ecoegg.mob;

import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoegg.m;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SaveMob {

    private LivingEntity entity;
    private Player player;

    private Location loc;
    private LoaderMob save;
    private Plugin plg;

    public SaveMob(LivingEntity ent, Player player, Location loc, LoaderMob save, Plugin plg) {
        this.entity = ent;
        this.player = player;
        this.loc = loc;
        this.save = save;
        this.plg = plg;

    }

    public LivingEntity save() {
        save.setUsed(false);

        save.setMobType((byte) entity.getType().getTypeId());


        save.setCustomName(entity.getCustomName());
        save.setMaxHealth(entity.getMaxHealth());
        save.setHealth(entity.getHealth());


        //ウマとかラバとかロバとかゾンビウマとかスケルトンウマとか
        //最初間違えてラマも登録してた()
        if (entity instanceof AbstractHorse) {
            saveHorse();
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
            ownerSave();
        }
        //動物なら年齢を登録
        if (entity instanceof Animals) {
            saveAnimal();
        }


        return this.entity;

    }

    private void saveRabbit() {

        Rabbit rabbit = (Rabbit) entity;

        save.setRabbitType(rabbit.getRabbitType());


    }

    private void saveWolf() {

        Wolf wolf = (Wolf) entity;

        save.setCollar(wolf.getCollarColor());
        save.setAngry(wolf.isAngry());

    }

    private void saveOcelot() {

        Ocelot ocelot = (Ocelot) entity;

        save.setCatType(ocelot.getCatType());


    }


    private void saveHorse() {

        AbstractHorse horse = (AbstractHorse) entity;

        // 装備とインベントリ内アイテムドロップ
        for (ItemStack i : horse.getEquipment().getArmorContents()) {
            if (i.getType() == Material.AIR) continue;
            entity.getWorld().dropItem(loc, i);
        }
        for (ItemStack item : horse.getInventory().getContents()) {
            if (item == null) {
                m.info("i null");
                continue;
            }
            if (item.getType() == Material.AIR) continue;
            if (loc == null) {
                m.info("loc null");
                return;
            }
            entity.getWorld().dropItem(loc, item);
        }
        save.setMaxDomestication(horse.getMaxDomestication());
        save.setDomestication(horse.getDomestication());
        if (horse instanceof Horse) {
            Horse _horse = (Horse) horse;
            save.setStyle(_horse.getStyle());
            save.setColor(_horse.getColor());
        }
        if (horse.getOwner() != null) save.setOwner(horse.getOwner().getName());
        save.setJumpStrength(horse.getJumpStrength());
        save.setVariant(horse.getVariant());
        save.setSpeed(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
        save.setBleed(horse.canBreed());


    }

    private void ownerSave() {

        Tameable tame_entity = (Tameable) entity;

        if (tame_entity.getOwner() != null) save.setOwner(tame_entity.getOwner().getName());

    }

    private void saveAnimal() {

        Animals animals = (Animals) entity;

        save.setChild(!animals.isAdult());
        save.setAge(animals.getAge());
        save.setBleed(animals.canBreed());

    }


}
