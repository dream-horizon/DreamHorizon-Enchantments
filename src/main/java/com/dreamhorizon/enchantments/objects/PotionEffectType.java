/*
 * DHExamplemodule
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

package com.dreamhorizon.enchantments.objects;

public class PotionEffectType {
    public enum BUFFS {
        ABSORPTION,
        DAMAGE_RESISTANCE,
        FIRE_RESISTANCE,
        INCREASE_DAMAGE,
        INVISIBILITY,
        JUMP,
        REGENERATION,
        NIGHT_VISION,
        SLOW_FALLING,
        SPEED,
        WATER_BREATHING
    }

    public enum DEBUFFS {
        BLINDNESS,
        CONFUSION,
        HUNGER,
        POISON,
        SLOW,
        WEAKNESS,
        WITHER
    }
}
