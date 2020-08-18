package com.example.hhtest.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.hhtest.R
import com.example.hhtest.util.dpToPx
import com.example.hhtest.util.spToPx

class PasswordInfoView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var background = (0xcFF323232).toInt()
    private var textColor = ContextCompat.getColor(context, R.color.colorAccent)
    private var textSize = 36f
    private var title = context.getText(R.string.password_must_contain)
    private val paddingValue = context.dpToPx(16).toInt()

    init {
        parseAttrs(attrs)
        prepareView()
    }

    private fun prepareView() {
        isClickable = true
        orientation = VERTICAL
        visibility = View.INVISIBLE
        setWillNotDraw(false)
        setPadding(paddingValue, paddingValue, paddingValue, paddingValue)
        addView(TextView(context).apply {
            text = title
            setAttrs()
        })
    }

    private fun parseAttrs(attrs: AttributeSet) {
        val attrList = context.obtainStyledAttributes(attrs, R.styleable.PasswordInfoView)
        textColor = attrList.getColor(R.styleable.PasswordInfoView_itemTextColor, textColor)
        textSize = attrList.getDimension(R.styleable.PasswordInfoView_itemTextSize, textSize)
        background = attrList.getColor(R.styleable.PasswordInfoView_android_background, background)
        title = attrList.getString(R.styleable.PasswordInfoView_title)
            ?: context.getText(R.string.password_must_contain)
        attrList.recycle()
    }

    fun clearConditions() {
        for (i in childCount.dec().downTo(1)) {
            removeViewAt(i)
        }
    }

    fun setItemsTextColor(color: Int) {
        textColor = color
        for (tv in children) {
            (tv as TextView).setTextColor(textColor)
        }
    }

    fun setItemsTextSize(sizeInPs: Int) {
        textSize = context.spToPx(sizeInPs)
        for (tv in children) {
            (tv as TextView).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
    }

    fun setBackground(color: Int) {
        background = color
        invalidate()
    }

    fun setTitle(title: String) {
        this.title = title
        (getChildAt(0) as TextView).text = title
    }

    fun addConditions(conditions: List<String>) {
        conditions.forEach {
            addView(TextView(context).apply {
                text = "• $it"
                setAttrs()
                val paddingBetweenStrings = context.dpToPx(4).toInt()
                setPadding(0, paddingBetweenStrings, 0, paddingBetweenStrings)
            })
        }
        invalidate()
        requestLayout()
    }

    fun addConditions(conditions: Array<String>) {
        addConditions(conditions.asList())
    }

    fun show() {
        visibility = View.VISIBLE
        invalidate()
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.piv_start))
        Thread {
            Thread.sleep(3000)
            Handler(context.mainLooper).post {
                visibility = View.INVISIBLE
                invalidate()
            }
        }.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rect = RectF()
        val paint = Paint()
        paint.color = background
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas?.drawRoundRect(rect, context.dpToPx(4), context.dpToPx(4), paint)
    }

    private fun TextView.setAttrs() {
        setTextColor(textColor)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, this@PasswordInfoView.textSize)
    }
}