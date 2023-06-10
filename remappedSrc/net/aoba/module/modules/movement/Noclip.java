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
 * Noclip Module
 */
package net.aoba.module.modules.movement;

import org.lwjgl.glfw.GLFW;
import net.aoba.module.Module;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.Packet;
import net.minecraft.util.math.Vec3d;

public class Noclip extends Module {
	private float flySpeed = 5;
	
	public Noclip() {
		this.setName("Noclip");
		this.setBind(new KeyBinding("key.noclip", GLFW.GLFW_KEY_UNKNOWN, "key.categories.aoba"));
		this.setCategory(Category.Movement);
		this.setDescription("Allows the player to clip through blocks (Only work clientside).");
	}

	public void setSpeed(float speed) {
		this.flySpeed = speed;
	}
	
	@Override
	public void onDisable() {
		MC.player.noClip = false;
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onToggle() {

	}

	@Override
	public void onUpdate() {
		ClientPlayerEntity player = MC.player;
		player.noClip = true;
		if (MC.options.sprintKey.isPressed()) {
			this.flySpeed *= 1.5;
		}
		player.setVelocity(new Vec3d(0,0,0));
		//player.setAIMoveSpeed(flySpeed * 0.2f);
		//player.jumpMovementFactor = flySpeed * 0.2f;

		Vec3d vec = new Vec3d(0,0,0);
		if (MC.options.jumpKey.isPressed()) {
			vec = new Vec3d(0,flySpeed * 0.2f,0);
		}
		if (MC.options.sneakKey.isPressed()) {
			vec = new Vec3d(0,-flySpeed * 0.2f,0);
		}
		if (MC.options.sprintKey.isPressed()) {
			this.flySpeed /= 1.5;
		}
		player.setVelocity(vec);
	}

	@Override
	public void onRender(MatrixStack matrixStack, float partialTicks) {
		
	}

	@Override
	public void onSendPacket(Packet<?> packet) {
		
	}

	@Override
	public void onReceivePacket(Packet<?> packet) {
		
		
	}
}