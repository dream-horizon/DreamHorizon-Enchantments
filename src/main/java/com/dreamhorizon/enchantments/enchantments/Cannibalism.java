
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
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class Cannibalism {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "cannibalism"),
            "Cannibalism",
            "Helmets",
            "Gain food and health upon killing an enemy",
            1,
            2,
            EnchantmentTarget.ARMOR_HEAD,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.LEATHER_HELMET,
                Material.IRON_HELMET,
                Material.GOLDEN_HELMET,
                Material.DIAMOND_HELMET
            )
        );
    }
    
    public static void addCannibalismEffect(ItemStack attackItem, LivingEntity damaged, LivingEntity damager) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.CANNIBALISM);
        AttributeInstance healthAttribute = damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (healthAttribute == null) {
            return;
        }
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        double playersCurrentHealth = damager.getHealth();
        double healthToGain = 0;
        int saturationDuration = 0;
        if (level == 1) {
            healthToGain = (healthAttribute.getDefaultValue() / 100) * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.CANNIBALISM_1_HEALTH));
            saturationDuration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.CANNIBALISM_1_SATURATION_DURATION));
        } else if (level == 2) {
            healthToGain = (healthAttribute.getDefaultValue() / 100) * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.CANNIBALISM_2_HEALTH));
            saturationDuration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.CANNIBALISM_2_SATURATION_DURATION));
        }
        
        double healthToSetTo = healthToGain + playersCurrentHealth;
        
        if (healthToSetTo > healthAttribute.getValue()) {
            damager.setHealth(healthAttribute.getValue());
        } else {
            damager.setHealth(damager.getHealth() + healthToGain);
        }
        damager.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, saturationDuration, 1));
    }
}
