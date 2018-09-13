package com.landa.library.net

import android.content.Context
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.landa.library.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * @author wyman
 * @date  2018/9/3
 * description :
 */
class ProgressObserver<T>(private val mSubscriberOnNextListener: SubscriberOnNextListener<T>, private var context: Context) : Observer<T>, ProgressCancelListener {
    private var disposable: Disposable? = null
    private var mProgressDialogHandler: ProgressDialogHandler = ProgressDialogHandler(context, true, this)

    override fun onCancelProgress() {
        disposable?.dispose()

    }

    override fun onComplete() {
        dismissProgressDialog()
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
        if (!NetworkUtils.isConnected()) {
            Toast.makeText(context, R.string.tip_not_net, Toast.LENGTH_SHORT).show()
            onComplete()
            return
        }
        showProgressDialog()
    }


    override fun onNext(t: T) {
        mSubscriberOnNextListener.onNext(t)
    }

    override fun onError(e: Throwable) {
        LogUtils.e("onError", e.message)
        ToastUtils.showShort("网络异常，请稍后再试")
        dismissProgressDialog()
//        mSubscriberOnNextListener.onError(e)

    }

    private fun showProgressDialog() {
        mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget()
    }

    private fun dismissProgressDialog() {
        mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget()
        disposable?.dispose()
    }

}