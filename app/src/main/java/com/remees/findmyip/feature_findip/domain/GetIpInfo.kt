package com.remees.findmyip.feature_findip.domain

import com.remees.findmyip.feature_findip.core.util.Resource
import com.remees.findmyip.feature_findip.data.remote.FindMyIpApi
import com.remees.findmyip.feature_findip.data.remote.dto.IpInfoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetIpInfo (
    private val api: FindMyIpApi
){
    fun getIpInfo() : Flow<Resource<IpInfoDto>> = flow {
        emit(Resource.Loading())

        var remoteData: IpInfoDto? = null
        try {
             remoteData = api.getIpInfo()
        } catch(e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = remoteData
            ))
        } catch(e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = remoteData
            ))
        }

        emit(Resource.Success(remoteData))
    }
}

