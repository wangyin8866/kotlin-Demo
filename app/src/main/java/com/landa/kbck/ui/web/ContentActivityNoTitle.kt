package com.landa.kbck.ui.web

import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.just.agentweb.AgentWeb
import com.landa.library.base.BaseActivity
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView
import com.landa.kbck.R
import com.landa.kbck.config.Constant
import com.landa.kbck.utils.getAgentWeb
import kotlinx.android.synthetic.main.container.*

/**
 * @author wyman
 * @date  2018/9/7
 * description :
 */
@Route(path = Constant.ROUTER_ACTIVITY_CONTENT_NO_TITLE)
class ContentActivityNoTitle : BaseActivity<BasePresenter<BaseView>, BaseView>() {

    private var agentWeb: AgentWeb? = null
    private lateinit var shareUrl: String
    override fun initView() {
        intent.extras.let {
            shareUrl = it.getString(Constant.CONTENT_URL_KEY, "")
        }

        agentWeb = shareUrl.getAgentWeb(this, container,
                LinearLayout.LayoutParams(-1, -1),
                webChromeClient,
                webViewClient)


    }

    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter(mContext)
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_content_no_title
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()

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
}