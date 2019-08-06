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
package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.core.configuration.implementation.EnumConfiguration;
import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.config.EnchantmentsConfiguration;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import com.dreamhorizon.enchantments.objects.PotionEffectType;
import com.dreamhorizon.enchantments.util.NumberUtil;
import com.google.common.base.Enums;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Milky {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "milky"),
            "Milky",
            "Swords, Axes, Bow, Crossbow",
            "Chance to remove buffs from opponent on hit",
            1,
            2,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.WOODEN_SWORD,
                Material.STONE_SWORD,
                Material.IRON_SWORD,
                Material.GOLDEN_SWORD,
                Material.DIAMOND_SWORD,
                Material.WOODEN_AXE,
                Material.STONE_AXE,
                Material.IRON_AXE,
                Material.GOLDEN_AXE,
                Material.DIAMOND_AXE,
                Material.BOW,
                Material.CROSSBOW
            )
        );
    }
    
    public static void addMilkyEffect(ItemStack attackItem, LivingEntity damaged) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.MILKY);
        double chance = NumberUtil.getRandomNumber(0, 100);
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        boolean cleanse = false;
        if (level == 1) {
            if (chance <= Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.MILKY_1_CLEANSE_CHANCE))) {
                cleanse = true;
            }
        } else if (level == 2) {
            if (chance <= Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.MILKY_2_CLEANSE_CHANCE))) {
                cleanse = true;
            }
        }
        if (cleanse) {
            Collection<PotionEffect> effects = damaged.getActivePotionEffects();
            for (PotionEffect effect : effects) {
                if (Enums.getIfPresent(PotionEffectType.BUFFS.class, effect.getType().getName()).isPresent()) {
                    damaged.removePotionEffect(effect.getType());
                }
            }
        }
    }
}
