package com.example.rickandmorty.data.serviceLocator


import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.BuildConfig
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dao.AppDatabase
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.data.repository.EpisodeRepository
import com.example.rickandmorty.data.repository.FavoritesRepository
import com.example.rickandmorty.data.repository.LocationRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.java

val dataModule: Module = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { get<Retrofit>().create(ApiService::class.java) }
    single { provideDatabase(androidContext()) }
    single { get<AppDatabase>().favoriteCharactersDao() }
    single { CharacterRepository(get()) }
    single { EpisodeRepository(get()) }
    single { LocationRepository(get()) }
    single { FavoritesRepository(get()) }
}
fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient{
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()
}
fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "rickmorty_database")
        .fallbackToDestructiveMigration()
        .build()
}