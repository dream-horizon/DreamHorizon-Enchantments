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

package com.dreamhorizon.enchantments.listener;

import com.dreamhorizon.enchantments.DHEnchantments;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Lukas Mansour
 * @since 1.0
 */
public class EntityListener implements Listener {
    @EventHandler
    public void onPlayerAttackPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
            LivingEntity damager = (LivingEntity) event.getDamager();
            LivingEntity damaged = (LivingEntity) event.getEntity();
            EntityEquipment equipmentSlot = damager.getEquipment();
            if (equipmentSlot == null) {
                return;
            }
            ItemStack attackItem = equipmentSlot.getItemInMainHand();
            if (attackItem.getType() == Material.AIR || attackItem.getType() == Material.CAVE_AIR || attackItem.getType() == Material.VOID_AIR) {
                return;
            }
            if (attackItem.containsEnchantment(DHEnchantments.POISON)) {
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
            if (attackItem.containsEnchantment(DHEnchantments.LIFE_LEECH)) {
                int level = attackItem.getEnchantmentLevel(DHEnchantments.LIFE_LEECH);
                double healthGained = 0.0D;
                if (level == 1) {
                    // 5%
                    healthGained = event.getDamage() / 20;
                } else if (level == 2) {
                    healthGained = (event.getDamage() / 100) * 8;
                }
                try {
                    damager.setHealth(damager.getHealth() + healthGained);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }
}
