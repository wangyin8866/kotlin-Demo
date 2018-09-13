package com.landa.kbck.ui.listener

import android.support.v4.widget.SwipeRefreshLayout
import android.widget.AbsListView

/**
 * @author wyman
 * @date  2018/9/12
 * description :处理swipeRefreshLayout和expandableListView滑动冲突
 */
class OnScrollListenerImpl(private var swipeRefreshLayout: SwipeRefreshLayout) : AbsListView.OnScrollListener {
    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        val firstView = view?.getChildAt(firstVisibleItem)
        swipeRefreshLayout.isEnabled = firstVisibleItem == 0 && (firstView == null || firstView.top == 0)
    }
    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

    }
}