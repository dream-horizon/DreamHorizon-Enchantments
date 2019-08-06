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

package com.dreamhorizon.enchantments.command;


import com.dreamhorizon.core.acf.annotation.CommandAlias;
import com.dreamhorizon.core.acf.annotation.CommandCompletion;
import com.dreamhorizon.core.acf.annotation.CommandPermission;
import com.dreamhorizon.core.acf.annotation.Default;
import com.dreamhorizon.core.acf.annotation.Subcommand;
import com.dreamhorizon.core.commands.implementation.DHCommand;
import com.dreamhorizon.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.config.loottables.LootTableHandler;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

@CommandAlias("dhenchantments|dhe|dhenchantment|dhenchant|dhenchants|dreamhorizonenchants")
public class EnchantCommand extends DHCommand {
    @SuppressWarnings("deprecation")
    @Subcommand("enchant|e")
    @CommandPermission("dhenchantments.enchant")
    @CommandCompletion("@dhenchantments @dhenchantmentlevel @boolean")
    public static void enchant(Player player, DHEnchantment enchantment, @Default(value = "1") int level, @Default(value = "false") boolean unsafe) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() == Material.AIR) {
            // TODO: Customize message
            player.sendMessage("You must have a valid item in your main hand to enchant it.");
            return;
        }
        ItemStack newItemStack = itemStack.clone();
        boolean isBook = newItemStack.getType() == Material.BOOK || newItemStack.getType() == Material.ENCHANTED_BOOK;
        if (!newItemStack.hasItemMeta() || newItemStack.getItemMeta() == null) {
            if (newItemStack.getType() == Material.BOOK) {
                newItemStack.setType(Material.ENCHANTED_BOOK);
                newItemStack.setAmount(1);
            }
            newItemStack.setItemMeta(Bukkit.getItemFactory().getItemMeta(newItemStack.getType()));
        }
        if (enchantment != null && !isBook) {
            if (enchantment.canEnchantItem(newItemStack)) {
                if (unsafe) {
                    newItemStack.addUnsafeEnchantment(enchantment, level);
                } else if (!newItemStack.getEnchantments().isEmpty()) {
                    for (Enchantment enchantmentPresent : newItemStack.getEnchantments().keySet()) {
                        if (enchantment.conflictsWith(enchantmentPresent)) {
                            // TODO: Customize message
                            player.sendMessage("The requested enchantment conflicts with " + enchantmentPresent.getName());
                            return;
                        }
                    }
                }
                if (level > enchantment.getMaxLevel()) {
                    // TODO: Customize message
                    player.sendMessage("The maximum level for " + enchantment.getName() + " is " + enchantment.getMaxLevel());
                    return;
                }
                newItemStack.addEnchantment(enchantment, level);
            } else {
                player.sendMessage("The requested enchantment cannot be applied to your item");
                return;
            }
        } else if (enchantment != null) {
            if (newItemStack.getItemMeta() instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) newItemStack.getItemMeta();
                if (unsafe) {
                    bookMeta.addStoredEnchant(enchantment, level, true);
                } else if (!bookMeta.getEnchants().isEmpty()) {
                    for (Enchantment enchantmentPresent : bookMeta.getStoredEnchants().keySet()) {
                        if (enchantment.conflictsWith(enchantmentPresent)) {
                            // TODO: Customize message
                            player.sendMessage("The requested enchantment conflicts with " + enchantmentPresent.getName());
                        }
                    }
                }
                if (level > enchantment.getMaxLevel()) {
                    // TODO: Customize message
                    player.sendMessage("The maximum level for " + enchantment.getName() + " is " + enchantment.getMaxLevel());
                    return;
                }
                bookMeta.addStoredEnchant(enchantment, level, false);
                newItemStack.setItemMeta(bookMeta);
            }
        }
        EnchantmentsHandler.getInstance().rebuildMeta(newItemStack);
        if (isBook) {
            if (itemStack.getType() == Material.BOOK) {
                // if the person is using a normal book just add the enchanted book to their inv
                // Check if full
                if (player.getInventory().firstEmpty() != -1) {
                    player.getInventory().addItem(newItemStack);
                } else {
                    // If full
                    // TODO: Customize Message
                    player.sendMessage("Clear out your inventory please :).");
                }
            } else {
                player.getInventory().setItemInMainHand(newItemStack);
            }
        } else {
            player.getInventory().setItemInMainHand(newItemStack);
        }
        // TODO: Customize message
        player.sendMessage("Enchanted hopefully.");
    }
    
    @Subcommand("reload")
    @CommandPermission("dhenchantments.reload")
    public static void reload(CommandSender sender) {
        // loadLootTables will also reload them.
        LootTableHandler.getInstance().loadLootTables();
    }

    @Subcommand("info")
    @CommandPermission("dhenchantments.enchant.info")
    @CommandCompletion("@dhenchantments")
    public static void info(CommandSender sender, DHEnchantment enchantment) {
        if (enchantment == null) {
            // TODO: Customize message
            sender.sendMessage("You need to specify an enchant (/dhenchant <enchant>)");
        } else {
            int maxLevel = enchantment.getMaxLevel();
            String name = enchantment.getName();
            String goesOn = enchantment.getGoesOn();
            String description = enchantment.getDescription();
            sender.sendMessage("===================");
            sender.sendMessage("Name: " + name);
            sender.sendMessage("Description: " + description);
            sender.sendMessage("Max Level: " + maxLevel);
            sender.sendMessage("Applies to: " + goesOn);
            sender.sendMessage("===================");
        }
    }
}
