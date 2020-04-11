
package jp.minecraftuser.ecoegg.config;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import jp.minecraftuser.ecoegg.EcoEgg;
import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;


/**
 * MOB卵ファイルR/Wクラス
 *
 * @author ecolight
 */
public class LoaderMob extends LoaderYaml {
    private EcoEgg plg = null;
    private FileConfiguration list = null;

    public LoaderMob(EcoEgg plg, UUID id) {
        super(plg, "" + id.getMostSignificantBits() + "_" + id.getLeastSignificantBits() + ".yml");
        this.plg = plg;
        reloadCnf();
        list = getCnf();
        list.options().copyDefaults(true);
        saveCnf();
    }

    public void saveGen(String name, String type, String date, String version) {
        list.set("gen_user", name);
        list.set("gen_type", type);
        list.set("gen_date", date);
        list.set("gen_plugin_version", version);
        saveCnf();
    }

    public void saveUse(String name, String type, String date) {
        list.set("use_user", name);
        list.set("use_type", type);
        list.set("use_date", date);
        saveCnf();
    }

    public void saveDate(long date) {
        list.set("date", date);
        saveCnf();
    }

    public void setUsed(boolean b) {
        list.set("used", b);
        saveCnf();
    }

    public void setMobType(short typeid) {
        list.set("mobid", typeid);
        saveCnf();
    }

    public void setCustomName(String str) {
        list.set("name", str);
        saveCnf();
    }

    public void setMaxHealth(double num) {
        list.set("maxhealth", num);
        saveCnf();
    }

    public void setHealth(double num) {
        list.set("health", num);
        saveCnf();
    }

    public void setOwner(String str) {
        list.set("owner", str);
        saveCnf();
    }

    public void setMaxDomestication(int num) {
        list.set("maxdomestication", num);
        saveCnf();
    }

    public void setDomestication(int num) {
        list.set("domestication", num);
        saveCnf();
    }

    public void setAge(int num) {
        list.set("age", num);
        saveCnf();
    }

    public void setSheepColor(DyeColor color) {
        list.set("sheepcolor", color.name());
        saveCnf();
    }

    public void setLlamaColor(Llama.Color color) {
        list.set("color", color.name());
        saveCnf();
    }

    public void setLlamaStrength(int strength) {
        list.set("strength", strength);
        saveCnf();
    }

    public void setStyle(Horse.Style style) {
        list.set("style", style.name());
        saveCnf();
    }

    public void setHorseColor(Horse.Color color) {
        list.set("color", color.name());
        saveCnf();
    }

    public void SetHorseVariant(Horse.Variant var) {
        list.set("variant", var.name());
        saveCnf();
    }

    public void setJumpStrength(double num) {
        list.set("jumpstrength", num);
        saveCnf();
    }

    public void setSpeed(double num) {
        list.set("speed", num);
        saveCnf();
    }

    public void setCollar(DyeColor collar) {
        list.set("collar", collar.name());
        saveCnf();
    }

    public void setCatType(Ocelot.Type cattype) {
        list.set("cattype", cattype.name());
        saveCnf();
    }

    public void setPower(boolean power) {
        list.set("power", power);
        saveCnf();
    }

    public void setRabbitType(Rabbit.Type rabbittype) {
        list.set("rabbittype", rabbittype.name());
        saveCnf();
    }

    //typoしてる
    public void setBreed(boolean breed) {
        list.set("bleed", breed);
        saveCnf();
    }

    public void setParrotVariant(Parrot.Variant variant) {
        list.set("parrotvariant", variant.name());
        saveCnf();
    }

    public void setTropicalFishPattern(TropicalFish.Pattern pattern) {
        list.set("tropicalfishpattern", pattern.name());
        saveCnf();

    }

    public void setTropicalFishBodyColor(DyeColor color) {
        list.set("tropicalfishbodycolor", color.name());
        saveCnf();

    }

    public void setTropicalFishPatternColor(DyeColor color) {
        list.set("tropicalfishpatterncolor", color.name());
        saveCnf();
    }

    public void setEntityEquipment(List<Map> entityEquipment) {
        list.set("entityequipment", entityEquipment);
        saveCnf();
    }

    public void savePotionEffectList(List<Map> potionEffectList) {
        list.set("potioneffectlist", potionEffectList);
        saveCnf();

    }

    public void setVillagerTradeList(List<Map> recipes) {
        list.set("villagersimpletradelist", recipes);
        saveCnf();
    }

    public void setVillagerRiches(int villagerRiches) {
        list.set("villagerriches", villagerRiches);
        saveCnf();
    }

    public void setVillagerProfession(Villager.Profession profession) {
        list.set("villagerprofession", profession.name());
        saveCnf();
    }

    public void setVillagerLevel(int villagerlevel) {
        list.set("villagerlevel", villagerlevel);
        saveCnf();
    }

    public void setChild(boolean child) {
        list.set("child", child);
        saveCnf();
    }

    public void setAngry(boolean angry) {
        list.set("angry", angry);
        saveCnf();
    }

    public void setTamed(boolean tamed) {
        list.set("tamed", tamed);
        saveCnf();
    }


    //--------------------------------------------------------------------------
    public boolean getUsed() {
        return list.getBoolean("used");
    }

    public short getMobType() {
        return (short) list.getInt("mobid");
    }

    public String getCustomName() {
        return list.getString("name");
    }

    public double getMaxHealth() {
        return list.getDouble("maxhealth");
    }

    public double getHealth() {
        return list.getDouble("health");
    }

    public String getOwner() {
        return list.getString("owner");
    }

    public int getMaxDomestication() {
        return list.getInt("maxdomestication");
    }

    public int getDomestication() {
        return list.getInt("domestication");
    }

    public int getAge() {return list.getInt("age");}


    public DyeColor getSheepColor() {
        return DyeColor.valueOf(list.getString("sheepcolor"));
    }

    public Llama.Color getLlamaColor() {
        return Llama.Color.valueOf(list.getString("color"));
    }

    public int getLlamaStrength() {
        return list.getInt("strength");
    }


    public Horse.Style getStyle() {
        return Horse.Style.valueOf(list.getString("style"));
    }

    public Horse.Color getHorseColor() {
        return Horse.Color.valueOf(list.getString("color"));
    }

    public Horse.Variant getHorseVariant() {
        return Horse.Variant.valueOf(list.getString("variant"));
    }

    public double getJumpStrength() {
        return list.getDouble("jumpstrength");
    }

    public double getSpeed() {
        return list.getDouble("speed");
    }

    public DyeColor getCollar() {
        return DyeColor.valueOf(list.getString("collar"));
    }

    public Ocelot.Type getCatType() {
        return Ocelot.Type.valueOf(list.getString("cattype"));
    }

    public boolean getPower() {
        return list.getBoolean("power");
    }

    public Rabbit.Type getRabbitType() {
        return Rabbit.Type.valueOf(list.getString("rabbittype"));
    }

    //typoしてる
    public boolean getBreed() {
        return list.getBoolean("bleed");
    }

    public Parrot.Variant getParrotVariant() {
        return Parrot.Variant.valueOf(list.getString("parrotvariant"));
    }

    public TropicalFish.Pattern getTropicalFishPattern() {

        return TropicalFish.Pattern.valueOf(list.getString("tropicalfishpattern"));

    }

    public DyeColor getTropicalFishBodyColor() {
        return DyeColor.valueOf(list.getString("tropicalfishbodycolor"));

    }

    public DyeColor getTropicalFishPatternColor() {
        return DyeColor.valueOf(list.getString("tropicalfishpatterncolor"));
    }

    public List<Map<?, ?>> getEntityEquipment() {
        return list.getMapList("entityequipment");
    }

    public List<Map<?, ?>> getPotionEffectList() {
        return list.getMapList("potioneffectlist");
    }

    public List<Map<?, ?>> getTradeList() {
        return list.getMapList("villagersimpletradelist");
    }

    public Villager.Profession getVillagerProfession() {
        return Villager.Profession.valueOf(list.getString("villagerprofession"));
    }
    /**
     * @deprecated 1.13以前の村人復元用メソッド 1.14以降は{@link LoaderMob#getVillagerProfession()}を使うこと｡
     */
    @Deprecated public String getVillagerCareer() {
        return (list.getString("villagercareer"));
    }

    /**
     * @deprecated 1.13以前の村人復元用メソッド 1.14以降は{@link LoaderMob#getVillagerLevel()}を使うこと｡
     */
    @Deprecated public int getVillagerCareerLevel() {
        return list.getInt("villagercareerlevel");
    }

    public int getVillagerLevel() {
        return list.getInt("villagerlevel");
    }


    public boolean getChild() {
        return list.getBoolean("child");
    }

    public boolean getAngry() {
        return list.getBoolean("angry");
    }

    public long getDate() {
        return list.getLong("date");
    }

    public String getGenType() {
        return list.getString("gen_type");
    }


    public boolean getTamed() {
        return list.getBoolean("tamed");
    }

    public String getPluginVersion() {
        return list.getString("gen_plugin_version");
    }


}
