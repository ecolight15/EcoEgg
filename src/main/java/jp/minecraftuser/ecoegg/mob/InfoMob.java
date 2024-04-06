package jp.minecraftuser.ecoegg.mob;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;


import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.Utl;

public class InfoMob {
    private PluginFrame plg;
    private LivingEntity entity;
    private Player player;

    public InfoMob(LivingEntity ent, Player player, PluginFrame plg_) {
        this.plg = plg_;
        this.entity = ent;
        this.player = player;
    }

    public void show() {
        Utl.sendPluginMessage(plg, player, "===== " + entity.getType() + "ステータス表示 =====");
        Utl.sendPluginMessage(plg, player, "EntityName:" + entity.getType());
        Utl.sendPluginMessage(plg, player, "CustomName:" + entity.getCustomName());
        Utl.sendPluginMessage(plg, player, "MaxHealth:" + entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        Utl.sendPluginMessage(plg, player, "Health:" + entity.getHealth());

        if (entity instanceof Zombie) {
            showZombie();
        }
        if (entity instanceof Creeper) {
            showCreeper();
        }
        if (entity instanceof AbstractHorse) {
            showHorse();
        }
        if (entity instanceof Sheep) {
            showSheep();
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
        if (entity instanceof Panda) {
            showPanda();
        }
        if (entity instanceof Cat) {
            showCat();
        }
        if (entity instanceof Fox) {
            showFox();
        }
        if (entity instanceof MushroomCow) {
            showMushroomCow();
        }
        if (entity instanceof  Axolotl){
            showAxolotl();
        }
        if (entity instanceof Frog){
            showFrog();
        }

        if (entity instanceof Tameable) {
            showOwner();
        }
        if (entity instanceof Animals) {
            showAnimal();
        }
        if (entity instanceof Villager) {
            showVillager();
        }
        if (entity instanceof Zombie || entity instanceof Skeleton) {
            showEntityEquipment();
        }
        showPotionEffect();
        Utl.sendPluginMessage(plg, player, "===== " + entity.getType() + "ステータスここまで =====");

    }


    private void showPotionEffect() {
        if (entity.getActivePotionEffects().size() >= 1) {
            Utl.sendPluginMessage(plg, player, "----ポーションエフェクトここから----");
            entity.getActivePotionEffects().forEach(potionEffect -> Utl.sendPluginMessage(plg, player, "Type: " + potionEffect.getType().getName() + " Level: " + potionEffect.getAmplifier() + " Time: " + potionEffect.getDuration() / 20 + "s"));
            Utl.sendPluginMessage(plg, player, "----ポーションエフェクトここまで----");
        }
    }

    private void showZombie() {
        Zombie zombie = (Zombie) entity;
        Utl.sendPluginMessage(plg, player, "isChild:" + zombie.isBaby());
    }

    private void showCreeper() {
        Creeper creeper = (Creeper) entity;
        Utl.sendPluginMessage(plg, player, "isPowered:" + creeper.isPowered());
    }

    private void showSheep() {
        Sheep sheep = (Sheep) entity;
        Utl.sendPluginMessage(plg, player, "SheepColor:" + sheep.getColor());
    }

    private void showRabbit() {
        Rabbit rabbit = (Rabbit) entity;
        Utl.sendPluginMessage(plg, player, "RabbitType:" + rabbit.getRabbitType().name());
    }

    private void showWolf() {
        Wolf wolf = (Wolf) entity;
        Utl.sendPluginMessage(plg, player, "Color:" + wolf.getCollarColor().name());
        Utl.sendPluginMessage(plg, player, "Angry:" + wolf.isAngry());
    }

    private void showOcelot() {
        Ocelot ocelot = (Ocelot) entity;
        Utl.sendPluginMessage(plg, player, "CatType:" + ocelot.getCatType().name());
    }

    private void showParrot() {
        Parrot parrot = (Parrot) entity;
        Utl.sendPluginMessage(plg, player, "Variant:" + parrot.getVariant());
    }

    private void showTropicalFish() {
        TropicalFish tropicalFish = (TropicalFish) entity;
        Utl.sendPluginMessage(plg, player, "Pattern:" + tropicalFish.getPattern());
        Utl.sendPluginMessage(plg, player, "BodyColor:" + tropicalFish.getBodyColor());
        Utl.sendPluginMessage(plg, player, "PatternColor:" + tropicalFish.getPatternColor());
    }

    private void showHorse() {

        // 馬ステータス表示
        AbstractHorse horse = (AbstractHorse) entity;

        if (horse.getCustomName() != null) Utl.sendPluginMessage(plg, player, "Name:" + horse.getCustomName());
        Utl.sendPluginMessage(plg, player, "MaxDomestication:" + horse.getMaxDomestication());
        Utl.sendPluginMessage(plg, player, "Domestication:" + horse.getDomestication());

        if (horse instanceof Horse) {
            Horse normal_Horse = (Horse) horse;
            Utl.sendPluginMessage(plg, player, "Style:" + normal_Horse.getStyle().name());
            Utl.sendPluginMessage(plg, player, "Color:" + normal_Horse.getColor().name());
        } else if (horse instanceof Llama) {
            Llama llama = (Llama) horse;
            Utl.sendPluginMessage(plg, player, "Color:" + llama.getColor().name());
            Utl.sendPluginMessage(plg, player, "Strength:" + llama.getStrength());

        }
        Utl.sendPluginMessage(plg, player, "Variant:" + horse.getVariant().name());
        Utl.sendPluginMessage(plg, player, "JumpStrength:" + horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue());
        Utl.sendPluginMessage(plg, player, "Speed:" + horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
    }

    private void showEntityEquipment() {
        EntityEquipment entityEquipment = entity.getEquipment();
        Utl.sendPluginMessage(plg, player, "----装備品ここから----");
        Utl.sendPluginMessage(plg, player, "Helmet:" + entityEquipment.getHelmet().getType().name());
        Utl.sendPluginMessage(plg, player, "ChestPlate:" + entityEquipment.getChestplate().getType().name());
        Utl.sendPluginMessage(plg, player, "Leggings:" + entityEquipment.getLeggings().getType().name());
        Utl.sendPluginMessage(plg, player, "Boots:" + entityEquipment.getBoots().getType().name());
        Utl.sendPluginMessage(plg, player, "MainHand:" + entityEquipment.getHelmet().getType().name());
        Utl.sendPluginMessage(plg, player, "OffHand:" + entityEquipment.getItemInMainHand().getType().name());
        Utl.sendPluginMessage(plg, player, "Helmet:" + entityEquipment.getItemInOffHand().getType().name());
        Utl.sendPluginMessage(plg, player, "----装備品ここまで----");


    }

    private void showVillager() {
        Villager villager = (Villager) entity;
        Utl.sendPluginMessage(plg, player, "----トレード内容ここから----");
        villager.getRecipes().forEach(merchantRecipe -> {
            StringBuilder trade_recipe = new StringBuilder();
            merchantRecipe.getIngredients().forEach(itemStack -> trade_recipe.append(itemStack.getType()).append(" * ").append(itemStack.getAmount()).append(" "));
            trade_recipe.append(" -> ");
            ItemStack resultItem = merchantRecipe.getResult();
            trade_recipe.append(resultItem.getType()).append(" * ").append(resultItem.getAmount());
            trade_recipe.append("(").append(merchantRecipe.getUses()).append("/").append(merchantRecipe.getMaxUses()).append(")");
            Utl.sendPluginMessage(plg, player, trade_recipe.toString());
            Utl.sendPluginMessage(plg,player,"PriceMultiplier :"+merchantRecipe.getPriceMultiplier());
            Utl.sendPluginMessage(plg,player,"VillagerExperience :"+merchantRecipe.getVillagerExperience());
        });
        Utl.sendPluginMessage(plg, player, "----トレード内容ここまで----");

        try {
            Utl.sendPluginMessage(plg, player, "Profession:" + villager.getProfession());
        } catch (IllegalArgumentException e) {
            Utl.sendPluginMessage(plg, player, "旧タイプの村人な為､職業を取得できませんでした");
            Utl.sendPluginMessage(plg, player, "取引を更新すると職業を取得できます");
            return;
        }
        Utl.sendPluginMessage(plg, player, "VillagerExperience" + villager.getVillagerExperience());
        Utl.sendPluginMessage(plg, player, "VillagerLevel:" + villager.getVillagerLevel());

        Utl.sendPluginMessage(plg, player, "JOB_SITE:" + villager.getMemory(MemoryKey.JOB_SITE));
        Utl.sendPluginMessage(plg, player, "VillagerHome:" + villager.getMemory(MemoryKey.HOME));


    }

    private void showPanda() {
        Panda panda = (Panda) entity;
        Utl.sendPluginMessage(plg, player, "MainGene:" + panda.getMainGene());
        Utl.sendPluginMessage(plg, player, "HiddenGene:" + panda.getHiddenGene());
    }

    private void showCat() {
        Cat cat = (Cat) entity;
        Utl.sendPluginMessage(plg, player, "CatType:" + cat.getCatType());
        Utl.sendPluginMessage(plg, player, "Color:" + cat.getCollarColor());
    }

    private void showMushroomCow() {
        MushroomCow mushroomCow = (MushroomCow) entity;
        Utl.sendPluginMessage(plg,player,"Variant:"+mushroomCow.getVariant());

    }
    private void showAxolotl() {
        Axolotl axolotl = (Axolotl) entity;
        Utl.sendPluginMessage(plg,player,"Variant:"+axolotl.getVariant());
    }
    private void showFrog() {
        Frog frog = (Frog) entity;
        Utl.sendPluginMessage(plg,player,"Variant:"+frog.getVariant());
    }

    private void showFox() {
        Fox fox = (Fox) entity;
        Utl.sendPluginMessage(plg, player, "FoxType:" + fox.getFoxType());

        if (fox.getFirstTrustedPlayer() != null) {
            Utl.sendPluginMessage(plg, player, "FirstTrustedPlayer:" + fox.getFirstTrustedPlayer().getName());
        } else {
            Utl.sendPluginMessage(plg, player, "FirstTrustedPlayer:" + "null");
        }

        if (fox.getSecondTrustedPlayer() != null) {
            Utl.sendPluginMessage(plg, player, "SecondTrustedPlayer:" + fox.getSecondTrustedPlayer().getName());
        } else {
            Utl.sendPluginMessage(plg, player, "SecondTrustedPlayer:" + "null");
        }
    }


    private void showOwner() {
        Tameable tame_entity = (Tameable) entity;

        if (tame_entity.getOwner() != null) {
            Utl.sendPluginMessage(plg, player, "Owner:" + tame_entity.getOwner().getName());
        } else {
            Utl.sendPluginMessage(plg, player, "Owner:" + "null");
        }
        Utl.sendPluginMessage(plg, player, "isTamed:" + tame_entity.isTamed());

    }

    private void showAnimal() {
        Animals animals = (Animals) entity;
        Utl.sendPluginMessage(plg, player, "Age:" + animals.getAge());
        Utl.sendPluginMessage(plg, player, "isChild:" + !animals.isAdult());
    }

}
