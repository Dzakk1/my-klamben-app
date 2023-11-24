package com.example.myklamben.ui.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myklamben.di.Injection
import com.example.myklamben.model.OrderItem
import com.example.myklamben.ui.ViewModelFactory
import com.example.myklamben.ui.components.Search
import com.example.myklamben.ui.components.ShopItem
import com.example.myklamben.ui.state.UiState



@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    viewModel : HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllItems()
            }
            is UiState.Success -> {


                Column {

                    Search(
                        query = query,
                        onQueryChange = viewModel::search,
                        modifier = Modifier
                    )

                    HomeContent(
                        orderItem = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderItem: List<OrderItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("OrderItemList")
    ) {
        items(orderItem) { data ->
            ShopItem(
                image = data.shopItem.image,
                title = data.shopItem.title,
                merk = data.shopItem.merk,
                price = data.shopItem.price,
                modifier = Modifier
                    .clickable { navigateToDetail(data.shopItem.id)
                }
            )
        }
    }
}
