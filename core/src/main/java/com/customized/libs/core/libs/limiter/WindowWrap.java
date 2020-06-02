package com.customized.libs.core.libs.limiter;

/**
 * @author yan
 */
public class WindowWrap<T> {

    private long windowsLengthInMs;

    private long windowStart;

    private long value;

    public WindowWrap(long windowsLengthInMs, long windowStart, long value) {
        this.windowsLengthInMs = windowsLengthInMs;
        this.windowStart = windowStart;
        this.value = value;
    }

    public long getWindowsLengthInMs() {
        return windowsLengthInMs;
    }

    public void setWindowsLengthInMs(long windowsLengthInMs) {
        this.windowsLengthInMs = windowsLengthInMs;
    }

    public long getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(long windowStart) {
        this.windowStart = windowStart;
    }

    public long getValue() {
        return value;
    }

    public void add() {
        this.value++;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
