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

import com.dreamhorizon.enchantments.config.loottables.LootGroup;
import com.dreamhorizon.enchantments.config.loottables.LootTable;
import com.dreamhorizon.enchantments.config.loottables.LootTableHandler;
import com.dreamhorizon.enchantments.util.NumberUtil;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MythicMobsListener implements Listener {
    private final LootTableHandler lootTableHandler = LootTableHandler.getInstance();
    
    @EventHandler
    public void onMythicMobDeathEvent(MythicMobDeathEvent event) {
        if (event.getMobType() == null) {
            return;
        }
        LootTable lootTable = lootTableHandler.getLootTables().get(event.getMobType());
        if (lootTable == null) {
            // no loot table found for said mob.
            return;
        }
        for (int i = 0; i < lootTable.getRollAmount(); i++) {
            double randomNumber = NumberUtil.getRandomNumber(0, 100);
            int indexGroups = (int) NumberUtil.getRandomNumber(0, lootTable.getGroups().size() - 1);
            LootGroup lootGroup = lootTable.getGroups().get(indexGroups);
            if (randomNumber <= lootGroup.getChance()) {
                // Usually a floor would be needed here, but thanks to it only being a positive integer it can be skipped.
                int indexItemStackList = (int) NumberUtil.getRandomNumber(0, lootGroup.getItemStackList().size() - 1);
                List<ItemStack> drops = event.getDrops();
                drops.addAll(lootGroup.getItemStackList().get(indexItemStackList));
                event.setDrops(drops);
            }
        }
    }
}
