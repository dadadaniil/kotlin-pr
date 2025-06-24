package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.AppDatabase
import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.network.TaskApiService
import com.example.todoapp.data.repository.TaskRepositoryImpl
import com.example.todoapp.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): TaskApiService = retrofit.create(TaskApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "tasks.db").build()

    @Provides
    fun provideDao(db: AppDatabase): TaskDao = db.taskDao()

    @Provides
    @Singleton
    fun provideRepository(api: TaskApiService, dao: TaskDao): TaskRepository =
        TaskRepositoryImpl(api, dao)
}
