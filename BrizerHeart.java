package org.huang.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class BrizerHeart  extends View{
	private static final float C=0.551915024494f; // A constant to caculate the positon
	private Paint mPaint;
	private int mCenterX,mCenterY;
	private PointF mCenter=new PointF(0,0);
	private float mCircleRadius =200; // cirle radius
	private float mDifference =mCircleRadius*C;   // the different from control and the data
	private float [] mData =new float[8];  //data point
	private float [] mCtrl = new float[16];
	
	private float mDuration=1000;    // the total duration at the 
	private float mCurrent = 0;      // current already start duration
	private float mCount=100;
	private float mPiece=mDuration/mCount;   // per part duration
	
	
	
	public BrizerHeart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BrizerHeart(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint=new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(8);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setTextSize(60);
		
		mData[0]=0;
		mData[1]=mCircleRadius;
		
		mData[2]=mCircleRadius;
		mData[3]=0;
		
		mData[4]=0;
		mData[5]=-mCircleRadius;
		
		mData[6]=-mCircleRadius;
		mData[7]=0;
		
		
		//init the control point
		mCtrl[0]=mData[0]+mDifference;
		mCtrl[1]=mData[1];
		mCtrl[2]=mData[2];
		mCtrl[3]=mData[3]+mDifference;
		
		mCtrl[4]=mData[2];
		mCtrl[5]=mCtrl[3]-mDifference;
		
		mCtrl[6]=mData[4]+mDifference;
		mCtrl[7]=mData[5];
		
		mCtrl[8]=mData[4]-mDifference;
		mCtrl[9]=mData[5];
		
		mCtrl[10]=mData[6];
		mCtrl[11]=mData[7]-mDifference;
		
		mCtrl[12]=mData[6];
		mCtrl[13]=mData[7]+mDifference;
		
		mCtrl[14]=mData[0]-mDifference;
		mCtrl[15]=mData[1];
		
	}

	public BrizerHeart(Context context) {
		super(context);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mCenterX=w/2;
		mCenterY=h/2;
	}
	private void drawAuxiliaryLine(Canvas canvas){
		// draw data and control
		mPaint.setColor(Color.GRAY);
		mPaint.setStrokeWidth(20);
		for(int i=0;i<8;i+=2){
			canvas.drawPoint(mData[i],mData[i+1],mPaint);
		}
		for(int i=0;i<16;i+=2){
			canvas.drawPoint(mCtrl[i], mCtrl[i+1], mPaint);
		}
		mPaint.setStrokeWidth(4);
		for(int i=2,j=2;i<8;i+=2,j+=4){
			Log.i("huang","-----"+j);
			Log.i("huang","--i---"+i);
			canvas.drawLine(mData[i], mData[i+1], mCtrl[j], mCtrl[j+1], mPaint);
			canvas.drawLine(mData[i], mData[i+1], mCtrl[j+2], mCtrl[j+3], mPaint);
		}
		canvas.drawLine(mData[0], mData[1], mCtrl[0], mCtrl[1], mPaint);
		canvas.drawLine(mData[0], mData[1], mCtrl[14], mCtrl[15], mPaint);
	}
	
	private void drawCoordinateSystem(Canvas canvas){
		canvas.save();
		canvas.translate(mCenterX, mCenterY);
		canvas.scale(1, -1);  //y f
		
		Paint mPaint=new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(0, -2000, 0, 2000, mPaint);
		canvas.drawLine(-2000, 0, 2000, 0, mPaint);
		canvas.restore();
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//drawCoordinateSystem(canvas);
		canvas.translate(mCenterX, mCenterY);
		canvas.scale(1, -1);
		//drawAuxiliaryLine(canvas);
		mPaint.setColor(Color.RED);
		// draw brizer
		Path path=new Path();
		path.moveTo(mData[0], mData[1]);
		path.cubicTo(mCtrl[0],mCtrl[1],mCtrl[2],mCtrl[3],mData[2],mData[3]);
		path.cubicTo(mCtrl[4],mCtrl[5], mCtrl[6], mCtrl[7], mData[4], mData[5]);
		path.cubicTo(mCtrl[8], mCtrl[9], mCtrl[10], mCtrl[11], mData[6], mData[7]);
		path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15], mData[0], mData[1]);
		canvas.drawPath(path, mPaint);
		 mCurrent += mPiece;
	        if (mCurrent < mDuration){

	            mData[1] -= 120/mCount;
	            mCtrl[7] += 80/mCount;
	            mCtrl[9] += 80/mCount;

	            mCtrl[4] -= 20/mCount;
	            mCtrl[10] += 20/mCount;

	            postInvalidateDelayed((long) mPiece);
	        }
	}
	

}
