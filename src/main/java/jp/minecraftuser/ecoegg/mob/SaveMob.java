package jp.minecraftuser.ecoegg.mob;

import jp.minecraftuser.ecoegg.SimpleTradeRecipe;
import jp.minecraftuser.ecoegg.SimpleEquipment;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.Utl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SaveMob {

    private LivingEntity entity;
    private Player player;

    private Location loc;
    private LoaderMob save;
    private PluginFrame plg;
    private boolean cancel;

    public SaveMob(LivingEntity entity, Player player, Location loc, LoaderMob save, PluginFrame plg) {
        this.entity = entity;
        this.player = player;
        this.loc = loc;
        this.save = save;
        this.plg = plg;
    }

    public void save() {
        save.setUsed(false);
        save.setMobType((byte) entity.getType().getTypeId());
        save.setCustomName(entity.getCustomName());
        save.setMaxHealth(entity.getMaxHealth());
        save.setHealth(entity.getHealth());


        //ウマとかラバとかロバとかゾンビウマとかスケルトンウマとか
        if (entity instanceof Zombie) {
            saveZombie();
        }
        if (entity instanceof Creeper) {
            saveCreeper();
        }
        if (entity instanceof AbstractHorse) {
            saveHorse();
        }
        if (entity instanceof Sheep) {
            saveSheep();
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
        if (entity instanceof Parrot) {
            saveParrot();
        }
        if (entity instanceof TropicalFish) {
            saveTropicalFish();
        }
        if (entity instanceof Villager) {
            saveVillager();
        }
        if (entity instanceof  Panda) {
            savePanda();
        }
        if(entity instanceof Cat){
            saveCat();
        }
        if (entity instanceof Fox) {
            saveFox();
        }
        if (entity instanceof Tameable) {
            saveTame();
        }
        if (entity instanceof  MushroomCow){
            saveMushroomCow();
        }

        //動物なら年齢を登録
        if (entity instanceof Animals) {
            saveAnimal();
        }
        if (entity instanceof Zombie || entity instanceof Skeleton) {
            saveEntityEquipment();
        }
        savePotionEffect();


    }


    private void saveZombie() {
        Zombie zombie = (Zombie) entity;
        save.setChild(zombie.isBaby());
    }

    private void saveCreeper() {
        Creeper creeper = (Creeper) entity;
        save.setPower(creeper.isPowered());
    }

    private void saveSheep() {
        Sheep sheep = (Sheep) entity;
        save.setSheepColor(sheep.getColor());
    }

    private void saveRabbit() {
        Rabbit rabbit = (Rabbit) entity;
        save.setRabbitType(rabbit.getRabbitType());
    }

    private void saveWolf() {
        Wolf wolf = (Wolf) entity;
        save.setDyeColor(wolf.getCollarColor());
        save.setAngry(wolf.isAngry());
    }

    private void saveOcelot() {
        Ocelot ocelot = (Ocelot) entity;
        save.setOcelotType(ocelot.getCatType());
    }

    private void saveParrot() {
        Parrot parrot = (Parrot) entity;
        save.setParrotVariant(parrot.getVariant());
    }

    private void saveTropicalFish() {
        TropicalFish tropicalFish = (TropicalFish) entity;
        save.setTropicalFishPattern(tropicalFish.getPattern());
        save.setTropicalFishBodyColor(tropicalFish.getBodyColor());
        save.setTropicalFishPatternColor(tropicalFish.getPatternColor());
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
                continue;
            }
            if (item.getType() == Material.AIR) continue;
            if (loc == null) {
                return;
            }
            entity.getWorld().dropItem(loc, item);
        }

        // 保存
        save.setMaxDomestication(horse.getMaxDomestication());
        save.setDomestication(horse.getDomestication());
        if (horse instanceof Horse) {
            Horse _horse = (Horse) horse;
            save.setStyle(_horse.getStyle());
            save.setHorseColor(_horse.getColor());
        } else if (horse instanceof Llama) {
            Llama _llama = (Llama) horse;
            save.setLlamaColor(_llama.getColor());
            save.setLlamaStrength(_llama.getStrength());
        }
        if (horse.getOwner() != null) save.setOwner(horse.getOwner().getName());
        save.setJumpStrength(horse.getJumpStrength());
        save.SetHorseVariant(horse.getVariant());
        save.setSpeed(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
        save.setBreed(horse.canBreed());
    }

    private void saveEntityEquipment() {
        SimpleEquipment simpleEquipment = new SimpleEquipment(entity.getEquipment());
        List<Map> tmp = new LinkedList<>();
        tmp.add(simpleEquipment.serialize());
        save.setEntityEquipment(tmp);
    }

    private void saveVillager() {
        Villager villager = (Villager) entity;
        List<Map> simpleTradeRecipeList = new LinkedList<>();

        villager.getRecipes().forEach(recipe -> simpleTradeRecipeList.add(new SimpleTradeRecipe(recipe).serialize()));

        // 保存
        save.setVillagerTradeList(simpleTradeRecipeList);
        try {
            save.setVillagerProfession(villager.getProfession());
        } catch (IllegalArgumentException e) {
            Utl.sendPluginMessage(plg, player, "旧タイプの村人な為､職業の取得に失敗しました｡");
            Utl.sendPluginMessage(plg, player, "取引を更新してください｡");
            cancel = true;
            return;
        }
        save.setVillagerLevel(villager.getVillagerLevel());

    }

    private void savePanda(){
        Panda panda = (Panda)entity;
        save.setPandaMainGene(panda.getMainGene());
        save.setPandaHiddenGene(panda.getHiddenGene());
    }

    private void saveCat(){
        Cat cat = (Cat)entity;
        save.setCatType(cat.getCatType());
        save.setDyeColor(cat.getCollarColor());
    }


    private void saveFox(){
        Fox fox = (Fox)entity;
        save.setFoxType(fox.getFoxType());
        if(fox.getFirstTrustedPlayer() != null){
            save.setFoxFirstTrustedPlayer(fox.getFirstTrustedPlayer().getName());
        }
        if(fox.getSecondTrustedPlayer() != null){
            save.setFoxSecondTrustedPlayer(fox.getSecondTrustedPlayer().getName());
        }
    }
    private void saveMushroomCow(){
        MushroomCow mushroomCow = (MushroomCow)entity;
        save.setMushroomCowVariant(mushroomCow.getVariant());
    }

    private void saveTame() {
        Tameable tame_entity = (Tameable) entity;

        // 保存
        if (tame_entity.getOwner() != null) save.setOwner(tame_entity.getOwner().getName());
        save.setTamed(tame_entity.isTamed());
    }

    private void saveAnimal() {
        Animals animals = (Animals) entity;

        // 保存
        save.setChild(!animals.isAdult());
        save.setAge(animals.getAge());
        save.setBreed(animals.canBreed());
    }

    private void savePotionEffect() {
        List<Map> potionList = new LinkedList<>();
        entity.getActivePotionEffects().forEach(effect -> potionList.add(effect.serialize()));
        save.savePotionEffectList(potionList);
    }

    public boolean isCancel() {
        return cancel;
    }
}
