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

package net.aoba.gui.navigation;

import net.aoba.Aoba;
import net.aoba.event.events.MouseClickEvent;
import net.aoba.event.events.MouseMoveEvent;
import net.aoba.event.listeners.MouseClickListener;
import net.aoba.event.listeners.MouseMoveListener;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Page implements MouseMoveListener, MouseClickListener{
    protected String title;
    protected List<Window> tabs = new ArrayList<Window>();

    private boolean isVisible;

    public Page(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void AddWindow(Window hud) {
    	hud.parent = this;
        tabs.add(hud);
    }
    
    public void RemoveWindow(Window hud) {
    	hud.parent = null;
    	tabs.remove(hud);
    }

    public void setVisible(boolean state) {
        this.isVisible = state;
        
        if(isVisible) {
        	Aoba.getInstance().eventManager.AddListener(MouseMoveListener.class, this);
        	Aoba.getInstance().eventManager.AddListener(MouseClickListener.class, this);
        }else {
        	Aoba.getInstance().eventManager.RemoveListener(MouseMoveListener.class, this);
        	Aoba.getInstance().eventManager.RemoveListener(MouseClickListener.class, this);
        }
        
        for (Window hud : tabs) {
        	hud.setVisible(state);
        }
    }

    public void update() {
        if (this.isVisible) {
            Iterator<Window> tabIterator = tabs.iterator();
            while (tabIterator.hasNext()) {
                tabIterator.next().update();
            }
        }
    }

    public void render(DrawContext drawContext, float partialTicks) {
        if (this.isVisible) {
            Iterator<Window> tabIterator = tabs.iterator();
            while (tabIterator.hasNext()) {
                tabIterator.next().draw(drawContext, partialTicks);
            }
        }
    }
    
    public void moveToFront(Window window) {
    	if(tabs.size() > 1) {
    		Window temp = tabs.get(tabs.size() - 1);
    		int indexOfWindow = tabs.indexOf(window);
    		tabs.set(indexOfWindow, temp);
    		tabs.set(tabs.size() - 1, window);
    	}
    }

	@Override
	public void OnMouseMove(MouseMoveEvent mouseMoveEvent) {
		if(Aoba.getInstance().hudManager.isClickGuiOpen()) {
			tabs.reversed().stream().collect(Collectors.toList()).forEach(s -> s.OnMouseMove(mouseMoveEvent));
		}
	}

	@Override
	public void OnMouseClick(MouseClickEvent mouseClickEvent) {
		if(Aoba.getInstance().hudManager.isClickGuiOpen()) {
			tabs.reversed().stream().collect(Collectors.toList()).forEach(s -> s.OnMouseClick(mouseClickEvent));
		}
	}
}
