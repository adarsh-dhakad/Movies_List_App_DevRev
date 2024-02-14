package com.devrev.app

import com.devrev.app.mapper.MovieMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModel { TopMoviesViewModel(get()) }
}
