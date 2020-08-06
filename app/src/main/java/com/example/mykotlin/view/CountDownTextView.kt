package com.example.mykotlin.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatTextView
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.example.mykotlin.R


/**
 * Created by zlp on 2020/7/29 0029.
 */
class CountDownTextView : AppCompatTextView {

    // 扫过的角度
    private var mSweepAngle = 360

    //动画
    private var animator: ValueAnimator? = null

    //区域
    private val mRect = RectF()

    private var bgColor : Int = R.color.colorAccent

    //进度画笔
    private val mProgressPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            strokeWidth = dp2px(2.0f).toFloat()
            style = Paint.Style.STROKE
        }
    }

    //背景画笔
   private val mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style)


    fun start(duration: Long) {
        // 在动画中
        if (mSweepAngle != 360) return
        mSweepAngle = 360
        animator = ValueAnimator.ofInt(mSweepAngle).setDuration(duration * 1000).apply {
            interpolator = LinearInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Int
                mSweepAngle = value
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {

        //画背景
        var x = (width / 2).toFloat()
        var y = (width / 2).toFloat()
        mBackgroundPaint.color = resources.getColor(bgColor)
        canvas.drawCircle(x, x, y, mBackgroundPaint)


        // 画倒计时线内圆
        val padding = dp2px(4f)
        mRect.top = padding.toFloat()
        mRect.left = padding.toFloat()
        mRect.right = (canvas.width - padding).toFloat()
        mRect.bottom = (canvas.height - padding).toFloat()
        canvas.drawArc(
            mRect,  //弧线所使用的矩形区域大小
            (-90).toFloat(),  //开始角度
            mSweepAngle.toFloat(),  //扫过的角度
            false,  //是否使用中心
            mProgressPaint
        )
        super.onDraw(canvas)
    }

    override fun onDetachedFromWindow() {
        stop()
        super.onDetachedFromWindow()
    }

    fun stop() {
        if (animator != null) {
            animator!!.cancel()
            animator = null
        }
    }

    fun setBgColor(color : Int){
        bgColor = color
        invalidate()
    }
}