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

import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.enchantments.Haste;
import com.dreamhorizon.enchantments.enchantments.Speed;
import com.dreamhorizon.enchantments.util.ArmorType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorEquipListener implements Listener {
    
    @EventHandler
    public final void inventoryClick(final InventoryClickEvent e) {
        if (e.isCancelled() || e.getAction() == InventoryAction.NOTHING) {
            return;
        }
        if (e.getSlotType() != SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR && e.getSlotType() != InventoryType.SlotType.CONTAINER) {
            return;
        }
        if (e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (!e.getInventory().getType().equals(InventoryType.CRAFTING) && !e.getInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        boolean shift = false, numberkey = false;
        if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            numberkey = true;
        }
        if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
            shift = true;
        }
        ArmorType newArmorType;
        if (shift) {
            newArmorType = ArmorType.matchType(e.getCurrentItem());
        } else {
            newArmorType = ArmorType.matchType(e.getCursor());
        }
        if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) {
            // Used for drag and drop checking to make sure you aren't trying to place a helmet in a wrong armor slot.
            return;
        }
        if (shift) {
            newArmorType = ArmorType.matchType(e.getCurrentItem());
            if (newArmorType != null) {
                boolean equipping = true;
                if (e.getRawSlot() == newArmorType.getSlot()) {
                    equipping = false;
                }
                if (newArmorType.equals(ArmorType.HELMET) && (equipping == isInvalidItemStack(e.getWhoClicked().getInventory().getHelmet())) || newArmorType.equals(ArmorType.CHESTPLATE) && (equipping == isInvalidItemStack(e.getWhoClicked().getInventory().getChestplate())) || newArmorType.equals(ArmorType.LEGGINGS) && (equipping == isInvalidItemStack(e.getWhoClicked().getInventory().getLeggings())) || newArmorType.equals(ArmorType.BOOTS) && (equipping == isInvalidItemStack(e.getWhoClicked().getInventory().getBoots()))) {
                    if (equipping) {
                        onPlayerEquipArmor((Player) e.getWhoClicked(), e.getCurrentItem());
                    } else {
                        onPlayerUnequipArmor((Player) e.getWhoClicked(), e.getCurrentItem());
                    }
                }
            }
        } else {
            ItemStack newArmorPiece = e.getCursor();
            ItemStack oldArmorPiece = e.getCurrentItem();
            if (numberkey) {
                if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                    ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                    if (!isInvalidItemStack(hotbarItem)) {
                        // Equipping
                        newArmorType = ArmorType.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                    } else {
                        // Unequipping
                        if (!isInvalidItemStack(e.getCurrentItem())) {
                            newArmorType = ArmorType.matchType(e.getCurrentItem());
                        } else {
                            newArmorType = ArmorType.matchType(e.getCursor());
                        }
                    }
                }
            } else {
                if (isInvalidItemStack(e.getCursor()) && !isInvalidItemStack(e.getCurrentItem())) {
                    newArmorType = ArmorType.matchType(e.getCurrentItem());
                }
            }
            if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                onPlayerUnequipArmor((Player) e.getWhoClicked(), oldArmorPiece);
                onPlayerEquipArmor((Player) e.getWhoClicked(), newArmorPiece);
            }
        }
    }
    
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {// Having both of these checks is useless, might as well do it though.
                // Some blocks have actions when you right click them which stops the client from equipping the armor in hand.
                Material mat = event.getClickedBlock().getType();
                if (mat.isInteractable()) {
                    return;
                }
            }
            ArmorType newArmorType = ArmorType.matchType(event.getItem());
            if (newArmorType != null) {
                if (newArmorType.equals(ArmorType.HELMET) && isInvalidItemStack(event.getPlayer().getInventory().getHelmet()) || newArmorType.equals(ArmorType.CHESTPLATE) && isInvalidItemStack(event.getPlayer().getInventory().getChestplate()) || newArmorType.equals(ArmorType.LEGGINGS) && isInvalidItemStack(event.getPlayer().getInventory().getLeggings()) || newArmorType.equals(ArmorType.BOOTS) && isInvalidItemStack(event.getPlayer().getInventory().getBoots())) {
                    onPlayerEquipArmor(event.getPlayer(), event.getItem());
                }
            }
        }
    }
    
    @EventHandler
    public void inventoryDrag(InventoryDragEvent event) {
        ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (event.getRawSlots().isEmpty()) {
            return;
        }
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0) && event.getWhoClicked() instanceof Player) {
            onPlayerEquipArmor((Player) event.getWhoClicked(), event.getOldCursor());
        }
    }
    
    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent event) {
        ArmorType type = ArmorType.matchType(event.getBrokenItem());
        if (type != null) {
            onPlayerUnequipArmor(event.getPlayer(), event.getBrokenItem());
        }
    }
    
    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        for (ItemStack i : player.getInventory().getArmorContents()) {
            if (!isInvalidItemStack(i)) {
                onPlayerUnequipArmor(player, i);
            }
        }
    }
    
    @EventHandler
    public void dispenseArmorEvent(BlockDispenseArmorEvent event) {
        ArmorType type = ArmorType.matchType(event.getItem());
        if (type != null) {
            if (event.getTargetEntity() instanceof Player) {
                onPlayerEquipArmor((Player) event.getTargetEntity(), event.getItem());
            }
        }
    }
    
    private void onPlayerEquipArmor(Player player, ItemStack equippedArmor) {
        if (equippedArmor != null && equippedArmor.getType() != Material.AIR) {
            if (equippedArmor.containsEnchantment(DHEnchantments.HASTE)) {
                Haste.applyHasteEffect(equippedArmor, player);
            }
            
            if (equippedArmor.containsEnchantment(DHEnchantments.SPEED)) {
                Speed.applySpeedEffect(equippedArmor, player);
            }
        }
    }
    
    private void onPlayerUnequipArmor(Player player, ItemStack unequippedArmor) {
        if (unequippedArmor != null && unequippedArmor.getType() != Material.AIR) {
            if (unequippedArmor.containsEnchantment(DHEnchantments.HASTE)) {
                Haste.removeHasteEffect(player);
            }
            
            if (unequippedArmor.containsEnchantment(DHEnchantments.SPEED)) {
                Speed.removeSpeedEffect(player);
            }
        }
    }
    
    private static boolean isInvalidItemStack(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }
    
}
