package com.customized.libs.core.libs.limiter;

/**
 * 规定固定容量的桶, token 以固定速度往桶内填充, 当桶满时 token 不会被继续放入, 每过来一个请求把 token 从桶中移除, 如果桶中没有 token 不能请求
 */
public class TokenBucket {
    /**
     * 时间
     */
    private long time;
    /**
     * 总量
     */
    private Double total;
    /**
     * token 放入速度
     */
    private Double rate;
    /**
     * 当前总量
     */
    private Double nowSize;

    public boolean limit() {
        long now = System.currentTimeMillis();
        nowSize = Math.min(total, nowSize + (now - time) * rate);
        time = now;
        if (nowSize < 1) {
            return false;
        } else {
            nowSize -= 1;
            return true;
        }
    }

}