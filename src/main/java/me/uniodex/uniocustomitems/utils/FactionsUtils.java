package me.uniodex.uniocustomitems.utils;

import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.zcore.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FactionsUtils {
    /*
     * - Neden Factions'takini kullanmıyorum, bunun farkı ne?
     * - Çünkü WarZone, SafeZone gibi yerlerde kontrol etmiyordu o.
     *
     */
    // TODO
    public static boolean canPlayerUseWandFactions(Player player, Block block, boolean justCheck) {
        String name = player.getName();
        if (Conf.playersWhoBypassAllProtection.contains(name)) return true;

        FPlayer me = FPlayers.getInstance().getById(name);
        if (me.isAdminBypassing()) return true;

        Material material = block.getType();
        FLocation loc = new FLocation(block);
        Faction otherFaction = Board.getInstance().getFactionAt(loc);

        if (otherFaction.isSafeZone() || otherFaction.isWarZone()) {
            return false;
        } else if (otherFaction.isNone()) {
            return true;
        }


        // We only care about some material types.
        if (otherFaction.hasPlayersOnline()) {
            if (!Conf.territoryDenyUseageMaterials.contains(material))
                return true;
        } else {
            if (!Conf.territoryDenyUseageMaterialsWhenOffline.contains(material))
                return true;
        }

        Faction myFaction = me.getFaction();
        Relation rel = myFaction.getRelationTo(otherFaction);

        // You may use any block unless it is another faction's territory...
        if (rel.isNeutral() || (rel.isEnemy() && Conf.territoryEnemyProtectMaterials) || (rel.isAlly() && Conf.territoryAllyProtectMaterials)) {
            if (!justCheck)
                me.msg("<b>You can't %s <h>%s<b> in the territory of <h>%s<b>.", (material == Material.SOIL ? "trample" : "use"), TextUtil.getMaterialName(material), otherFaction.getTag(myFaction));

            return false;
        }

        // Also cancel if player doesn't have ownership rights for this claim
        if (Conf.ownedAreasEnabled && Conf.ownedAreaProtectMaterials && !otherFaction.playerHasOwnershipRights(me, loc)) {
            if (!justCheck)
                me.msg("<b>You can't use <h>%s<b> in this territory, it is owned by: %s<b>.", TextUtil.getMaterialName(material), otherFaction.getOwnerListString(loc));

            return false;
        }

        return true;
    }
}
