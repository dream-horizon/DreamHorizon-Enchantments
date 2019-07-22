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

package com.dreamhorizon.enchantments;

import com.dreamhorizon.enchantments.objects.DHEnchantment;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lukas Mansour
 * @since 1.0
 */
public class EnchantmentsHandler {
    private static final EnchantmentsHandler instance = new EnchantmentsHandler();
    private static final Pattern ENCHANTMENT_LORE_PATTERN = Pattern.compile("(.*) (X|IV|V?I{0,3})");
    
    private EnchantmentsHandler() {
        DHEnchantments.all.forEach(this::register);
    }
    
    public void register(@NotNull Enchantment enchantment) {
        try {
            Field acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public void unregister(@NotNull Enchantment enchantment) {
        try {
            Field byKeyField = Enchantment.class.getDeclaredField("byKey");
            Field byNameField = Enchantment.class.getDeclaredField("byName");
            byKeyField.setAccessible(true);
            byNameField.setAccessible(true);
            Map<NamespacedKey, Enchantment> byId = (Map<NamespacedKey, Enchantment>) byKeyField.get(null);
            Map<String, Enchantment> byName = (Map<String, Enchantment>) byNameField.get(null);
            byId.remove(enchantment.getKey());
            byName.remove(enchantment.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void rebuildMeta(@NotNull ItemStack itemStack) {
        if (!itemStack.hasItemMeta() || itemStack.getItemMeta() == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta().clone();
        List<String> newLoreEnchants = new ArrayList<>();
        List<String> newLoreGeneral = new ArrayList<>();
        // Don't check hasEnchants() since this is only called after the enchantsmap is empty.
        for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
            if (enchantment instanceof DHEnchantment) {
                newLoreEnchants.add(ChatColor.translateAlternateColorCodes('&', "&7" + enchantment.getName() + " " + getRoman(itemMeta.getEnchantLevel(enchantment))));
            }
        }
        if (itemMeta.hasLore() && itemMeta.getLore() != null) {
            for (String oldLore : itemMeta.getLore()) {
                Matcher m = ENCHANTMENT_LORE_PATTERN.matcher(oldLore);
                if (m.matches()) {
                    continue;
                } else {
                    newLoreGeneral.add(oldLore);
                }
            }
        }
        newLoreEnchants.addAll(newLoreGeneral);
        itemMeta.setLore(newLoreEnchants);
        itemStack.setItemMeta(itemMeta);
    }
    
    private String getRoman(int level) {
        switch (level) {
            case 1: {
                return "I";
            }
            case 2: {
                return "II";
            }
            case 3: {
                return "III";
            }
            case 4: {
                return "IV";
            }
            case 5: {
                return "V";
            }
            case 6: {
                return "VI";
            }
            case 7: {
                return "VII";
            }
            case 8: {
                return "VIII";
            }
            case 9: {
                return "IX";
            }
            case 10: {
                return "X";
            }
            default:
            case 0: {
                return String.valueOf(level);
            }
        }
    }
    
    public static EnchantmentsHandler getInstance() {
        return instance;
    }
}
