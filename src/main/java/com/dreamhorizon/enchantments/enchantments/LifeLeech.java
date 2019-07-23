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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Collin Herber
 * @version 1.0
 */
public class LifeLeech {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
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
    }
    
    public static void addLifeLeechEffect(ItemStack attackItem, EntityDamageByEntityEvent event, LivingEntity damager) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.LIFE_LEECH);
        double healthGained = 0.0D;
        if (level == 1) {
            // 10%
            healthGained = (event.getDamage() / 100) * 10;
        } else if (level == 2) {
            // 20%
            healthGained = (event.getDamage() / 100) * 20;
        }
        try {
            damager.setHealth(damager.getHealth() + healthGained);
        } catch (IllegalArgumentException ignored) {
        }
    }
}
