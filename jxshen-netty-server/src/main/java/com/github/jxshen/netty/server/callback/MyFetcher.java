package com.github.jxshen.netty.server.callback;

/**
 * reference from: Netty In Action
 */
public class MyFetcher implements Fetcher {

    final Data data;
    
    public MyFetcher(Data data) {
        this.data = data;
    }
    
    @Override
    public void fetchData(FetcherCallback callback) {
        try {
            callback.onData(data);
        } catch (Exception e) {
            callback.onError(e);
        }
    }

}
