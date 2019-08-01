package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.core.configuration.implementation.EnumConfiguration;
import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.config.EnchantmentsConfiguration;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import com.dreamhorizon.enchantments.util.NumberUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class LevitateVictim {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "levitate_victim"),
            "Levitate Victim",
            1,
            2,
            EnchantmentTarget.WEAPON,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.BOW,
                Material.CROSSBOW
            )
        );
    }

    public static void addLevitateEffect(ItemStack attackItem, LivingEntity damaged) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.LEVITATE_VICTIM);
        double chance = NumberUtil.getRandomNumber(0, 100);
        int duration = 0;
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        if (level == 1) {
            if (chance <= Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.LEVITATE_1_CHANCE))) {
                duration =+ Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.LEVITATE_1_DURATION));
                damaged.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, duration, 0));
            }
        } else if (level == 2) {
            if (chance <= Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.LEVITATE_2_CHANCE))) {
                duration =+ Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.LEVITATE_2_DURATION));
                damaged.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, duration, 0));
            }
        }
    }
}
