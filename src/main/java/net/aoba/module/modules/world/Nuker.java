/*
* Aoba Hacked Client
* Copyright (C) 2019-2023 coltonk9043
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
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Nuker Module
 */
package net.aoba.module.modules.world;

import org.lwjgl.glfw.GLFW;
import net.aoba.Aoba;
import net.aoba.core.settings.types.FloatSetting;
import net.aoba.event.events.RenderEvent;
import net.aoba.event.events.TickEvent;
import net.aoba.event.listeners.RenderListener;
import net.aoba.event.listeners.TickListener;
import net.aoba.gui.Color;
import net.aoba.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

public class Nuker extends Module implements RenderListener, TickListener {
	private MinecraftClient mc;
	
	private FloatSetting radius;

	public Nuker() {
		this.setName("Nuker");
		this.setBind(new KeyBinding("key.nuker", GLFW.GLFW_KEY_UNKNOWN, "key.categories.aoba"));
		this.setCategory(Category.World);
		this.setDescription("Destroys blocks around the player.");
		this.radius = new FloatSetting("nuker_radius", "Radius", 5f, 0f, 15f, 1f);
		this.addSetting(radius);
		mc = MinecraftClient.getInstance();
	}

	public void setRadius(int radius) {
		this.radius.setValue((double)radius);
	}

	@Override
	public void onDisable() {
		Aoba.getInstance().eventManager.RemoveListener(RenderListener.class, this);
		Aoba.getInstance().eventManager.RemoveListener(TickListener.class, this);
	}

	@Override
	public void onEnable() {
		Aoba.getInstance().eventManager.AddListener(RenderListener.class, this);
		Aoba.getInstance().eventManager.AddListener(TickListener.class, this);
	}

	@Override
	public void onToggle() {
	}

	@Override
	public void OnUpdate(TickEvent event) {
		int rad = radius.getValue().intValue();
		for (int x = -rad; x < rad; x++) {
			for (int y = rad; y > -rad; y--) {
				for (int z = -rad; z < rad; z++) {
					BlockPos blockpos = new BlockPos(mc.player.getBlockX() + x, (int) mc.player.getBlockY() + y,
							(int) mc.player.getBlockZ() + z);
					Block block = mc.world.getBlockState(blockpos).getBlock();
					if (block == Blocks.AIR)
						continue;
					
					mc.player.networkHandler.sendPacket(
							new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, blockpos, Direction.NORTH));
					mc.player.networkHandler
							.sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, blockpos, Direction.NORTH));
				}
			}
		}
	}

	@Override
	public void OnRender(RenderEvent event) {
		int rad = radius.getValue().intValue();
		for (int x = -rad; x < rad; x++) {
			for (int y = rad; y > -rad; y--) {
				for (int z = -rad; z < rad; z++) {
					BlockPos blockpos = new BlockPos(mc.player.getBlockX()+ x, mc.player.getBlockY() + y,
							mc.player.getBlockZ()+ z);
					Block block = mc.world.getBlockState(blockpos).getBlock();

					if (block == Blocks.AIR || block == Blocks.WATER || block == Blocks.LAVA)
						continue;

					this.getRenderUtils().draw3DBox(event.GetMatrixStack(), new Box(blockpos), new Color(255,0,0), 0.2f);
				}
			}
		}
	}
}
