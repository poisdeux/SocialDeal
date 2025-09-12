package com.example.socialdeal.ui.components

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.socialdeal.R
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal
import com.example.socialdeal.ui.theme.TextStyles
import com.example.socialdeal.ui.values.Price
import java.net.URL

@Composable
fun DealItem(
    modifier: Modifier = Modifier,
    deal: Deal,
    description: DealsRepositoryInterface.DealDescription? = null
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_default))
                .fillMaxWidth()
                .aspectRatio(1.77f)
                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large))),
            model = deal.imageUrl.toString(),
            contentScale = ContentScale.Crop,
            contentDescription = "Image of ${deal.title}"
        )

        Text(
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_default)),
            text = deal.title,
            style = TextStyles.title
        )

        Text(
            text = deal.company,
            style = TextStyles.label
        )

        Text(
            text = deal.city,
            style = TextStyles.label
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_default)),
        ) {
            Text(
                text = deal.sold,
                style = TextStyles.labelSold
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )
            Row {
                deal.originalPrice?.let { originalPrice ->
                    OriginalPrice(
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_default)),
                        price = originalPrice,
                        currencySymbol = deal.currencySymbol
                    )
                }
                deal.discountedPrice?.let { discountedPrice ->
                    DiscountedPrice(
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_default)),
                        price = discountedPrice,
                        currencySymbol = deal.currencySymbol
                    )
                }
            }
        }

        description?.let {
            Text(
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_default)),
                text = AnnotatedString.fromHtml(it.value),
                style = TextStyles.default
            )
        }
    }
}

@Composable
fun OriginalPrice(
    modifier: Modifier = Modifier,
    price: Price,
    currencySymbol: String
) {
    Text(
        modifier = modifier,
        text = currencySymbol+ price.integerValue + "," + price.decimalValue,
        style = TextStyles.fromPrice
    )
}

@Composable
fun DiscountedPrice(
    modifier: Modifier = Modifier,
    price: Price,
    currencySymbol: String
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = currencySymbol + price.integerValue,
            style = TextStyles.discountInteger
        )
        Text(
            text = "," + price.decimalValue,
            style = TextStyles.discountDecimal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DealItemPreview() {
    DealItem(
        modifier = Modifier.width(280.dp),
        deal = Deal(
            id = DealsRepositoryInterface.UniqueID("id"),
            imageUrl = URL("https://picsum.photos/200"),
            title = "Geweldige aanbieding!",
            company = "Het bedrijf",
            city = "De stad",
            sold = "Verkocht: 2.321",
            currencySymbol = NumberFormat.getCurrencyInstance().currency.symbol,
            originalPrice = Price(12.toBigInteger(), 33.toBigInteger()),
            discountedPrice = Price(9.toBigInteger(), 95.toBigInteger()),
        ),
        description = DealsRepositoryInterface.DealDescription("<p>Heb je zin om lekker onderuit te zakken in een goede bioscoopstoel tijdens een leuke film? Dan zit je hier helemaal goed. Bij Corendon Cinema heb je heerlijke bioscoopstoelen en een leuk aanbod van de nieuwste films. Daarnaast krijg je er ook nog popcorn en een drankje naar keuze bij! Een leuk uitje om te doen met vrienden, je gezin of je partner. Alvast veel plezier!<br /><br /><strong>Bioscooparrangement</strong></p><ul><li><strong>Filmticket</strong></li><li><strong>Popcorn medium</strong></li><li><strong>Drankje naar keuze</strong><br /><em>fris, bier of wijn</em></li></ul><p></p>")
    )
}