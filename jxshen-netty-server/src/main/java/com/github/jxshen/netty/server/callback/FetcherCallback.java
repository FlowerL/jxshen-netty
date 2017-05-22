package com.github.jxshen.netty.server.callback;

/**
 * reference from: Netty In Action
 */
public interface FetcherCallback {

    void onData(Data data) throws Exception;
    void onError(Throwable cause);
}
