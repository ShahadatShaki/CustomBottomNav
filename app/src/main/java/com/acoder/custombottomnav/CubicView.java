package com.acoder.custombottomnav;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import static android.graphics.Bitmap.Config.ALPHA_8;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.PorterDuff.Mode.SRC_IN;


public class CubicView extends CardView {
    private Path mPath;
    private Paint mPaint, mPaintStroke;
    private int mNavigationBarWidth;
    private int mNavigationBarActualWidth;
    private int mNavigationBarActualHeight;

    Path navBar;
    Path stroke;

    private int upperPadding = 60; //left some space in top of the view
    private int mNavigationBarHeight;
    private Point mFirstCurveStartPoint;
    private Point mFirstCurveEndPoint;
    private Point mSecondCurveStartPoint;
    private Point mSecondCurveEndPoint;
    private Point mFirstCurveControlPoint1;
    private Point mFirstCurveControlPoint2;
    private Point mSecondCurveControlPoint1;
    private Point mSecondCurveControlPoint2;
    private int CURVE_CIRCLE_RADIUS = 100;
    private Bitmap mShadow;

    private final Paint mShadowPaint = new Paint(ANTI_ALIAS_FLAG);



    public CubicView(Context context) {
        super(context);
        init();


    }

    public CubicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CubicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFirstCurveStartPoint = new Point();
        mFirstCurveEndPoint = new Point();
        mSecondCurveStartPoint = new Point();
        mSecondCurveEndPoint = new Point();

        mFirstCurveControlPoint1 = new Point();
        mFirstCurveControlPoint2 = new Point();
        mSecondCurveControlPoint1 = new Point();
        mSecondCurveControlPoint2 = new Point();

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        mPaintStroke = new Paint();
        mPaintStroke.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintStroke.setColor(0xFFE4E4E4);
        setBackgroundColor(TRANSPARENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // get width and height of navigation bar
        // Navigation bar bounds (width & height)

        mNavigationBarWidth = getWidth();
        mNavigationBarHeight = getHeight();

        navBar = cratePath(mNavigationBarHeight, mNavigationBarWidth, upperPadding+5);
        stroke = cratePath(mNavigationBarHeight, mNavigationBarWidth, upperPadding);

    }

    private Path cratePath(int mNavigationBarHeight, int mNavigationBarWidth, int upperPadding){


        int stroke = upperPadding - this.upperPadding;
        Path mPath = new Path();
        int mNavigationBarActualHeight;

        mNavigationBarActualHeight = mNavigationBarHeight;


        Point mFirstCurveStartPoint = new Point();
        Point mFirstCurveEndPoint = new Point();
        Point mSecondCurveStartPoint = new Point();
        Point mSecondCurveEndPoint = new Point();
        Point mFirstCurveControlPoint1  =new Point();
        Point mFirstCurveControlPoint2 = new Point();
        Point mSecondCurveControlPoint1 = new Point();
        Point mSecondCurveControlPoint2 = new Point();

        int totalCurbeSize = mNavigationBarWidth/3;

        // the coordinates (x,y) of the start point before curve
        mFirstCurveStartPoint.set((mNavigationBarWidth / 2) - totalCurbeSize/2, upperPadding);
//        mFirstCurveStartPoint.set((mNavigationBarWidth / 2) - mNavigationBarWidth/5, upperPadding);


        // the coordinates (x,y) of the end point after curve

        mFirstCurveEndPoint.set(mNavigationBarWidth / 2, stroke);
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint;

        mSecondCurveEndPoint.set((mNavigationBarWidth / 2) + totalCurbeSize/2, upperPadding);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mFirstCurveControlPoint1.set(mFirstCurveStartPoint.x + totalCurbeSize/4, upperPadding);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2.set(mFirstCurveStartPoint.x + totalCurbeSize/4, stroke);

        mSecondCurveControlPoint1.set(mSecondCurveStartPoint.x + (totalCurbeSize/4), stroke);
        mSecondCurveControlPoint2.set(mSecondCurveStartPoint.x + totalCurbeSize/4, upperPadding);


        mPath.reset();
        mPath.moveTo(stroke, upperPadding);
        mPath.lineTo(mFirstCurveStartPoint.x, mFirstCurveStartPoint.y);

        mPath.cubicTo(mFirstCurveControlPoint1.x, mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x, mFirstCurveControlPoint2.y,
                mFirstCurveEndPoint.x, mFirstCurveEndPoint.y);

        mPath.cubicTo(mSecondCurveControlPoint1.x, mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x, mSecondCurveControlPoint2.y,
                mSecondCurveEndPoint.x, mSecondCurveEndPoint.y);

        mPath.lineTo(mNavigationBarWidth, upperPadding);
        mPath.lineTo(mNavigationBarWidth, mNavigationBarActualHeight);
        mPath.lineTo(stroke, mNavigationBarActualHeight);
        mPath.close();

        return mPath;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(stroke, mPaintStroke);
        canvas.drawPath(navBar, mPaint);

    }


}