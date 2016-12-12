package org.huang.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.togglebutton.R;

public class CheckView extends View
{
	private static final int anim_null=0;
	private static final int anim_check=2;
	private static final int anim_nucheck=3;
	private Paint mPaint;
	private  Context mContext;
	private  int mWidth,mHeight;
	private Handler mHandler;
	private Bitmap mOkBitmap;
	private int animCurrentPage=-1;
	private int animaxPage=13;
	private int animDuration=150;  // the duraton of 
	private int animState=anim_null;
	private boolean isCheck=false;  //to judge if the state is checked
	
	public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CheckView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init( context);
	}

	public CheckView(Context context) {
		super(context);
	}
	
	public void init(Context context){
		mContext =context;
		mPaint =new Paint();
		mPaint.setColor(0xffFF5317);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
		// here the picture id you  must put your self
		mOkBitmap =BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	    mHandler=new Handler(){
	    	@Override
	    	public void handleMessage(Message msg) {
	    		super.handleMessage(msg);
	    		if(animCurrentPage<animaxPage||animCurrentPage>=0){
	    			// re draw
	    			invalidate();
	    			if(animState==anim_null){
	    				return ;
	    			}
	    			if(animState==anim_check){
	    				animCurrentPage++;
	    			}
	    			if(animState==anim_nucheck){
	    				animCurrentPage--;
	    			}
	    			this.sendEmptyMessageDelayed(0, animDuration/animaxPage);
	    			Log.i("huangdakang","---count----"+animCurrentPage);
	    		}else{
	    			if(isCheck){
	    				animCurrentPage=animaxPage-1;
	    			}else{
	    				animCurrentPage=-1;
	    			}
	    			invalidate();
	    			animState=anim_null;
	    		}
	    	}
	    };
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth=w;
		mHeight=h;
				
	}
	
	// the main think is to cut the img part to part
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//translate to center
		canvas.translate(mWidth/2, mHeight/2);
		
		//draw the background circle
		canvas.drawCircle(0, 0, 240, mPaint);
		
		//get the length
		int sideLength=mOkBitmap.getHeight();
		
		// GET the img
		Rect src=new Rect(sideLength*animCurrentPage,0,
				sideLength*(animCurrentPage+1),sideLength);
		Rect dst=new Rect(-200,-200,200,200);
		//redraw
		canvas.drawBitmap(mOkBitmap, src, dst,mPaint);
	}
	// to cansel check
	public void unCheck(){
		// state is not right
		if(animState!=anim_null||animState==anim_nucheck){
			return;
		}
		animState=anim_nucheck;
		animCurrentPage=animaxPage- 1;
		// to handler to redraw
		mHandler.sendEmptyMessageDelayed(0, animDuration/animaxPage);
		isCheck=false;
	}
	
	// to set the check state to checked
	public void check(){
		if(animState==anim_null||animState==anim_check){
			return;
		}
		animState=anim_check;
		animCurrentPage=0;
		mHandler.sendEmptyMessageDelayed(0,animDuration/animaxPage);
		isCheck=true;
	}
	
	// to set Anmiduration
	public void setAnimDuration(int animDration){
		if(animDuration<=0){
			return;
		}
		this.animDuration=animDration;
	}
	
	
	// to set color
	public void setBackgroundColor(int color){
		mPaint.setColor(color);
	}
	
	

}
