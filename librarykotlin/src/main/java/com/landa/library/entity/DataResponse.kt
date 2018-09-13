package com.landa.kbck.entity

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */


data class HttpResult<Any>(
        val code: Int,
        val msg: String,
        val data: Any
)




