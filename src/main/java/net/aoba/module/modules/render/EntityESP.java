/*
* Aoba Hacked Client
* Copyright (C) 2019-2024 coltonk9043
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
 * EntityESP Module
 */
package net.aoba.module.modules.render;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import net.aoba.Aoba;
import net.aoba.event.events.RenderEvent;
import net.aoba.event.events.TickEvent;
import net.aoba.event.listeners.RenderListener;
import net.aoba.event.listeners.TickListener;
import net.aoba.gui.colors.Color;
import net.aoba.misc.RenderUtils;
import net.aoba.module.Module;
import net.aoba.settings.types.BooleanSetting;
import net.aoba.settings.types.ColorSetting;
import net.aoba.settings.types.FloatSetting;
import net.aoba.settings.types.KeybindSetting;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.Box;

public class EntityESP extends Module implements RenderListener {
	private ColorSetting color_passive = new ColorSetting("entityesp_color_passive", "Passive Color",  "Passive Color", new Color(0, 1f, 1f));
	private ColorSetting color_enemies = new ColorSetting("entityesp_color_enemy", "Enemy Color", "Enemy Color", new Color(0, 1f, 1f));
	private ColorSetting color_misc = new ColorSetting("entityesp_color_misc", "Misc. Color", "Misc. Color", new Color(0, 1f, 1f));
	
	public BooleanSetting rainbow = new BooleanSetting("entityesp_rainbow", "Rainbow","Rainbow", false);
	public FloatSetting effectSpeed = new FloatSetting("entityesp_effectspeed", "Effect Speed", "Effect Speed", 4f, 1f, 20f, 0.1f);
	
	public EntityESP() {
		super(new KeybindSetting("key.entityesp", "EntityESP Key", InputUtil.fromKeyCode(GLFW.GLFW_KEY_UNKNOWN, 0)));

		this.setName("EntityESP");
		this.setCategory(Category.Render);
		this.setDescription("Allows the player to see entities with an ESP.");
		
		this.addSetting(color_passive);
		this.addSetting(color_enemies);
		this.addSetting(color_misc);
		
		this.addSetting(rainbow);
		this.addSetting(effectSpeed);
	}

	@Override
	public void onDisable() {
		Aoba.getInstance().eventManager.RemoveListener(RenderListener.class, this);
	}

	@Override
	public void onEnable() {
		Aoba.getInstance().eventManager.AddListener(RenderListener.class, this);
	}

	@Override
	public void onToggle() {

	}

	@Override
	public void OnRender(RenderEvent event) {
		Matrix4f matrix4f = event.GetMatrix().peek().getPositionMatrix();
		
		for (Entity entity : MC.world.getEntities()) {
			if (entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
				
				Box boundingBox = entity.getBoundingBox(); 
				//Vec3d offset = RenderUtils.getEntityPositionOffsetInterpolated(entity, partialTicks);
				//boundingBox = boundingBox.offset(offset);
				if (entity instanceof AnimalEntity) {
					RenderUtils.draw3DBox(matrix4f, boundingBox, color_passive.getValue());
				} else if (entity instanceof Monster) {
					RenderUtils.draw3DBox(matrix4f, boundingBox, color_enemies.getValue());
				} else {
					RenderUtils.draw3DBox(matrix4f, boundingBox, color_misc.getValue());
				}
			}
		}
	}
}
