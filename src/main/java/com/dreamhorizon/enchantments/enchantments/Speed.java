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

public class Speed {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "speed"),
            "Speed",
            "Boots",
            "Gain a speed boost when equipped",
            1,
            1,
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

    public static void applySpeedEffect(ItemStack equippedItem, LivingEntity equipper) {
        int level = equippedItem.getEnchantmentLevel(DHEnchantments.SPEED);
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        if (level == 1) {
            equipper.addPotionEffect(
                new PotionEffect(
                    PotionEffectType.SPEED,
                    99999,
                    Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SPEED_1_APLIFIER)) - 1)
            );
        } else if(level == 2) {
            equipper.addPotionEffect(
                new PotionEffect(
                    PotionEffectType.SPEED,
                    99999,
                    Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.SPEED_2_APLIFIER)) - 1)
            );
        }
    }

    public static void removeSpeedEffect(LivingEntity equipper) {
        equipper.removePotionEffect(PotionEffectType.SPEED);
    }
}
