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

package com.dreamhorizon.example;

import com.dreamhorizon.core.commands.implementation.DHCommand;
import com.dreamhorizon.core.configuration.implementation.ConfigurationNode;
import com.dreamhorizon.core.modulation.implementation.Module;
import com.dreamhorizon.core.modulation.implementation.ModuleInfo;
import com.dreamhorizon.example.command.ExampleCommand;
import com.dreamhorizon.example.config.ExampleConfig;
import com.dreamhorizon.example.listener.ExampleListener;
import com.dreamhorizon.example.object.ExamplePlayer;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lukas Mansour
 * @since 1.0
 */
@ModuleInfo(name = "ExampleModule", author = "Articdive")
public class ExampleModule extends Module {
    @Override
    public List<Listener> getListeners() {
        return Collections.singletonList((Listener) new ExampleListener());
    }
    
    @Override
    public List<DHCommand> getCommands() {
        return Collections.singletonList((DHCommand) new ExampleCommand());
    }
    
    @Override
    public void onEnable() {
        ExamplePlayer player = new ExamplePlayer();
        player.setUUID(UUID.randomUUID());
        player.save();
    }
    
    @Override
    public void onDisable() {
    
    }
    
    @Override
    public Map<String, Class<? extends ConfigurationNode>> getModuleConfigNodes() {
        HashMap<String, Class<? extends ConfigurationNode>> map = new HashMap<String, Class<? extends ConfigurationNode>>();
        map.put("examplemodule", ExampleConfig.class);
        return map;
    }
    
    public String getSchemaResourcesPath() {
        return "example_module/master.xml";
    }
    
    
    @Override
    public List<String> getSchemaProperties() {
        return Collections.singletonList("exampleplayers");
    }
}
