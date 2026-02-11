package dev.mamkin.smartstep.app.di

import dev.mamkin.smartstep.core.data.repository.UserProfileRepositoryImpl
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import dev.mamkin.smartstep.feature.settings.presentation.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::UserProfileRepositoryImpl).bind<UserProfileRepository>()
    viewModelOf(::SettingsViewModel)
}