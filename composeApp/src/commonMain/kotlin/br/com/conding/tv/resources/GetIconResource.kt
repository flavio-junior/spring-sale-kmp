package br.com.conding.tv.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.arrow_back
import springsale.composeapp.generated.resources.arrow_forward
import springsale.composeapp.generated.resources.arrow_outward
import springsale.composeapp.generated.resources.arrow_upward
import springsale.composeapp.generated.resources.box
import springsale.composeapp.generated.resources.brand_awareness
import springsale.composeapp.generated.resources.campaign
import springsale.composeapp.generated.resources.check
import springsale.composeapp.generated.resources.close
import springsale.composeapp.generated.resources.create
import springsale.composeapp.generated.resources.delete
import springsale.composeapp.generated.resources.downward
import springsale.composeapp.generated.resources.draw
import springsale.composeapp.generated.resources.edit
import springsale.composeapp.generated.resources.filter
import springsale.composeapp.generated.resources.home_pin
import springsale.composeapp.generated.resources.label
import springsale.composeapp.generated.resources.lock
import springsale.composeapp.generated.resources.logout
import springsale.composeapp.generated.resources.mail
import springsale.composeapp.generated.resources.power_off
import springsale.composeapp.generated.resources.refresh
import springsale.composeapp.generated.resources.search
import springsale.composeapp.generated.resources.settings
import springsale.composeapp.generated.resources.user
import springsale.composeapp.generated.resources.visibility
import springsale.composeapp.generated.resources.visibility_off

/**
 * You can remove this and set icon inside each composable
 * @Composable
 * expect fun getIconResource(iconName: IconName): Painter
 */

//TODO You can remove this and set icon inside each composable
@Composable
internal fun getIconResource(iconName: IconName): Painter {
    return when (iconName) {
        IconName.ARROW_BACK -> painterResource(resource = Res.drawable.arrow_back)
        IconName.ARROW_FORWARD -> painterResource(resource = Res.drawable.arrow_forward)
        IconName.ARROW_OUTWARD -> painterResource(resource = Res.drawable.arrow_outward)
        IconName.BOX -> painterResource(resource = Res.drawable.box)
        IconName.BRAND_AWARENESS -> painterResource(resource = Res.drawable.brand_awareness)
        IconName.CAMPAIGN -> painterResource(resource = Res.drawable.campaign)
        IconName.CREATE -> painterResource(resource = Res.drawable.create)
        IconName.DELETE -> painterResource(resource = Res.drawable.delete)
        IconName.CHECK_BOX -> painterResource(resource = Res.drawable.check)
        IconName.CLOSE -> painterResource(resource = Res.drawable.close)
        IconName.DOWNWARD -> painterResource(resource = Res.drawable.downward)
        IconName.DRAW -> painterResource(resource = Res.drawable.draw)
        IconName.EDIT -> painterResource(resource = Res.drawable.edit)
        IconName.FILTER -> painterResource(resource = Res.drawable.filter)
        IconName.HOME_PIN -> painterResource(resource = Res.drawable.home_pin)
        IconName.LABEL -> painterResource(resource = Res.drawable.label)
        IconName.LOCK -> painterResource(resource = Res.drawable.lock)
        IconName.LOGOUT -> painterResource(resource = Res.drawable.logout)
        IconName.MAIL -> painterResource(resource = Res.drawable.mail)
        IconName.POWER_OFF -> painterResource(resource = Res.drawable.power_off)
        IconName.REFRESH -> painterResource(resource = Res.drawable.refresh)
        IconName.SEARCH -> painterResource(resource = Res.drawable.search)
        IconName.SETTINGS -> painterResource(resource = Res.drawable.settings)
        IconName.ARROW_UPWARD -> painterResource(resource = Res.drawable.arrow_upward)
        IconName.VISIBILITY -> painterResource(resource = Res.drawable.visibility)
        IconName.VISIBILITY_OFF -> painterResource(resource = Res.drawable.visibility_off)
        IconName.USER -> painterResource(resource = Res.drawable.user)
    }
}
