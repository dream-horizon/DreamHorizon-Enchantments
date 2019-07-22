package com.dreamhorizon.enchantments.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.ArrayList;
import java.util.Arrays;

public class Poison {
    public static final DHEnchantment POISON;

    public static DHEnchantment getEnchantmentInformation() {
        return POISON;
    }

    static {
        POISON = new DHEnchantment(
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
}
