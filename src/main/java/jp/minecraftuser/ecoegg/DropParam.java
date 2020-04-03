
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
    private double l1 = 1.0f;
    private double l2 = 1.0f;
    private double l3 = 1.0f;
    public DropParam(EntityType type, int amount, int rate, double l1, double l2, double l3) {
        this.type = type;
        this.amount = amount;
        this.rate = rate;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
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
    public double getLootBonus(int level) {
        switch (level) {
            case 1: return l1;
            case 2: return l2;
            case 3: return l3;
        }
        return 1.0f;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Type=");
        s.append(type.toString());
        s.append(", Amount=");
        s.append(amount);
        s.append(", Rate=");
        s.append(rate);
        s.append(", LootBonus L1=");
        s.append(l1);
        s.append(", L2=");
        s.append(l2);
        s.append(", L3=");
        s.append(l3);
        return s.toString();
    }
}
