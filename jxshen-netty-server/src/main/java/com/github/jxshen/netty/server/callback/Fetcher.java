package com.github.jxshen.netty.server.callback;

/**
 * reference from: Netty In Action
 */
public interface Fetcher {

    void fetchData(FetcherCallback callback);
}
