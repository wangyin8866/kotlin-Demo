package com.landa.kbck.utils

import android.content.Context
import android.view.KeyEvent
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.landa.kbck.R

/**
 * @author wyman
 * @date  2018/9/6
 * description :
 */
object WyUtils {
    private const val phoneRex = "^1\\d{10}"

    fun checkPhone(phone: String): Boolean {
        return phone.matches(phoneRex.toRegex())

    }

    public var mExitTime: Long = 0


    fun clickBack(keyCode: Int, event: KeyEvent, context: Context): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                AppUtils.exitApp()
            } else {
                mExitTime = System.currentTimeMillis()

                ToastUtils.showShort(context.getString(R.string.exit_tip))
            }
            return true
        }
        return false
    }
}