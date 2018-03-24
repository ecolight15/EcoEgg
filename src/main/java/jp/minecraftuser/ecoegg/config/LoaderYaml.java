
package jp.minecraftuser.ecoegg.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.minecraftuser.ecoegg.EcoEgg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LoaderYaml {
    private EcoEgg plg = null;
    private String filename = null;
    
    private FileConfiguration cnf = null;
    private File cnfFile = null;
    
    public LoaderYaml(EcoEgg plg, String filename) {
        this.plg = plg;
        this.filename = filename;
    }
    public void reloadCnf() {
        if (cnfFile == null) {
            cnfFile = new File(plg.getDataFolder(), this.filename);
        }
        cnf = YamlConfiguration.loadConfiguration(cnfFile);

        try {
            Reader def = new InputStreamReader(plg.getResource(filename), "UTF8") ;
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(def);
            cnf.setDefaults(defConfig);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoaderYaml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FileConfiguration getCnf() {
        if (cnf == null) {
            this.reloadCnf();
        }
        return cnf;
    }

    public void saveCnf() {
        if (cnf == null || cnfFile == null) {
        return;
        }
        try {
            getCnf().save(cnfFile);
        } catch (IOException ex) {
            plg.getLogger().log(Level.SEVERE, "Could not save config to " + cnfFile, ex);
        }
    }
}
