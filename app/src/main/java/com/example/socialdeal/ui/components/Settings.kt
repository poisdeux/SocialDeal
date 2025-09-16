package com.example.socialdeal.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.R
import com.example.socialdeal.ui.mappers.convert
import com.example.socialdeal.ui.screens.MainScreenAction
import com.example.socialdeal.ui.theme.SocialDealTheme
import com.example.socialdeal.ui.values.CurrencyTypes

@Composable
fun Settings(
    modifier: Modifier,
    currencySetting: CurrencyTypes,
    onAction: (MainScreenAction) -> Unit
) {
    val currencyTypesStringHashMap = mutableMapOf<String, CurrencyTypes>()
    CurrencyTypes.entries.forEach {
        currencyTypesStringHashMap.put(stringResource(it.convert { it.value }), it)
    }

    ItemSelector(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_default)),
        label = stringResource(R.string.currency),
        selectedItem = stringResource(currencySetting.convert { it.value }),
        items = CurrencyTypes.entries.map { stringResource(it.convert { it.value }) }
    ) { itemSelected ->
        currencyTypesStringHashMap[itemSelected]?.let {
            onAction(MainScreenAction.CurrencySettingChanged(it))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsPreview() {
    SocialDealTheme {
        Settings(
            modifier = Modifier,
            currencySetting = CurrencyTypes.EURO,
            onAction = {}
        )
    }
}