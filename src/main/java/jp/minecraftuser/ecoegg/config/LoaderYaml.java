package jp.minecraftuser.ecoegg.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.minecraftuser.ecoframework.PluginFrame;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * YAML読み込みクラス
 * @author ecolight
 */
public class LoaderYaml {
    private PluginFrame plg = null;
    private String filename = null;
    protected Logger log = null;

    private FileConfiguration cnf = null;
    private File cnfFile = null;

    /**
     * コンストラクタ
     * @param plg_
     * @param filename_
     */
    public LoaderYaml(PluginFrame plg_, String filename_) {
        plg = plg_;
        filename = filename_;
        log = plg.getLogger();
    }

    /**
     * コンフィグ再読み込み
     */
    public void reloadCnf() {
        // 無ければ新規作成する
        if (cnfFile == null) {
            cnfFile = new File(plg.getDataFolder(), this.filename);
        }
        // 現在の内容を読み込み
        cnf = YamlConfiguration.loadConfiguration(cnfFile);

        // JARファイル内の初期値ファイルに書かれている値を取得してデフォルト値にする
        InputStream defCnfStream = plg.getResource(this.filename);
        if (defCnfStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defCnfStream, StandardCharsets.UTF_8));
            cnf.setDefaults(defConfig);
        }
    }

    /**
     * 直接操作用にコンフィグインスタンスを取得する
     * @return コンフィグファイルインスタンス
     */
    public FileConfiguration getCnf() {
        /**
         * まだ未ロードであれば読み込んでから返す
         */
        if (cnf == null) {
            this.reloadCnf();
        }
        return cnf;
    }

    /**
     * セーブする
     */
    public void saveCnf() {
        // まだ読み込みされていない場合には何もしない
        // R->Wが前提のため読み込みされていない状態はまだ保存すべき状態でないと断定する
        if (cnf == null || cnfFile == null) return;
        try {
            cnf.save(cnfFile);
        } catch (IOException ex) {
            plg.getLogger().log(Level.SEVERE, "failed save configuration file. file=" + cnfFile, ex);
        }
    }
}