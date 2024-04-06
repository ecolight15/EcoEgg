
package jp.minecraftuser.ecoegg.listener;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import jp.minecraftuser.ecoegg.EcoEgg;
import jp.minecraftuser.ecoegg.EcoEggUtil;
import jp.minecraftuser.ecoegg.config.EcoEggConfig;
import jp.minecraftuser.ecoegg.config.LoaderMob;
import jp.minecraftuser.ecoegg.mob.CreateMob;
import jp.minecraftuser.ecoegg.mob.SaveMob;
import jp.minecraftuser.ecoframework.ListenerFrame;
import jp.minecraftuser.ecoframework.PluginFrame;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @EventHandler(priority = EventPriority.LOW)
    public void PlayerInteractEntity(PlayerInteractEntityEvent event) {

        //----------------------------------------------------------------------
        // たまごのあるMOBの場合
        //----------------------------------------------------------------------
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        //LivienEntity以外はキャンセル
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        LivingEntity le = (LivingEntity) entity;

        //モンスターエッグに変換できない場合は何もしない
        if (!EcoEggUtil.existMonsterEgg(entity)) {
            return;
        }

        //----------------------------------------------------------------------
        // プレイヤーの持っている物が魔道書か
        //----------------------------------------------------------------------
        boolean bookcheck = true;
        if (((EcoEgg) plg).getGetter() != null) {
            if (((EcoEgg) plg).getGetter().equals(player)) {
                // 魔道書判定スキップ、初期化
                bookcheck = false;
                ((EcoEgg) plg).setGetter(null);
            }
        }
        if (bookcheck) {
            if(!((EcoEgg) plg).isBook(player.getInventory().getItemInMainHand())){
                return;
            }
        }
        //----------------------------------------------------------------------
        // WorldGuardで保護されていないかチェック
        //----------------------------------------------------------------------
        if (conf.getBoolean("WorldGuard.Enabled")) {
            log.info("Check world guard plugin...");
            WorldGuardPlugin wgp = ((EcoEgg) plg).getWorldGuard();
            if (wgp != null) {
                LocalPlayer localPlayer = wgp.wrapPlayer(player);

                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionQuery query = container.createQuery();

                com.sk89q.worldedit.util.Location player_loc = localPlayer.getLocation();
                com.sk89q.worldedit.util.Location entity_loc = new com.sk89q.worldedit.util.Location(player_loc.getExtent(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());

                if (!query.testState(player_loc, localPlayer, (StateFlag) EcoEgg.USE_ECO_EGG_FLAG) || !query.testState(entity_loc, localPlayer, (StateFlag) EcoEgg.USE_ECO_EGG_FLAG)) {
                    Utl.sendPluginMessage(plg, player, ("この場所ではえこたまごを使用できません"));
                    return;
                }
            }
        }

        //----------------------------------------------------------------------
        // 他者のMOBの場合は魔道書のみ消滅
        //----------------------------------------------------------------------

        Location loc = le.getLocation();
        boolean reject = false;
        String rejectMsg = "";

        // Ignore 設定のMOBの場合はリジェクトし魔道書のみ消滅
        log.info("EcoEgg interact entyti: " + le.getType().toString());
        List<String> list = conf.getArrayList("IgnoreEntity");
        if (list.indexOf(le.getType().toString()) != -1) {
            reject = true;
            rejectMsg = "プラグインで該当のMOBに対するえこたまごの使用が禁止されています";
        }

        // テイム可能なMOBかつ別オーナーの場合はリジェクトし魔道書のみ消滅
        if (!reject) {
            if (le instanceof Tameable) {
                Tameable tame_entity = (Tameable) le;
                if (tame_entity.getOwner() != null && tame_entity.getOwner().getName() != null) {
                    if (!(tame_entity.getOwner().getName().equals(player.getName()))) {
                        reject = true;
                        rejectMsg = "他のプレイヤーの動物には力が及びませんでした";
                    }
                }
            }
            if (le instanceof Fox) {
                Fox fox = (Fox) le;
                //信頼できるプレイヤーが存在していてかつ他人のMOBだった場合はリジェクト
                if(fox.getFirstTrustedPlayer() != null || fox.getSecondTrustedPlayer() != null) {
                    reject = true;
                    if (fox.getFirstTrustedPlayer() != null) {
                        if (fox.getFirstTrustedPlayer().getName().equals(player.getName())) {
                            reject = false;
                        }
                    }
                    if (fox.getSecondTrustedPlayer() != null) {
                        if (fox.getSecondTrustedPlayer().getName().equals(player.getName())) {
                            reject = false;
                        }
                    }
                }
            }
        }

        PlayerInventory playerInventory = player.getInventory();
        ItemStack itemStack = playerInventory.getItemInMainHand();
        if ((reject) && (!player.isOp())) {
            Utl.sendPluginMessage(plg, event.getPlayer(), rejectMsg);
            if (player.getGameMode() != GameMode.CREATIVE) {
                if (itemStack.getAmount() == 1) {
                    playerInventory.setItemInMainHand(new ItemStack(Material.AIR));
                } else {
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    playerInventory.setItemInMainHand(itemStack);
                }
            }
            loc.getWorld().strikeLightningEffect(loc);
            return;
        }

        //----------------------------------------------------------------------
        // MOBたまご生成
        //----------------------------------------------------------------------
        event.setCancelled(true);
        ItemStack egg;

        egg = new ItemStack(Material.matchMaterial("minecraft:" + entity.getType().getName() + "_spawn_egg"));//雑い

        LoaderMob save = new LoaderMob((EcoEgg) plg, le.getUniqueId());
        SaveMob saveMob = new SaveMob(le, player, loc, save, plg);
        saveMob.save();
        if (saveMob.isCancel()) {
            Utl.sendPluginMessage(plg, player, "モンスター保存処理に失敗しました｡");
            return;
        }

        ItemMeta itemMeta = egg.getItemMeta();
        itemMeta.setDisplayName("[EcoEgg]," + le.getUniqueId().getMostSignificantBits() + "," + le.getUniqueId().getLeastSignificantBits());
        egg.setItemMeta(itemMeta);

        if (itemStack.getAmount() == 1) {
            playerInventory.setItemInMainHand(egg);
        } else {
            // アイテムスロットに空きがなければ
            int ii = 0;
            for (ItemStack i : playerInventory.getContents()) {
                if (i == null) {
                    break;
                }
                ii++;
            }
            if (ii == playerInventory.getSize()) {
                Utl.sendPluginMessage(plg, player, ChatColor.AQUA + "インベントリに空きが無いので使用できません");

                return;
            } else {
                itemStack.setAmount(itemStack.getAmount() - 1);
                player.getInventory().setItemInMainHand(itemStack);
                playerInventory.setItem(ii, egg);
                player.getInventory().getItem(ii).setAmount(1);
            }
        }
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        save.saveGen(player.getName(), entity.getType().name(), sdf1.format(date), plg.getDescription().getVersion());
        save.setDate(date.getTime());

        le.remove();
        player.getWorld().strikeLightningEffect(loc);

        Utl.sendPluginMessage(plg, player, ChatColor.AQUA + "MOBをたまごに変換しました");
        log.info("ChangeMobEgg[" + player.getName() + "]" + player.getLocation().toString() + ",MOB:" + (byte) entity.getType().getTypeId());

    }

    /**
     * プレイヤー作用イベントハンドラ
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOW)
    public void PlayerInteract(PlayerInteractEvent event) {

        //右クリック(ブロック)以外は無視
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }


        //----------------------------------------------------------------------
        // 卵の孵化処理
        //----------------------------------------------------------------------
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ItemStack itemStack = event.getItem();


        if (itemStack == null) return;

        // モンスターエッグ以外は処理しない
        if (!EcoEggUtil.isMonsterEgg(itemStack)) {
            return;
        }

        // メタ取得
        String item_name = itemStack.getItemMeta().getDisplayName();

        // エコエッグの表記で始まるか
        if (!item_name.startsWith("[EcoEgg]")) return;


        //----------------------------------------------------------------------
        // WorldGuardで保護されていないかチェック
        //----------------------------------------------------------------------
        if (conf.getBoolean("WorldGuard.Enabled")) {
            log.info("Check world guard plugin...");
            WorldGuardPlugin wgp = ((EcoEgg) plg).getWorldGuard();
            if (wgp != null) {
                LocalPlayer localPlayer = wgp.wrapPlayer(player);
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionQuery query = container.createQuery();

                com.sk89q.worldedit.util.Location player_loc = localPlayer.getLocation();
                com.sk89q.worldedit.util.Location block_loc = new com.sk89q.worldedit.util.Location(player_loc.getExtent(), block.getX(), block.getY(), block.getZ());

                if (!query.testState(player_loc, localPlayer, (StateFlag) EcoEgg.USE_ECO_EGG_FLAG) || !query.testState(block_loc, localPlayer, (StateFlag) EcoEgg.USE_ECO_EGG_FLAG)) {
                    Utl.sendPluginMessage(plg, player, ("この場所ではえこたまごを使用できません"));
                    return;
                }
            }
        }

        event.setCancelled(true);
        String[] token = item_name.split(",");
        String most = token[token.length - 2];
        String least = token[token.length - 1];

        // MOBのyaml情報を取得
        LoaderMob load = new LoaderMob((EcoEgg) plg, new UUID(Long.parseLong(most), Long.parseLong(least)));

        // 使用済みなら不正アイテム、ただしOPは問題なし
        if (load.getUsed()) {
            if(!event.getPlayer().isOp()) {
                log.info("使用済みたまご利用:" + most + "," + least + "[" + event.getPlayer().getName() + "]");
                Utl.sendPluginMessage(plg, event.getPlayer(), "管理者に通報されました：使用済みたまご利用:" + most + "," + least + "[" + event.getPlayer().getName() + "]");
                return;
            }else{
                Utl.sendPluginMessage(plg, event.getPlayer(), "使用済みたまご利用:" + most + "," + least + "[" + event.getPlayer().getName() + "]");
            }
        }

        // インターバルの監視
        Date date = new Date();
        int wait_time = 3;
        if (date.getTime() < load.getDate() + 1000 * wait_time) {
            Utl.sendPluginMessage(plg, event.getPlayer(), "再使用まであと " + (wait_time - (date.getTime() - load.getDate()) / 1000) + " 秒必要です");

            return;
        }


        // MOBスポーン処理
        Location loc = block.getLocation();
        Location site_loc = block.getLocation();
        BlockFace blockface = event.getBlockFace();

        loc.add(blockface.getModX(), blockface.getModY(), blockface.getModZ());
        loc.setX(loc.getX() + 0.5);
        loc.setZ(loc.getZ() + 0.5);

        CreateMob createMob = new CreateMob(player, block.getType(), loc, site_loc, load, plg);
        LivingEntity livingEntity = createMob.create();
        if (createMob.isCancel()) {
            Utl.sendPluginMessage(plg, event.getPlayer(), "モンスター復元処理に失敗しました");
            livingEntity.remove();
            return;
        }


        // MOBスポーン後の処理
        // 保存yamlに使用済みをマークする
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        load.saveUse(player.getName(), livingEntity.getType().name(), sdf1.format(date));
        load.setUsed(true);

        if (player.getGameMode() != GameMode.CREATIVE) {
            if (event.getHand() == EquipmentSlot.HAND) {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            } else {
                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            }
        }
        Utl.sendPluginMessage(plg, event.getPlayer(), livingEntity.getType().name() + "を出現させました");
    }
}
