package com.chicken.fast.road.di

import android.content.Context
import androidx.room.Room
import com.chicken.fast.road.data.Db
import com.chicken.fast.road.data.dao.RoundDao
import com.chicken.fast.road.data.repository.RoundRepository
import com.chicken.fast.road.data.repository.RoundRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

private const val DATABASE_NAME = "db"

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): Db {
        return Room.databaseBuilder(
            context,
            Db::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRoundDao(db: Db): RoundDao {
        return db.roundDao()
    }

    @Singleton
    @Provides
    fun provideRoundRepository(dao: RoundDao): RoundRepository =
        RoundRepositoryImpl(dao)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}