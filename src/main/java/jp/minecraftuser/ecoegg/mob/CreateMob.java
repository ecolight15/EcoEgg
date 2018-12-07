package jp.minecraftuser.ecoegg.mob;

import jp.minecraftuser.ecoegg.SimpleTradeRecipe;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoframework.Utl;
import net.minecraft.server.v1_13_R2.EntityVillager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftVillager;
import org.bukkit.entity.*;
import org.bukkit.inventory.MerchantRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateMob {
    private LivingEntity entity;
    private EntityType type;
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
            type = EntityType.fromId(load.getMobType());
        } else {
            type = EntityType.valueOf(load.getGenType());
        }

        if (isOldFormatEgg()) {
            Utl.sendPluginMessage(plg, player, "旧式のモンスターエッグです");
            if (type == EntityType.HORSE && Horse.Variant.valueOf(load.getHorseVariant().name()) != Horse.Variant.HORSE) {
                EntityType new_entity_type = EntityType.valueOf(load.getHorseVariant().name());
                Utl.sendPluginMessage(plg, player, "EntityTypeを変更" + type + "->" + new_entity_type);
                type = new_entity_type;
            }
        }

        entity = (LivingEntity) player.getWorld().spawnEntity(loc, type);
        if(material == Material.SOUL_SAND){
            Utl.sendPluginMessage(plg,player,"ノーマルのモンスターエッグとして使用しました");
            return entity;
        }


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
        if (entity instanceof Parrot) {
            createParrot();
        }
        if (entity instanceof TropicalFish) {
            createTropical_Fish();
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
        return entity;
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
        if (horse instanceof org.bukkit.entity.Horse) {

            org.bukkit.entity.Horse normal_horse = (org.bukkit.entity.Horse) horse;
            normal_horse.setStyle(load.getStyle());
            normal_horse.setColor(load.getColor());
        }

        horse.setMaxDomestication(load.getMaxDomestication());
        horse.setDomestication(load.getDomestication());
        horse.setAge(load.getAge());

        horse.setJumpStrength(load.getJumpStrength());
        horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(load.getSpeed());
        horse.setBreed(load.getBreed());
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

        //先にProfessionを登録すること;
        villager.setProfession(load.getVillagerCareer().getProfession());
        villager.setCareer(load.getVillagerCareer(), false);
        villager.setRecipes(trade_list);
        villager.setRiches(load.getVillagerRiches());

        try {
            setVillagerCareerLevel(villager, load.getVillagerCareerLevel());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            cancel = true;
            Utl.sendPluginMessage(plg, player, "CareerLevelの復元処理に失敗しました 管理者に報告してください");

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

    private void setVillagerCareerLevel(Villager villager, int level) throws NoSuchFieldException, IllegalAccessException {
        EntityVillager entityVillager = ((CraftVillager) villager).getHandle();
        Field careerLevelField = EntityVillager.class.getDeclaredField("careerLevel");
        careerLevelField.setAccessible(true);
        careerLevelField.set(entityVillager, level);
    }

    private boolean isOldFormatEgg() {
        return load.getPluginVersion() == null;//雑い
    }

    public boolean isCancel() {
        return this.cancel;
    }
}
