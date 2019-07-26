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

package com.dreamhorizon.enchantments.config;

import com.dreamhorizon.core.configuration.implementation.ConfigurationNode;

public enum EnchantmentsConfiguration implements ConfigurationNode {
    CANNIBALISM_1_HEALTH("cannibalism_1_health", "10", "# Percentage of mob max health regained."),
    CANNIBALISM_2_HEALTH("cannibalism_2_health", "15", "# Percentage of mob max health regained."),
    CANNIBALISM_1_SATURATION_DURATION("cannibalism_1_saturation_duration", "2", "# Duration of the saturation effect (In seconds)."),
    CANNIBALISM_2_SATURATION_DURATION("cannibalism_2_saturation_duration", "2", "# Duration of the saturation effect (In seconds)."),
    EXHAUST_1_SLOW_CHANCE("exhaust_1_slow_chance", "2", "# Percentage chance of slowing an entity."),
    EXHAUST_2_SLOW_CHANCE("exhaust_2_slow_chance", "4", "# Percentage chance of slowing an entity."),
    EXHAUST_3_SLOW_CHANCE("exhaust_3_slow_chance", "6", "# Percentage chance of slowing an entity."),
    EXHAUST_1_SLOW_DURATION("exhaust_1_slow_duration", "2", "# Duration of the slow effect (In seconds)."),
    EXHAUST_2_SLOW_DURATION("exhaust_2_slow_duration", "2", "# Duration of the slow effect (In seconds)."),
    EXHAUST_3_SLOW_DURATION("exhaust_3_slow_duration", "2", "# Duration of the slow effect (In seconds)."),
    LIFE_LEECH_1_HEALTH("life_leech_1_health", "10", "# Percentage of attack damage regained."),
    LIFE_LEECH_2_HEALTH("life_leech_2_health", "20", "# Percentage of attack damage regained."),
    POISON_1_POISON_DURATION("poison_1_poison_duration", "5", "# Duration of the poison effect (In seconds)."),
    POISON_2_POISON_DURATION("poison_2_poison_duration", "8", "# Duration of the poison effect (In seconds)."),
    POISON_3_POISON_DURATION("poison_3_poison_duration", "12", "# Duration of the poison effect (In seconds)."),
    MILKY_1_CLEANSE_CHANCE("milky_1_cleanse_chance", "25", "# Percentage chance to remove (cleanse) all debuffs."),
    MILKY_2_CLEANSE_CHANCE("milky_2_cleanse_chance", "35", "# Percentage chance to remove (cleanse) all debuffs."),
    BATTLE_RUSH_1_SPEED_DURATION("battle_rush_1_speed_duration", "3", "# Duration of the speed effect on enemy death (In seconds)."),
    BATTLE_RUSH_2_SPEED_DURATION("battle_rush_2_speed_duration", "5", "# Duration of the speed effect on enemy death (In seconds)."),
    SKILL_SWIPE_1_STEAL_CHANCE("skill_swipe_1_steal_chance", "10", "# Chance to steal experience for level 1 enchantment."),
    SKILL_SWIPE_2_STEAL_CHANCE("skill_swipe_2_steal_chance", "15", "# Chance to steal experience for level 2 enchantment."),
    SKILL_SWIPE_3_STEAL_CHANCE("skill_swipe_3_steal_chance", "20", "# Chance to steal experience for level 3 enchantment."),
    SKILL_SWIPE_1_EXPERIENCE_STEAL("skill_swipe_1_experience_steal", "15", "# Amount of experience to steal for level 1 enchantment."),
    SKILL_SWIPE_2_EXPERIENCE_STEAL("skill_swipe_2_experience_steal", "30", "# Amount of experience to steal for level 2 enchantment"),
    SKILL_SWIPE_3_EXPERIENCE_STEAL("skill_swipe_3_experience_steal", "60", "# Amount of experience to steal for level 3 enchantment");
    
    private final String path;
    private final Object defaultValue;
    private final String[] comments;
    
    EnchantmentsConfiguration(String path, Object defaultValue, String... comments) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.comments = comments;
    }
    
    public String getPath() {
        return path;
    }
    
    public Object getDefaultValue() {
        return defaultValue;
    }
    
    public String[] getComments() {
        return comments;
    }
}
