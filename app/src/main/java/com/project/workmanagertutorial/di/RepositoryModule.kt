package com.project.workmanagertutorial.di

import com.project.workmanagertutorial.network.RandomUserRepository
import com.project.workmanagertutorial.network.RandomUserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRandomUserRepository(randomUserRepositoryImpl: RandomUserRepositoryImpl) : RandomUserRepository
}