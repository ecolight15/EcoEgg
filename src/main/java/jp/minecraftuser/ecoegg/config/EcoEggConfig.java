
package jp.minecraftuser.ecoegg.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import jp.minecraftuser.ecoegg.DropParam;
import jp.minecraftuser.ecoframework.ConfigFrame;
import jp.minecraftuser.ecoframework.PluginFrame;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;

/**
 * EceEgg設定ファイル
 * @author ecolight
 */
public class EcoEggConfig extends ConfigFrame {
    private HashMap<EntityType, DropParam> dropTable = null;
    private String booktitle = "";
    private String bookdispname = "";
    private String bookauthor = "";
    private List<String> bookpage = null;
    /**
     * コンストラクタ
     * @param plg_ 
     */
    public EcoEggConfig(PluginFrame plg_) {
        super(plg_);
        
        // ドロップテーブル読み込み
        dropTable = new HashMap();
        Configuration root = conf.getRoot();
        Map<String, Object> list = conf.getValues(true);
        for (String obj : list.keySet()) {
            String[] mobs = obj.split("\\.");
            if (mobs[0].equalsIgnoreCase("droptable")) {
                if (mobs.length == 2) {
                    // MOB指定確定(mobs[1]がMOB名)
                    // MOB名からEntityTypeに変更
                    EntityType entityType;
                    try {
                        entityType = EntityType.valueOf(mobs[1].toUpperCase());
                    }catch (java.lang.IllegalArgumentException e){
                        // 変換失敗は次の定義へ
                        log.warning("無効なMOB指定が存在します["+mobs[1]+"]");
                        continue;
                    }
                    if (dropTable.containsKey(entityType)) {
                        //追加済みの場合は次の定義へ
                        log.warning("このMOBは既に登録されています["+mobs[1]+"]");
                        continue;
                    }
                    // 取得値の初期値を設定しておく
                    conf.addDefault("droptable."+mobs[1]+".amount", 1);
                    conf.addDefault("droptable."+mobs[1]+".rate", 1000);
                    conf.addDefault("droptable."+mobs[1]+".lootbonus.level1", 1.0);
                    conf.addDefault("droptable."+mobs[1]+".lootbonus.level2", 1.0);
                    conf.addDefault("droptable."+mobs[1]+".lootbonus.level3", 1.0);
                    DropParam p = new DropParam(entityType,
                        conf.getInt("droptable."+mobs[1]+".amount"),
                        conf.getInt("droptable."+mobs[1]+".rate"),
                        conf.getDouble("droptable."+mobs[1]+".lootbonus.level1"),
                        conf.getDouble("droptable."+mobs[1]+".lootbonus.level2"),
                        conf.getDouble("droptable."+mobs[1]+".lootbonus.level3")
                    );
                    dropTable.put(entityType, p);
                    log.log(Level.INFO, "register drop table[{0}]", p.toString());
                }
            }
        }

        // 魔道書の中身
        booktitle = conf.getString("title");
        bookdispname = conf.getString("name");
        bookauthor = conf.getString("author");
        bookpage = conf.getStringList("pages");
    }
    public DropParam getDropParam(EntityType ent) { return dropTable.get(ent); }
    public String getTitle() { return this.booktitle; }
    public String getDispName() { return this.bookdispname; }
    public String getAuthor() { return this.bookauthor; }
    public List<String> getPages() { return this.bookpage; }
}
