
package jp.minecraftuser.ecoegg.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.EcoEggUtil;
import jp.minecraftuser.ecoegg.mob.CreateMob;
import jp.minecraftuser.ecoegg.mob.SaveMob;
import jp.minecraftuser.ecoframework.PluginFrame;
import jp.minecraftuser.ecoegg.config.EcoEggConfig;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoframework.ListenerFrame;
import jp.minecraftuser.ecoframework.Utl;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * 孵化イベントリスナクラス
 *
 * @author ecolight
 */
public class HatchingListener extends ListenerFrame {
    private static EcoEggConfig eceConf = null;

    /**
     * コンストラクタ
     *
     * @param plg_  プラグインインスタンス
     * @param name_ 名前
     */
    public HatchingListener(PluginFrame plg_, String name_) {
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

        //モンスターエッグに変換できない場合はキャンセル
        if (!EcoEggUtil.existMonsterEgg(ent)) {
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

        // テイム可能なMOBの場合はリジェクト
        if (le instanceof Tameable) {
            Tameable tame_entity = (Tameable) le;
            if (tame_entity.getOwner() != null && tame_entity.getOwner().getName() != null) {
                if (!(tame_entity.getOwner().getName().equals(pl.getName()))) {
                    reject = true;
                }
            }
        }
        PlayerInventory pi = pl.getInventory();
        ItemStack is = pi.getItemInMainHand();
        if ((reject) && (!pl.isOp())) {
            Utl.sendPluginMessage(plg, ent, "他のプレイヤーの動物には力が及びませんでした");
            if (pl.getGameMode() != GameMode.CREATIVE) {
                if (is.getAmount() == 1) {
                    pi.setItemInMainHand(new ItemStack(Material.AIR));
                } else {
                    is.setAmount(is.getAmount() - 1);
                    pi.setItemInMainHand(is);
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
        im.setDisplayName("[EcoEgg]," + le.getUniqueId().getMostSignificantBits() + "," + le.getUniqueId().getLeastSignificantBits());
        egg.setItemMeta(im);

        if (is.getAmount() == 1) {
            pi.setItemInMainHand(egg);
        } else {
            // アイテムスロットに空きがなければ
            int ii = 0;
            for (ItemStack i : pi.getContents()) {
                if (i == null) {
                    break;
                }
                ii++;
            }
            if (ii == pi.getSize()) {
                pl.sendMessage(ChatColor.AQUA + "[EcoEgg] インベントリに空きが無いので使用できません");
                event.setCancelled(true);
                return;
            } else {
                is.setAmount(is.getAmount() - 1);
                pl.getInventory().setItemInMainHand(is);
                pi.setItem(ii, egg);
                pl.getInventory().getItem(ii).setAmount(1);
            }
        }
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        save.saveGen(pl.getName(), ent.getType().name(), sdf1.format(date), plg.getDescription().getVersion());
        save.saveDate(date.getTime());

        le.remove();
        pl.getWorld().strikeLightningEffect(loc);
        pl.sendMessage(ChatColor.AQUA + "[EcoEgg] MOBをたまごに変換しました");
        log.info("ChangeMobEgg[" + pl.getName() + "]" + pl.getLocation().toString() + ",MOB:" + (byte) ent.getType().getTypeId());
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
        if (!EcoEggUtil.isMonsterEgg(item)) {
            return;
        }

        // メタ取得
        String item_name = item.getItemMeta().getDisplayName();

        // エコエッグの表記で始まるか
        if (!item_name.startsWith("[EcoEgg]")) return;
        String[] token = item_name.split(",");
        String most = token[token.length - 2];
        String least = token[token.length - 1];

        // MOBのyaml情報を取得
        LoaderMob load = new LoaderMob((EcoEgg) plg, new UUID(Long.parseLong(most), Long.parseLong(least)));

        // 使用済みなら不正アイテム、ただしOPは問題なし
        if (load.getUsed() && (!event.getPlayer().isOp())) {
            log.info("使用済みたまご利用:" + most + "," + least + "[" + event.getPlayer().getName() + "]");
            Utl.sendPluginMessage(plg, event.getPlayer(), "管理者に通報されました：使用済みたまご利用:" + most + "," + least + "[" + event.getPlayer().getName() + "]");
            event.setCancelled(true);
            return;
        }

        // インターバルの監視
        Date date = new Date();
        if (date.getTime() < load.getDate() + 1000 * 10) {
            Utl.sendPluginMessage(plg, event.getPlayer(), "再使用まであと " + (10 - (date.getTime() - load.getDate()) / 1000) + " 秒必要です");
            event.setCancelled(true);
            return;
        }

        // 一応本のクリック判定をキャンセル？
        Block block = event.getClickedBlock();
        if (block == null) {
            event.setCancelled(true);
            return;
        }

        // MOBスポーン処理
        Player player = event.getPlayer();
        Location loc = block.getLocation();
        BlockFace blockface = event.getBlockFace();

        loc.add(blockface.getModX(), blockface.getModY(), blockface.getModZ());
        loc.setX(loc.getX() + 0.5);
        loc.setZ(loc.getZ() + 0.5);

        CreateMob createMob = new CreateMob(player, block.getType(), loc, load, plg);
        LivingEntity entity = createMob.create();

        // MOBスポーン後の処理
        // 保存yamlに使用済みをマークする
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        load.saveUse(player.getName(), entity.getType().name(), sdf1.format(date));

        event.setCancelled(true);
        if (player.getGameMode() != GameMode.CREATIVE) player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        Utl.sendPluginMessage(plg, event.getPlayer(), entity.getType().name() + "を出現させました");
    }
}