/*
 * DHEnchantments
 * Copyright (C) 2019 Dream Horizon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.dreamhorizon.enchantments;

import com.dreamhorizon.enchantments.enchantments.*;
import com.dreamhorizon.enchantments.objects.DHEnchantment;

import java.util.ArrayList;
import java.util.List;

public class DHEnchantments {
    public static final List<DHEnchantment> all = new ArrayList<>();
    public static final DHEnchantment POISON = Poison.getEnchantmentInformation();
    public static final DHEnchantment LIFE_LEECH = LifeLeech.getEnchantmentInformation();
    public static final DHEnchantment EXHAUST = Exhaust.getEnchantmentInformation();
    public static final DHEnchantment MILKY = Milky.getEnchantmentInformation();
    public static final DHEnchantment CANNIBALISM = Cannibalism.getEnchantmentInformation();
    public static final DHEnchantment BATTLE_RUSH = BattleRush.getEnchantmentInformation();
    public static final DHEnchantment SKILL_SWIPE = SkillSwipe.getEnchantmentInformation();
    public static final DHEnchantment HASTE = Haste.getEnchantmentInformation();
    public static final DHEnchantment SPEED = Speed.getEnchantmentInformation();
    public static final DHEnchantment BATTLE_RAGE = BattleRage.getEnchantmentInformation();
    
    static {
        all.add(POISON);
        all.add(LIFE_LEECH);
        all.add(MILKY);
        all.add(EXHAUST);
        all.add(CANNIBALISM);
        all.add(BATTLE_RUSH);
        all.add(SKILL_SWIPE);
        all.add(HASTE);
        all.add(SPEED);
        all.add(BATTLE_RAGE);
    }
}
