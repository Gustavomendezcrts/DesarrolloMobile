package cl.duoc.leoley.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cl.duoc.leoley.R

@Composable
fun Logo(tamano: Dp) {
    Image(
        painter = painterResource(id = R.drawable.login),
        contentDescription = "Logo de LeoLey",
        modifier = Modifier
            .size(tamano)
            .clip(RoundedCornerShape(24.dp))
    )
}