package net.aoba.gui.hud;

import net.aoba.gui.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public class InfoHud extends AbstractHud {

	String positionText = "";
	String timeText = "";
	String fpsText = "";
	
	// 
	public InfoHud(int x, int y) {
		super("InfoHud", x, y, 190, 60);
	}
	
	@Override
	public void update(double mouseX, double mouseY, boolean mouseClicked) {
		super.update(mouseX, mouseY, mouseClicked);
		
		MinecraftClient mc = MinecraftClient.getInstance();

		int time = ((int)mc.world.getTime() + 6000)% 24000;
		String suffix = time >= 12000 ? "PM" : "AM";
		String timeString = (time / 10) % 1200 + "";
		for (int n = timeString.length(); n < 4; ++n) {
			timeString = "0" + timeString;
        }
		final String[] strsplit = timeString.split("");
		String hours = strsplit[0] + strsplit[1];
		if(hours.equalsIgnoreCase("00")) {
			hours = "12";
		}
		final int minutes = (int)Math.floor(Double.parseDouble(strsplit[2] + strsplit[3]) / 100.0 * 60.0);
		String sm = minutes + "";
        if (minutes < 10) {
            sm = "0" + minutes;
        }
		timeString = hours + ":" + sm.charAt(0) + sm.charAt(1) + suffix;
		positionText = "XYZ: " + (int)mc.player.getBlockX() + ", " + (int)mc.player.getBlockY() + ", " + (int)mc.player.getBlockZ();
		timeText = "Time: " + timeString;
		fpsText = "FPS: " + mc.fpsDebugString.split(" ", 2)[0] + " Day: " + (int) (mc.world.getTime() / 24000);
		
		int newWidth = (int)(mc.textRenderer.getWidth(positionText) * 2) + 20;
		if(this.getWidth()!= newWidth) {
			if(newWidth >= 190) {
				this.setWidth(newWidth);
			}else {
				this.setWidth(190);
			}
		}
	}

	@Override
	public void draw(DrawContext drawContext, float partialTicks, Color color) {
		MatrixStack matrixStack = drawContext.getMatrices();
		// Draws background depending on components width and height
		renderUtils.drawRoundedBox(matrixStack, x, y, width, height, 6, new Color(30,30,30), 0.4f);
		renderUtils.drawRoundedOutline(matrixStack, x, y, width, height, 6, new Color(0,0,0), 0.8f);
		
		renderUtils.drawString(drawContext, positionText, x + 5, y + 4, color);
		renderUtils.drawString(drawContext, timeText, x + 5, y + 24, color);
		renderUtils.drawString(drawContext, fpsText, x + 5, y + 44, color);
	}
}
