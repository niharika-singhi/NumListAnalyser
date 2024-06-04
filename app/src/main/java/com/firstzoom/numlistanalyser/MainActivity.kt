package com.firstzoom.numlistanalyser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.firstzoom.numlistanalyser.presentation.NumberListAnalyserViewModel
import com.firstzoom.numlistanalyser.ui.theme.NumListAnalyserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            NumListAnalyserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NumberOperationsScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberOperationsScreen(viewModel: NumberListAnalyserViewModel = hiltViewModel()) {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var text3 by remember { mutableStateOf("") }
    val intersectionResult = viewModel.intersectionResult.value
    val unionResult = viewModel.unionResult.value
    val highestNumberResult = viewModel.highestNumberResult.value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Number List Analyser") })
        }
    ) {paddingValues->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Enter list of comma separated numbers like 1,3,5,6,7 in each text box.",
                        fontSize = 13.sp)
                    TextFieldInput(label = "TextBox1", value = text1, onValueChange = { text1 = it })
                    TextFieldInput(label = "TextBox2", value = text2, onValueChange = { text2 = it })
                    TextFieldInput(label = "TextBox3", value = text3, onValueChange = { text3 = it })
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            val list1 = text1.split(",").map { it.trim().toIntOrNull() }
                                .filterNotNull() //takes only int
                            val list2 = text2.split(",").map { it.trim().toIntOrNull() }.filterNotNull()
                            val list3 = text3.split(",").map { it.trim().toIntOrNull() }.filterNotNull()

                            viewModel.calculateResults(listOf(list1, list2, list3))
                        }) {
                            Text("Calculate")
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = intersectionResult)
                    Text(text = unionResult)
                    Text(text = highestNumberResult)
                }
            }
        }
    }
}

@Composable
fun TextFieldInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    keyboardType: KeyboardType = KeyboardType.Number // Set default keyboard type
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}


@Preview(showBackground = true)
@Composable
fun NumberOperationsPreview() {
    NumListAnalyserTheme {
        NumberOperationsScreen()
    }
}

