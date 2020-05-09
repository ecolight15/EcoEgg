package jp.minecraftuser.ecoegg.mob;

import jp.minecraftuser.ecoegg.SimpleTradeRecipe;
import jp.minecraftuser.ecoegg.SimpleEquipment;
import jp.minecraftuser.ecoegg.Version;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.Utl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CreateMob {
    private LivingEntity entity;
    private EntityType entityType;
    private Player player;
    private Material material;
    private Location loc;
    private LoaderMob load;
    private PluginFrame plg;
    private boolean cancel = false;

    public CreateMob(Player player, Material material, Location loc, LoaderMob load, PluginFrame plg) {
        this.player = player;
        this.material = material;
        this.loc = loc;
        this.load = load;
        this.plg = plg;
    }

    public LivingEntity create() {
        //もしmob_typeが-1ならgen_typeからモンスターの種類を取ってくる
        if (load.getMobType() != -1) {
            entityType = EntityType.fromId(load.getMobType());
        } else {
            entityType = EntityType.valueOf(load.getGenType());
        }

        if (isOldFormatEgg()) {
            Utl.sendPluginMessage(plg, player, "旧式のモンスターエッグです");
            if (entityType == EntityType.HORSE && Horse.Variant.valueOf(load.getHorseVariant().name()) != Horse.Variant.HORSE) {
                EntityType new_entity_type = EntityType.valueOf(load.getHorseVariant().name());
                Utl.sendPluginMessage(plg, player, "EntityTypeを変更" + entityType + "->" + new_entity_type);
                entityType = new_entity_type;
            }
        }

        entity = (LivingEntity) player.getWorld().spawnEntity(loc, entityType);
        try {
            if (material == Material.SOUL_SAND) {
                Utl.sendPluginMessage(plg, player, "ノーマルのモンスターエッグとして使用しました");
                return entity;
            }


            entity.setMaxHealth(load.getMaxHealth());
            entity.setHealth(load.getHealth());

            String name = load.getCustomName();

            if (name != null) {
                entity.setCustomName(name);
                entity.setRemoveWhenFarAway(false);
            }


            if (entity instanceof Zombie) {
                createZombie();
            }
            if (entity instanceof Creeper) {
                createCreeper();
            }
            if (entity instanceof AbstractHorse) {
                createHorse();
            }
            if (entity instanceof Sheep) {
                createSheep();
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
            if (entity instanceof Parrot) {
                createParrot();
            }
            if (entity instanceof TropicalFish) {
                createTropical_Fish();
            }
            if (entity instanceof Panda) {
                createPanda();
            }
            if (entity instanceof Cat) {
                createCat();
            }
            if (entity instanceof Fox) {
                createFox();
            }
            if (entity instanceof MushroomCow) {
                createMushroomCow();
            }

            if (entity instanceof Tameable) {
                setTame();
            }
            if (entity instanceof Animals) {
                createAnimal();
            }
            if (entity instanceof Villager) {
                createVillager();
            }
            if (entity instanceof Zombie || entity instanceof Skeleton) {
                createEntityEquipment();
            }
            createPotionEffect();
        } catch (Exception e) {
            if (!isOldFormatEgg()) {
                //復元に失敗した場合はキャンセルする
                plg.getLogger().log(Level.SEVERE, "Mobの復元に失敗しました");
                e.printStackTrace();

                cancel = true;
            }
        }


        return entity;
    }

    private void createZombie() {
        if (isOldFormatEgg()) {
            Utl.sendPluginMessage(plg, player, "チルド復元処理をスキップしました");
        }
        Zombie zombie = (Zombie) entity;
        zombie.setBaby(load.getChild());
    }

    private void createCreeper() {
        if (Version.compare("0.8", load.getPluginVersion())) {
            Creeper creeper = (Creeper) entity;
            creeper.setPowered(load.getPower());
        } else {
            Utl.sendPluginMessage(plg, player, "帯電復元処理をスキップしました");
        }
    }

    private void createSheep() {
        if (isOldFormatEgg()) {
            Utl.sendPluginMessage(plg, player, "色復元処理をスキップしました");
        }
        Sheep sheep = (Sheep) entity;
        sheep.setColor(load.getSheepColor());

    }

    private void createRabbit() {
        Rabbit rabbit = (Rabbit) entity;
        rabbit.setRabbitType(load.getRabbitType());
    }

    private void createWolf() {
        Wolf wolf = (Wolf) entity;
        wolf.setCollarColor(load.getDyeColor());
        wolf.setAngry(load.getAngry());
    }

    private void createOcelot() {
        Ocelot ocelot = (Ocelot) entity;
        ocelot.setCatType(load.getOcelotType());
    }

    private void createParrot() {
        Parrot parrot = (Parrot) entity;
        parrot.setVariant(load.getParrotVariant());
    }

    private void createTropical_Fish() {
        TropicalFish tropicalFish = (TropicalFish) entity;
        tropicalFish.setPattern(load.getTropicalFishPattern());
        tropicalFish.setBodyColor(load.getTropicalFishBodyColor());
        tropicalFish.setPatternColor(load.getTropicalFishPatternColor());
    }

    private void createHorse() {
        AbstractHorse horse = (AbstractHorse) entity;
        if (horse instanceof Horse) {
            Horse normal_horse = (Horse) horse;
            normal_horse.setStyle(load.getStyle());
            normal_horse.setColor(load.getHorseColor());
        } else if (horse instanceof Llama) {
            if (Version.compare("0.6", load.getPluginVersion())) {
                Llama llama = (Llama) horse;
                llama.setColor(load.getLlamaColor());
                llama.setStrength(load.getLlamaStrength());
            } else {
                Utl.sendPluginMessage(plg, player, "色復元処理をスキップしました");
            }

        }

        horse.setMaxDomestication(load.getMaxDomestication());
        horse.setDomestication(load.getDomestication());
        horse.setAge(load.getAge());

        horse.setJumpStrength(load.getJumpStrength());
        horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(load.getSpeed());
        horse.setBreed(load.getBreed());
    }

    private void createEntityEquipment() {
        if (isOldFormatEgg()) {
            Utl.sendPluginMessage(plg, player, "インベントリ復元処理をスキップしました");
            return;
        }

        EntityEquipment entityEquipment = entity.getEquipment();
        List<Map<?, ?>> serialize_entityEquipment = load.getEntityEquipment();
        SimpleEquipment simpleEquipment = SimpleEquipment.deserialize((Map<String, Object>) serialize_entityEquipment.get(0));
        simpleEquipment.create_EntityEquipment(entityEquipment);//ここで設定

    }

    private void createVillager() {
        Villager villager = (Villager) entity;
        if (isOldFormatEgg()) {
            Utl.sendPluginMessage(plg, player, "トレード内容復元処理をスキップしました");
            return;
        }

        List<Map<?, ?>> serialize_tradeList = load.getTradeList();
        List<MerchantRecipe> trade_list = new ArrayList<>();

        serialize_tradeList.forEach(trade -> {
            SimpleTradeRecipe simpleTradeRecipe = SimpleTradeRecipe.deserialize((Map<String, Object>) trade);
            MerchantRecipe merchantRecipe = simpleTradeRecipe.create_MerchantRecipe();
            trade_list.add(merchantRecipe);
        });

        Villager.Profession villagerProfession = null;
        int villagerLevel = 0;
        int villagerExperience = 0;

        if (Version.compare("1.0", load.getPluginVersion())) {
            villagerProfession = load.getVillagerProfession();
            villagerLevel = load.getVillagerLevel();
            if (Version.compare("1.3", load.getPluginVersion())) {
                villagerExperience = load.getVillagerExperience();
            }
        } else {
            Utl.sendPluginMessage(plg, player, "1.15以前の村人です､変換処理を行います");
            String old_profession = load.getVillagerCareer();
            switch (old_profession) {
                case "WEAPON_SMITH":
                    villagerProfession = Villager.Profession.valueOf("WEAPONSMITH");
                    break;
                case "TOOL_SMITH":
                    villagerProfession = Villager.Profession.valueOf("TOOLSMITH");
                    break;
                default:
                    villagerProfession = Villager.Profession.valueOf(old_profession);
            }

            villagerLevel = load.getVillagerCareerLevel();
            Utl.sendPluginMessage(plg, player, "profession: " + old_profession + "->" + villagerProfession);

        }
        //1~5の範囲に収めないとエラーが発生するので設定
        if (villagerLevel <= 0 || villagerLevel >= 6) {
            int oldVillagerLevel = villagerLevel;
            villagerLevel = Math.max(Math.min(villagerLevel, 5), 1);
            Utl.sendPluginMessage(plg, player, "VillagerLevel: " + oldVillagerLevel + "->" + villagerLevel);
        }
        //villagerExperience復元用処理､Level1=0,2=10,3=50,4=100,5=150
        if (villagerExperience == 0) {
            int oldVillagerExperience = villagerExperience;
            int[] exp = {0, 0, 10, 50, 100, 150};
            villagerExperience = exp[villagerLevel];
            Utl.sendPluginMessage(plg, player, "VillagerExperience: " + oldVillagerExperience + "->" + villagerExperience);
        }
        villager.setProfession(villagerProfession);
        villager.setVillagerExperience(villagerExperience);
        villager.setVillagerLevel(villagerLevel);

        villager.setRecipes(trade_list);
    }

    private void createPanda() {
        Panda panda = (Panda) entity;
        panda.setMainGene(load.getPandaMainGene());
        panda.setHiddenGene(load.getPandaHiddenGene());
    }

    private void createCat() {
        Cat cat = (Cat) entity;
        cat.setCatType(load.getCatType());
        cat.setCollarColor(load.getDyeColor());
    }


    private void createFox() {
        Fox fox = (Fox) entity;
        boolean ownerreset = false;
        String firstTrustedPlayer = load.getFoxFirstTrustedPlayer();
        String secondTrustedPlayer = load.getFoxSecondTrustedPlayer();
        fox.setFoxType(load.getFoxType());

        if (material == Material.CARROTS) ownerreset = true;

        if (ownerreset) {
            fox.setFirstTrustedPlayer(player);
        } else {
            if (firstTrustedPlayer != null) {
                fox.setFirstTrustedPlayer(plg.getServer().getOfflinePlayer(firstTrustedPlayer));
            }
            if (secondTrustedPlayer != null) {
                fox.setSecondTrustedPlayer(plg.getServer().getOfflinePlayer(secondTrustedPlayer));
            }
        }
    }

    private void createMushroomCow() {
        if (Version.compare("1.0", load.getPluginVersion())) {
            MushroomCow mushroomCow = (MushroomCow) entity;
            mushroomCow.setVariant(load.getMushroomCowVariant());
        } else {
            Utl.sendPluginMessage(plg, player, "MushroomCowVariant 復元処理をスキップしました");
        }

    }

    private void setTame() {
        Tameable tame_entity = (Tameable) entity;
        String owner = load.getOwner();
        boolean ownerreset = false;

        if (material == Material.CARROTS) ownerreset = true;
        if (ownerreset) {
            Utl.sendPluginMessage(plg, player, "オーナーをリセットしました");
            tame_entity.setOwner(player);
        } else {
            if (owner != null) tame_entity.setOwner(plg.getServer().getOfflinePlayer(owner));
        }

        if (!isOldFormatEgg()) {
            tame_entity.setTamed(load.getTamed());
        }
    }

    private void createAnimal() {
        Animals animals = (Animals) entity;
        if (load.getChild()) {
            animals.setBaby();
        } else {
            animals.setAdult();
        }
        animals.setBreed(load.getBreed());
    }

    private void createPotionEffect() {
        if (isOldFormatEgg()) {
            Utl.sendPluginMessage(plg, player, "PotionEffect復元機能をスキップしました");
            return;
        }

        List<Map<?, ?>> serialize_PotionEffectList = load.getPotionEffectList();
        List<PotionEffect> potionEffectList = new ArrayList<>();

        serialize_PotionEffectList.forEach(potionEffect -> {
            potionEffectList.add(new PotionEffect((Map<String, Object>) potionEffect));
        });
        entity.addPotionEffects(potionEffectList);
    }


    private boolean isOldFormatEgg() {
        return load.getPluginVersion() == null;//雑い
    }


    public boolean isCancel() {
        return this.cancel;
    }
}
