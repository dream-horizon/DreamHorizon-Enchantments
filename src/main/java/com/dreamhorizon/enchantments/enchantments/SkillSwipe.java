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
import com.dreamhorizon.core.configuration.implementation.EnumConfiguration;
import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.config.EnchantmentsConfiguration;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import com.dreamhorizon.enchantments.util.NumberUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class SkillSwipe {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "skill_swipe"),
            "Skill Swipe",
            1,
            3,
            EnchantmentTarget.WEAPON,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.STONE_AXE, Material.STONE_SWORD,
                Material.IRON_AXE, Material.IRON_SWORD,
                Material.GOLDEN_AXE, Material.GOLDEN_SWORD,
                Material.DIAMOND_AXE, Material.DIAMOND_SWORD
            )
        );
    }

    public static void addSkillSwipeEffect(ItemStack attackItem, LivingEntity damaged, LivingEntity damager) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.SKILL_SWIPE);
        Player damagedAsPlayer = ((Player) damaged);
        Player damagerAsPlayer = ((Player) damager);
        float damagedExperience = damagedAsPlayer.getExp();
        float damagerExperience = damagerAsPlayer.getExp();
        double chance = NumberUtil.getRandomNumber(0, 100);
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();

        if (level == 1) {
            if (chance < Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_1_STEAL_CHANCE))) {
                damagedExperience =- Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_1_EXPERIENCE_STEAL));
                damagerExperience =+ Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_1_EXPERIENCE_STEAL));
            }
        } else if (level == 2) {
            if (chance < Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_2_STEAL_CHANCE))) {
                damagedExperience =- Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_2_EXPERIENCE_STEAL));
                damagerExperience =+ Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_2_EXPERIENCE_STEAL));
            }
        } else if (level == 3) {
            if (chance < Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_3_STEAL_CHANCE))) {
                damagedExperience =- Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_3_EXPERIENCE_STEAL));
                damagerExperience =+ Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SKILL_SWIPE_3_EXPERIENCE_STEAL));
            }
        }

        damagedAsPlayer.setExp(damagedExperience);
        damagerAsPlayer.setExp(damagerExperience);
    }
}

