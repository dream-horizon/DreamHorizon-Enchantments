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

public class Starve {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "starve"),
            "Starve",
            1,
            1,
            EnchantmentTarget.WEAPON,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.WOODEN_SWORD,
                Material.STONE_SWORD,
                Material.IRON_SWORD,
                Material.GOLDEN_SWORD,
                Material.DIAMOND_SWORD,
                Material.WOODEN_AXE,
                Material.STONE_AXE,
                Material.IRON_AXE,
                Material.GOLDEN_AXE,
                Material.DIAMOND_AXE
            )
        );
    }

    public static void addStarveEffect(ItemStack attackItem, LivingEntity damager) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.STARVE);
        int duration = 0;
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        if (level == 1) {
            duration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.STARVE_1_DURATION));
        } else if (level == 2) {
            duration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.STARVE_2_DURATION));
        }
        damager.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, duration, 0));
    }

}
