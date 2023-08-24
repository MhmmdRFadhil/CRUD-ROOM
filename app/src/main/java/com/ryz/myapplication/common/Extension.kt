package com.ryz.myapplication.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.ryz.myapplication.R

fun MaterialToolbar.customToolbar(
    activity: AppCompatActivity,
    title: String? = null,
    isShowArrow: Boolean = false,
    isShowMenu: Boolean = false,
    menuId: Int = 0
) {
    with(this) {
        elevation = 4F
        setTitle(title)

        if (isShowArrow) {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { activity.onBackPressedDispatcher.onBackPressed() }
        } else {
            navigationIcon = null
            setNavigationOnClickListener(null)
        }

        if (isShowMenu && menuId != 0) inflateMenu(menuId)
    }
}

fun Context.hideSoftInput(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}