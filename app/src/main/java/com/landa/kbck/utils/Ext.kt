package com.landa.kbck.utils

import android.app.Activity
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.TextView
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.landa.kbck.R
import com.landa.kbck.config.Constant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author wyman
 * @date  2018/9/7
 * description :
 */
/**
 * getAgentWeb
 */
fun String.getAgentWeb(
        activity: Activity, webContent: ViewGroup,
        layoutParams: ViewGroup.LayoutParams,
        webChromeClient: WebChromeClient?,
        webViewClient: WebViewClient
) = AgentWeb.with(activity)//传入Activity or Fragment
        .setAgentWebParent(webContent, layoutParams)//传入AgentWeb 的父控件
        .useDefaultIndicator()// 使用默认进度条
        .setWebChromeClient(webChromeClient)
        .setWebViewClient(webViewClient)
        .setMainFrameErrorView(R.layout.activity_agentweb_error_page, R.id.tv_error)
        .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
        .createAgentWeb()//
        .ready()
        .go(this)!!

fun countDown2(tv: TextView, rest: String) {
    var currentTime = Constant.SMS_TIME - 1


    tv.text = (Constant.SMS_TIME - 1000).toString() + "s"

    var mCompositeDisposable = CompositeDisposable()

    mCompositeDisposable.add(Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mCompositeDisposable.clear()

            })

}

fun countDown(tv: TextView, rest: String) {
    var currentTime = Constant.SMS_TIME - 1
    val mCompositeDisposable = CompositeDisposable()
    tv.isEnabled = false
    tv.text = (Constant.SMS_TIME - 1).toString() + "s"

    mCompositeDisposable.add(Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                currentTime -= 1
                if (currentTime <= 0) {
                    tv.text = rest
                    tv.isEnabled = true
                    mCompositeDisposable.clear()

                } else {
                    tv.text = currentTime.toString() + "s"
                }
            })

}
