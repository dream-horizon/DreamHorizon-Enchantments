package com.dreamhorizon.enchantments.util;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static ItemStack getAttackItem(LivingEntity damager) {
        EntityEquipment equipmentSlot = damager.getEquipment();

        if (equipmentSlot == null) {
            return null;
        }

        ItemStack attackItem = equipmentSlot.getItemInMainHand();

        if (attackItem.getType() == Material.AIR
            || attackItem.getType() == Material.CAVE_AIR
            || attackItem.getType() == Material.VOID_AIR) {
            return null;
        }

        return attackItem;
    }
}
