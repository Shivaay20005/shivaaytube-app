package io.github.shivaay.shivaaytube.ui.components.donation

import androidx.annotation.StringRes
import io.github.shivaay.shivaaytube.R

internal const val GITHUB_SPONSORS_URL = "https://github.com/sponsors/Shivaay20005"
internal const val UPI_ID = "Shivaay20005@okaxis"

internal class CryptoWallet(
    @StringRes val name: Int,
    val ticker: String,
    val address: String,
)

/** The addresses the donations page lists; the donation prompt names the same ones. */
internal val DonationWallets = listOf(
    CryptoWallet(R.string.donation_currency_upi, "UPI", UPI_ID),
)
