/*
 * DHEnchantments
 * Copyright (C) 2019 Dream Horizon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.dreamhorizon.enchantments.listeners;

import com.dreamhorizon.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;

public class AnvilListener implements Listener {
    @EventHandler
    public void onPrepareAnvilEvent(PrepareAnvilEvent event) {
        // Check for three items in the anvil,
        ItemStack slotA = event.getInventory().getItem(0);
        ItemStack slotB = event.getInventory().getItem(1);
        ItemStack slotResult = event.getResult();
        if (slotA == null || slotB == null) {
            return;
        }
        // Override to changeable itemstacks
        slotA = slotA.clone();
        slotB = slotB.clone();
        EnchantmentStorageMeta slotABookMeta = null;
        if (slotA.getType() == Material.ENCHANTED_BOOK && slotA.hasItemMeta() && slotA.getItemMeta() != null && slotA.getItemFlags() instanceof EnchantmentStorageMeta) {
            slotABookMeta = (EnchantmentStorageMeta) slotB.getItemMeta();
        }
        EnchantmentStorageMeta slotBBookMeta = null;
        if (slotB.getType() == Material.ENCHANTED_BOOK && slotB.hasItemMeta() && slotB.getItemMeta() != null && slotB.getItemMeta() instanceof EnchantmentStorageMeta) {
            slotBBookMeta = (EnchantmentStorageMeta) slotB.getItemMeta();
        }
        boolean addXPPrice = true;
        boolean vanillaAnvil = true;
        
        // both of them are books
        if (slotABookMeta != null && slotBBookMeta != null) {
            // If both of the enchanted books have no enchants (in-theory only possible with /give, still added for consistency)
            if (slotABookMeta.getStoredEnchants().isEmpty() && slotBBookMeta.getStoredEnchants().isEmpty()) {
                event.setResult(new ItemStack(Material.AIR));
                return;
            }
            List<Enchantment> enchantments = new ArrayList<>(slotABookMeta.getStoredEnchants().keySet());
            enchantments.addAll(slotBBookMeta.getStoredEnchants().keySet());
            // There is no need for a canEnchant check here, since the books always return true for canENchantItem()
            slotResult = slotA.clone();
            for (Enchantment enchantment : enchantments) {
                if (enchantment instanceof DHEnchantment) {
                    DHEnchantment dhEnchantment = (DHEnchantment) enchantment;
                    vanillaAnvil = false;
                    int resultingLevel;
                    if (slotABookMeta.getStoredEnchants().containsKey(dhEnchantment) && slotBBookMeta.getStoredEnchants().containsKey(dhEnchantment)) {
                        int aLevel = slotABookMeta.getStoredEnchantLevel(dhEnchantment);
                        int bLevel = slotBBookMeta.getStoredEnchantLevel(dhEnchantment);
                        if (aLevel == bLevel) {
                            resultingLevel = aLevel + 1;
                        } else {
                            resultingLevel = Math.max(aLevel, bLevel);
                            addXPPrice = false;
                        }
                    } else if (slotABookMeta.getStoredEnchants().containsKey(dhEnchantment)) {
                        resultingLevel = slotABookMeta.getStoredEnchantLevel(dhEnchantment);
                    } else if (slotBBookMeta.getStoredEnchants().containsKey(dhEnchantment)) {
                        resultingLevel = slotBBookMeta.getStoredEnchantLevel(dhEnchantment);
                    } else {
                        continue;
                    }
                    if (resultingLevel > dhEnchantment.getMaxLevel()) {
                        resultingLevel = dhEnchantment.getMaxLevel();
                        addXPPrice = false;
                    }
                    if (dhEnchantment.canEnchantItem(slotResult)) {
                        slotResult.addUnsafeEnchantment(dhEnchantment, resultingLevel);
                    }
                }
            }
        } else if (slotABookMeta != null) {
            // Only a is a book, so since it is the first slot stop.
            // This is to emulate vanilla behavior
            event.setResult(new ItemStack(Material.AIR));
            return;
        } else if (slotBBookMeta != null) {
            // only b is a book, so since it is the second slot continue.
            // return if the enchants are incompatible.
            for (Enchantment enchantment : slotBBookMeta.getStoredEnchants().keySet()) {
                if (!enchantment.canEnchantItem(slotA)) {
                    event.setResult(new ItemStack(Material.AIR));
                    return;
                }
            }
            List<Enchantment> enchantments = new ArrayList<>(slotA.getEnchantments().keySet());
            enchantments.addAll(slotBBookMeta.getStoredEnchants().keySet());
            if (slotResult == null || slotResult.getType() == Material.AIR) {
                slotResult = slotA.clone();
            }
            for (Enchantment enchantment : enchantments) {
                if (enchantment instanceof DHEnchantment) {
                    DHEnchantment dhEnchantment = (DHEnchantment) enchantment;
                    vanillaAnvil = false;
                    int resultingLevel;
                    if (slotA.getEnchantments().containsKey(dhEnchantment) && slotBBookMeta.getStoredEnchants().containsKey(dhEnchantment)) {
                        int aLevel = slotA.getEnchantmentLevel(dhEnchantment);
                        int bLevel = slotBBookMeta.getStoredEnchantLevel(dhEnchantment);
                        if (aLevel == bLevel) {
                            resultingLevel = aLevel + 1;
                        } else {
                            // Pick the bigger of the two enchantments.
                            // This is expected vanilla behavior.
                            resultingLevel = Math.max(aLevel, bLevel);
                            addXPPrice = false;
                        }
                    } else if (slotA.getEnchantments().containsKey(dhEnchantment)) {
                        resultingLevel = slotA.getEnchantmentLevel(dhEnchantment);
                    } else if (slotBBookMeta.getStoredEnchants().containsKey(dhEnchantment)) {
                        resultingLevel = slotBBookMeta.getStoredEnchantLevel(dhEnchantment);
                    } else {
                        continue;
                    }
                    if (resultingLevel > dhEnchantment.getMaxLevel()) {
                        resultingLevel = dhEnchantment.getMaxLevel();
                        addXPPrice = false;
                    }
                    if (dhEnchantment.canEnchantItem(slotResult)) {
                        slotResult.addUnsafeEnchantment(dhEnchantment, resultingLevel);
                    }
                }
            }
        } else {
            // This means none of the arguments are a book, but now we need to filter out illegal items
            if (slotB.getType() != slotA.getType()) {
                event.setResult(new ItemStack(Material.AIR));
                return;
            }
            // return if the enchants are incompatible.
            for (Enchantment enchantment : slotB.getEnchantments().keySet()) {
                if (!enchantment.canEnchantItem(slotA)) {
                    event.setResult(new ItemStack(Material.AIR));
                    return;
                }
            }
            List<Enchantment> enchantments = new ArrayList<>(slotA.getEnchantments().keySet());
            enchantments.addAll(slotB.getEnchantments().keySet());
            if (slotResult == null || slotResult.getType() == Material.AIR) {
                slotResult = slotA.clone();
            }
            for (Enchantment enchantment : enchantments) {
                if (enchantment instanceof DHEnchantment) {
                    DHEnchantment dhEnchantment = (DHEnchantment) enchantment;
                    vanillaAnvil = false;
                    int resultingLevel;
                    if (slotA.getEnchantments().containsKey(dhEnchantment) && slotB.getEnchantments().containsKey(dhEnchantment)) {
                        int aLevel = slotA.getEnchantmentLevel(dhEnchantment);
                        int bLevel = slotB.getEnchantmentLevel(dhEnchantment);
                        if (aLevel == bLevel) {
                            resultingLevel = aLevel + 1;
                        } else {
                            // Pick the bigger of the two enchantments.
                            // This is expected vanilla behavior.
                            resultingLevel = Math.max(aLevel, bLevel);
                            addXPPrice = false;
                        }
                    } else if (slotA.getEnchantments().containsKey(dhEnchantment)) {
                        resultingLevel = slotA.getEnchantmentLevel(dhEnchantment);
                    } else if (slotB.getEnchantments().containsKey(dhEnchantment)) {
                        resultingLevel = slotB.getEnchantmentLevel(dhEnchantment);
                    } else {
                        // This shouldn't happen but anyway just iterate over it again for the next enchant, no items have it.
                        continue;
                    }
                    if (resultingLevel > dhEnchantment.getMaxLevel()) {
                        resultingLevel = dhEnchantment.getMaxLevel();
                        addXPPrice = false;
                    }
                    if (dhEnchantment.canEnchantItem(slotResult)) {
                        slotResult.addUnsafeEnchantment(dhEnchantment, resultingLevel);
                    }
                }
            }
        }
        if (vanillaAnvil) {
            return;
        }
        if (addXPPrice) {
            event.getInventory().setMaximumRepairCost(50);
            event.getInventory().setRepairCost(event.getInventory().getRepairCost() + 30);
        } else {
            // Unfortuantely we need to add 1 level to allow this to work, free operations don't exist :(
            event.getInventory().setMaximumRepairCost(50);
            event.getInventory().setRepairCost(event.getInventory().getRepairCost() + 1);
        }
        EnchantmentsHandler.getInstance().rebuildMeta(slotResult);
        event.setResult(slotResult);
        
    }
}
