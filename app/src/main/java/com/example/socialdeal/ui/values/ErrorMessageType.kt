package com.example.socialdeal.ui.values

import androidx.annotation.StringRes
import com.example.socialdeal.R

enum class ErrorMessageType(@param:StringRes val stringResourceId: Int) {
    UNKNOWN_ERROR(R.string.ooops_something_went_wrong)
}