/*
 * DHExamplemodule
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
import com.dreamhorizon.core.commands.implementation.DHCommand;
import com.dreamhorizon.enchantments.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.enchantments.implementation.DHEnchantment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Lukas Mansour
 * @since 1.0
 */
@CommandAlias("dhenchantments|dhe|dhenchantment|dhenchant|dhenchants|dreamhorizonenchants")
public class EnchantCommand extends DHCommand {
    @Default
    @CommandAlias("enchant|e")
    @CommandPermission("dhenchantments.enchant")
    @CommandCompletion("@dhenchantments @dhenchantmentlevel @boolean")
    public static void onEnchant(Player player, DHEnchantment enchantment, @Default(value = "1") int level, @Default(value = "false") boolean unsafe) {
        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.getType() == Material.AIR || handItem.getType() == Material.CAVE_AIR || handItem.getType() == Material.VOID_AIR) {
            // TODO: Customize message
            player.sendMessage("You must have an item in your main hand to enchant it.");
            return;
        }
        ItemStack newHandItem = handItem.clone();
        if (enchantment.canEnchantItem(handItem)) {
            if (!handItem.getEnchantments().isEmpty()) {
                for (Enchantment enchantmentPresent : handItem.getEnchantments().keySet()) {
                    if (enchantment.conflictsWith(enchantmentPresent)) {
                        if (unsafe) {
                            newHandItem.addUnsafeEnchantment(enchantment, level);
                        } else {
                            player.sendMessage("The requested enchantment conflicts with " + enchantmentPresent.getName());
                        }
                    }
                }
            } else {
                newHandItem.addEnchantment(enchantment, level);
            }
            EnchantmentsHandler.getInstance().rebuildMeta(newHandItem);
            player.getInventory().setItemInMainHand(newHandItem);
        } else {
            player.sendMessage("This item cannot be enchanted with this enchantment.");
        }
        player.sendMessage("Enchanted hopefully.");
    }
}
