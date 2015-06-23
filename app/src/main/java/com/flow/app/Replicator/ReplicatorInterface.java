package com.flow.app.Replicator;

public interface ReplicatorInterface  {
    void pull();
    void pull(final String url);
    void push();
}
