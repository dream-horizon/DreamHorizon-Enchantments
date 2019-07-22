package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Poison {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "poison"),
            "Poison",
            1,
            3,
            EnchantmentTarget.WEAPON,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.WOODEN_AXE, Material.WOODEN_SWORD,
                Material.STONE_AXE, Material.STONE_SWORD,
                Material.IRON_AXE, Material.IRON_SWORD,
                Material.GOLDEN_AXE, Material.GOLDEN_SWORD,
                Material.DIAMOND_AXE, Material.DIAMOND_SWORD
            )
        );
    }

    public static void addPoisonEffect(ItemStack attackItem, LivingEntity damaged) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.POISON);
        // 20 Ticks = 1 Second
        if (level == 1) {
            damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
        } else if (level == 2) {
            damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 160, 0));
        } else if (level == 3) {
            damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 240, 0));
        }
    }
}
