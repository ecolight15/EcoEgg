
package jp.minecraftuser.ecoegg;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/**
 *
 * @author ecolight
 */
public class InfoParam {
    private Player player = null;
    private CommandType type = null;
    private OptionType opt = null;
    private double val = 0.0;
    private String str = "";
    private Horse.Style hstyle = null; 
    private Horse.Color hcolor = null;
    
    public InfoParam(Player player, CommandType type) {
        this.player = player;
        this.type = type;
    }
    public Player getPlayer() { return this.player; }
    public CommandType getType() { return this.type; }
    public void setOpt(OptionType opt) { this.opt = opt; }
    public OptionType getOpt() { return this.opt; }
    public void setVal(double val) { this.val = val; }
    public double getVal() { return this.val; }
    public void setStr(String str) { this.str = str; }
    public String getStr() { return this.str; }
    public void setHorseStyle(Horse.Style style) { this.hstyle = style; }
    public Horse.Style getHorseStyle() { return this.hstyle; }
    public void setHorseColor(Horse.Color color) { this.hcolor = color; }
    public Horse.Color getHorseColor() { return this.hcolor; }
    
    
}
