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

    public SimpleTradeRecipe(MerchantRecipe recipe) {
        this.input_item_0 = recipe.getIngredients().get(0);
        this.input_item_1 = recipe.getIngredients().get(1);
        this.output_item = recipe.getResult();
        this.uses = recipe.getUses();
        this.max_uses = recipe.getMaxUses();
    }

    public SimpleTradeRecipe(ItemStack input_item_0, ItemStack input_item_1, ItemStack output_item, int uses, int max_uses) {
        this.input_item_0 = input_item_0;
        this.input_item_1 = input_item_1;
        this.output_item = output_item;
        this.uses = uses;
        this.max_uses = max_uses;
    }

    public MerchantRecipe create_MerchantRecipe() {
        MerchantRecipe mr = new MerchantRecipe(output_item, uses, max_uses, false);
        if (input_item_0 != null) {
            mr.addIngredient(input_item_0);
        }
        if (input_item_1 != null) {
            mr.addIngredient(input_item_1);
        }
        return mr;
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap();
        map.put("input_item_0", input_item_0.serialize());
        map.put("input_item_1", input_item_1.serialize());
        map.put("output_item", output_item.serialize());
        map.put("uses", uses);
        map.put("max_uses", max_uses);
        return map;
    }

    public static SimpleTradeRecipe deserialize(Map<String, Object> trade) {
        ItemStack input_item_0 = ItemStack.deserialize((Map<String, Object>) trade.get("input_item_0"));
        ItemStack input_item_1 = ItemStack.deserialize((Map<String, Object>) trade.get("input_item_1"));
        ItemStack output_item = ItemStack.deserialize((Map<String, Object>) trade.get("output_item"));
        int uses = Integer.parseInt(String.valueOf(trade.get("uses")));
        int max_uses = Integer.parseInt(String.valueOf(trade.get("max_uses")));

        return new SimpleTradeRecipe(input_item_0, input_item_1, output_item, uses, max_uses);


    }
}
