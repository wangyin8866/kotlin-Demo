package com.landa.kbck

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.KeyEvent
import com.alibaba.android.arouter.facade.annotation.Route
import com.landa.kbck.config.Constant
import com.landa.kbck.ui.bill.BillFragment
import com.landa.kbck.ui.home.HomeFragment
import com.landa.kbck.ui.my.MyFragment
import com.landa.kbck.ui.payroll.PayrollFragment
import com.landa.kbck.ui.shopping.ShoppingFragment
import com.landa.kbck.utils.WyUtils
import com.landa.kbck.widget.BottomNavigationViewHelper
import com.landa.library.base.BaseActivity
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView
import kotlinx.android.synthetic.main.activity_main.*
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
        bottom_navigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemReselectedListener)
            BottomNavigationViewHelper.disableShiftMode(this)
            itemIconTintList = null
            selectedItemId = this.menu.getItem(0).itemId
        }
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

    }

    private val onNavigationItemReselectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        resetToDefaultIcon()//重置到默认不选中图片
        when (item.itemId) {
            R.id.menu_tab1 -> {
                item.setIcon(R.drawable.tab_home_select)
                showHideFragment(mFragments[0], mFragments[currentPage])
                currentPage = 0
            }
            R.id.menu_tab2 -> {
                showHideFragment(mFragments[1], mFragments[currentPage])
                item.setIcon(R.drawable.tab_shopping_select)
                currentPage = 1
            }
            R.id.menu_tab3 -> {
                showHideFragment(mFragments[2], mFragments[currentPage])
                item.setIcon(R.drawable.tab_payroll_select)
                currentPage = 2
            }
            R.id.menu_tab4 -> {
                showHideFragment(mFragments[3], mFragments[currentPage])
                item.setIcon(R.drawable.tab_bill_select)
                currentPage = 3
            }
        }
        true
    }

    private fun resetToDefaultIcon() {
        val item1 = bottom_navigation.menu.findItem(R.id.menu_tab1)
        item1.setIcon(R.drawable.tab_home)
        val item2 = bottom_navigation.menu.findItem(R.id.menu_tab2)
        item2.setIcon(R.drawable.tab_shopping)
        val item3 = bottom_navigation.menu.findItem(R.id.menu_tab3)
        item3.setIcon(R.drawable.tab_payroll)
        val item4 = bottom_navigation.menu.findItem(R.id.menu_tab4)
        item4.setIcon(R.drawable.tab_bill)
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


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return WyUtils.clickBack(keyCode, event, mContext)
    }
}