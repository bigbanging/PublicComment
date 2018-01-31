/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView.ScaleType;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.R;

public class RotateLoadingLayout extends LoadingLayout {

	/*static final int ROTATION_ANIMATION_DURATION = 1200;

	private final Animation mRotateAnimation;
	private final Matrix mHeaderImageMatrix;

	private float mRotationPivotX, mRotationPivotY;

	private final boolean mRotateDrawableWhilePulling;*/
	int[] animId = new int[]{
			R.drawable.dropdown_anim_00,
			R.drawable.dropdown_anim_01,
			R.drawable.dropdown_anim_02,
			R.drawable.dropdown_anim_03,
			R.drawable.dropdown_anim_04,
			R.drawable.dropdown_anim_05,
			R.drawable.dropdown_anim_06,
			R.drawable.dropdown_anim_07,
			R.drawable.dropdown_anim_08,
			R.drawable.dropdown_anim_09,
			R.drawable.dropdown_anim_10,
	};

	public RotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);

//		mRotateDrawableWhilePulling = attrs.getBoolean(R.styleable.PullToRefresh_ptrRotateDrawableWhilePulling, true);

//		mHeaderImage.setScaleType(ScaleType.MATRIX);
//		mHeaderImageMatrix = new Matrix();
//		mHeaderImage.setImageMatrix(mHeaderImageMatrix);
//
//		mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//				0.5f);
//		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
//		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
//		mRotateAnimation.setRepeatCount(Animation.INFINITE);
//		mRotateAnimation.setRepeatMode(Animation.RESTART);
	}

	/**
	 * 旋转中心点
	 * @param imageDrawable
	 */
	public void onLoadingDrawableSet(Drawable imageDrawable) {
		/*if (null != imageDrawable) {
			mRotationPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);
			mRotationPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);
		}*/
	}

	/**
	 * 只要在下拉就会不断被调用
	 *
	 * 原生：随着下拉的距离，计算一个应该旋转的角度
	 * 利用Matrix计算旋转该角度时，ImageView
	 *
	 * 自定义的：随着下拉的距离，，不断的切换图片
	 * @param scaleOfLayout
	 */
	protected void onPullImpl(float scaleOfLayout) {

		int idx = (int) ((scaleOfLayout*10)+1);

		if (idx<10){
			//根据原始图片的大小创建一个缩小的图片
			Bitmap src = BitmapFactory.decodeResource(getResources(),animId[idx]);
			int width = src.getWidth()*idx/10;
			int height = src.getHeight()*idx/10;
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(src, width, height, true);
			mHeaderImage.setImageBitmap(scaledBitmap);
//			另一种思路，利用原始图片创建一个ScaleDrawable
			/*Drawable drawable = getResources().getDrawable(idx);
			drawable.setLevel(100);
			ScaleDrawable scaleDrawable = new ScaleDrawable(drawable, Gravity.CENTER,0.5f,0.5f);*/
			mHeaderImage.setImageBitmap(scaledBitmap);
		}else {
			int resId = animId[10];
			mHeaderImage.setImageResource(resId);
		}
		/*float angle;
		if (mRotateDrawableWhilePulling) {
			angle = scaleOfLayout * 90f;
		} else {
			angle = Math.max(0f, Math.min(180f, scaleOfLayout * 360f - 180f));
		}

		mHeaderImageMatrix.setRotate(angle, mRotationPivotX, mRotationPivotY);
		mHeaderImage.setImageMatrix(mHeaderImageMatrix);*/
	}

	/**
	 * 回掉方法
	 * 为ImageView启动一个补间动画
	 * 下拉松手后，进入刷新状态时被回掉
	 */
	@Override
	protected void refreshingImpl() {
//		mHeaderImage.startAnimation(mRotateAnimation);
		mHeaderImage.setImageResource(R.drawable.refresh_anim);
		Drawable drawable = mHeaderImage.getDrawable();
		((AnimationDrawable)drawable).start();
	}

	/**
	 * 刷新完毕后，头部缩起时调用
	 */
	@Override
	protected void resetImpl() {
		mHeaderImage.clearAnimation();
		resetImageRotation();
	}

	private void resetImageRotation() {
//		if (null != mHeaderImageMatrix) {
//			mHeaderImageMatrix.reset();
//			mHeaderImage.setImageMatrix(mHeaderImageMatrix);
//		}
	}

	@Override
	protected void pullToRefreshImpl() {
		// NO-OP
	}

	@Override
	protected void releaseToRefreshImpl() {
		// NO-OP
	}

	@Override
	protected int getDefaultDrawableResId() {
		return R.drawable.dropdown_anim_00;
	}

}
