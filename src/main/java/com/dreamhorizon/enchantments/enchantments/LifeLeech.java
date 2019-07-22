package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.ArrayList;
import java.util.Arrays;

public class LifeLeech {
    public static DHEnchantment getEnchantmentInformation() {
        return new DHEnchantment(
            new NamespacedKey(DHCore.getPlugin(DHCore.class), "lifeleech"),
            "Life Leech",
            1,
            2,
            EnchantmentTarget.WEAPON,
            false,
            false,
            new ArrayList<>(),
            Arrays.asList(
                Material.WOODEN_SWORD,
                Material.STONE_SWORD,
                Material.IRON_SWORD,
                Material.GOLDEN_SWORD,
                Material.DIAMOND_SWORD
            )
        );
    }
}
