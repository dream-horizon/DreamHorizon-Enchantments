package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.core.configuration.implementation.EnumConfiguration;
import com.dreamhorizon.enchantments.DHEnchantments;
import com.dreamhorizon.enchantments.EnchantmentsHandler;
import com.dreamhorizon.enchantments.config.EnchantmentsConfiguration;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class Cannibalism {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "cannibalism"),
            "Cannibalism",
            1,
            2,
            EnchantmentTarget.ARMOR_HEAD,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.LEATHER_HELMET,
                Material.IRON_HELMET,
                Material.GOLDEN_HELMET,
                Material.DIAMOND_HELMET
            )
        );
    }
    
    public static void addCannibalismEffect(ItemStack attackItem, LivingEntity damaged, LivingEntity damager) {
        int level = attackItem.getEnchantmentLevel(DHEnchantments.EXHAUST);
        AttributeInstance healthAttribute = damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (healthAttribute == null) {
            return;
        }
        EnumConfiguration enchantmentsConfig = EnchantmentsHandler.getInstance().getEnchantmentConfiguration();
        double playersCurrentHealth = damager.getHealth();
        double healthToGain = 0;
        int saturationDuration = 0;
        if (level == 1) {
            healthToGain = (healthAttribute.getDefaultValue() / 100) * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.CANNIBALISM_1_HEALTH)); //10% of mob's health as life
            saturationDuration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.CANNIBALISM_1_SATURATION_DURATION));
        } else if (level == 2) {
            healthToGain = (healthAttribute.getDefaultValue() / 100) * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.CANNIBALISM_2_HEALTH)); //10% of mob's health as life
            saturationDuration = 20 * Integer.parseInt((String) enchantmentsConfig.get(EnchantmentsConfiguration.CANNIBALISM_2_SATURATION_DURATION));
        }
        
        double healthToSetTo = healthToGain + playersCurrentHealth;
        
        if (healthToSetTo > healthAttribute.getDefaultValue()) {
            damager.setHealth(healthAttribute.getValue());
        } else {
            damager.setHealth(damager.getHealth() + healthToGain);
        }
        
        damager.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, saturationDuration, 0)); //Add food to user
    }
}
