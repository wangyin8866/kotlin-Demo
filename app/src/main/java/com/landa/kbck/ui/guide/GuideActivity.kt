package com.landa.kbck.ui.guide

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.landa.library.base.BaseActivity
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView
import com.landa.kbck.R
import com.landa.kbck.config.Constant
import kotlinx.android.synthetic.main.activity_welcom_guide.*
import kotlinx.android.synthetic.main.guide4.view.*

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */
@Route(path = Constant.ROUTER_ACTIVITY_GUIDE)
class GuideActivity : BaseActivity<BasePresenter<BaseView>, BaseView>() {
    override fun initView() {
        init()
    }

    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter(mContext)
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_welcom_guide
    }

    private var views = mutableListOf<View>()
    private val pageAdapter: GuideAdapter by lazy {
        GuideAdapter(views)
    }

    private fun init() {
        val view1: View = layoutInflater.inflate(R.layout.guide1, null)
        val view2: View = layoutInflater.inflate(R.layout.guide2, null)
        val view3: View = layoutInflater.inflate(R.layout.guide3, null)
        val view4: View = layoutInflater.inflate(R.layout.guide4, null)
        views.run {
            add(view1)
            add(view2)
            add(view3)
            add(view4)
        }
        welcome_pager.adapter = pageAdapter
        view4.btn_travel_join.setOnClickListener {
            openActivity(Constant.ROUTER_ACTIVITY_LOGIN)
            finish()
        }
    }
}