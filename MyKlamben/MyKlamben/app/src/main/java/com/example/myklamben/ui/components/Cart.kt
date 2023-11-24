package com.example.myklamben.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
fun Cart(
    itemId : Long,
    image : Int,
    title: String,
    totalPrice : Int,
    counter : Int,
    onProductCountChange : (id : Long, count : Int) -> Unit,
    modifier : Modifier = Modifier
) {
    Row (
        modifier = modifier.fillMaxWidth()
    ){
        Image(painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(Shapes().small)
        )
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {


            Text(text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(text = stringResource(R.string.price, totalPrice),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        ProductCounter(orderId = itemId,
            orderCount = counter,
            onProductIncreased = {onProductCountChange(itemId,  counter + 1)},
            onProductDecreased = {onProductCountChange(itemId, counter - 1)},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    MyKlambenTheme {
        Cart(
            4, R.drawable.item_10, "Sweater Rajut", 899000, 0,
            onProductCountChange = { rewardId, count -> },
        )
    }
}