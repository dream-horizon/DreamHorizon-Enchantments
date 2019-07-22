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

package com.dreamhorizon.enchantments.enchantments.implementation;

import com.dreamhorizon.core.DHCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DHEnchantments {
    public static final List<DHEnchantment> all = new ArrayList<>();
    public static final DHEnchantment POISON;
    public static final DHEnchantment LIFE_LEECH;
    
    static {
        POISON = new DHEnchantment(
                new NamespacedKey(DHCore.getPlugin(DHCore.class), "poison"),
                "Poison",
                1,
                3,
                EnchantmentTarget.WEAPON,
                false,
                false,
                new ArrayList<>(),
                Arrays.asList(
                        Material.WOODEN_AXE, Material.WOODEN_SWORD,
                        Material.STONE_AXE, Material.STONE_SWORD,
                        Material.IRON_AXE, Material.IRON_SWORD,
                        Material.GOLDEN_AXE, Material.GOLDEN_SWORD,
                        Material.DIAMOND_AXE, Material.DIAMOND_SWORD
                )
        );
        all.add(POISON);
        LIFE_LEECH = new DHEnchantment(
                new NamespacedKey(DHCore.getPlugin(DHCore.class), "lifeleech"),
                "Life Leech",
                1,
                2,
                EnchantmentTarget.WEAPON,
                false,
                false,
                new ArrayList<>(),
                Arrays.asList(
                        Material.WOODEN_SWORD,
                        Material.STONE_SWORD,
                        Material.IRON_SWORD,
                        Material.GOLDEN_SWORD,
                        Material.DIAMOND_SWORD
                )
        );
        all.add(LIFE_LEECH);
        
    }
}
