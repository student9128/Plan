package com.example.kevinjing.plan.kotlin

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.kevinjing.plan.R

/**
 * Created by Kevin on 2018/11/13<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class TestView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mColor: Int
    private var mLeft = 0
    private var mTop = 0
    private var mRight = 0
    private var mBottom = 0
    init {
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.TestView)
        mColor = typedArray.getColor(R.styleable.TestView_testBackgroundColor, Color.parseColor
        ("#65eeb7"))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mLeft = left
        mTop = top
        mRight = right
        mBottom = bottom
        Log.d("TView", "mLeft:$mLeft,mTop:$mTop,mRight:$mRight,mBottom:$mBottom")
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawColor(Color.GRAY)
        mPaint.color = mColor
        mPaint.style=Paint.Style.STROKE
        mPaint.strokeWidth=50f
        var path = Path()
//        path.lineTo(100F, 100F)
        path.lineTo(mRight.toFloat(),mBottom.toFloat())
        var round = RectF(mLeft.toFloat(), mTop.toFloat(), mRight.toFloat(), mBottom.toFloat())
        canvas.translate(0f,-1229f)
        var roundf = RectF(0f, 1229f, 700f, 1929f)
//        canvas!!.drawPath(path, mPaint)
        canvas.drawRect(roundf, mPaint)
        canvas.translate(0f,1229f)
//        canvas.drawRect(mLeft.toFloat(), mTop.toFloat(), mRight.toFloat(), mBottom.toFloat(),mPaint)

    }
}