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

package com.dreamhorizon.example.config;

import com.dreamhorizon.core.configuration.implementation.ConfigurationNode;

/**
 * @author Lukas Mansour
 * @since 1.0
 */
public enum ExampleConfig implements ConfigurationNode {
    EXAMPLE_CONFIG("example", "exampleValue", "# ExampleComment");
    
    private final String path;
    private final Object defaultValue;
    private final String[] comments;
    
    ExampleConfig(String path, Object defaultValue, String... comments) {
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
