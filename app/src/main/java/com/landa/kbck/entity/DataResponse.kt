package com.landa.kbck.entity

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */



data class Banner(
        val desc: String,
        val id: Int,
        val imagePath: String,
        val isVisible: Int,
        val order: Int,
        val title: String,
        val type: Int,
        val url: String
)

data class UpdateVersion(
        val os: String,
        val version: String,
        val changeLog: String,
        val downLoadUrl: String,
        val upgrade: Int
)

data class HomeInfo(
        val total: Int,
        val quotaToday: Int
)

