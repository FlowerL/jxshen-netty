package com.github.jxshen.netty.server.callback;

/**
 * reference from: Netty In Action
 */
public class Data {

    private int n;
    private int m;
    
    public Data(int n, int m) {
        this.n = n;
        this.m = m;
    }
    
    @Override
    public String toString() {
        int r = n/m;
        return String.format("%d / %d = %d", n, m, r);
    }
}
