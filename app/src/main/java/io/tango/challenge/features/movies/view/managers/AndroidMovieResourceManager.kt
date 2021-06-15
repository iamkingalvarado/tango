package io.tango.challenge.features.movies.view.managers

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.tango.challenge.R
import io.tango.challenge.features.movies.domain.managers.MovieResourceManager
import javax.inject.Inject

class AndroidMovieResourceManager @Inject constructor(
    @ApplicationContext context: Context
) : MovieResourceManager {
    override val upcoming: String = context.getString(R.string.item_upcoming_title)

    override val popular: String = context.getString(R.string.item_popular_title)
}
