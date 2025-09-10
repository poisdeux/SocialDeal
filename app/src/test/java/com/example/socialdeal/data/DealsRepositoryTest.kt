package com.example.socialdeal.data

import com.example.socialdeal.data.network.ApiClient
import com.example.socialdeal.data.repositories.DealsRepository
import com.example.socialdeal.data.utilities.Result
import kotlinx.coroutines.test.runTest
import org.junit.Assert.fail
import org.junit.Test

class DealsRepositoryTest {

    @Test
    fun `test retrieving list of deals`() = runTest {
        val dealsRepository = DealsRepository.getInstance(ApiClient.getInstance())
        val deals = dealsRepository.getDeals()
        (deals as? Result.Success)?.let { deals ->
            assert(deals.result.deals?.isNotEmpty() == true)
            assert(deals.result.deals?.size == deals.result.numDeals)
        } ?: fail("Failed to retrieve deals: ${deals as Result.Failure}")
    }
}