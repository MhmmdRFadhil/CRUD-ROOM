package com.ryz.myapplication.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.ryz.myapplication.R
import java.util.Objects

class CustomEditText : AppCompatEditText {
    private val defaultColor = Color.parseColor("#808080")
    private var clearIconTint: Int = 0
    private var mClearButtonImage: Drawable? = null
    private var isClearIconVisible: Boolean = false

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        mClearButtonImage =
            ContextCompat.getDrawable(context, R.drawable.ic_clear_cancel) as Drawable
        isClearIconVisible =
            a.getBoolean(R.styleable.CustomEditText_MyApplication_setClearIconVisible, false)
        clearIconTint =
            a.getColor(R.styleable.CustomEditText_MyApplication_clearIconTint, defaultColor)

        setOnTouchListener(OnTouchListener { view, event ->
            val editText = this@CustomEditText

            if (editText.compoundDrawables[2] == null) return@OnTouchListener false
            if (event.action != MotionEvent.ACTION_UP) return@OnTouchListener false
            if (isClearIconVisible) {
                val width = if (mClearButtonImage == null) 0 else mClearButtonImage!!.intrinsicWidth
                if (event.x > editText.width - editText.paddingRight - width) {
                    editText.setText("")
                    this@CustomEditText.handleClearButton()
                }
            }
            view.performClick()
            false
        })
        a.recycle()
    }

    @SuppressLint("NewApi")
    private fun handleClearButton() {
        if (isClearIconVisible) {
            DrawableCompat.setTint(mClearButtonImage!!, clearIconTint)
            mClearButtonImage!!.setBounds(0, 0, 48, 48)
            if (Objects.requireNonNull<Editable>(this.text).isEmpty()) {
                this.setCompoundDrawables(
                    this.compoundDrawables[0],
                    this.compoundDrawables[1],
                    null,
                    this.compoundDrawables[3]
                )
            } else {
                this.setCompoundDrawables(
                    this.compoundDrawables[0],
                    this.compoundDrawables[1],
                    mClearButtonImage,
                    this.compoundDrawables[3]
                )
            }
        }
    }

    public override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {
        try {
            if (isClearIconVisible) this@CustomEditText.handleClearButton()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}