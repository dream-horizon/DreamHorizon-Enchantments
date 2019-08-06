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

package com.dreamhorizon.enchantments.objects;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class DHEnchantment extends Enchantment {
    private final String name;
    private final String goesOn;
    private final String description;
    private final int maxLevel;
    private final int minLevel;
    private final boolean cursed;
    private final boolean treasure;
    private final List<Enchantment> conflicts;
    private final List<Material> enchantableItems;
    
    public DHEnchantment(
        NamespacedKey key,
        String name,
        String goesOn,
        String description,
        int minLevel,
        int maxLevel,
        boolean cursed,
        boolean treasure,
        List<Enchantment> conflicts,
        List<Material> enchantableItems) {
        super(key);
        this.name = name;
        this.goesOn = goesOn;
        this.description = description;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.cursed = cursed;
        this.treasure = treasure;
        this.conflicts = conflicts;
        this.enchantableItems = enchantableItems;
    }
    
    @Override
    @NotNull
    public String getName() {
        return name;
    }
    
    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
    
    @Override
    // We can make this configurable later but all enchants currently start at 1.
    public int getStartLevel() {
        return minLevel;
    }
    
    @Override
    @NotNull
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }
    
    @Override
    public boolean isTreasure() {
        return treasure;
    }
    
    @Override
    public boolean isCursed() {
        return cursed;
    }
    
    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return conflicts.contains(other);
    }
    
    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        if (item.getType() == Material.ENCHANTED_BOOK) {
            return true;
        }
        return enchantableItems.contains(item.getType());
    }
    
    public String getGoesOn() {
        return goesOn;
    }
    
    public String getDescription() {
        return description;
    }
}
