package com.flow.app.Views;


public interface ListViewInterface {

    void initReceivers();

    void initListView();

    void pullRemoteData();

    void setLoadingState();

    void refreshListViewData();
}
