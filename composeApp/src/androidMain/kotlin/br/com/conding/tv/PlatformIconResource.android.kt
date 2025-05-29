package br.com.conding.tv

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import br.com.conding.tv.resources.IconName

@Composable
actual fun getIconResource(iconName: IconName): Painter {
    return when (iconName) {
        IconName.ARROW_BACK -> painterResource(id = R.drawable.arrow_back)
        IconName.ARROW_FORWARD -> painterResource(id = R.drawable.arrow_forward)
        IconName.ARROW_OUTWARD -> painterResource(id = R.drawable.arrow_outward)
        IconName.BOX -> painterResource(id = R.drawable.box)
        IconName.BRAND_AWARENESS -> painterResource(id = R.drawable.brand_awareness)
        IconName.CAMPAIGN -> painterResource(id = R.drawable.campaign)
        IconName.CLOSE -> painterResource(id = R.drawable.close)
        IconName.DOWNWARD -> painterResource(id = R.drawable.downward)
        IconName.DRAW -> painterResource(id = R.drawable.draw)
        IconName.EDIT -> painterResource(id = R.drawable.edit)
        IconName.FILTER -> painterResource(id = R.drawable.filter)
        IconName.HOME_PIN -> painterResource(id = R.drawable.home_pin)
        IconName.LABEL -> painterResource(id = R.drawable.label)
        IconName.LOCK -> painterResource(id = R.drawable.lock)
        IconName.LOGOUT -> painterResource(id = R.drawable.logout)
        IconName.MAIL -> painterResource(id = R.drawable.mail)
        IconName.POWER_OFF -> painterResource(id = R.drawable.power_off)
        IconName.REFRESH -> painterResource(id = R.drawable.refresh)
        IconName.SEARCH -> painterResource(id = R.drawable.search)
        IconName.SETTINGS -> painterResource(id = R.drawable.settings)
        IconName.ARROW_UPWARD -> painterResource(id = R.drawable.arrow_upward)
        IconName.VISIBILITY -> painterResource(id = R.drawable.visibility)
        IconName.VISIBILITY_OFF -> painterResource(id = R.drawable.visibility_off)
    }
}
