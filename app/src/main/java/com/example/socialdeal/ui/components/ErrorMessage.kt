package com.example.socialdeal.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.ui.theme.SocialDealTheme
import com.example.socialdeal.ui.theme.TextStyles
import com.example.socialdeal.ui.values.ErrorMessageType

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorMessageType: ErrorMessageType
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Companion.Center
    ) {
        Text(
            text = stringResource(errorMessageType.stringResourceId),
            style = TextStyles.default
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorMessagePreview() {
    SocialDealTheme {
        ErrorMessage(
            errorMessageType = ErrorMessageType.UNKNOWN_ERROR
        )
    }
}