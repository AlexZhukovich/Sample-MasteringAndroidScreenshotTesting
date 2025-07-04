package com.alexzh.moodtracker.domain

import androidx.compose.ui.graphics.Color
import com.alexzh.moodtracker.ui.theme.additionalPastelAquaDark
import com.alexzh.moodtracker.ui.theme.additionalPastelAquaLight
import com.alexzh.moodtracker.ui.theme.additionalPastelBeigeDark
import com.alexzh.moodtracker.ui.theme.additionalPastelBeigeLight
import com.alexzh.moodtracker.ui.theme.additionalPastelBlueDark
import com.alexzh.moodtracker.ui.theme.additionalPastelBlueLight
import com.alexzh.moodtracker.ui.theme.additionalPastelBrownDark
import com.alexzh.moodtracker.ui.theme.additionalPastelBrownLight
import com.alexzh.moodtracker.ui.theme.additionalPastelCoralDark
import com.alexzh.moodtracker.ui.theme.additionalPastelCoralLight
import com.alexzh.moodtracker.ui.theme.additionalPastelCyanDark
import com.alexzh.moodtracker.ui.theme.additionalPastelCyanLight
import com.alexzh.moodtracker.ui.theme.additionalPastelFuchsiaDark
import com.alexzh.moodtracker.ui.theme.additionalPastelFuchsiaLight
import com.alexzh.moodtracker.ui.theme.additionalPastelGreenDark
import com.alexzh.moodtracker.ui.theme.additionalPastelGreenLight
import com.alexzh.moodtracker.ui.theme.additionalPastelGreyDark
import com.alexzh.moodtracker.ui.theme.additionalPastelGreyLight
import com.alexzh.moodtracker.ui.theme.additionalPastelLavenderDark
import com.alexzh.moodtracker.ui.theme.additionalPastelLavenderLight
import com.alexzh.moodtracker.ui.theme.additionalPastelLemonDark
import com.alexzh.moodtracker.ui.theme.additionalPastelLemonLight
import com.alexzh.moodtracker.ui.theme.additionalPastelMagentaDark
import com.alexzh.moodtracker.ui.theme.additionalPastelMagentaLight
import com.alexzh.moodtracker.ui.theme.additionalPastelMintDark
import com.alexzh.moodtracker.ui.theme.additionalPastelMintLight
import com.alexzh.moodtracker.ui.theme.additionalPastelOrangeDark
import com.alexzh.moodtracker.ui.theme.additionalPastelOrangeLight
import com.alexzh.moodtracker.ui.theme.additionalPastelPeachDark
import com.alexzh.moodtracker.ui.theme.additionalPastelPeachLight
import com.alexzh.moodtracker.ui.theme.additionalPastelPinkDark
import com.alexzh.moodtracker.ui.theme.additionalPastelPinkLight
import com.alexzh.moodtracker.ui.theme.additionalPastelPurpleDark
import com.alexzh.moodtracker.ui.theme.additionalPastelPurpleLight
import com.alexzh.moodtracker.ui.theme.additionalPastelRedDark
import com.alexzh.moodtracker.ui.theme.additionalPastelRedLight
import com.alexzh.moodtracker.ui.theme.additionalPastelVioletDark
import com.alexzh.moodtracker.ui.theme.additionalPastelVioletLight
import com.alexzh.moodtracker.ui.theme.additionalPastelYellowDark
import com.alexzh.moodtracker.ui.theme.additionalPastelYellowLight

enum class PastelAccentColor(
    private val lightColor: Color,
    private val darkColor: Color
) {
    BLUE(additionalPastelBlueLight, additionalPastelBlueDark),
    GREEN(additionalPastelGreenLight, additionalPastelGreenDark),
    VIOLET(additionalPastelVioletLight, additionalPastelVioletDark),
    PINK(additionalPastelPinkLight, additionalPastelPinkDark),
    ORANGE(additionalPastelOrangeLight, additionalPastelOrangeDark),
    YELLOW(additionalPastelYellowLight, additionalPastelYellowDark),
    BROWN(additionalPastelBrownLight, additionalPastelBrownDark),
    GREY(additionalPastelGreyLight, additionalPastelGreyDark),
    RED(additionalPastelRedLight, additionalPastelRedDark),
    PURPLE(additionalPastelPurpleLight, additionalPastelPurpleDark),
    MINT(additionalPastelMintLight, additionalPastelMintDark),
    CYAN(additionalPastelCyanLight, additionalPastelCyanDark),
    MAGENTA(additionalPastelMagentaLight, additionalPastelMagentaDark),
    LAVENDER(additionalPastelLavenderLight, additionalPastelLavenderDark),
    PEACH(additionalPastelPeachLight, additionalPastelPeachDark),
    BEIGE(additionalPastelBeigeLight, additionalPastelBeigeDark),
    FUCHSIA(additionalPastelFuchsiaLight, additionalPastelFuchsiaDark),
    LEMON(additionalPastelLemonLight, additionalPastelLemonDark),
    AQUA(additionalPastelAquaLight, additionalPastelAquaDark),
    CORAL(additionalPastelCoralLight, additionalPastelCoralDark);

    companion object {
        fun getPastelAccentColorsByName(
            name: String?,
            defaultColor: PastelAccentColor = GREY
        ): PastelAccentColor {
            return PastelAccentColor.entries.firstOrNull { it.name == name } ?: defaultColor
        }
    }

    fun getColor(isDark: Boolean): Color = if (isDark) darkColor else lightColor
}