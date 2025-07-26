package com.phoenixhell.app.event;

import java.net.URI;

public final class BrowseEvent extends Event {

    private final URI uri;

    public BrowseEvent(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "BrowseEvent{"
                + "uri=" + uri
                + "} " + super.toString();
    }

    public static void fire(String url) {
        com.phoenixhell.app.event.Event.publish(new BrowseEvent(URI.create(url)));
    }
}
