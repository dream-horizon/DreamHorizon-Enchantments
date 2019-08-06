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

public class BattleRage {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "battle_rage"),
            "Battle Rage",
            "Axes",
            "Gain additional strength when killing an enemy",
            1,
            2,
            EnchantmentTarget.WEAPON,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.WOODEN_AXE,
                Material.STONE_AXE,
                Material.IRON_AXE,
                Material.GOLDEN_AXE,
                Material.DIAMOND_AXE
            )
        );
    }

    public static void addBattleRageEffect(ItemStack attackItem, LivingEntity damager) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.BATTLE_RAGE);
        int duration = 20;
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        if (level == 1) {
            duration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.BATTLE_RAGE_1_SPEED_DURATION));
        } else if (level == 2) {
            duration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.BATTLE_RAGE_2_SPEED_DURATION));
        }
        damager.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration, 0));
    }
}
