package com.yilmaz.stackapiapp.di

import android.content.Context
import androidx.room.Room
import com.yilmaz.stackapiapp.data.local.db.AppDatabase
import com.yilmaz.stackapiapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext contex: Context
    ) = Room.databaseBuilder(
        contex,
        AppDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideQuestionsDAO(
        db: AppDatabase
    ) = db.questionsDAO()

    @Provides
    @Singleton
    fun provideQuestionKeysDAO(
        db: AppDatabase
    ) = db.questionKeysDAO()

}