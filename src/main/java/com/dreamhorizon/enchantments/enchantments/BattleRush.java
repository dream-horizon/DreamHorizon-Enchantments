package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.enchantments.DHEnchantments;
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
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "cannibalism"),
            "Cannibalism",
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
        int duration = 30;
        if (level == 1) {
            duration = 60; //3 Seconds of Speed
        } else if (level == 2) {
            duration = 100; //5 Seconds of Speed
        }
        damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 0));
    }
}
