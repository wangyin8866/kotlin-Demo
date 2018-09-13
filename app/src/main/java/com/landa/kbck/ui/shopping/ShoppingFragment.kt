package com.landa.kbck.ui.shopping

import android.app.Activity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.landa.library.base.BaseFragment
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView
import com.landa.library.config.BaseConstant
import com.landa.library.utils.PreferenceData
import com.landa.kbck.R
import com.landa.kbck.utils.getAgentWeb
import kotlinx.android.synthetic.main.container.*

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */
class ShoppingFragment : BaseFragment<BasePresenter<BaseView>, BaseView>() {
    private var agentWeb: AgentWeb? = null
    private lateinit var shareUrl: String
    private var token: String by PreferenceData(BaseConstant.KEY_TOKEN, "")

    companion object {
        fun getInstance(): ShoppingFragment = ShoppingFragment()
    }

    override fun initView(rootView: View?) {
        shareUrl = "http://www.baidu.com"
        agentWeb = shareUrl.getAgentWeb(mContext as Activity, container,
                LinearLayout.LayoutParams(-1, -1),
                webChromeClient,
                webViewClient)
    }
    /**
     * webViewClient
     */
    private val webViewClient = object : WebViewClient() {
    }

    /**
     * webChromeClient
     */
    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            title.let {
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_shopping_main
    }

    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter(mContext)
    }


}