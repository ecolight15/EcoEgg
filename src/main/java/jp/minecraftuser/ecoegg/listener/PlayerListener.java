
package jp.minecraftuser.ecoegg.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.InfoParam;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoegg.config.EcoEggConfig;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoegg.m;
import jp.minecraftuser.ecoframework.ListenerFrame;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.MonsterEggs;
import org.bukkit.material.SpawnEgg;

/**
 * プレイヤー系イベントリスナクラス
 *
 * @author ecolight
 */
public class PlayerListener extends ListenerFrame {
    private static EcoEggConfig eceConf = null;

    /**
     * コンストラクタ
     *
     * @param plg_  プラグインインスタンス
     * @param name_ 名前
     */
    public PlayerListener(PluginFrame plg_, String name_) {
        super(plg_, name_);
        eceConf = (EcoEggConfig) conf;
    }

    /**
     * プレイヤーエンティティ作用イベント
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerInteractEntity(PlayerInteractEntityEvent event) {

        //----------------------------------------------------------------------
        // たまごのあるMOBの場合
        //----------------------------------------------------------------------
        Player pl = event.getPlayer();
        Entity ent = event.getRightClicked();

        if (((EcoEgg) plg).chkInfoUser(pl)) {
            switch (ent.getType()) {
                case HORSE:
                case OCELOT:
                case WOLF:
                    break;
                default:
                    pl.sendMessage(m.plg("infoコマンドの対象外のエンティティです"));
                    return;
            }

            // MOB個別処理
            InfoParam param;
            switch (ent.getType()) {
                case HORSE:
                    Horse horse = (Horse) ent;
                    param = ((EcoEgg) plg).getParamUser(pl);

                    // 馬ステータス表示
                    pl.sendMessage(m.plg("===== 馬ステータス表示 ====="));
                    if (horse.getCustomName() != null) pl.sendMessage(m.plg("Name:" + horse.getCustomName()));
                    pl.sendMessage(m.plg("MaxHealth:" + horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
                    pl.sendMessage(m.plg("Health:" + horse.getHealth()));
                    if (horse.getOwner() != null) pl.sendMessage(m.plg("Owner:" + horse.getOwner().getName()));
                    pl.sendMessage(m.plg("MaxDomestication:" + horse.getMaxDomestication()));
                    pl.sendMessage(m.plg("Domestication:" + horse.getDomestication()));
                    pl.sendMessage(m.plg("Age:" + horse.getAge()));
                    pl.sendMessage(m.plg("Style:" + horse.getStyle().name()));
                    pl.sendMessage(m.plg("Color:" + horse.getColor().name()));
                    pl.sendMessage(m.plg("Variant:" + horse.getVariant().name()));
                    pl.sendMessage(m.plg("JumpStrength:" + horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue()));
                    pl.sendMessage(m.plg("Speed:" + horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()));
                    pl.sendMessage(m.plg("===== 馬ステータスここまで ====="));
                    break;
                case OCELOT:
                    Ocelot cat = (Ocelot) ent;
                    param = ((EcoEgg) plg).getParamUser(pl);

                    // 猫ステータス表示
                    pl.sendMessage(m.plg("===== 猫ステータス表示 ====="));
                    if (cat.getCustomName() != null) pl.sendMessage(m.plg("Name:" + cat.getCustomName()));
                    pl.sendMessage(m.plg("MaxHealth:" + cat.getMaxHealth()));
                    pl.sendMessage(m.plg("Health:" + cat.getHealth()));
                    if (cat.getOwner() != null) pl.sendMessage(m.plg("Owner:" + cat.getOwner().getName()));
                    pl.sendMessage(m.plg("Age:" + cat.getAge()));
                    pl.sendMessage(m.plg("CatType:" + cat.getCatType().name()));
                    pl.sendMessage(m.plg("===== 猫ステータスここまで ====="));
                    break;
                case WOLF:
                    Wolf dog = (Wolf) ent;
                    param = ((EcoEgg) plg).getParamUser(pl);

                    // 猫ステータス表示
                    pl.sendMessage(m.plg("===== 犬ステータス表示 ====="));
                    if (dog.getCustomName() != null) pl.sendMessage(m.plg("Name:" + dog.getCustomName()));
                    pl.sendMessage(m.plg("MaxHealth:" + dog.getMaxHealth()));
                    pl.sendMessage(m.plg("Health:" + dog.getHealth()));
                    if (dog.getOwner() != null) pl.sendMessage(m.plg("Owner:" + dog.getOwner().getName()));
                    pl.sendMessage(m.plg("Age:" + dog.getAge()));
                    pl.sendMessage(m.plg("Color:" + dog.getCollarColor().name()));
                    pl.sendMessage(m.plg("===== 犬ステータスここまで ====="));
                    break;


            }

            event.setCancelled(true);
            return;
        } else if (((EcoEgg) plg).chkSetUser(pl)) {
            switch (ent.getType()) {
                case HORSE:
                    break;
                default:
                    pl.sendMessage(m.plg("setコマンドの対象外のエンティティです"));
                    return;
            }
            InfoParam param;
            switch (ent.getType()) {
                case HORSE:
                    Horse horse = (Horse) ent;
                    param = ((EcoEgg) plg).getParamUser(pl);
                    switch (param.getOpt()) {
                        case JUMP:
                            horse.setJumpStrength(param.getVal());
                            pl.sendMessage(m.plg("ジャンプ力[") + horse.getJumpStrength() + "]を設定しました");
                            break;
                        case SPEED:
                            horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(param.getVal());
                            pl.sendMessage(m.plg("スピード[") + horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() + "]を設定しました");
                            break;
                        case HEALTH:
                            horse.setMaxHealth(param.getVal());
                            pl.sendMessage(m.plg("HP最大値[") + horse.getMaxHealth() + "]を設定しました");
                            break;
                        case OWNER:
                            if (plg.getServer().getPlayerExact(param.getStr()) == null) return;
                            horse.setOwner(plg.getServer().getPlayerExact(param.getStr()));
                            pl.sendMessage(m.plg("飼い主[") + horse.getOwner().getName() + "]を設定しました");
                            break;
                        case STYLE:
                            horse.setStyle(param.getHorseStyle());
                            pl.sendMessage(m.plg("スタイル[") + horse.getStyle().name() + "]を設定しました");
                            break;
                        case COLOR:
                            horse.setColor(param.getHorseColor());
                            pl.sendMessage(m.plg("色[") + horse.getColor().name() + "]を設定しました");
                            break;
                        case VARIANT:
                            horse.setVariant(param.getHorseVariant());
                            pl.sendMessage(m.plg("Variant[") + horse.getVariant().name() + "]を設定しました");
                            break;
                    }
                    break;
            }

            event.setCancelled(true);
            return;
        }


        String dispname = "";
        byte mobnum = 0;
        //モンスターエッグに変換できない場合はキャンセル
        if (!existMonsterEgg(ent)) {
            pl.sendMessage("モンスターエッグ非対応エンティティです" + ent.getType());
            return;
        }

        mobnum = (byte) ent.getType().getTypeId();


        //----------------------------------------------------------------------
        // プレイヤーの持っている物が魔道書か
        //----------------------------------------------------------------------
        boolean bookcheck = true;
        if (((EcoEgg) plg).getGetter() != null) {
            if (((EcoEgg) plg).getGetter().equals(pl)) {
                // 魔道書判定スキップ、初期化
                bookcheck = false;
                ((EcoEgg) plg).setGetter(null);
            }
        }
        if (bookcheck) {
            ItemStack is = pl.getItemInHand();
            if (is.getType() != Material.WRITTEN_BOOK) return;
            // 魔道書の記述が正しいか
            BookMeta meta = (BookMeta) pl.getItemInHand().getItemMeta();

            if (!meta.getAuthor().equals(eceConf.getAuthor())) return;
            if (!meta.getTitle().equals(eceConf.getTitle())) return;
            if (!meta.getDisplayName().equals(eceConf.getDispName())) return;
        }

        //----------------------------------------------------------------------
        // 他者のMOBの場合は魔道書のみ消滅
        //----------------------------------------------------------------------
        LivingEntity le = (LivingEntity) ent;
        Location loc = le.getLocation();
        boolean reject = false;
        switch (le.getType()) {
            case WOLF:
                if (((Wolf) le).getOwner() == null) break;
                if (((Wolf) le).getOwner().getName().equals(pl.getName())) break;
                reject = true;
                break;
            case OCELOT:
                if (((Ocelot) le).getOwner() == null) break;
                if (((Ocelot) le).getOwner().getName().equals(pl.getName())) break;
                reject = true;
                break;
            case HORSE:
                if (((Horse) le).getOwner() == null) break;
                if (((Horse) le).getOwner().getName().equals(pl.getName())) break;
                reject = true;
                break;
        }
        if ((reject) && (!pl.isOp())) {
            pl.sendMessage(m.plg("他のプレイヤーの動物には力が及びませんでした"));
            if (pl.getGameMode() != GameMode.CREATIVE) {
                ItemStack is = pl.getItemInHand();
                if (is.getAmount() == 1) {
                    pl.setItemInHand(new ItemStack(Material.AIR));
                } else {
                    is.setAmount(is.getAmount() - 1);
                    pl.setItemInHand(is);
                }
            }
            loc.getWorld().strikeLightningEffect(loc);
            return;
        }

        //----------------------------------------------------------------------
        // MOBたまご生成
        //----------------------------------------------------------------------
        ItemStack egg = new ItemStack(Material.matchMaterial("minecraft:" + ent.getType() + "_spawn_egg"));//雑い

        LoaderMob save = new LoaderMob((EcoEgg) plg, le.getUniqueId());
        save.setUsed(false);
        save.setMobType(mobnum);
        save.setCustomName(le.getCustomName());
        save.setMaxHealth(le.getMaxHealth());
        save.setHealth(le.getHealth());

        // MOB情報収集
        switch (le.getType()) {
            case HORSE:
                Horse horse = (Horse) le;
                // 装備とインベントリ内アイテムドロップ
                for (ItemStack i : horse.getEquipment().getArmorContents()) {
                    if (i.getType() == Material.AIR) continue;
                    le.getWorld().dropItem(loc, i);
                }
                for (ItemStack i : horse.getInventory().getContents()) {
                    if (i == null) {
                        m.info("i null");
                        continue;
                    }
                    if (i.getType() == Material.AIR) continue;
                    if (loc == null) {
                        m.info("loc null");
                        return;
                    }
                    le.getWorld().dropItem(loc, i);
                }
                save.setMaxDomestication(horse.getMaxDomestication());
                save.setDomestication(horse.getDomestication());
                save.setAge(horse.getAge());
                save.setStyle(horse.getStyle());
                save.setColor(horse.getColor());
                if (horse.getOwner() != null) save.setOwner(horse.getOwner().getName());
                save.setJumpStrength(horse.getJumpStrength());
                save.setVariant(horse.getVariant());
                save.setSpeed(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
                save.setBleed(horse.canBreed());
                save.setChild(!horse.isAdult());
                break;
            case WOLF:
                Wolf wolf = (Wolf) le;
                save.setAge(wolf.getAge());
                save.setCollar(wolf.getCollarColor());
                save.setBleed(wolf.canBreed());
                save.setChild(!wolf.isAdult());
                save.setAngry(wolf.isAngry());
                if (wolf.getOwner() != null) save.setOwner(wolf.getOwner().getName());
                break;
            case OCELOT:
                Ocelot ocelot = (Ocelot) le;
                save.setAge(ocelot.getAge());
                save.setCatType(ocelot.getCatType());
                save.setBleed(ocelot.canBreed());
                save.setChild(!ocelot.isAdult());
                if (ocelot.getOwner() != null) save.setOwner(ocelot.getOwner().getName());
                break;
            case CREEPER:
                Creeper creeper = (Creeper) le;
                save.setPower(creeper.isPowered());
                break;
            case RABBIT:
                Rabbit rabbit = (Rabbit) le;
                save.setRabbitType(rabbit.getRabbitType());
                save.setBleed(rabbit.canBreed());
                save.setAge(rabbit.getAge());
                save.setChild(!rabbit.isAdult());
            default:
                break;
        }

        ItemMeta im = egg.getItemMeta();
//        if (name != null) {
//            im.setDisplayName("[EcoEgg]("+name+"),"+le.getUniqueId().getMostSignificantBits()+","+le.getUniqueId().getLeastSignificantBits());
//        } else {
        im.setDisplayName("[EcoEgg]," + le.getUniqueId().getMostSignificantBits() + "," + le.getUniqueId().getLeastSignificantBits());
//        }
        egg.setItemMeta(im);


        ItemStack is = pl.getItemInHand();
        if (is.getAmount() == 1) {
            pl.getInventory().setItemInMainHand(egg);
//            pl.setItemInHand(new ItemStack(Material.AIR));
        } else {
            // アイテムスロットに空きがなければ
            PlayerInventory in = pl.getInventory();
            int ii = 0;
            for (ItemStack i : in.getContents()) {
                if (i == null) {
                    break;
                }
                ii++;
            }
            if (ii == in.getSize()) {
                pl.sendMessage(ChatColor.AQUA + "[EcoEgg] インベントリに空きが無いので使用できません");
                event.setCancelled(true);
                return;
            } else {
                is.setAmount(is.getAmount() - 1);
                pl.getInventory().setItemInMainHand(is);
                in.setItem(ii, egg);
                pl.getInventory().getItem(ii).setAmount(1);
            }
        }
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        save.saveGen(pl.getName(), ent.getType().name(), sdf1.format(date));
        save.saveDate(date.getTime());
        le.remove();
        pl.getWorld().strikeLightningEffect(loc);
        pl.sendMessage(ChatColor.AQUA + "[EcoEgg] MOBをたまごに変換しました");
        m.info("ChangeMobEgg[" + pl.getName() + "]" + pl.getLocation().toString() + ",MOB:" + mobnum);
        event.setCancelled(true);
    }

    /**
     * プレイヤー作用イベントハンドラ
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerInteract(PlayerInteractEvent event) {
        //----------------------------------------------------------------------
        // 卵の孵化処理
        //----------------------------------------------------------------------
        ItemStack item = event.getItem();
        if (item == null) return;

        // モンスターエッグ以外は処理しない
        if (!isMonsterEgg(item)) {
            return;
        }


        // メタ取得
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        String itemname = meta.getDisplayName();
        if (itemname == null) return;

        // エコエッグの表記で始まるか
        if (!itemname.startsWith("[EcoEgg]")) return;
        String[] token = itemname.split(",");
        String most = token[token.length - 2];
        String least = token[token.length - 1];

        // MOBのyaml情報を取得
        LoaderMob load = new LoaderMob((EcoEgg) plg, new UUID(Long.parseLong(most), Long.parseLong(least)));

        // 使用済みなら不正アイテム、ただしOPは問題なし
        if (load.getUsed() && (!event.getPlayer().isOp())) {
            m.info("使用済みたまご利用:" + most + "," + least + "[" + event.getPlayer().getName() + "]");
            event.getPlayer().sendMessage(m.plg("管理者に通報されました：使用済みたまご利用:" + most + "," + least + "[" + event.getPlayer().getName() + "]"));
            event.setCancelled(true);
            return;
        }

        // インターバルの監視
        Date date = new Date();
        if (date.getTime() < load.getDate() + 1000 * 5) {
            event.getPlayer().sendMessage(m.plg("再使用まであと " + (5 - (date.getTime() - load.getDate()) / 1000) + " 秒必要です"));
            event.setCancelled(true);
            return;
        }

        EntityType type;
        //もし-MobTypeが-1ならgen_typeからモンスターの種類を取ってくる
        if (load.getMobType() != -1) {
            type = EntityType.fromId(load.getMobType());
        } else {
            type = EntityType.valueOf(load.getGenType());
        }

        // 一応本のクリック判定をキャンセル？
        boolean ownerreset = false;
        Block b = event.getClickedBlock();
        if (b == null) {
            event.setCancelled(true);
            return;
        }

        // MOBスポーン処理
        Location loc = b.getLocation();
        loc.setY(loc.getY() + 2);
        Player pl = event.getPlayer();
        LivingEntity ent = (LivingEntity) b.getWorld().spawnEntity(loc, type);

        // MOBスポーン後のMOB共通処理
        load.setUsed(true);
        ent.setMaxHealth(load.getMaxHealth());
        ent.setHealth(load.getHealth());
        String name = load.getCustomName();
        if (name != null) ent.setCustomName(name);
        String owner = load.getOwner();

        // MOBスポーン後のMOB個別処理
        switch (type) {
            // 犬用
            case WOLF:
                Wolf wolf = (Wolf) ent;
                wolf.setCollarColor(load.getCollar());
                wolf.setBreed(load.getBleed());
                wolf.setAngry(load.getAngry());
                if (load.getChild()) {
                    wolf.setBaby();
                } else {
                    wolf.setAdult();
                }
                if (b.getType() == Material.LEGACY_MELON_BLOCK) ownerreset = true;
                if (ownerreset) {
                    if (owner != null) wolf.setOwner(pl);
                } else {
                    if (owner != null) wolf.setOwner(plg.getServer().getOfflinePlayer(owner));
                }
                break;
            // 猫用
            case OCELOT:
                Ocelot ocelot = (Ocelot) ent;
                ocelot.setCatType(load.getCatType());
                ocelot.setBreed(load.getBleed());
                if (load.getChild()) {
                    ocelot.setBaby();
                } else {
                    ocelot.setAdult();
                }
                if (b.getType() == Material.LEGACY_CAKE_BLOCK) ownerreset = true;
                if (ownerreset) {
                    if (owner != null) ocelot.setOwner(pl);
                } else {
                    if (owner != null) ocelot.setOwner(plg.getServer().getOfflinePlayer(owner));
                }
                break;
            // 馬用
            case HORSE:
                Horse horse = (Horse) ent;
                horse.setMaxDomestication(load.getMaxDomestication());
                horse.setDomestication(load.getDomestication());
                horse.setAge(load.getAge());
                horse.setStyle(load.getStyle());
                horse.setColor(load.getColor());
                horse.setJumpStrength(load.getJumpStrength());
                horse.setVariant(load.getVariant());
                horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(load.getSpeed());
                horse.setBreed(load.getBleed());
                if (load.getChild()) {
                    horse.setBaby();
                } else {
                    horse.setAdult();
                }
                if (b.getType() == Material.CARROT) ownerreset = true;
                if (ownerreset) {
                    if (owner != null) horse.setOwner(pl);
                } else {
                    if (owner != null) horse.setOwner(plg.getServer().getOfflinePlayer(owner));
                }
                break;
            case CREEPER:
                Creeper creeper = (Creeper) ent;
                creeper.setPowered(load.getPower());
                break;
            case RABBIT:
                Rabbit rabbit = (Rabbit) ent;
                rabbit.setBreed(load.getBleed());
                rabbit.setRabbitType(load.getRabbitType());
                rabbit.setAge(load.getAge());
                if (load.getChild()) {
                    rabbit.setBaby();
                } else {
                    rabbit.setAdult();
                }
                break;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        load.saveUse(pl.getName(), ent.getType().name(), sdf1.format(date));

        event.setCancelled(true);
        if (pl.getGameMode() != GameMode.CREATIVE) pl.setItemInHand(new ItemStack(Material.AIR));
    }

    /**
     * エコエッグ本生成
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void PrepareItemCraft(PrepareItemCraftEvent event) {
        ItemStack is = event.getInventory().getResult();
        //plg.getServer().broadcastMessage("is:"+is.toString());
        if (is.getType() != Material.WRITTEN_BOOK) return;
        // 魔道書の記述が正しいか
        BookMeta meta = (BookMeta) is.getItemMeta();
        if (meta == null) return;
        if (!meta.getAuthor().equals(eceConf.getAuthor())) return;
        if (!meta.getTitle().equals(eceConf.getTitle())) return;
        if (!meta.getDisplayName().equals(eceConf.getDispName())) return;
        int pagenum = 0;
        is.setAmount(0);
        event.getInventory().setResult(is);
    }

    /**
     * エンティティダメージイベントハンドラ
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void EntityDamage(EntityDamageEvent event) {
        // OP騎乗の落下ダメージはキャンセル
        if (isFallingDamageCanceled(event)) {
            event.setCancelled(true);
        }
    }

    /**
     * MOB落下ダメージ判定
     * OPの乗り物にはダメージ入れない
     * 後でプロパティ化する
     *
     * @param event
     * @return
     */
    private boolean isFallingDamageCanceled(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return false;
        Entity ent = event.getEntity();
        if (ent == null) return false;
        Entity pas = ent.getPassenger();
        if (pas == null) return false;
        if (pas.getType() != EntityType.PLAYER) return false;
        Player pl = (Player) pas;
        if (!pl.isOp()) return false;
        return true;
    }

    public boolean existMonsterEgg(Entity e) {

        return Material.matchMaterial("minecraft:" + e.getType().toString() + "_spawn_egg") != null;

    }

    public boolean isMonsterEgg(ItemStack item) {
        return item.getType().getKey().toString().matches(".*_spawn_egg");
    }


}
