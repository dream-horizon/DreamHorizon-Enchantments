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

package com.dreamhorizon.enchantments.config.loottables;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LootGroup {
    private final int chance;
    private final List<List<ItemStack>> itemStacks;
    
    LootGroup(int chance, List<List<ItemStack>> itemStacks) {
        this.chance = chance;
        this.itemStacks = itemStacks;
    }
    
    public int getChance() {
        return chance;
    }
    
    public List<List<ItemStack>> getItemStackList() {
        return itemStacks;
    }
}
