package com.example.mywheatherapp

import android.app.Activity
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.view.LayoutInflater
import com.example.mywheatherapp.databinding.ActivityMainBinding
import com.google.android.material.progressindicator.BaseProgressIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.BindException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//http://dataservice.accuweather.com/locations/v1/cities/search
//f7f0fbcadeb7755638b1929e42b22289

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchWeatherData("jaipur")
    }

    private fun fetchWeatherData(cityName:String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(APIInterface::class.java)
        val response = retrofit.getWeatherData("jaipur", "f7f0fbcadeb7755638b1929e42b22289", "metric")
        response.enqueue(object : Callback<WeatherApp> {
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val temperature = responseBody.main.temp.toString()
//                    val humidity = responseBody.main.humidity
//                    val windSpeed = responseBody.wind.speed
//                    val sunrise = responseBody.sys.sunrise
//                    val sunset = responseBody.sys.sunset
//                    val sealevel = responseBody.main.pressure
//                    val condition = responseBody.weather.firstOrNull()?.main?:"unknown"
//                    val maxTemp = responseBody.main.temp_max
//                    val minTemp = responseBody.main.temp_min
//                    binding.temperature.text = "$temperature ºC"
//                    binding.weather.text = condition
//                    binding.maxtemp.text = "Max Temp: $maxTemp ºC"
//                    binding.mintemp.text = "Min Temp: $minTemp ºC"
//                    binding.humidity.text = "$humidity"
//                    binding.windspeed.text = "$windSpeed"
//                    binding.sunrise.text = "$sunrise"
//                    binding.sunset.text = "$sunset"
//                    binding.sea.text = "$sealevel hPa"
//                    binding.conditions.text = condition
//                    binding.day.text = dayName(System.currentTimeMillis())
//                    binding.date.text = date()
                    //binding.cityname.text = "$cityName"
                    Log.d("TAG", "onResponse: $temperature")
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        }

    private fun date(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }

    fun dayName(timestamp: Long): String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))

    }
}