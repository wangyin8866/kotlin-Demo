package com.landa.kbck.ui.home.view

import com.landa.library.base.BaseView
import com.landa.kbck.entity.HomeInfo
import com.landa.kbck.entity.UpdateVersion

/**
 * @author wyman
 * @date  2018/9/6
 * description :
 */
interface HomeView :BaseView {
    fun showHomeData(homeInfo: HomeInfo)
    fun showUpdateVersion(updateVersion: UpdateVersion)
}