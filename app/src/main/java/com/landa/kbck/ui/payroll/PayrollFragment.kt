package com.landa.kbck.ui.payroll

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.landa.library.base.BaseFragment
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView
import com.landa.kbck.R
import com.landa.kbck.adapter.PayrollExpandableListAdapter
import com.landa.kbck.config.Constant
import com.landa.kbck.ui.listener.OnScrollListenerImpl
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.fragment_payroll_main.*
import java.util.*

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */
class PayrollFragment : BaseFragment<BasePresenter<BaseView>, BaseView>() {
    private var strs = arrayListOf<String>()
    private var iData = arrayListOf<ArrayList<String>>()

    companion object {
        fun getInstance(): PayrollFragment = PayrollFragment()
    }

    override fun initView(rootView: View?) {
        tooBar_title.text = "工资条"
        strs.add("2018年")
        strs.add("2017年")
        strs.add("2016年")
        strs.add("2015年")
        val list1 = arrayListOf<String>()
        val list2 = arrayListOf<String>()
        val list3 = arrayListOf<String>()
        val list4 = arrayListOf<String>()
        list1.add("wyman")
        list1.add("wyman")
        list1.add("wyman")
        list1.add("wyman")
        list1.add("wyman")
        list4.add("wyman")
        list2.add("wyman")
        list2.add("wyman")
        list2.add("wyman")
        list2.add("wyman")
        list3.add("wyman")
        list3.add("wyman")
        iData.add(list1)
        iData.add(list2)
        iData.add(list3)
        iData.add(list4)
        payroll_expandable.setAdapter(PayrollExpandableListAdapter(strs, iData, mContext))
        for (i in 0 until strs.size) {
            payroll_expandable.expandGroup(i)
        }
        payroll_expandable.setOnGroupClickListener { _, _, _, _ ->
            true
        }

        payroll_expandable.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->

            openActivity(Constant.ROUTER_ACTIVITY_PAYROLL_DETAIL)
            false
        }


        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }

        payroll_expandable.setOnScrollListener(OnScrollListenerImpl(swipeRefreshLayout))
    }


    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        swipeRefreshLayout.isRefreshing = false

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_payroll_main
    }

    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter(mContext)
    }

}