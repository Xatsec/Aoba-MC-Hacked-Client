/*
 * Aoba Hacked Client
 * Copyright (C) 2019-2024 coltonk9043
 *
 * Licensed under the GNU General Public License, Version 3 or later.
 * See <http://www.gnu.org/licenses/>.
 */

package net.aoba.event.events;

import net.aoba.event.listeners.AbstractListener;
import net.aoba.event.listeners.SendMessageListener;

import java.util.ArrayList;
import java.util.List;

public class SendMessageEvent extends AbstractEvent {
    private String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    @Override
    public void Fire(ArrayList<? extends AbstractListener> listeners) {
        for (AbstractListener listener : List.copyOf(listeners)) {
            SendMessageListener sendMessageListener = (SendMessageListener) listener;
            sendMessageListener.onSendMessage(this);
        }
    }

    @Override
    public Class<SendMessageListener> GetListenerClassType() {
        return SendMessageListener.class;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
