package com.phoenixhell.app.event;

public final class DevToolsEvent extends Event {

    public DevToolsEvent() {
    }

    public static void fire() {
        com.phoenixhell.app.event.Event.publish(new DevToolsEvent());
    }
}
