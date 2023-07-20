package br.com.chalenge.rickmorty.data.remote

import br.com.chalenge.rickmorty.R
import br.com.chalenge.rickmorty.data.response.NetworkException
import br.com.chalenge.rickmorty.data.response.UnexpectedErrorResponse
import br.com.chalenge.rickmorty.utils.ResourceManager
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

interface ErrorInterceptor : Interceptor

class ErrorInterceptorImpl @Inject constructor(private val resourceManager: ResourceManager) : ErrorInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response: Response
        try {
            response = chain.proceed(request)
        } catch (exception: Exception) {
            val error = when (exception) {
                is HttpException -> NetworkException(resourceManager.getString(R.string.http_error))
                is UnknownHostException -> NetworkException(resourceManager.getString(R.string.network_error))
                is IOException -> NetworkException(resourceManager.getString(R.string.network_error))
                else -> UnexpectedErrorResponse(resourceManager.getString(R.string.unexpected_error))
            }
            throw error
        }
        return response
    }
}