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

package com.dreamhorizon.enchantments.listeners;

import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.enchantments.BattleRush;
import com.dreamhorizon.enchantments.enchantments.Cannibalism;
import com.dreamhorizon.enchantments.enchantments.Exhaust;
import com.dreamhorizon.enchantments.enchantments.LifeLeech;
import com.dreamhorizon.enchantments.enchantments.Milky;
import com.dreamhorizon.enchantments.enchantments.Poison;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {
    @EventHandler
    public void onPlayerAttackPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
            LivingEntity damager = (LivingEntity) event.getDamager();
            LivingEntity damaged = (LivingEntity) event.getEntity();
            ItemStack attackItem = getAttackItem(damager);
            if (attackItem == null) {
                return;
            }
            
            if (attackItem.containsEnchantment(DHEnchantments.POISON)) {
                Poison.addPoisonEffect(attackItem, damaged);
            }
            if (attackItem.containsEnchantment(DHEnchantments.LIFE_LEECH)) {
                LifeLeech.addLifeLeechEffect(attackItem, event, damaged);
            }
            
            if (attackItem.containsEnchantment(DHEnchantments.EXHAUST)) {
                Exhaust.addExhaustEffect(attackItem, damaged);
            }
            
            if (attackItem.containsEnchantment(DHEnchantments.MILKY)) {
                Milky.addMilkyEffect(attackItem, damaged);
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entityThatDied = event.getEntity();
        
        if (event.getEntity().getKiller() != null) {
            LivingEntity killer = entityThatDied.getKiller();
            if (killer != null) {
                
                ItemStack attackItem = getAttackItem(killer);
                if (attackItem == null) {
                    return;
                }
                
                if (attackItem.containsEnchantment(DHEnchantments.CANNIBALISM)) {
                    Cannibalism.addCannibalismEffect(attackItem, entityThatDied, killer);
                }
                
                if (attackItem.containsEnchantment(DHEnchantments.BATTLE_RUSH)) {
                    BattleRush.addBattleRushEffect(attackItem, killer);
                }
            }
        }
    }
    
    private ItemStack getAttackItem(LivingEntity damager) {
        EntityEquipment equipmentSlot = damager.getEquipment();
        
        if (equipmentSlot == null) {
            return null;
        }
        
        ItemStack attackItem = equipmentSlot.getItemInMainHand();
        
        if (attackItem.getType() == Material.AIR
            || attackItem.getType() == Material.CAVE_AIR
            || attackItem.getType() == Material.VOID_AIR) {
            return null;
        }
        
        return attackItem;
    }
}