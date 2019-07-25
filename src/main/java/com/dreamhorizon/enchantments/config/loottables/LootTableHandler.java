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

package com.dreamhorizon.enchantments.config.loottables;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.core.com.electronwill.nightconfig.core.Config;
import com.dreamhorizon.core.com.electronwill.nightconfig.core.file.FileConfig;
import com.dreamhorizon.core.configuration.ConfigurationHandler;
import com.dreamhorizon.core.util.FileUtil;
import com.dreamhorizon.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootTableHandler {
    private static final Logger LOGGER = LogManager.getLogger("com.dreamhorizon.core");
    private final static LootTableHandler instance = new LootTableHandler();
    private final Map<MythicMob, LootTable> lootTables = new HashMap<>();
    
    private LootTableHandler() {
        File configFile = new File(ConfigurationHandler.getInstance().getConfigFolder() + File.separator + "enchantment_loot_mythicmobs.yml");
        FileConfig config = FileConfig.of(configFile);
        if (!FileUtil.createFile(configFile)) {
            LOGGER.log(Level.ERROR, "enchantment_loot_mythicmobs.yml couldn't be created.");
            throw new RuntimeException("enchantment_loot_mythicmobs.yml couldn't be created.");
        }
        config.load();
        // config is empty so. yeah.
        if (config.isEmpty()) {
            return;
        }
        for (MythicMob mob : MythicMobs.inst().getMobManager().getMobTypes()) {
            String mobName = mob.getInternalName();
            if (config.get(mobName) != null) {
                int rollAmount = config.get(mobName + ".rollAmount");
                if (rollAmount == 0) {
                    continue;
                }
                if (config.get(mobName + ".groups") != null && config.get(mobName + ".groups") instanceof Config) {
                    List<LootGroup> groups = new ArrayList<>();
                    for (String group : ((Config) config.get(mobName + ".groups")).valueMap().keySet()) {
                        int chance = config.get(mobName + ".groups." + group + ".chance");
                        if (chance == 0) {
                            continue;
                        }
                        List<List<ItemStack>> itemStacks = getEnchantmentsFromString(config.get(mobName + ".groups." + group + ".drops"));
                        if (!itemStacks.isEmpty()) {
                            groups.add(new LootGroup(chance, itemStacks));
                        }
                    }
                    lootTables.put(mob, new LootTable(rollAmount, groups));
                }
            }
        }
    }
    
    private List<List<ItemStack>> getEnchantmentsFromString(List<String> itemList) {
        // the pipe split (|) = enchant and level separator
        // the comma split (,) = multiple enchants on 1 book separator
        // the hashtag split (#) = multiple books with enchants.
        List<List<ItemStack>> outerItemStacks = new ArrayList<>();
        for (String item : itemList) {
            List<ItemStack> innerItemStacks = new ArrayList<>();
            for (String enchantmentList : item.split("#")) {
                ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                ItemMeta bookMeta = Bukkit.getItemFactory().getItemMeta(Material.ENCHANTED_BOOK);
                String[] enchantmentArr = enchantmentList.split(",");
                for (String enchantmentStr : enchantmentArr) {
                    String[] splitEnchantment = enchantmentStr.split("\\|");
                    if (splitEnchantment.length == 2) {
                        String enchantName = splitEnchantment[0];
                        int level = Integer.parseInt(splitEnchantment[1]);
                        Enchantment enchantment = Enchantment.getByKey(new NamespacedKey(DHCore.getPlugin(DHCore.class), enchantName));
                        if (enchantment instanceof DHEnchantment) {
                            ((EnchantmentStorageMeta) bookMeta).addStoredEnchant(
                                enchantment
                                , level
                                , true);
                            EnchantmentsHandler.getInstance().rebuildMeta(enchantedBook);
                            enchantedBook.setItemMeta(bookMeta);
                        } else {
                            LOGGER.log(Level.ERROR, "Could not find a custom enchant by the name of " + enchantName + "!");
                            LOGGER.log(Level.ERROR, "Skipping...");
                        }
                    } else {
                        LOGGER.log(Level.ERROR, "The enchantment " + enchantmentStr + " does not have the correct format enchantment|level");
                        LOGGER.log(Level.ERROR, "Skipping...");
                    }
                }
                innerItemStacks.add(enchantedBook);
            }
            outerItemStacks.add(innerItemStacks);
        }
        return outerItemStacks;
    }
    
    public Map<MythicMob, LootTable> getLootTables() {
        return lootTables;
    }
    
    public static LootTableHandler getInstance() {
        return instance;
    }
}
