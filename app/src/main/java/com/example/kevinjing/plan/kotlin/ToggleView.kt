package com.example.kevinjing.plan.kotlin

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.kevinjing.plan.R
import com.example.kevinjing.plan.util.DisplayUtils

/**
 * Created by Kevin on 2018/11/13<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ToggleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var mDefaultHeight: Int = 0
    private var mDefaultWidth: Int = 0
    private var mBackgroundColor: Int = 0
    private var mToggleColor: Int = 0

    private var mBorderColor = 0
    private var mmBorderColor = 0
    private var mBorderPaint: Paint? = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mTogglePaint: Paint? = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mToggleBorderPaint: Paint? = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBackgroundPaint: Paint? = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mLeft = 0
    private var mTop = 0
    private var mRight = 0
    private var mBottom = 0
    private var mR: Int = 0
    private var mCheckedColor = 0
    private var mUnCheckedColor = 0
    private var mBorderWidth = 0

    private var mChecked = false

    private var fraction = 0f
    private var defaultPadding: Int = 0
    private var animator: ValueAnimator
    private var mPaddingLeft = 0
    private var mPaddingTop = 0
    private var mPaddingRight = 0
    private var mPaddingBottom = 0


    companion object {
        const val TAG = "ToggleView.kt"

    }

    init {
        mBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTogglePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mToggleBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToggleView, defStyleAttr, 0)

        mBackgroundColor = typedArray.getColor(R.styleable.ToggleView_toggleBackgroundColor, 0xffffffff.toInt())
        mBorderColor = typedArray.getColor(R.styleable.ToggleView_borderColor, 0xffcacaca.toInt())
        mCheckedColor = typedArray.getColor(R.styleable.ToggleView_checkedColor, 0xff65eeb7.toInt())
        mUnCheckedColor = typedArray.getColor(R.styleable.ToggleView_unCheckedColor, 0xffffffff.toInt())
        mToggleColor = typedArray.getColor(R.styleable.ToggleView_toggleColor, 0xffffffff.toInt())
        mChecked = typedArray.getBoolean(R.styleable.ToggleView_checked, false)
        mBorderWidth = typedArray.getDimension(R.styleable.ToggleView_borderWidth, 3f).toInt()

        typedArray.recycle()


        defaultPadding = DisplayUtils.dp2px(context, 5F)
        mDefaultWidth = DisplayUtils.dp2px(context, 50F)
        mDefaultHeight = DisplayUtils.dp2px(context, 30f)

        animator = ValueAnimator.ofFloat(0f, 1f)
        startAnimation()
    }


    private fun startAnimation() {
        animator.duration = 300
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            fraction = it.animatedValue as Float
            invalidate()
        }
        animator.start()
        if (fraction >= 1) cancelAnimation()

    }

    private fun cancelAnimation() {
        if (animator.isRunning) animator.cancel()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)


    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mPaddingLeft = paddingLeft
        mPaddingTop = paddingTop
        mPaddingRight = paddingRight
        mPaddingBottom = paddingBottom

        if (mPaddingLeft <= defaultPadding) {
            mPaddingLeft = defaultPadding
        }
        if (mPaddingTop <= defaultPadding) {
            mPaddingTop = defaultPadding
        }
        if (mPaddingRight <= defaultPadding) {
            mPaddingRight = defaultPadding
        }
        if (mPaddingBottom < defaultPadding) {
            mPaddingBottom = defaultPadding
        }

        mR = Math.min(width - mPaddingLeft - mPaddingRight, height - mPaddingTop - mPaddingBottom) / 2
        mLeft = left
        mTop = top
        mRight = right
        mBottom = bottom

        Log.d(TAG, "mLeft:$mLeft,mTop:$mTop,mRight:$mRight,mBottom:$mBottom")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mmBorderColor = if (mChecked) {
            mCheckedColor
        } else {
            mBorderColor
        }
        mBackgroundColor = if (mChecked) {
            mCheckedColor
        } else {
            mUnCheckedColor
        }

        mTogglePaint!!.color = mToggleColor


        var round = RectF((mLeft + mPaddingLeft).toFloat(), (mTop + mPaddingTop).toFloat(), (mRight - mPaddingRight).toFloat(), (mBottom - mPaddingBottom).toFloat())
        var roundBorder = RectF((mLeft + mPaddingLeft + mBorderWidth / 2).toFloat(), (mTop + mPaddingTop + mBorderWidth / 2).toFloat(),
                (mRight - mPaddingRight - mBorderWidth / 2).toFloat(), (mBottom - mPaddingBottom - mBorderWidth / 2).toFloat())

        canvas!!.translate((-mLeft).toFloat(), -mTop.toFloat())
        canvas.drawRoundRect(round, mR.toFloat(), mR.toFloat(), mBackgroundPaint)
//
        mBorderPaint!!.color = mmBorderColor
        mBorderPaint!!.style = Paint.Style.STROKE
        mBorderPaint!!.strokeWidth = mBorderWidth.toFloat()
        canvas.drawRoundRect(roundBorder, mR.toFloat(), mR.toFloat(), mBorderPaint)

        mToggleBorderPaint!!.color = mmBorderColor
        mToggleBorderPaint!!.style = Paint.Style.STROKE
        mToggleBorderPaint!!.strokeWidth = mBorderWidth.toFloat()

        mBackgroundPaint!!.color = mBorderColor
        val moveWidth = width - mPaddingRight - mPaddingLeft - mR * 2
        val cy = (mTop + mBottom + mPaddingTop - mPaddingBottom) / 2
        val cxLeft = mLeft + mPaddingLeft + mR
        val cxRight = mRight - mPaddingRight - mR
        val argbEvaluator = ArgbEvaluator()
        if (mChecked) {
            val evaluate = argbEvaluator.evaluate(fraction, mUnCheckedColor, mCheckedColor)
            mBackgroundPaint!!.color = evaluate as Int
            canvas.drawRoundRect(round, mR.toFloat(), mR.toFloat(), mBackgroundPaint)
            canvas.drawRoundRect(roundBorder, mR.toFloat(), mR.toFloat(), mBorderPaint)
            canvas.drawCircle((cxLeft + fraction * moveWidth), cy.toFloat(), mR.toFloat(), mTogglePaint)
            canvas.drawCircle((cxLeft + fraction * moveWidth), cy.toFloat(), (mR-mBorderWidth/2).toFloat(), mToggleBorderPaint)
        } else {
            val evaluate = argbEvaluator.evaluate(fraction, mCheckedColor, mUnCheckedColor)
            mBackgroundPaint!!.color = evaluate as Int
            canvas.drawRoundRect(round, mR.toFloat(), mR.toFloat(), mBackgroundPaint)
            canvas.drawRoundRect(roundBorder, mR.toFloat(), mR.toFloat(), mBorderPaint)
            canvas.drawCircle((cxRight - fraction * moveWidth), cy.toFloat(), mR.toFloat(), mTogglePaint)
            canvas.drawCircle((cxRight - fraction * moveWidth), cy.toFloat(), (mR-mBorderWidth/2).toFloat(), mToggleBorderPaint)

        }
        canvas.translate(mLeft.toFloat(), mTop.toFloat())

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_UP -> {
                mChecked = !mChecked
                requestLayout()
                if (listener != null) {
                    listener!!.onToggleClick(mChecked)
                }
                startAnimation()
            }
        }
        invalidate()
        return true
    }

    public interface OnToggleClickListener {
        fun onToggleClick(toggleState: Boolean)
    }

    private var listener: OnToggleClickListener? = null

    public fun setOnToggleClickListener(listener: OnToggleClickListener) {
        this.listener = listener
    }

    private fun measureHeight(measureSpec: Int): Int {
        var result = mDefaultHeight
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.UNSPECIFIED ->  Math.min(result, specSize)
            MeasureSpec.AT_MOST -> result = Math.min(result, specSize)
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result

    }

    private fun measureWidth(measureSpec: Int): Int {
        var result = mDefaultWidth
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.UNSPECIFIED -> result = specSize
            MeasureSpec.AT_MOST -> result = Math.min(result, specSize)
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result

    }

    public fun getBorderWidth(): Int {
        return mBorderWidth
    }

    public fun setBorderWidth(mBorderWidth: Int) {
        this.mBorderWidth = mBorderWidth
    }

    public fun getToggleBackgroundColor(): Int {
        return mBackgroundColor
    }

    public fun setToggleBackgroundColor(mBackgroundColor: Int) {
        this.mBackgroundColor = mBackgroundColor
    }

    public fun getToggleColor(): Int {
        return mToggleColor
    }

    public fun setToggleColor(mToggleColor: Int) {
        this.mToggleColor = mToggleColor
    }

    public fun getBorderColor(): Int {
        return mBorderColor
    }

    public fun setBorderColor(mBorderColor: Int) {
        this.mBorderColor = mBorderColor
    }

    public fun getCheckedColor(): Int {
        return mCheckedColor
    }

    public fun setCheckedColor(mCheckedColor: Int) {
        this.mCheckedColor = mCheckedColor
    }

    public fun getUnCheckedColor(): Int {
        return mUnCheckedColor
    }

    public fun setUnCheckedColor(mUnCheckedColor: Int) {
        this.mUnCheckedColor = mUnCheckedColor
    }

    public fun isChecked(): Boolean {
        return mChecked
    }

    public fun setChecked(mChecked: Boolean) {
        this.mChecked = mChecked
    }


}