package com.example.composeclocks

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeclocks.clock.ClockComponent
import com.example.composeclocks.clock.TimePosition
import com.example.composeclocks.ui.theme.ComposeClocksTheme

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeClocksTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    CardList(timesList = viewModel.cityTimes.value)
                }
            }
        }
    }
}

@Composable
private fun CardList(
    timesList: List<Pair<TimePosition,String>>
) {
    LazyColumn {
        items(timesList) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ClockComponent(time = it.first)
                    Spacer(modifier = Modifier.requiredHeight(10.dp))
                    Text(
                        text = it.second,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.requiredHeight(10.dp))
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview(){
    CardList(
        timesList = listOf(
            Triple(12,20,43) to "China",
            Triple(14,24,43) to "Moscow",
            Triple(16,23,43) to "Paris",
            Triple(21,50,43) to "London",
        ))
}
