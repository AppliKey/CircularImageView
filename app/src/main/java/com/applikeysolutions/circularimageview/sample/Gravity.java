package com.applikeysolutions.circularimageview.sample;

public enum Gravity {
    START(8388611),
    END(8388613),
    TOP(48),
    BOTTOM(80),
    CENTER(17),
    FILL(119);

    private final int gravity;

    Gravity(int gravity) {
        this.gravity = gravity;
    }

    public int getGravity() {
        return gravity;
    }
}
