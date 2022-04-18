package org.xtimms.ridebus.ui.favourite

import android.os.Bundle
import org.xtimms.ridebus.data.database.entity.Route
import org.xtimms.ridebus.data.preference.PreferencesHelper
import org.xtimms.ridebus.ui.base.presenter.BasePresenter
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class FavouritesPresenter(
    private val preferences: PreferencesHelper = Injekt.get()
) : BasePresenter<FavouritesController>() {

    var favourites = mutableListOf( // TODO
        Route(
            1,
            0,
            1,
            1,
            "1",
            "Тест",
            "Тест",
            "Тест",
            "Тест",
            "Тест",
            "Тест",
            "Тест",
            "Тест",
            0,
            0,
            0,
            0,
            0
        )
    )

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        loadFavourites()
    }

    private fun loadFavourites() {
        val pinnedFavourites = mutableListOf<FavouriteItem>()
        val pinnedFavouriteIds = preferences.pinnedFavourites().get()

        val byType = favourites.groupBy { it.kindId }
        var favouriteItems = byType.flatMap {
            val typeItem = TypeItem("Автобус") // TODO
            it.value.map { favourite ->
                val isPinned = favourite.routeId.toString() in pinnedFavouriteIds
                if (isPinned) {
                    pinnedFavourites.add(FavouriteItem(favourite, TypeItem(PINNED_KEY), isPinned))
                }
                FavouriteItem(favourite, typeItem, isPinned)
            }
        }

        if (pinnedFavourites.isNotEmpty()) {
            favouriteItems = pinnedFavourites + favouriteItems
        }

        view?.setFavourites(favouriteItems)
    }

    fun updateFavourites() {
        loadFavourites()
    }

    companion object {
        const val PINNED_KEY = "pinned"
    }
}
