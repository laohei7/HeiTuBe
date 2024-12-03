package com.laohei.heitube.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Hots(
    val list: List<VideoItem>
)

@Serializable
data class VideoItem(
    val aid: Long,
    val videos: Int,
    val tid: Int,
    val tname: String,
    val copyright: Int,
    val pic: String,
    val title: String,
    val pubdate: Long,
    val ctime: Long,
    val desc: String,
    val state: Int,
    val duration: Int,
    @SerialName("mission_id") val missionId: Int = -1,
    val rights: Rights,
    val owner: Owner,
    val stat: Stat,
    val dynamic: String,
    val cid: Long,
    val dimension: Dimension,
    @SerialName("season_id") val seasonId: Int = -1,
    @SerialName("short_link_v2") val shortLinkV2: String,
    @SerialName("first_frame") val firstFrame: String = "",
    @SerialName("pub_location") val pubLocation: String = "",
    val cover43: String,
    val bvid: String,
    @SerialName("season_type") val seasonType: Int,
    @SerialName("is_ogv") val isOgv: Boolean,
    @SerialName("ogv_info") val ogvInfo: OgvInfo? = null,
    @SerialName("enable_vt") val enableVt: Int,
    @SerialName("ai_rcmd") val aiRcmd: String? = null,
    @SerialName("rcmd_reason") val rcmdReason: RcmdReason
)

@Serializable
data class Rights(
    val bp: Int,
    val elec: Int,
    val download: Int,
    val movie: Int,
    val pay: Int,
    val hd5: Int,
    @SerialName("no_reprint") val noReprint: Int,
    val autoplay: Int,
    @SerialName("ugc_pay") val ugcPay: Int,
    @SerialName("is_cooperation") val isCooperation: Int,
    @SerialName("ugc_pay_preview") val ugcPayPreview: Int,
    @SerialName("no_background") val noBackground: Int,
    @SerialName("arc_pay") val arcPay: Int,
    @SerialName("pay_free_watch") val payFreeWatch: Int
)

@Serializable
data class Owner(
    val mid: Long,
    val name: String,
    val face: String
)

@Serializable
data class Stat(
    val aid: Long,
    val view: Int,
    val danmaku: Int,
    val reply: Int,
    val favorite: Int,
    val coin: Int,
    val share: Int,
    @SerialName("now_rank") val nowRank: Int,
    @SerialName("his_rank") val hisRank: Int,
    val like: Int,
    val dislike: Int,
    val vt: Int,
    val vv: Int
)

@Serializable
data class Dimension(
    val width: Int,
    val height: Int,
    val rotate: Int
)

@Serializable
data class OgvInfo(
    val info: String? // 根据实际结构补充
)

@Serializable
data class RcmdReason(
    val content: String,
    @SerialName("corner_mark") val cornerMark: Int
)
