package com.landa.kbck.service

import com.landa.kbck.config.NetValueConstant
import com.landa.kbck.entity.HttpResult
import com.landa.kbck.entity.UpdateVersion
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by chenxz on 2018/4/21.
 */
interface ApiService {


    @POST(NetValueConstant.home_info)
    fun getHomeInfo(): Observable<HttpResult<Any>>

    // APP更新信息
    @GET(NetValueConstant.update_version)
    fun updateInfo(@QueryMap map: HashMap<String, Any>): Observable<HttpResult<UpdateVersion>>

    @POST(NetValueConstant.login_sms)
    @FormUrlEncoded
    fun sendSms(@FieldMap map: HashMap<String, Any>): Observable<HttpResult<Any>>

    @POST(NetValueConstant.login_login)
    @FormUrlEncoded
    fun login(@FieldMap map: HashMap<String, Any>): Observable<HttpResult<Any>>

}