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

package com.dreamhorizon.enchantments;

import com.dreamhorizon.core.configuration.ConfigurationHandler;
import com.dreamhorizon.core.configuration.implementation.EnumConfiguration;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnchantmentsHandler {
    private static final EnchantmentsHandler instance = new EnchantmentsHandler();
    private static final Pattern ENCHANTMENT_LORE_PATTERN = Pattern.compile("(.*) (\\d+|X|IV|V?I{0,3})");
    
    private EnchantmentsHandler() {
        DHEnchantments.all.forEach(this::register);
    }
    
    private void register(@NotNull Enchantment enchantment) {
        try {
            // Unregister it before registering it again. This is for reloading to work.
            unregister(enchantment);
            Field acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings({"unchecked", "deprecation"})
    private void unregister(@NotNull Enchantment enchantment) {
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
    
    @SuppressWarnings("deprecation")
    public void rebuildMeta(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR || !itemStack.hasItemMeta() || itemStack.getItemMeta() == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof EnchantmentStorageMeta) {
            if (!((EnchantmentStorageMeta) itemStack.getItemMeta()).hasStoredEnchants() && !itemMeta.hasLore()) {
                return;
            }
        } else {
            if (!itemStack.getItemMeta().hasEnchants() && !itemStack.getItemMeta().hasLore()) {
                return;
            }
        }
        List<String> newLoreEnchants = new ArrayList<>();
        List<String> newLoreGeneral = new ArrayList<>();
        // Get already stored enchantments.
        if (!(itemMeta instanceof EnchantmentStorageMeta) && itemMeta.hasEnchants()) {
            for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                if (enchantment instanceof DHEnchantment) {
                    newLoreEnchants.add(ChatColor.translateAlternateColorCodes('&', "&7" + enchantment.getName() + " " + getRoman(itemMeta.getEnchantLevel(enchantment))));
                }
            }
        } else if (itemMeta instanceof EnchantmentStorageMeta && ((EnchantmentStorageMeta) itemMeta).hasStoredEnchants()) {
            for (Enchantment enchantment : ((EnchantmentStorageMeta) itemMeta).getStoredEnchants().keySet()) {
                if (enchantment instanceof DHEnchantment) {
                    newLoreEnchants.add(ChatColor.translateAlternateColorCodes('&', "&7" + enchantment.getName() + " " + getRoman(((EnchantmentStorageMeta) itemMeta).getStoredEnchantLevel(enchantment))));
                }
            }
        }
        // Get enchantments removed, but lore still existing
        if (itemMeta.hasLore() && itemMeta.getLore() != null) {
            for (String oldLore : itemMeta.getLore()) {
                Matcher m = ENCHANTMENT_LORE_PATTERN.matcher(oldLore);
                if (m.matches()) {
                    // Failsafe incase the enchant keys get nuked.
                    Enchantment enchantment = Enchantment.getByName(ChatColor.stripColor(m.group(1)));
                    if (enchantment != null) {
                        if (!(itemMeta instanceof EnchantmentStorageMeta) && !itemMeta.hasEnchant(enchantment)) {
                            itemMeta.addEnchant(enchantment, fromRoman(m.group(2)), true);
                            newLoreEnchants.add(ChatColor.translateAlternateColorCodes('&', "&7" + enchantment.getName() + " " + getRoman(itemMeta.getEnchantLevel(enchantment))));
                        } else if (itemMeta instanceof EnchantmentStorageMeta && !((EnchantmentStorageMeta) itemMeta).hasStoredEnchant(enchantment)) {
                            ((EnchantmentStorageMeta) itemMeta).addStoredEnchant(enchantment, fromRoman(m.group(2)), true);
                            newLoreEnchants.add(ChatColor.translateAlternateColorCodes('&', "&7" + enchantment.getName() + " " + getRoman(itemMeta.getEnchantLevel(enchantment))));
                        }
                    }
                } else {
                    newLoreGeneral.add(oldLore);
                }
            }
        }
        newLoreEnchants.addAll(newLoreGeneral);
        itemMeta.setLore(newLoreEnchants);
        itemStack.setItemMeta(itemMeta);
    }
    
    private int fromRoman(String group) {
        switch (group) {
            case "I": {
                return 1;
            }
            case "II": {
                return 2;
            }
            case "III": {
                return 3;
            }
            case "IV": {
                return 4;
            }
            case "V": {
                return 5;
            }
            case "VI": {
                return 6;
            }
            case "VII": {
                return 7;
            }
            case "VIII": {
                return 8;
            }
            case "IX": {
                return 9;
            }
            case "X": {
                return 10;
            }
            default: {
                return Integer.parseInt(group);
            }
        }
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
    
    public EnumConfiguration getEnchantmentConfiguration() {
        return ConfigurationHandler.getInstance().getConfig("enchantments_configuration");
    }
    
    public static EnchantmentsHandler getInstance() {
        return instance;
    }
}
