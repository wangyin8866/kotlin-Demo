package com.landa.kbck.ui.payroll

import com.alibaba.android.arouter.facade.annotation.Route
import com.landa.library.base.BaseActivity
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView
import com.landa.kbck.R
import com.landa.kbck.config.Constant
import kotlinx.android.synthetic.main.activity_toolbar_back.*

/**
 * @author wyman
 * @date  2018/9/11
 * description :
 */
@Route(path = Constant.ROUTER_ACTIVITY_PAYROLL_DETAIL)
class PayrollDetailActivity : BaseActivity<BasePresenter<BaseView>, BaseView>() {
    override fun initView() {
        tooBar_title.text = "工资详情"
        toolBar_back.setOnClickListener {
            closeActivity()
        }
    }

    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter(mContext)
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_payroll_detail
    }

}