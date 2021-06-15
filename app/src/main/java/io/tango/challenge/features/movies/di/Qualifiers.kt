package io.tango.challenge.features.movies.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieAPIMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieDBMapper