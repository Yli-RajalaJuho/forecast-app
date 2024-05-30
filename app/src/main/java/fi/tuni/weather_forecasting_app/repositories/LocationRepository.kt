package fi.tuni.weather_forecasting_app.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

interface LocationClient {
    fun startLocationUpdates(callback: (Location?) -> Unit)
    fun stopLocationUpdates()

    class LocationException(message: String): Exception()
}

class LocationRepository(private val context: Context): LocationClient {
    private var locationCallback: LocationCallback? = null

    override fun stopLocationUpdates() {
        // Stop location updates
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        locationCallback?.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
    }

    override fun startLocationUpdates(callback: (Location?) -> Unit) {

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Location", "Permissions for location updates not granted!")
            throw LocationClient.LocationException("Missing location permissions")
        }

        // Check if GPS and network providers are enabled
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!isGpsEnabled && !isNetworkEnabled) {
            Log.d("Location", "Gps is disabled!")
            throw LocationClient.LocationException("Gps is disabled")
        }

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest.Builder(10000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setIntervalMillis(5000L)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { newLocation ->
                    callback(newLocation)
                }
            }
        }

        // fetch location
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback as LocationCallback,
            Looper.getMainLooper() // callback is done in the main thread
        )
    }
}
