package com.landa.kbck.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.landa.kbck.R
import kotlinx.android.synthetic.main.dialog_update.*

/**
 * @author wyman
 * @date  2018/9/7
 * description :
 */
class UpdateVersionDialog(context: Context) : Dialog(context, R.style.DialogStyle) {
    private var instance: UpdateVersionDialog = this

    init {
        setContentView(R.layout.dialog_update)
        setCanceledOnTouchOutside(false)
        window.setGravity(Gravity.CENTER)
        // 设置显示动画
        window.setWindowAnimations(R.style.main_animstyle)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    fun setText(version: String, content: String): UpdateVersionDialog {

        tv_update_version.text = version
        tv_update_content.text = content
        return instance
    }

    fun setOnDoubleClickListener(onClickListener: View.OnClickListener) {
        with(onClickListener) {
            tv_update.setOnClickListener(this)
            dialog_version_iv_close.setOnClickListener(this)
        }
    }

    fun btnGone() {
        dialog_version_iv_close.visibility = View.GONE
    }

    fun btnEnable(enable: Boolean) {
        tv_update.isEnabled = enable
    }

    fun btnChangeText(str1:String,str2:String) {
        tv_update_content.text = str1
        tv_update.text = str2
    }
}