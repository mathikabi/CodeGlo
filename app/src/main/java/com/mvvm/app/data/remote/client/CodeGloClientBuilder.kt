package com.mvvm.app.data.remote.client

import com.gatebot.app.BuildConfig
import com.google.gson.GsonBuilder
import com.mvvm.app.data.remote.interfaces.FetchDataController
import com.mvvm.app.utilities.Constants
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class CodeGloClientBuilder private constructor() {

    companion object {

        //private const val API_BASE_URL = BuildConfig.HOST /*+ BuildConfig.API_URL_VALUE*/
        //private val API_BASE_URL = Constants.BASE_URL
        private var sClient: OkHttpClient? = null
        private const val CONNECT_TIMEOUT = 15
        private const val READ_TIMEOUT = 5
        private const val WRITE_TIMEOUT = 5

        private var clientBuilder: CodeGloClientBuilder? = null

        private val httpLoggingInterceptor = HttpLoggingInterceptor()
        private val apiInterceptor = ApiInterceptor()

        private var sRetrofit: Retrofit? = null

        private val sBuilder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())

        init {
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            }
        }

        fun getInstance(): CodeGloClientBuilder {

            if (clientBuilder == null) {

                clientBuilder = CodeGloClientBuilder()

                val httpClient = OkHttpClient.Builder()

                if (BuildConfig.DEBUG) {
                    httpClient.addInterceptor(httpLoggingInterceptor)
                }
                val builder = Retrofit.Builder().baseUrl(BuildConfig.HOST)
                        .addConverterFactory(GsonConverterFactory
                                .create(GsonBuilder().setLenient().create()))
                sRetrofit = builder.client(httpClient.build()).build()

            }

            return clientBuilder!!
        }

        fun <A> createApi(apiClass: Class<A>): A? {
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(5, TimeUnit.MINUTES)
            httpClient.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.MINUTES)
            httpClient.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MINUTES)
            httpClient.addInterceptor(apiInterceptor)
            httpClient.retryOnConnectionFailure(true)
            httpClient.protocols(arrayListOf(Protocol.HTTP_1_1))

            /* HTTPS SSL ByePass */
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

                    }

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.getSocketFactory()
            val trust = trustAllCerts[0] as X509TrustManager
            httpClient.sslSocketFactory(sslSocketFactory, trust)
            httpClient.hostnameVerifier(HostnameVerifier { hostname, session -> true })

            if (BuildConfig.DEBUG){
                httpClient.addInterceptor(httpLoggingInterceptor)
            }
            sClient = httpClient.build()
            sRetrofit = sBuilder
                .baseUrl(Constants.BASE_URL)
                .client(sClient!!).build()
            return sRetrofit?.create(apiClass)
        }

        private class UvCookieJar : CookieJar {

            private val cookies = mutableListOf<Cookie>()

            override fun saveFromResponse(url: HttpUrl, cookieList: List<Cookie>) {
                cookies.clear()
                cookies.addAll(cookieList)
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> =
                    cookies
        }

        interface ServicesApiInterface {
            companion object {
                fun fetchManagers(): FetchDataController? = createApi(FetchDataController::class.java)
            }
        }
    }
}
