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

package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Poison {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
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
    }

    public static void addPoisonEffect(ItemStack attackItem, LivingEntity damaged) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.POISON);
        // 20 Ticks = 1 Second
        if (level == 1) {
            damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
        } else if (level == 2) {
            damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 160, 0));
        } else if (level == 3) {
            damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 240, 0));
        }
    }
}
