package uk.ac.gla.focuswatch.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import uk.ac.gla.focuswatch.R;

public class CurvedTextView extends View {
    private Path mArc;
    private String mText;
    private int mVAlign;
    private float mTextSize;

    private Paint mPaintText;

    public CurvedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributesArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CurvedText,0, 0);

        try {
            mText = attributesArray.getString(R.styleable.CurvedText_text);
            mVAlign = attributesArray.getInteger(R.styleable.CurvedText_valign, 0);
            mTextSize = attributesArray.getDimension(R.styleable.CurvedText_textSize, 10f);
        } finally {
            attributesArray.recycle();
        }

        mArc = new Path();
        // calculate where the arc needs to start
        int arcAngleMultiplier = mVAlign == 0 ? -1 : 1;
        int arcInitAngle = mVAlign == 0 ? -90 : 90;
        int fontMultiplier = mVAlign == 0 ? 4 : 3;
        int arcInitAngleDelta = arcAngleMultiplier * mText.length() * fontMultiplier; // TODO: better curve calc based on font size
        RectF oval = new RectF(30,30,290,290);
        // define arc path
        mArc.addArc(oval, arcInitAngle + arcInitAngleDelta, -1 * arcAngleMultiplier*180);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintText.setTextSize(mTextSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawTextOnPath(mText, mArc, 0, 20, mPaintText);
        invalidate();
    }
}
