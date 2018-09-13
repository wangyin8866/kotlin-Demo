package com.landa.kbck.ui.home

import android.Manifest
import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.support.v4.widget.SwipeRefreshLayout
import android.view.KeyEvent
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.landa.library.base.BaseFragment
import com.landa.library.utils.PreferenceLocal
import com.landa.kbck.R
import com.landa.kbck.config.Constant.KEY_VERSION_IS_FINISH
import com.landa.kbck.entity.HomeInfo
import com.landa.kbck.entity.UpdateVersion
import com.landa.kbck.service.UpdateService2
import com.landa.kbck.ui.home.presenter.HomePresenter
import com.landa.kbck.ui.home.view.HomeView
import com.landa.kbck.widget.UpdateVersionDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_home_main.*

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */
class HomeFragment : BaseFragment<HomePresenter, HomeView>(), HomeView {
    private var updateVersionDialog: UpdateVersionDialog? = null

    private var isDownVersionFinish: Boolean by PreferenceLocal(KEY_VERSION_IS_FINISH, false)
    override fun showUpdateVersion(updateVersion: UpdateVersion) {
        if (updateVersion.upgrade == 0) {
            return
        }
        updateVersionDialog = UpdateVersionDialog(mContext)

        updateVersionDialog?.setText("V" + updateVersion.version, updateVersion.changeLog)
        updateVersionDialog?.btnEnable(true)
        if (updateVersion.upgrade == 2) {
            updateVersionDialog?.btnGone()
        }

        updateVersionDialog?.setOnKeyListener { _, _, event ->
            if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
                if (updateVersion.upgrade == 1) {
                    updateVersionDialog?.dismiss()
                    updateVersionDialog = null
                } else if (updateVersion.upgrade == 2) {
                    updateVersionDialog?.dismiss()
                    updateVersionDialog = null
                    System.exit(0)
                }

            }
            false
        }
        updateVersionDialog?.setOnDoubleClickListener(View.OnClickListener { it ->
            if (it.id == R.id.dialog_version_iv_close) {
                updateVersionDialog?.dismiss()
                updateVersionDialog = null
            } else if (it.id == R.id.tv_update) {
                LogUtils.eTag("wyman", "click")
                activity?.unbindService(serviceConnection)
                RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe {
                            if (it) {
                                LogUtils.eTag("wyman", "RxPermissions")
                                // 请求成功，开启服务，下载文件
                                val intent = Intent(activity, UpdateService2::class.java)
                                intent.putExtra("url", updateVersion.downLoadUrl)
                                intent.putExtra("title", "蓝薪卡V" + updateVersion.version)
//                                activity?.startService(intent)
                                activity?.bindService(intent, serviceConnection, BIND_AUTO_CREATE)

                                if (isDownVersionFinish) {
                                    updateVersionDialog?.btnEnable(true)
                                    updateVersionDialog?.btnChangeText("下载完成\n\n请点击安装", "立即安装")
                                } else {
                                    updateVersionDialog?.btnEnable(false)
                                    updateVersionDialog?.btnChangeText("请耐心等待，下载中...\n\n通知栏可查看下载进度", "正在更新")
                                }


                            } else {
                                ToastUtils.showShort("请去设置中开启权限")
                            }
                        }
            }
        })
        updateVersionDialog?.show()

    }

    override fun showHomeData(homeInfo: HomeInfo) {

        home_tv_my_amount.text = homeInfo.total.toString()
        home_tv_add_amount.text = homeInfo.quotaToday.toString()

    }

    private var serviceConnection = object : ServiceConnection {


        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            val myBinder: UpdateService2.MyBinder = service as UpdateService2.MyBinder
            val updateService = myBinder.service
            updateService.setDownFinish(object : UpdateService2.DownFinish {
                override fun finish() {
                    updateVersionDialog?.btnEnable(true)
                    updateVersionDialog?.btnChangeText("下载完成\n\n请点击安装", "立即安装")


                }

            })
        }
    }

    private val images = mutableListOf<Int>()

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    override fun initView(rootView: View?) {

        images.run {
            add(R.drawable.banner_one)
            add(R.drawable.banner_two)
            add(R.drawable.banner_three)
        }

        home_banner.setImages(images).setImageLoader(GlideImageLoader()).start()


        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }

        //版本更新
        mPresenter.updateVersion()
        //获取首页数据
//        mPresenter.getHomeInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (updateVersionDialog != null) {
            updateVersionDialog?.dismiss()
            updateVersionDialog = null
        }
        activity?.unbindService(serviceConnection)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_main
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter(mContext)
    }

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        swipeRefreshLayout.isRefreshing = false

    }

}