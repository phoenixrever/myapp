/* SPDX-License-Identifier: MIT */

package com.phoenixhell.app.fake.domain;

import javafx.beans.property.SimpleStringProperty;

public final class Metric {

    private final SimpleStringProperty queries = new SimpleStringProperty("");
    private final SimpleStringProperty cacheHitRate = new SimpleStringProperty("");
    private final SimpleStringProperty latency = new SimpleStringProperty("");
    private final SimpleStringProperty requests = new SimpleStringProperty("");

    public Metric() {
    }

    public Metric(String string, String string2, String string3, String string4) {
        this.queries.set(string);
        this.cacheHitRate.set(string2);
        this.latency.set(string3);
        this.requests.set(string4);
    }

    public String getQueries() {
        return queries.get();
    }

    public SimpleStringProperty queriesProperty() {
        return queries;
    }

    public void setQueries(String queries) {
        this.queries.set(queries);
    }

    public String getCacheHitRate() {
        return cacheHitRate.get();
    }

    public SimpleStringProperty cacheHitRateProperty() {
        return cacheHitRate;
    }

    public void setCacheHitRate(String cacheHitRate) {
        this.cacheHitRate.set(cacheHitRate);
    }

    public String getLatency() {
        return latency.get();
    }

    public SimpleStringProperty latencyProperty() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency.set(latency);
    }

    public String getRequests() {
        return requests.get();
    }

    public SimpleStringProperty requestsProperty() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests.set(requests);
    }
}
