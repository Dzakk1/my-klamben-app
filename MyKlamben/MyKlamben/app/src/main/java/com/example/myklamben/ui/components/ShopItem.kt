package com.example.myklamben.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myklamben.R
import com.example.myklamben.ui.theme.MyKlambenTheme

@Composable
fun ShopItem(
    image : Int,
    title: String,
    merk : String,
    price : Int,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .padding(3.dp)
    ) {
        Image(painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(190.dp)
                .clip(Shapes().medium)
        )

        Text(text = merk,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold)
        )
        Text(text = stringResource(R.string.price, price),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary)

    }
}

@Composable
@Preview(showBackground = true)
fun ItemsPreview() {
    MyKlambenTheme {
        ShopItem(R.drawable.item_10, "Sweater Rajut Dry Stretch", "Uniqlo" ,699000)
    }
}