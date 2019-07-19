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

package com.dreamhorizon.example.command;


import com.dreamhorizon.core.acf.annotation.CommandAlias;
import com.dreamhorizon.core.acf.annotation.Default;
import com.dreamhorizon.core.acf.annotation.Subcommand;
import com.dreamhorizon.core.commands.implementation.DHCommand;
import org.bukkit.entity.Player;

/**
 * @author Lukas Mansour
 * @since 1.0
 */
@CommandAlias("test")
public class ExampleCommand extends DHCommand {
    @Default
    @Subcommand("test")
    public static void onTest(Player player) {
        player.sendMessage("Test Command!");
    }
}
