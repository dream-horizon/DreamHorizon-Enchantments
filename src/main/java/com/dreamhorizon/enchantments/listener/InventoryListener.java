/*
 * DHEnchants
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

package com.dreamhorizon.enchantments.listener;

import com.dreamhorizon.enchantments.enchantments.EnchantmentsHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Lukas Mansour
 * @since 1.0
 */
public class InventoryListener implements Listener {
    @SuppressWarnings("ConstantConditions")
    @EventHandler(ignoreCancelled = true)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        for (ItemStack itemStack : event.getDestination().getContents()) {
            if (itemStack == null || itemStack.getEnchantments().isEmpty() || !itemStack.hasItemMeta()) {
                continue;
            }
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        for (ItemStack itemStack : event.getSource().getContents()) {
            if (itemStack == null || itemStack.getEnchantments().isEmpty() || !itemStack.hasItemMeta()) {
                continue;
            }
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
    }
    
    @SuppressWarnings("ConstantConditions")
    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        for (ItemStack itemStack : event.getInventory().getContents()) {
            if (itemStack == null || itemStack.getEnchantments().isEmpty() || !itemStack.hasItemMeta()) {
                continue;
            }
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
            if (itemStack == null || itemStack.getEnchantments().isEmpty() || !itemStack.hasItemMeta()) {
                continue;
            }
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        if (event.getPlayer() instanceof Player) {
            ((Player) event.getPlayer()).updateInventory();
        }
    }
    
    @SuppressWarnings("ConstantConditions")
    @EventHandler(ignoreCancelled = true)
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        for (ItemStack itemStack : event.getInventory().getContents()) {
            if (itemStack == null || itemStack.getEnchantments().isEmpty() || !itemStack.hasItemMeta()) {
                continue;
            }
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
            if (itemStack == null || itemStack.getEnchantments().isEmpty() || !itemStack.hasItemMeta()) {
                continue;
            }
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        if (event.getPlayer() instanceof Player) {
            ((Player) event.getPlayer()).updateInventory();
        }
    }
    
    @SuppressWarnings("ConstantConditions")
    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        ItemStack itemStack = event.getItemDrop().getItemStack();
        if (!itemStack.getEnchantments().isEmpty() && itemStack.hasItemMeta()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
            event.getItemDrop().setItemStack(itemStack);
        }
        for (ItemStack invItemStack : event.getPlayer().getInventory().getContents()) {
            if (invItemStack == null || invItemStack.getEnchantments().isEmpty() || !invItemStack.hasItemMeta()) {
                continue;
            }
            EnchantmentsHandler.getInstance().rebuildMeta(invItemStack);
        }
        ItemStack itemStackCursor = event.getPlayer().getItemOnCursor();
        if (itemStackCursor.getType() != Material.AIR && itemStackCursor.getType() != Material.CAVE_AIR && itemStackCursor.getType() != Material.VOID_AIR) {
            if (!itemStackCursor.getEnchantments().isEmpty() && itemStackCursor.hasItemMeta()) {
                EnchantmentsHandler.getInstance().rebuildMeta(itemStackCursor);
                event.getPlayer().setItemOnCursor(itemStackCursor);
            }
        }
        event.getPlayer().updateInventory();
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onEntityDropItemEvent(EntityDropItemEvent event) {
        ItemStack itemStack = event.getItemDrop().getItemStack();
        if (!itemStack.getEnchantments().isEmpty() && itemStack.hasItemMeta()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
            event.getItemDrop().setItemStack(itemStack);
        }
    }
    
    @SuppressWarnings("ConstantConditions")
    @EventHandler(ignoreCancelled = true)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            for (ItemStack itemStack : event.getClickedInventory().getContents()) {
                if (itemStack == null || itemStack.getEnchantments().isEmpty() || !itemStack.hasItemMeta()) {
                    continue;
                }
                EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
            }
        }
        for (ItemStack itemStack : event.getWhoClicked().getInventory().getContents()) {
            if (itemStack == null || itemStack.getEnchantments().isEmpty() || !itemStack.hasItemMeta()) {
                continue;
            }
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        if (event.getCurrentItem() != null) {
            ItemStack itemStack = event.getCurrentItem();
            if (!itemStack.getEnchantments().isEmpty() && itemStack.hasItemMeta()) {
                EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
                event.setCurrentItem(itemStack);
            }
        }
        ItemStack itemStackCursor = event.getWhoClicked().getItemOnCursor();
        if (itemStackCursor.getType() != Material.AIR && itemStackCursor.getType() != Material.CAVE_AIR && itemStackCursor.getType() != Material.VOID_AIR) {
            if (!itemStackCursor.getEnchantments().isEmpty() && itemStackCursor.hasItemMeta()) {
                EnchantmentsHandler.getInstance().rebuildMeta(itemStackCursor);
                event.getWhoClicked().setItemOnCursor(itemStackCursor);
            }
        }
    }
}
