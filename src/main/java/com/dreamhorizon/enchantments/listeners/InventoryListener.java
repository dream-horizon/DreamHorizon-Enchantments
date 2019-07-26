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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        for (ItemStack itemStack : event.getDestination().getContents()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        for (ItemStack itemStack : event.getSource().getContents()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        for (ItemStack itemStack : event.getInventory().getContents()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        if (event.getPlayer() instanceof Player) {
            ((Player) event.getPlayer()).updateInventory();
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        for (ItemStack itemStack : event.getInventory().getContents()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        if (event.getPlayer() instanceof Player) {
            ((Player) event.getPlayer()).updateInventory();
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItemEvent(EntityPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();
        EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        event.getItem().setItemStack(itemStack);
        if (event.getEntity() instanceof InventoryHolder) {
            for (ItemStack invItemStack : ((InventoryHolder) event.getEntity()).getInventory()) {
                if (invItemStack == null || invItemStack.getType() == Material.AIR) {
                    continue;
                }
                EnchantmentsHandler.getInstance().rebuildMeta(invItemStack);
            }
        }
        if (event.getEntity() instanceof Player) {
            ((Player) event.getEntity()).updateInventory();
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onEntityDropItemEvent(EntityDropItemEvent event) {
        ItemStack itemStack = event.getItemDrop().getItemStack();
        EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        event.getItemDrop().setItemStack(itemStack);
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            for (ItemStack itemStack : event.getClickedInventory().getContents()) {
                EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
            }
        }
        for (ItemStack itemStack : event.getWhoClicked().getInventory().getContents()) {
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
        }
        if (event.getCurrentItem() != null) {
            ItemStack itemStack = event.getCurrentItem();
            EnchantmentsHandler.getInstance().rebuildMeta(itemStack);
            event.setCurrentItem(itemStack);
        }
    }
}
