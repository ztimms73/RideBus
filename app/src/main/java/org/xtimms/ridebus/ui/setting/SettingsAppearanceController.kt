package org.xtimms.ridebus.ui.setting

import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceScreen
import kotlinx.coroutines.flow.launchIn
import org.xtimms.ridebus.R
import org.xtimms.ridebus.data.preference.PreferenceKeys
import org.xtimms.ridebus.data.preference.PreferenceValues
import org.xtimms.ridebus.data.preference.asImmediateFlow
import org.xtimms.ridebus.util.preference.*
import org.xtimms.ridebus.util.system.isTablet
import org.xtimms.ridebus.widget.preference.ThemesPreference
import java.util.*

//
// Created by Xtimms on 28.08.2021.
//
class SettingsAppearanceController : SettingsController() {

    override fun setupPreferenceScreen(screen: PreferenceScreen) = screen.apply {
        titleRes = R.string.pref_category_appearance

        preferenceCategory {
            titleRes = R.string.pref_category_theme

            listPreference {
                key = PreferenceKeys.themeMode
                titleRes = R.string.pref_theme_mode

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    entriesRes = arrayOf(
                        R.string.theme_system,
                        R.string.theme_light,
                        R.string.theme_dark
                    )
                    entryValues = arrayOf(
                        PreferenceValues.ThemeMode.system.name,
                        PreferenceValues.ThemeMode.light.name,
                        PreferenceValues.ThemeMode.dark.name
                    )
                    defaultValue = PreferenceValues.ThemeMode.system.name
                } else {
                    entriesRes = arrayOf(
                        R.string.theme_light,
                        R.string.theme_dark
                    )
                    entryValues = arrayOf(
                        PreferenceValues.ThemeMode.light.name,
                        PreferenceValues.ThemeMode.dark.name
                    )
                    defaultValue = PreferenceValues.ThemeMode.light.name
                }

                summary = "%s"
            }
            initThenAdd(ThemesPreference(context)) {
                key = PreferenceKeys.appTheme
                titleRes = R.string.pref_app_theme

                val appThemes = PreferenceValues.AppTheme.values().filter {
                    val monetFilter = if (it == PreferenceValues.AppTheme.MONET) {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                    } else {
                        true
                    }
                    it.titleResId != null && monetFilter
                }
                entries = appThemes
                defaultValue = appThemes[0].name

                onChange {
                    activity?.let { ActivityCompat.recreate(it) }
                    true
                }
            }
            switchPreference {
                key = PreferenceKeys.themeDarkAmoled
                titleRes = R.string.pref_dark_theme_pure_black
                defaultValue = false

                preferences.themeMode().asImmediateFlow { isVisible = it != PreferenceValues.ThemeMode.light }
                    .launchIn(viewScope)

                onChange {
                    activity?.let { ActivityCompat.recreate(it) }
                    true
                }
            }
        }

        preferenceCategory {
            titleRes = R.string.pref_category_navigation

            if (context.isTablet()) {
                intListPreference {
                    key = PreferenceKeys.sideNavIconAlignment
                    titleRes = R.string.pref_side_nav_icon_alignment
                    entriesRes = arrayOf(
                        R.string.alignment_top,
                        R.string.alignment_center,
                        R.string.alignment_bottom,
                    )
                    entryValues = arrayOf("0", "1", "2")
                    defaultValue = "0"
                    summary = "%s"
                }
            } else {
                switchPreference {
                    key = PreferenceKeys.hideBottomBarOnScroll
                    titleRes = R.string.pref_hide_bottom_bar_on_scroll
                    defaultValue = true
                }
            }
        }

        preferenceCategory {
            titleRes = R.string.pref_category_timestamps

            listPreference {
                key = PreferenceKeys.dateFormat
                titleRes = R.string.pref_date_format
                entryValues = arrayOf("", "MM/dd/yy", "dd/MM/yy", "yyyy-MM-dd", "dd MMM yyyy", "MMM dd, yyyy")

                val now = Date().time
                entries = entryValues.map { value ->
                    val formattedDate = preferences.dateFormat(value.toString()).format(now)
                    if (value == "") {
                        "${context.getString(R.string.system_default)} ($formattedDate)"
                    } else {
                        "$value ($formattedDate)"
                    }
                }.toTypedArray()

                defaultValue = ""
                summary = "%s"
            }
        }
    }
}