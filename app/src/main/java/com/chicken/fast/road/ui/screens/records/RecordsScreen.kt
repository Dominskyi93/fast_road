package com.chicken.fast.road.ui.screens.records

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chicken.fast.road.R
import com.chicken.fast.road.data.entity.Round
import com.chicken.fast.road.ui.base.BorderedTitle

@Composable
fun RecordsScreen(viewModel: RecordsVM = hiltViewModel()) {
    val records by viewModel.records.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.getAll()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(R.drawable.img_box_bg),
                contentDescription = null
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.offset(y =



























































                30.dp)
            ) {
                BorderedTitle(text = "STATS", modifier = Modifier)

                if (records.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 50.dp)
                    ) {
                        itemsIndexed(
                            items = records,
                            key = { _, item ->
                                item.id
                            }
                        ) { index, item ->
                            RecordItem(item, index + 1)
                        }
                    }
                } else {
                    Text(
                        text = "The list of records is empty",
                        color = Color.Black
                    )
                }
            }

        }
    }
}

@Composable
fun RecordItem(round: Round, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$index st.",
            fontWeight = FontWeight.W900,
            fontSize = 18.sp,
            color = Color.White
        )

        Row(horizontalArrangement = Arrangement.Start) {
            Image(
                painter = painterResource(R.drawable.img_coin),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${round.score}",
                fontWeight = FontWeight.W900,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}