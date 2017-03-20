package com.applikeysolutions.circularimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;

import com.deniskolesnik.circleimageview.R;

import java.util.ArrayList;
import java.util.List;

public class CircularImageView extends ImageView {

    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_SECTOR_ANGLE = 360;

    private List<Drawable> mIcons = new ArrayList<>();
    private int mAngleOffset = DEFAULT_OFFSET;
    private int mSectorAngle = DEFAULT_SECTOR_ANGLE;
    private int mGravity = Gravity.FILL;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttr(attrs, 0, 0);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttr(attrs, defStyleAttr, 0);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        obtainAttr(attrs, defStyleAttr, defStyleRes);
    }

    @SuppressWarnings("WrongConstant")
    private void obtainAttr(AttributeSet attributeSet, int defStyleAttr, int defStyleRes) {
        final TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet,
                R.styleable.CircularImageView, defStyleAttr, defStyleRes);
        try {
            mGravity = typedArray.getInteger(R.styleable.CircularImageView_gravity, Gravity.FILL);
            mAngleOffset = typedArray.getInteger(R.styleable.CircularImageView_offsetAngle, DEFAULT_OFFSET);
            mSectorAngle = typedArray.getInteger(R.styleable.CircularImageView_sectorAngle, DEFAULT_SECTOR_ANGLE);
        } finally {
            typedArray.recycle();
        }
    }

    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        mGravity = gravity;
        invalidate();
    }

    public List<Drawable> getIcons() {
        return mIcons;
    }

    public void setIcons(List<Drawable> icons) {
        mIcons = icons;
        calculatePadding();
        invalidate();
    }

    public void addIcon(Drawable drawable) {
        mIcons.add(drawable);
        calculatePadding();
        invalidate();
    }

    public int getAngleOffset() {
        return mAngleOffset;
    }

    public void setAngleOffset(int angleOffset) {
        mAngleOffset = angleOffset;
        invalidate();
    }

    public int getSectorAngle() {
        return mSectorAngle;
    }

    public void setSectorAngle(int sectorAngle) {
        mSectorAngle = sectorAngle;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIcons.size() > 0) {
            float dAngle = ((float) mSectorAngle) / (mIcons.size());
            for (int i = 0; i < mIcons.size(); i++) {

                final Drawable icon = mIcons.get(i);
                canvas.save();

                final int iconHeight = icon.getIntrinsicHeight();
                final int iconWidth = icon.getIntrinsicWidth();

                float angle = mAngleOffset + dAngle * i;
                final int height = getEllipseHeight(mGravity, iconHeight);
                final int width = getEllipseWidth(mGravity, iconWidth);

                final int xOffset = getXOffset(mGravity, height, iconHeight);
                final int yOffset = getYOffset(mGravity, width, iconWidth);

                final int top = (int) (xOffset + height / 2 * Math.cos(Math.toRadians(angle)));
                final int left = (int) (yOffset + -width / 2 * Math.sin(Math.toRadians(angle)));

                final int right = left + iconWidth;
                final int bottom = top + iconHeight;

                icon.setBounds(left, top, right, bottom);
                icon.draw(canvas);

                canvas.restore();
            }
        }
    }

    private int getEllipseHeight(int gravity, int iconHeight) {
        final int fixedHeight;
        if (gravity == android.view.Gravity.FILL) {
            fixedHeight = getHeight() - iconHeight;
        } else {
            fixedHeight = getSize() - iconHeight;
        }
        return fixedHeight;
    }

    private int getEllipseWidth(int gravity, int iconWidth) {
        final int fixedWidth;
        if (gravity == android.view.Gravity.FILL) {
            fixedWidth = getWidth() - iconWidth;
        } else {
            fixedWidth = getSize() - iconWidth;
        }
        return fixedWidth;
    }

    private int getXOffset(int gravity, int height, int iconHeight) {
        final int xRadius;
        if (gravity == android.view.Gravity.BOTTOM) {
            xRadius = getHeight() - height / 2 - iconHeight;
        } else if (gravity == Gravity.CENTER) {
            xRadius = (getHeight() - iconHeight) / 2;
        } else {
            xRadius = height / 2;
        }
        return xRadius;
    }

    private int getYOffset(int gravity, int width, int iconWidth) {
        final int yRadius;
        if (gravity == android.view.Gravity.CENTER) {
            yRadius = (getWidth() - iconWidth) / 2;
        } else if (gravity == android.view.Gravity.END) {
            yRadius = getWidth() - width / 2 - iconWidth;
        } else {
            yRadius = width / 2;
        }
        return yRadius;
    }

    private int getSize() {
        return Math.min(getWidth(), getHeight());
    }

    private void calculatePadding() {
        int padding = 0;
        for (Drawable item : mIcons) {
            int max = Math.max(item.getIntrinsicHeight(), item.getIntrinsicWidth());
            if (max > padding) {
                padding = max;
            }
        }
        padding /= 2;
        setPadding(padding, padding, padding, padding);
    }
}
