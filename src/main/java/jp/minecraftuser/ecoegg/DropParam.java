
package jp.minecraftuser.ecoegg;

import org.bukkit.entity.EntityType;

/**
 *
 * @author ecolight
 */
public class DropParam {
    private EntityType type = null;
    private int amount = 1;
    private int rate = 1000;
    public DropParam(EntityType type, int amount, int rate) {
        this.type = type;
        this.amount = amount;
        this.rate = rate;
    }
    public EntityType getType() {
        return this.type;
    }
    public int getAmount() {
        return this.amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getRate() {
        return this.rate;
    }
}
