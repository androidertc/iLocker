package com.qc4w.ilocker.ui.view;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.qc4w.ilocker.ui.anim.FlowerHolder;

public class TouchArtistView extends View {

    private static final int RED = 0x00FF8080;
    private static final int BLUE = 0x008080FF;
    private float oldX;
    private float oldY;

    private float startX;
    private float startY;
    
    private OnTouchEndListener mListener;
    
    public final ArrayList<FlowerHolder> balls = new ArrayList<FlowerHolder>();
    AnimatorSet animation = null;

    public TouchArtistView(Context context, AttributeSet attrs) {
		super(context, attrs);
        ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
	}
    
    public void setListener(OnTouchEndListener listener) {
    	mListener = listener;
    }

    public int distance(float oldX, float oldY, float x, float y) {
    	float temp_A, temp_B;
    	temp_A = oldX > x ? (oldX - x) : (x - oldX);
    	temp_B = oldY > y ? (oldY - y) : (y - oldY);
    	double c = Math.sqrt(temp_A * temp_A + temp_B * temp_B);  // º∆À„
    	return (int) c;
    }
    
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
			case MotionEvent.ACTION_MOVE:
				if(distance(oldX, oldY, event.getX(), event.getY()) <= 100) {
					return false;
				}
				oldX = event.getX();
		        oldY = event.getY();
		        
		        FlowerHolder newBall = addBall(event.getX(), event.getY());
		        int duration = 500;
		        
		        ValueAnimator bounceAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0.1f);
		        bounceAnim.setDuration(duration);
		        bounceAnim.setInterpolator(new AccelerateInterpolator());
		        bounceAnim.addListener(new AnimatorListenerAdapter() {
		            @Override
		            public void onAnimationEnd(Animator animation) {
		                balls.remove(((ObjectAnimator)animation).getTarget());
		            }
		        });

		        AnimatorSet animatorSet = new AnimatorSet();
		        animatorSet.play(bounceAnim);
		        animatorSet.start();
				break;
			case MotionEvent.ACTION_UP:
				if(mListener != null) {
					int distance = distance(startX, startY, event.getX(), event.getY());
					mListener.onTouchEnd(distance);
				}
				return false;
			default:
				break;
		}
        return true;
    }

    private FlowerHolder addBall(float x, float y) {
        OvalShape circle = new OvalShape();
        circle.resize(FlowerHolder.OVAL_SIZE, FlowerHolder.OVAL_SIZE);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        FlowerHolder shapeHolder = new FlowerHolder(drawable);
        shapeHolder.setX(x - FlowerHolder.OVAL_SIZE / 2);
        shapeHolder.setY(y - FlowerHolder.OVAL_SIZE / 2);
        Paint paint = drawable.getPaint();
        paint.setColor(0x88FFFFFF);
        shapeHolder.setPaint(paint);
        balls.add(shapeHolder);
        return shapeHolder;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < balls.size(); ++i) {
            FlowerHolder shapeHolder = balls.get(i);
            canvas.save();
            canvas.translate(shapeHolder.getX(), shapeHolder.getY());
            shapeHolder.getShape().draw(canvas);
            canvas.restore();
        }
    }
    
    public static interface OnTouchEndListener {
    	public void onTouchEnd(int distance);
    }
    
}
