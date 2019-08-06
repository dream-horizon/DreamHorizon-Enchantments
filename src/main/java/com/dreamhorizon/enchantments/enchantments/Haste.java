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

public class Haste {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "haste"),
            "Haste",
            1,
            2,
            EnchantmentTarget.TOOL,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.WOODEN_PICKAXE,
                Material.STONE_PICKAXE,
                Material.IRON_PICKAXE,
                Material.GOLDEN_PICKAXE,
                Material.DIAMOND_PICKAXE,
                Material.WOODEN_SHOVEL,
                Material.STONE_SHOVEL,
                Material.IRON_SHOVEL,
                Material.GOLDEN_SHOVEL,
                Material.DIAMOND_SHOVEL
            )
        );
    }

    public static void applyHasteEffect(ItemStack equippedItem, LivingEntity equipper) {
        int level = equippedItem.getEnchantmentLevel(DHEnchantments.HASTE);
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        if (level == 1) {
            equipper.addPotionEffect(
                new PotionEffect(
                    PotionEffectType.FAST_DIGGING,
                    99999,
                    Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.HASTE_1_APLIFIER)) - 1)
            );
        }
    }

    public static void removeHasteEffect(LivingEntity equipper) {
        equipper.removePotionEffect(PotionEffectType.FAST_DIGGING);
    }
}
