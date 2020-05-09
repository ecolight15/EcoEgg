package jp.minecraftuser.ecoegg;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.*;

public class SimpleTradeRecipe implements ConfigurationSerializable {

    private int uses;
    private int max_uses;
    private ItemStack input_item_0;
    private ItemStack input_item_1;
    private ItemStack output_item;
    private float priceMultiplier;
    private int villagerExperience;

    public SimpleTradeRecipe(MerchantRecipe recipe) {
        this.input_item_0 = recipe.getIngredients().get(0);
        this.input_item_1 = recipe.getIngredients().get(1);
        this.output_item = recipe.getResult();
        this.uses = recipe.getUses();
        this.max_uses = recipe.getMaxUses();
        this.priceMultiplier = recipe.getPriceMultiplier();
        this.villagerExperience = recipe.getVillagerExperience();
    }

    public SimpleTradeRecipe(ItemStack input_item_0, ItemStack input_item_1, ItemStack output_item, int uses, int max_uses,float priceMultiplier,int villagerExperience) {
        this.input_item_0 = input_item_0;
        this.input_item_1 = input_item_1;
        this.output_item = output_item;
        this.uses = uses;
        this.max_uses = max_uses;
        this.priceMultiplier = priceMultiplier;
        this.villagerExperience = villagerExperience;
    }

    public MerchantRecipe create_MerchantRecipe() {
        MerchantRecipe mr = new MerchantRecipe(output_item, uses, max_uses, true);
        if (input_item_0 != null) {
            mr.addIngredient(input_item_0);
        }
        if (input_item_1 != null) {
            mr.addIngredient(input_item_1);
        }
        mr.setPriceMultiplier(priceMultiplier);
        //経験値量が0の場合は村人の経験値が増えなくなってしまうため､1を設定する
        if(villagerExperience == 0){
            villagerExperience = 1;
        }
        mr.setVillagerExperience(villagerExperience);
        return mr;
    }


    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("input_item_0", input_item_0.serialize());
        map.put("input_item_1", input_item_1.serialize());
        map.put("output_item", output_item.serialize());
        map.put("uses", uses);
        map.put("max_uses", max_uses);
        map.put("price_multiplier",priceMultiplier);
        map.put("villager_experience",villagerExperience);
        return map;
    }

    public static SimpleTradeRecipe deserialize(Map<String, Object> trade) {
        ItemStack input_item_0 = ItemStack.deserialize((Map<String, Object>) trade.get("input_item_0"));
        ItemStack input_item_1 = ItemStack.deserialize((Map<String, Object>) trade.get("input_item_1"));
        ItemStack output_item = ItemStack.deserialize((Map<String, Object>) trade.get("output_item"));
        int uses = Integer.parseInt(String.valueOf(trade.get("uses")));
        int max_uses = Integer.parseInt(String.valueOf(trade.get("max_uses")));
        float price_multiplier = 0;
        if(trade.containsKey("price_multiplier")){
            price_multiplier = Float.parseFloat(String.valueOf(trade.get("price_multiplier")));
        }
        int villager_experience = 0;
        if(trade.containsKey("villager_experience")){
            villager_experience = Integer.parseInt(String.valueOf(trade.get("villager_experience")));
        }
        return new SimpleTradeRecipe(input_item_0, input_item_1, output_item, uses, max_uses,price_multiplier,villager_experience);
    }
}
