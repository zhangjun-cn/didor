package com.dali.didor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

public class SlideListView extends ListView {
	

	public static int MOD_FORBID = 0;

	public static int MOD_LEFT = 1;

	public static int MOD_RIGHT = 2;

	public static int MOD_BOTH = 3;

	private int mode = MOD_FORBID;

	private int leftLength = 0;

	private int rightLength = 0;
	
	private int slidePosition;

	private int downY;

	private int downX;

	private View itemView;

	private Scroller scroller;

	private int mTouchSlop;

	private boolean canMove = false;

	private boolean isSlided = false;
	
	private SlideLock slideLock;
	
	public void setSlideLock(SlideLock slideLock) {
		this.slideLock = slideLock;
	}
	
	public SlideListView(Context context) {
		this(context, null);
	}

	public SlideListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		scroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	public void initSlideMode(int mode){
		this.mode = mode;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		int[] location = new int[2];  
        this.getLocationOnScreen(location);
        System.out.println("location-->" + location[0]);
        if(location[0] > 10) {
        	return super.onTouchEvent(ev);
        }
        
        System.out.println("slideLock-->" + slideLock.getCurrent());
        if(slideLock.getCurrent() != SlideLock.NONE && slideLock.getCurrent() != SlideLock.LIST) {
        	return super.onTouchEvent(ev);
        }

		final int action = ev.getAction();
		int lastX = (int) ev.getX();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("touch-->" + "down");
			if(this.mode == MOD_FORBID){
				return super.onTouchEvent(ev);
			}
			
			if (isSlided) {
				scrollBack();
				return false;
			}
			if (!scroller.isFinished()) {
				return false;
			}
			downX = (int) ev.getX();
			downY = (int) ev.getY();

			slidePosition = pointToPosition(downX, downY);

			if (slidePosition == AdapterView.INVALID_POSITION) {
				return super.onTouchEvent(ev);
			}

			itemView = getChildAt(slidePosition - getFirstVisiblePosition());
			
			if(this.mode == MOD_BOTH){
				this.leftLength = -itemView.getPaddingLeft();
				this.rightLength = -itemView.getPaddingRight();
			}else if(this.mode == MOD_LEFT){
				this.leftLength = -itemView.getPaddingLeft();
			}else if(this.mode == MOD_RIGHT){
				this.rightLength = -itemView.getPaddingRight();
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			System.out.println("touch-->" + "move");
			
			int[] itemViewLocation = new int[2];  
			itemView.getLocationOnScreen(itemViewLocation);
	        System.out.println("location-->" + (ev.getX() - downX));
	        System.out.println("location-->>" + rightLength);
			
			if (!canMove
					&& slidePosition != AdapterView.INVALID_POSITION
					&& (Math.abs(ev.getX() - downX) > mTouchSlop && Math.abs(ev
							.getY() - downY) < mTouchSlop)) {
				int offsetX = downX - lastX;
				if(offsetX > 0 && (this.mode == MOD_BOTH || this.mode == MOD_RIGHT)){
					canMove = true;
				}else if(offsetX < 0 && (this.mode == MOD_BOTH || this.mode == MOD_LEFT)){
					canMove = true;
				}else{
					canMove = false;
				}
				MotionEvent cancelEvent = MotionEvent.obtain(ev);
				cancelEvent
						.setAction(MotionEvent.ACTION_CANCEL
								| (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
				onTouchEvent(cancelEvent);
			}
			if (canMove) {
				requestDisallowInterceptTouchEvent(true);
				
				int deltaX = downX - lastX;
				if(deltaX < 0 && (this.mode == MOD_BOTH || this.mode == MOD_LEFT)){
					itemView.scrollTo(deltaX, 0);
				}else if(deltaX > 0 && (this.mode == MOD_BOTH || this.mode == MOD_RIGHT)){
					if(downX - ev.getX() <= rightLength) {
					itemView.scrollTo(deltaX, 0);
					}
				}else{
					itemView.scrollTo(0, 0);
				}
				return true;
			}
		case MotionEvent.ACTION_UP:
			System.out.println("touch-->" + "up");
			if (canMove){
				canMove = false;
				scrollByDistanceX();
			}
			break;
		}

		return super.onTouchEvent(ev);
	}

	private void scrollByDistanceX() {
		if(this.mode == MOD_FORBID){
			return;
		}
		if(itemView.getScrollX() > 0 && (this.mode == MOD_BOTH || this.mode == MOD_RIGHT)){
			if (itemView.getScrollX() >= rightLength / 2) {
				scrollLeft();
			}  else {
				scrollBack();
			}
		}else if(itemView.getScrollX() < 0 && (this.mode == MOD_BOTH || this.mode == MOD_LEFT)){
			if (itemView.getScrollX() <= -leftLength / 2) {
				scrollRight();
			} else {
				scrollBack();
			}
		}else{
			scrollBack();
		}

	}

	private void scrollRight() {
		slideLock.setCurrent(SlideLock.LIST);
		isSlided = true;
		final int delta = (leftLength + itemView.getScrollX());
		scroller.startScroll(itemView.getScrollX(), 0, -delta, 0,
				Math.abs(delta));
		postInvalidate();
	}

	private void scrollLeft() {
		slideLock.setCurrent(SlideLock.LIST);
		isSlided = true;
		final int delta = (rightLength - itemView.getScrollX());
		scroller.startScroll(itemView.getScrollX(), 0, delta, 0,
				Math.abs(delta));
		postInvalidate();
	}

	private void scrollBack() {
		isSlided = false;
		scroller.startScroll(itemView.getScrollX(), 0, -itemView.getScrollX(),
				0, Math.abs(itemView.getScrollX()));
		postInvalidate();
		slideLock.setCurrent(SlideLock.NONE);
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());

			postInvalidate();
		}
	}

	public void slideBack() {
		this.scrollBack();
	}

}