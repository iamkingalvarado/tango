package io.tango.challenge.core.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

/**
 * This core function will handle database caching after performing network request
 */
@ExperimentalCoroutinesApi
inline fun <DB, REMOTE, DOMAIN> networkBoundResource(
    crossinline fetchFromLocal: suspend () -> DB,
    crossinline shouldFetchFromRemote: suspend (DB?) -> Boolean = { true },
    crossinline fetchFromRemote: suspend () -> REMOTE,
    crossinline processRemoteResponse: (response: REMOTE) -> DOMAIN? = { null },
    crossinline saveRemoteData: suspend (DOMAIN?) -> Unit = { },
    crossinline onFetchFailed: (errorBody: String?, statusCode: Int) -> Unit = { _: String?, _: Int -> }
) = flow<Resource<DB>> {
    emit(Resource.loading(null))
    val localData = fetchFromLocal()
    if (shouldFetchFromRemote(localData)) {
        emit(Resource.loading(localData))
        try {
            val apiResponse = fetchFromRemote()
            val processedResponse = processRemoteResponse(apiResponse)
            saveRemoteData(processedResponse)
            emit(Resource.success(fetchFromLocal()))
        } catch (ex: Exception) {
            val error: String
            if (ex is HttpException) {
                error = ex.message()
                onFetchFailed(error, ex.code())
            } else {
                error = ex.localizedMessage ?: ""
            }
            emit(Resource.error(error, fetchFromLocal()))

        }
    } else {
        emit(Resource.success(fetchFromLocal()))
    }
}