package com.remees.findmyip.feature_findip.presentation

import com.remees.findmyip.feature_findip.data.remote.dto.IpInfoDto
import kotlinx.coroutines.flow.emptyFlow

data class FindMyIpState(
    val ipInfoItems: IpInfoDto? = null,
    val isLoading: Boolean = false
)