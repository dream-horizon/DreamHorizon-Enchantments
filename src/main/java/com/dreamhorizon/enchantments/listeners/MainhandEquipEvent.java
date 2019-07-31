package com.dreamhorizon.enchantments.listeners;

import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.enchantments.Haste;
import com.dreamhorizon.enchantments.util.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class MainhandEquipEvent implements Listener {

    @EventHandler
    public void playerEquipNewMainhand(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack mainhandItem = InventoryUtil.getAttackItem(player);

        if(mainhandItem != null) {
            if (mainhandItem.containsEnchantment(DHEnchantments.HASTE)) {
                Haste.applyHasteEffect(mainhandItem, player);
            }
        }
    }
}
