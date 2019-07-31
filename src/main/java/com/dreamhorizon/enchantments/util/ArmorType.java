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

package com.dreamhorizon.enchantments.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public enum ArmorType {
    HELMET(5), CHESTPLATE(6), LEGGINGS(7), BOOTS(8);
    
    private final int slot;
    
    ArmorType(int slot) {
        this.slot = slot;
    }
    
    public int getSlot() {
        return slot;
    }
    
    public static ArmorType matchType(final ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return null;
        }
        switch (itemStack.getType()) {
            case LEATHER_HELMET:
            case IRON_HELMET:
            case GOLDEN_HELMET:
            case CHAINMAIL_HELMET:
            case DIAMOND_HELMET:
            case TURTLE_HELMET: {
                return HELMET;
            }
            case LEATHER_CHESTPLATE:
            case IRON_CHESTPLATE:
            case GOLDEN_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case ELYTRA: {
                return CHESTPLATE;
            }
            case LEATHER_LEGGINGS:
            case IRON_LEGGINGS:
            case GOLDEN_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case DIAMOND_LEGGINGS: {
                return LEGGINGS;
            }
            case LEATHER_BOOTS:
            case IRON_BOOTS:
            case GOLDEN_BOOTS:
            case CHAINMAIL_BOOTS:
            case DIAMOND_BOOTS: {
                return BOOTS;
            }
            default: {
                return null;
            }
        }
    }
}
