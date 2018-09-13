package com.landa.library.config

/**
 * @author wyman
 * @date  2018/9/4
 * description : 接口地址
 */
object BaseNetValueConfig {

    private const val ONLINE_SERVER_URL = "http://sign.lanxinka.com/"
    private const val DEV_SERVER_URL = "http://sign.lhb.dev.lanxinka.com/"
    private const val TEST_SERVER_URL = "http://test-sign.lanxinka.com/"
    var SERVER_URL = ONLINE_SERVER_URL

    private const val ONLINE_H5_URL = "http://h5.lanxinka.com"
    private const val DEV_H5_URL = "http://dev-h5.lanxinka.com"
    private const val TEST_H5_URL = "http://test-h5.lanxinka.com"
    var H5_URL = ONLINE_H5_URL


    /**
     * 登录接口地址
     */
    private const val LOGIN_ONLINE_URL = "http://member-api.lanxinka.com/"
    private const val LOGIN_DEV_URL = "http://dev-member-api.lanxinka.com/"
    private const val LOGIN_TEST_URL = "http://test-member-api.lanxinka.com/"
    var LOGIN_SERVER_URL = LOGIN_ONLINE_URL


    fun initUrl(which: Int) {
        when (which) {
            0 -> {
                SERVER_URL = DEV_SERVER_URL
                H5_URL = DEV_H5_URL

                LOGIN_SERVER_URL = LOGIN_DEV_URL
            }
            1 -> {
                SERVER_URL = ONLINE_SERVER_URL
                H5_URL = ONLINE_H5_URL
                LOGIN_SERVER_URL = LOGIN_ONLINE_URL
            }
            2 -> {
                SERVER_URL = TEST_SERVER_URL
                H5_URL = TEST_H5_URL
                LOGIN_SERVER_URL = LOGIN_TEST_URL
            }
        }

    }
}