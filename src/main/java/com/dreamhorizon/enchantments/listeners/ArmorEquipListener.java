package com.dreamhorizon.enchantments.listeners;
import com.codingforcookies.armorequip.ArmorEquipEvent;
import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.enchantments.Haste;
import com.dreamhorizon.enchantments.enchantments.Speed;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ArmorEquipListener implements Listener {

    @EventHandler
    public void armorEquipEvent(ArmorEquipEvent event) {
        ItemStack equippedArmor = event.getOldArmorPiece();
        ItemStack unequippedArmor = event.getOldArmorPiece();
        LivingEntity player = event.getPlayer();

        //Equipping
        if(equippedArmor != null && equippedArmor.getType() != Material.AIR) {
            if(equippedArmor.containsEnchantment(DHEnchantments.HASTE)) {
                Haste.applyHasteEffect(equippedArmor, player);
            }

            if(equippedArmor.containsEnchantment(DHEnchantments.SPEED)) {
                Speed.applySpeedEffect(equippedArmor, player);
            }
        }

        //Un-equipping
        if(unequippedArmor != null && unequippedArmor.getType() != Material.AIR) {
            if(unequippedArmor.containsEnchantment(DHEnchantments.HASTE)) {
                Haste.removeHasteEffect(player);
            }

            if(unequippedArmor.containsEnchantment(DHEnchantments.SPEED)) {
                Speed.removeSpeedEffect(player);
            }
        }
    }
}
