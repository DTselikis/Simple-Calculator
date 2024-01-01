package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.R
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@Composable
fun MediumLayout(
    input: String,
    result: String,
    modifier: Modifier = Modifier,
    onClick: (CalculatorAction) -> Unit,
) {
    Row(modifier = modifier) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(mediumLayoutButtons) { buttonsList ->
                ButtonsRow(
                    buttons = buttonsList,
                    onClick = onClick
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f, true)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = input,
                    style = MaterialTheme.typography.titleMedium
                )
                if (result.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.result, result),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f, true)
        ) {
            items(mediumLayoutOperationButtons) { button ->
                CalculatorButton(
                    symbol = button.symbol
                ) {
                    onClick(button.action)
                }
            }
        }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun MediumLayoutPreview() {
    SimpleCalculatorTheme {
        Surface {
            MediumLayout(
                input = "1+2",
                result = "3",
                onClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}