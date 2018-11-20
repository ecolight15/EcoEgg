package jp.minecraftuser.ecoegg;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.*;

public class SimpleTradeRecipes implements ConfigurationSerializable {


    private List<ItemStack> result_items = new ArrayList<>();

    public SimpleTradeRecipes(List<MerchantRecipe> recpies) {
        recpies.forEach(m_recipe -> result_items.add(m_recipe.getResult()));
    }



    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result_items", result_items);
        return map;
    }


    public static List hoge(Map<String, Object> map) {
        return ((ArrayList)(map.get("result_items")));

    }
}
