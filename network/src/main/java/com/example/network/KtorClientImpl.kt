package com.example.network

import com.example.network.models.RestaurantFilter
import com.example.network.models.RestaurantOpenStatus
import com.example.network.models.RestaurantResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val BASE_URL = ""

class KtorClientImpl : KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url(BASE_URL) }
        install(Logging) { logger = Logger.SIMPLE }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override suspend fun getRestaurants(): RestaurantResponse =
        client.get("restaurants").body()

    override suspend fun getRestaurantOpenStatus(restaurantId: String)
            : RestaurantOpenStatus = client.get("open/$restaurantId").body()

    override suspend fun getRestaurantFilter(filterId: String): RestaurantFilter =
        client.get("filter/$filterId").body()

    override suspend fun getRestaurantFilters(filterIds: List<String>)
            : List<RestaurantFilter> = filterIds.map { client.get("filter/$it").body() }
}
