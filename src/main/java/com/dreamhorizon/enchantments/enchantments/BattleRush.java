package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.core.configuration.implementation.EnumConfiguration;
import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.config.EnchantmentsConfiguration;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class BattleRush {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "battle_rush"),
            "Battle Rush",
            1,
            2,
            EnchantmentTarget.ARMOR_FEET,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.LEATHER_BOOTS,
                Material.IRON_BOOTS,
                Material.GOLDEN_BOOTS,
                Material.DIAMOND_BOOTS
            )
        );
    }
    
    public static void addBattleRushEffect(ItemStack attackItem, LivingEntity damager) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.BATTLE_RUSH);
        int duration = 20;
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        if (level == 1) {
            duration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.BATTLE_RUSH_1_SPEED_DURATION));
        } else if (level == 2) {
            duration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.BATTLE_RUSH_2_SPEED_DURATION));
        }
        damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 1));
    }
}
