package com.example.socialdeal.ui.values

import androidx.annotation.StringRes
import com.example.socialdeal.R

enum class ErrorMessage(@param:StringRes val stringResourceId: Int) {
    UNKNOWN_ERROR(R.string.ooops_something_went_wrong),
    TEST(23)
}