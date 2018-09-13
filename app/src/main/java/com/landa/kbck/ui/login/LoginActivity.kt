package com.landa.kbck.ui.login

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding2.view.RxView
import com.landa.library.base.BaseActivity
import com.landa.kbck.R
import com.landa.kbck.config.Constant
import com.landa.kbck.utils.WyUtils
import com.landa.kbck.widget.CustomDialog
import kotlinx.android.synthetic.main.ativity_login.*
import java.util.concurrent.TimeUnit

/**
 * @author wyman
 * @date  2018/9/6
 * description :
 */
@Route(path = Constant.ROUTER_ACTIVITY_LOGIN)
class LoginActivity : BaseActivity<LoginPresenter, LoginView>(), LoginView {
    override fun loginSuccess() {
        openActivity(Constant.ROUTER_ACTIVITY_MAIN)

    }

    private var customDialog: CustomDialog? = null
    override fun initView() {

        login_et_phone.run {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    login_line_1.setBackgroundColor(resources.getColor(R.color.text_special_color))

                } else {
                    login_line_1.setBackgroundColor(resources.getColor(R.color.split_line_color))
                }
            }

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    login_btn_login.isEnabled = !TextUtils.isEmpty(s) && !TextUtils.isEmpty(login_et_emsCode.text.toString().trim())
                }

            })
        }
        login_et_emsCode.run {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    login_line_2.setBackgroundColor(resources.getColor(R.color.text_special_color))

                } else {
                    login_line_2.setBackgroundColor(resources.getColor(R.color.split_line_color))
                }
            }
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    login_btn_login.isEnabled = !TextUtils.isEmpty(s) && !TextUtils.isEmpty(login_et_phone.text.toString().trim())
                }

            })
        }

        /**
         * 发送短信
         */
        login_tv_sms.also { it ->
            RxView.clicks(it).throttleFirst(2, TimeUnit.SECONDS).subscribe {

                val phone = login_et_phone.text.toString().trim()

                if (TextUtils.isEmpty(phone) || !WyUtils.checkPhone(phone)) {
                    ToastUtils.showShort(R.string.login_tip_phone_regex)
                } else {
                    //todo 发送短信
                    mPresenter.sendSms(phone, login_tv_sms, getString(R.string.login_tip_sms_rest), getString(R.string.login_tip_sms_tip))
                }

            }
        }
        /**
         * 登录
         */

        login_btn_login.apply {
            RxView.clicks(this).throttleFirst(2, TimeUnit.SECONDS).subscribe { _ ->
                val phone = login_et_phone.text.toString().trim()
                val sms = login_et_emsCode.text.toString().trim()


                if (login_cb.isChecked) {
                    mPresenter.login(phone, sms)
                } else {
                    customDialog = CustomDialog(mContext)
                    customDialog?.let { it ->
                        it.loginDialog(View.OnClickListener {
                            if (it.id == R.id.dialog_login_agree) {
                                login_cb.isChecked = true
                                customDialog?.dismiss()
                                customDialog = null
                            } else if (it.id == R.id.dialog_login_iv_close) {
                                customDialog?.dismiss()
                                customDialog = null
                            }
                        })
                    }
                    customDialog?.show()
                }
            }
        }
        login_tv_protocol.apply {
            RxView.clicks(this).throttleFirst(2, TimeUnit.SECONDS).subscribe {
                ARouter.getInstance().build(Constant.ROUTER_ACTIVITY_CONTENT_NO_TITLE).withString("url", "http://h5.lanxinka.com/userKnow").navigation()
            }
        }
    }

    override fun createPresenter(): LoginPresenter {

        return LoginPresenter(mContext)
    }

    override fun getLayoutId(): Int {
        return R.layout.ativity_login
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return WyUtils.clickBack(keyCode, event, mContext)
    }
}
