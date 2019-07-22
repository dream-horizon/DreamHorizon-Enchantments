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

package com.dreamhorizon.enchantments;

import com.dreamhorizon.core.DHCore;
import com.dreamhorizon.core.acf.BukkitCommandCompletionContext;
import com.dreamhorizon.core.acf.BukkitCommandExecutionContext;
import com.dreamhorizon.core.acf.CommandCompletions;
import com.dreamhorizon.core.acf.InvalidCommandArgument;
import com.dreamhorizon.core.acf.contexts.ContextResolver;
import com.dreamhorizon.core.commands.implementation.DHCommand;
import com.dreamhorizon.core.configuration.implementation.ConfigurationNode;
import com.dreamhorizon.core.modulation.implementation.Module;
import com.dreamhorizon.core.modulation.implementation.ModuleInfo;
import com.dreamhorizon.enchantments.command.EnchantCommand;
import com.dreamhorizon.enchantments.config.EnchantmentsConfiguration;
import com.dreamhorizon.enchantments.listener.EntityListener;
import com.dreamhorizon.enchantments.listener.InventoryListener;
import com.dreamhorizon.enchantments.objects.DHEnchantment;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Lukas Mansour
 * @since 1.0
 */
@ModuleInfo(name = "EnchantmentsModule", author = "Articdive")
public class EnchantmentsModule extends Module {
    private EnchantmentsHandler enchantmentsHandler;
    
    @Override
    public List<Listener> getListeners() {
        return Arrays.asList(new EntityListener(), new InventoryListener());
    }
    
    @Override
    public List<DHCommand> getCommands() {
        return Collections.singletonList(new EnchantCommand());
    }
    
    @Override
    public void onLoad() {
        enchantmentsHandler = EnchantmentsHandler.getInstance();
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public Map<String, Class<? extends ConfigurationNode>> getModuleConfigNodes() {
        HashMap<String, Class<? extends ConfigurationNode>> map = new HashMap<>();
        map.put("enchantments_configuration", EnchantmentsConfiguration.class);
        return map;
    }
    
    @Override
    public Map<String, CommandCompletions.CommandCompletionHandler<BukkitCommandCompletionContext>> getSyncCommandCompletions() {
        HashMap<String, CommandCompletions.CommandCompletionHandler<BukkitCommandCompletionContext>> map = new HashMap<>();
        map.put("dhenchantments", context -> DHEnchantments.all.stream().map(enchantment -> enchantment.getKey().getKey()).collect(Collectors.toList()));
        map.put("dhenchantmentlevel", context -> {
            DHEnchantment enchantment = context.getContextValue(DHEnchantment.class);
            List<String> output = new ArrayList<>();
            if (enchantment == null) {
                return output;
            }
            for (int i = enchantment.getStartLevel(); i <= enchantment.getMaxLevel(); i++) {
                output.add(String.valueOf(i));
            }
            return output;
        });
        return map;
    }
    
    @Override
    public Map<Class, ContextResolver<?, BukkitCommandExecutionContext>> getCommandContexts() {
        HashMap<Class, ContextResolver<?, BukkitCommandExecutionContext>> map = new HashMap<>();
        DHCore dhCore = DHCore.getPlugin(DHCore.class);
        map.put(DHEnchantment.class, context -> {
            String enchantName = context.popFirstArg();
            if (enchantName.trim().isEmpty()) {
                return null;
            }
            if (DHEnchantment.getByKey(new NamespacedKey(dhCore, enchantName)) instanceof DHEnchantment) {
                return DHEnchantment.getByKey(new NamespacedKey(dhCore, enchantName));
            } else {
                throw new InvalidCommandArgument("Could not find a custom enchant with that name!");
            }
        });
        return map;
    }
    
    @SuppressWarnings("unused")
    public EnchantmentsHandler getEnchantmentsHandler() {
        return enchantmentsHandler;
    }
}
