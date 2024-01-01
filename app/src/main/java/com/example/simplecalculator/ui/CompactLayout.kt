package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun CompactLayout(
    input: String,
    result: String,
    onClick: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .weight(2F)
                .fillMaxWidth()
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
        compactLayoutButtons.forEach { buttonsList ->
            ButtonsRow(
                buttons = buttonsList,
                onClick = onClick
            )
        }
    }
}


@Preview
@Composable
fun CompactLayoutPreview() {
    SimpleCalculatorTheme {
        Surface {
            CompactLayout(
                input = "1+2",
                result = "3",
                onClick = {}
            )
        }
    }
}