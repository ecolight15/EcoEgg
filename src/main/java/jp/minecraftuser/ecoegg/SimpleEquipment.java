package jp.minecraftuser.ecoegg;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleEquipment implements ConfigurationSerializable {


    private ItemStack helmet;
    private ItemStack chestPlate;
    private ItemStack leggings;
    private ItemStack boots;
    private ItemStack mainHand;
    private ItemStack offHand;

    private ItemStack output_item;

    public SimpleEquipment(EntityEquipment equipment) {

        this.helmet = equipment.getHelmet();
        this.chestPlate = equipment.getChestplate();
        this.leggings = equipment.getLeggings();
        this.boots = equipment.getBoots();
        this.mainHand = equipment.getItemInMainHand();
        this.offHand = equipment.getItemInOffHand();
    }

    public SimpleEquipment(ItemStack helmet, ItemStack chestPlate, ItemStack leggings, ItemStack boots, ItemStack mainHand, ItemStack offHand) {

        this.helmet = helmet;
        this.chestPlate = chestPlate;
        this.leggings = leggings;
        this.boots = boots;
        this.mainHand = mainHand;
        this.offHand = offHand;
    }

    public EntityEquipment create_EntityEquipment(EntityEquipment equipment) {


        equipment.setHelmet(this.helmet);
        equipment.setHelmetDropChance(0);

        equipment.setChestplate(this.chestPlate);
        equipment.setChestplateDropChance(0);

        equipment.setLeggings(this.leggings);
        equipment.setLeggingsDropChance(0);

        equipment.setBoots(this.boots);
        equipment.setBootsDropChance(0);

        equipment.setItemInMainHand(this.mainHand);
        equipment.setItemInMainHandDropChance(0);

        equipment.setItemInOffHand(this.offHand);
        equipment.setItemInOffHandDropChance(0);

        return equipment;

    }


    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap map = new LinkedHashMap();

        map.put("helmet", helmet.serialize());
        map.put("chestPlate", chestPlate.serialize());
        map.put("leggings", leggings.serialize());
        map.put("boots", boots.serialize());

        map.put("mainHand", mainHand.serialize());
        map.put("offHand", offHand.serialize());
        return map;
    }

    public static SimpleEquipment deserialize(Map<String, Object> trade) {

        ItemStack helmet = ItemStack.deserialize((Map<String, Object>) trade.get("helmet"));
        ItemStack chestPlate = ItemStack.deserialize((Map<String, Object>) trade.get("chestPlate"));
        ItemStack leggings = ItemStack.deserialize((Map<String, Object>) trade.get("leggings"));
        ItemStack boots = ItemStack.deserialize((Map<String, Object>) trade.get("boots"));
        ItemStack mainHand = ItemStack.deserialize((Map<String, Object>) trade.get("mainHand"));
        ItemStack offHand = ItemStack.deserialize((Map<String, Object>) trade.get("offHand"));

        return new SimpleEquipment(helmet,chestPlate,leggings,boots,mainHand,offHand);


    }
}