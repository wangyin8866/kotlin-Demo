package com.landa.kbck.config

/**
 * @author wyman
 * @date  2018/9/6
 * description : 接口二级地址
 */
object NetValueConstant {

    //发送短信
    const val login_sms = "1.0/captcha/send"
    //登录
    const val login_login = "1.0/users/login"

    //首页
    const val home_info = "api/backend/v1/checkin/getCheckinHome"
    //版本跟新
    const val update_version = "/api/backend/v1/app/upgrade/check"

    //工资条
    const val payroll_info = "api/backend/v1/member/salary"

}