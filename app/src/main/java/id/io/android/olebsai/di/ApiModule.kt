package id.io.android.olebsai.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.io.android.olebsai.BuildConfig
import id.io.android.olebsai.data.source.remote.address.AddressService
import id.io.android.olebsai.data.source.remote.basket.BasketService
import id.io.android.olebsai.data.source.remote.product.ProductService
import id.io.android.olebsai.data.source.remote.user.UserService
import id.io.android.olebsai.di.interceptor.AuthInterceptor
import id.io.android.olebsai.util.remote.MockNetworkInterceptor
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = BuildConfig.API_BASE_URL

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val collector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(collector)
            .maxContentLength(250_000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        authInterceptor: AuthInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        mockNetworkInterceptor: MockNetworkInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient =
        if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(mockNetworkInterceptor)
                .addInterceptor(chuckerInterceptor)
                .build()
        } else {
            OkHttpClient
                .Builder()
                .build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun providesProductService(retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun providesAddressService(retrofit: Retrofit): AddressService =
        retrofit.create(AddressService::class.java)

    @Singleton
    @Provides
    fun providesBasketService(retrofit: Retrofit): BasketService =
        retrofit.create(BasketService::class.java)
}