package com.flow.app.Replicator;

import android.app.ProgressDialog;

public interface AsyncTaskListener<T> {
    public void onComplete(T result,ProgressDialog progressDialog);
}
