package com.example.myklamben.ui.view.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myklamben.R
import com.example.myklamben.di.Injection
import com.example.myklamben.ui.ViewModelFactory
import com.example.myklamben.ui.components.Cart
import com.example.myklamben.ui.components.OrderButton
import com.example.myklamben.ui.state.UiState


@Composable
fun CartView(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked : (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderItems()
            }

            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = {itemId, count ->
                        viewModel.updateOrderItem(itemId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state : CartState,
    onProductCountChanged : (id : Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderItem.count(),
        state.totalPrice
    )
    Column (
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(R.string.menu_cart),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(state.orderItem, key = { it.shopItem.id}) {item ->
                Cart(
                    itemId = item.shopItem.id,
                    image = item.shopItem.image,
                    title = item.shopItem.title,
                    totalPrice = item.shopItem.price * item.count,
                    counter = item.count,
                    onProductCountChange = onProductCountChanged,
                )
                Divider()
            }
        }

        OrderButton(text = stringResource(R.string.total_order, state.totalPrice),
            enabled = state.orderItem.isNotEmpty(),
            onClick = {
                      onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }

}