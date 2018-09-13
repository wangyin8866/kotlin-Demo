package com.landa.kbck

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.landa.library.base.BaseActivity
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView
import com.landa.kbck.config.Constant
import com.landa.kbck.ui.bill.BillFragment
import com.landa.kbck.ui.home.HomeFragment
import com.landa.kbck.ui.my.MyFragment
import com.landa.kbck.ui.payroll.PayrollFragment
import com.landa.kbck.ui.shopping.ShoppingFragment
import com.landa.kbck.utils.WyUtils
import kotlinx.android.synthetic.main.public_bottom_main.*
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */
@Route(path = Constant.ROUTER_ACTIVITY_MAIN)
class MainActivity : BaseActivity<BasePresenter<BaseView>, BaseView>() {
    private val BOTTOM_INDEX: String = "bottom_index"
    private val mFragments = arrayOfNulls<ISupportFragment>(5)

    private var currentPage: Int = 0
    override fun initView() {
        val homeFragment: ISupportFragment? = findFragment(HomeFragment::class.java)
        if (homeFragment == null) {
            mFragments[0] = HomeFragment.getInstance()
            mFragments[1] = ShoppingFragment.getInstance()
            mFragments[2] = PayrollFragment.getInstance()
            mFragments[3] = BillFragment.getInstance()
            mFragments[4] = MyFragment.getInstance()
            loadMultipleRootFragment(R.id.layout_fragment, 0, mFragments[0], mFragments[1], mFragments[2], mFragments[3], mFragments[4])
        } else {
            mFragments[0] = homeFragment
            mFragments[1] = findFragment(ShoppingFragment::class.java)
            mFragments[2] = findFragment(PayrollFragment::class.java)
            mFragments[3] = findFragment(BillFragment::class.java)
            mFragments[4] = findFragment(MyFragment::class.java)

        }
        setTabSelection(currentPage)


        id_tab_ll_01.run { setOnClickListener(onClickListener) }
        id_tab_ll_02.run { setOnClickListener(onClickListener) }
        id_tab_ll_03.run { setOnClickListener(onClickListener) }
        id_tab_ll_04.run { setOnClickListener(onClickListener) }
        id_tab_ll_05.run { setOnClickListener(onClickListener) }

    }


    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter(mContext)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BOTTOM_INDEX, currentPage)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(BOTTOM_INDEX)
        }
    }

    private fun setTabSelection(currentPage: Int) {
        //选中前清除状态
        restView()

        when (currentPage) {
            0 -> {
                id_tab_iv_01.setImageResource(R.drawable.tab_home_select)
            }
            1 -> {
                id_tab_iv_02.setImageResource(R.drawable.tab_shopping_select)
            }
            2 -> {
                id_tab_iv_03.setImageResource(R.drawable.tab_payroll_select)
            }
            3 -> {
                id_tab_iv_04.setImageResource(R.drawable.tab_bill_select)
            }
            4 -> {
                id_tab_iv_05.setImageResource(R.drawable.tab_me_select)
            }
        }
    }

    private fun restView() {
        id_tab_iv_01.setImageResource(R.drawable.tab_home)
        id_tab_iv_02.setImageResource(R.drawable.tab_shopping)
        id_tab_iv_03.setImageResource(R.drawable.tab_payroll)
        id_tab_iv_04.setImageResource(R.drawable.tab_bill)
        id_tab_iv_05.setImageResource(R.drawable.tab_me)
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        return@OnClickListener when (it.id) {
            R.id.id_tab_ll_01 -> {
                showHideFragment(mFragments[0], mFragments[currentPage])
                currentPage = 0
                setTabSelection(currentPage)
            }
            R.id.id_tab_ll_02 -> {
                showHideFragment(mFragments[1], mFragments[currentPage])
                currentPage = 1
                setTabSelection(currentPage)
            }
            R.id.id_tab_ll_03 -> {
                showHideFragment(mFragments[2], mFragments[currentPage])
                currentPage = 2
                setTabSelection(currentPage)
            }
            R.id.id_tab_ll_04 -> {
                showHideFragment(mFragments[3], mFragments[currentPage])
                currentPage = 3
                setTabSelection(currentPage)
            }
            R.id.id_tab_ll_05 -> {
                showHideFragment(mFragments[4], mFragments[currentPage])
                currentPage = 4
                setTabSelection(currentPage)
            }
            else -> {
                setTabSelection(currentPage)
            }

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return WyUtils.clickBack(keyCode, event,mContext)
    }
}