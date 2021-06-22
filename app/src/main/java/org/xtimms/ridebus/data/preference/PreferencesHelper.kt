package org.xtimms.ridebus.data.preference

import android.content.Context
import androidx.preference.PreferenceManager
import com.tfcporciuncula.flow.FlowSharedPreferences
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.xtimms.ridebus.data.preference.PreferenceKeys as Keys
import org.xtimms.ridebus.data.preference.PreferenceValues as Values

fun <T> Preference<T>.asImmediateFlow(block: (T) -> Unit): Flow<T> {
    block(get())
    return asFlow()
        .onEach { block(it) }
}


class PreferencesHelper(val context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val flowPrefs = FlowSharedPreferences(prefs)

    fun confirmExit() = prefs.getBoolean(Keys.confirmExit, false)

    fun startScreen() = prefs.getInt(Keys.startScreen, 1)

    fun themeMode() = flowPrefs.getEnum(Keys.themeMode, Values.ThemeMode.system)

    fun themeLight() = flowPrefs.getEnum(Keys.themeLight, Values.LightThemeVariant.default)

    fun themeDark() = flowPrefs.getEnum(Keys.themeDark, Values.DarkThemeVariant.default)

    fun lastSearchQuerySearchSettings() = flowPrefs.getString("last_search_query", "")

    fun hideBottomBar() = flowPrefs.getBoolean(Keys.hideBottomBar, true)

}