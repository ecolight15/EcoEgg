package jp.minecraftuser.ecoegg.mob;

import jp.minecraftuser.ecoegg.config.LoaderMob;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

public class CreateMob {

    private LivingEntity entity;
    private Player player;
    private Block b;
    private LoaderMob load;
    private Plugin plg;

    public CreateMob(LivingEntity ent, Player player, Block b, LoaderMob load, Plugin plg) {
        this.entity = ent;
        this.player = player;
        this.b = b;
        this.load = load;
        this.plg = plg;

    }

    public LivingEntity create() {
        load.setUsed(true);
        entity.setMaxHealth(load.getMaxHealth());
        entity.setHealth(load.getHealth());
        String name = load.getCustomName();
        if (name != null) entity.setCustomName(name);


        if (entity instanceof AbstractHorse) {
            createHorse();
        }
        if (entity instanceof Ocelot) {
            createOcelot();
        }
        if (entity instanceof Rabbit) {
            createRabbit();
        }
        if (entity instanceof Wolf) {
            createWolf();
        }

        if (entity instanceof Tameable) {
            ownerSet();
        }
        if (entity instanceof Animals) {
            createAnimal();
        }


        return this.entity;

    }

    private void createRabbit() {

        Rabbit rabbit = (Rabbit) entity;

        rabbit.setRabbitType(load.getRabbitType());

    }

    private void createWolf() {
        Wolf wolf = (Wolf) entity;

        wolf.setCollarColor(load.getCollar());
        wolf.setAngry(load.getAngry());

    }

    private void createOcelot() {
        Ocelot ocelot = (Ocelot) entity;
        ocelot.setCatType(load.getCatType());

    }


    private void createHorse() {

        ownerSet();

        AbstractHorse horse = (AbstractHorse) entity;
        horse.setMaxDomestication(load.getMaxDomestication());
        horse.setDomestication(load.getDomestication());
        horse.setAge(load.getAge());

        if (horse instanceof org.bukkit.entity.Horse) {
            org.bukkit.entity.Horse normal_horse = (org.bukkit.entity.Horse) horse;
            normal_horse.setStyle(load.getStyle());
            normal_horse.setColor(load.getColor());
        }

        horse.setJumpStrength(load.getJumpStrength());
        horse.setVariant(load.getVariant());
        horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(load.getSpeed());
        horse.setBreed(load.getBleed());


    }

    private void ownerSet() {
        Tameable tame_entity = (Tameable) entity;
        String owner = load.getOwner();
        boolean ownerreset = false;
        if (b.getType() == Material.CARROT) ownerreset = true;
        if (ownerreset) {
            if (owner != null) tame_entity.setOwner(player);
        } else {
            if (owner != null) tame_entity.setOwner(plg.getServer().getOfflinePlayer(owner));
        }
    }

    private void createAnimal() {
        Animals animals = (Animals) entity;
        if (load.getChild()) {
            animals.setBaby();
        } else {
            animals.setAdult();
        }
        animals.setBreed(load.getBleed());
    }


}
