package com.flow.app.Replicator;

public interface AsyncTaskListener<T> {
    public void onComplete(T result);
}
