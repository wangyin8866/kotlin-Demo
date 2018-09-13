package com.landa.kbck.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.landa.kbck.R
import kotlinx.android.synthetic.main.dialog_login_agree.*
import kotlinx.android.synthetic.main.dialog_update.*

/**
 * @author wyman
 * @date  2018/9/7
 * description :
 */
class CustomDialog(context: Context) : Dialog(context, R.style.DialogStyle) {
    private var instance: CustomDialog

    init {
        setCanceledOnTouchOutside(false)
        instance = this
    }

    fun loginDialog(onClickListener: View.OnClickListener): CustomDialog {
        setContentView(R.layout.dialog_login_agree)
        window.setGravity(Gravity.CENTER)
        // 设置显示动画
        window.setWindowAnimations(R.style.main_animstyle)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        with(onClickListener) {
            dialog_login_agree.setOnClickListener(this)
            dialog_login_iv_close.setOnClickListener(this)
        }
        return instance

    }

    fun updateVersionDialog(upgrade: Int, verisonName: String, content: String, onClickListener: View.OnClickListener): CustomDialog {
        setContentView(R.layout.dialog_update)
        window.setGravity(Gravity.CENTER)
        // 设置显示动画
        window.setWindowAnimations(R.style.main_animstyle)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        if (upgrade == 2) {
            dialog_version_iv_close.visibility = View.GONE
        }

        tv_update_version.text = verisonName
        tv_update_content.text = content
        with(onClickListener) {
            tv_update.setOnClickListener(this)
            dialog_version_iv_close.setOnClickListener(this)
        }



        return instance

    }
}