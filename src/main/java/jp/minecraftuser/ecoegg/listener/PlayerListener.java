
package jp.minecraftuser.ecoegg.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.InfoParam;
import jp.minecraftuser.ecoegg.mob.CreateMob;
import jp.minecraftuser.ecoegg.mob.InfoMob;
import jp.minecraftuser.ecoegg.mob.SaveMob;
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
import org.bukkit.block.BlockFace;
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
            if (!existMonsterEgg(ent)) {
                pl.sendMessage(m.plg("infoコマンドの対象外のエンティティです"));
                return;
            }

            // MOB個別処理
            InfoParam param = ((EcoEgg) plg).getParamUser(pl); //何もしない

            InfoMob infoMob = new InfoMob((LivingEntity) ent, pl, plg);
            infoMob.show();


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

        //モンスターエッグに変換できない場合はキャンセル
        if (!existMonsterEgg(ent)) {
            pl.sendMessage("モンスターエッグ非対応エンティティです" + ent.getType());
            return;
        }


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

        if (le instanceof Tameable) {
            Tameable team_entity = (Tameable) le;
            if (team_entity.getOwner() != null) {
                if (!(team_entity.getOwner().getName().equals(pl.getName()))) {
                    reject = true;
                }
            }
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

        SaveMob saveMob = new SaveMob(le, pl, loc, save, plg);
        saveMob.save();

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
        m.info("ChangeMobEgg[" + pl.getName() + "]" + pl.getLocation().toString() + ",MOB:" + (byte) ent.getType().getTypeId());
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
        if (date.getTime() < load.getDate() + 1000 * 1) {
            event.getPlayer().sendMessage(m.plg("再使用まであと " + (1 - (date.getTime() - load.getDate()) / 1000) + " 秒必要です"));
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

        Block block = event.getClickedBlock();
        if (block == null) {
            event.setCancelled(true);
            return;
        }

        // MOBスポーン処理
        Location loc = block.getLocation();
        BlockFace blockface = event.getBlockFace();
        loc.add(blockface.getModX(), blockface.getModY(), blockface.getModZ());
        loc.setX(loc.getX() + 0.5);
        loc.setZ(loc.getZ() + 0.5);

        Player player = event.getPlayer();
        LivingEntity entity = (LivingEntity) block.getWorld().spawnEntity(loc, type);


        // MOBスポーン後の処理

        CreateMob createMob = new CreateMob(entity, player, block, load, plg);
        createMob.create();


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        load.saveUse(player.getName(), entity.getType().name(), sdf1.format(date));

        event.setCancelled(true);
        if (player.getGameMode() != GameMode.CREATIVE) player.setItemInHand(new ItemStack(Material.AIR));
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
