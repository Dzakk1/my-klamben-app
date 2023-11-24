package com.example.myklamben.ui.view.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myklamben.R
import com.example.myklamben.ui.theme.MyKlambenTheme

@Composable
fun AboutView(
    modifier: Modifier = Modifier
) {

    val image = painterResource(R.drawable.profile)
    val name = stringResource(R.string.profile_name)
    val email = stringResource(R.string.profile_email)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column {
            Box(modifier = modifier) {
                Image(
                    painter = painterResource(R.drawable.bg_banner),
                    contentDescription = "Background Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(190.dp)
                        .fillMaxWidth()
                )

                Box(modifier = Modifier
                    .padding(top = 85.dp, bottom = 8.dp)
                    .size(175.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
                ) {
                    Image(painter = image,
                        contentDescription = "profile_image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds)
                }

            }
            Text(
                text = name,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            Text(
                text = email,
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun AboutPreview() {
    MyKlambenTheme {
        AboutView()
    }
}