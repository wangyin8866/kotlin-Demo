package com.landa.kbck.ui.my

import android.view.View
import com.landa.kbck.R
import com.landa.library.base.BaseFragment
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */
class MyFragment : BaseFragment<BasePresenter<BaseView>, BaseView>() {
    companion object {
        fun getInstance(): MyFragment = MyFragment()
    }
    override fun initView(rootView: View?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_main
    }

    override fun createPresenter(): BasePresenter<BaseView> {
       return BasePresenter(mContext)
    }
}