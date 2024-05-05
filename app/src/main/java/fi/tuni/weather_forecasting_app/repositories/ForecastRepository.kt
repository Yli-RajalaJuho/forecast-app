package fi.tuni.weather_forecasting_app.repositories

import fi.tuni.weather_forecasting_app.models.WeatherData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getInitialWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String,
        @Query("past_days") past: Int,
        @Query("forecast_days") forecast: Int
    ): WeatherData // Assuming WeatherData is your response data class
}

object ForecastRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getInitialWeatherForecast(
        latitude: Double, longitude: Double, hourly: String, past: Int, forecast: Int): WeatherData {
        return service.getInitialWeatherForecast(latitude, longitude, hourly, past, forecast)
    }
}
