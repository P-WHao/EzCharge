package my.edu.tarc.ezcharge.Location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.PumpCharging.*
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.FragmentLocationBinding
import my.edu.tarc.ezcharge.databinding.LayoutDrinkItemBinding

class LocationFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    //Location Items

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentLocationBinding

    private lateinit var db: DatabaseReference
    private lateinit var db2: DatabaseReference
    private lateinit var db3: DatabaseReference
    private lateinit var db4: DatabaseReference
    private lateinit var db5: DatabaseReference
    private lateinit var db6: DatabaseReference

//     private var locationArrayList: ArrayList<LatLng>? = null
//     private var distanceArrayList: ArrayList<Float>? = null

    private val EzCharge1 = LatLng(3.203856, 101.736902)
    private val EzCharge2 = LatLng(3.215155, 101.729247)
    private val EzCharge3 = LatLng(3.201750, 101.717668)
    private val EzCharge4 = LatLng(3.148622, 101.762434)
    private val EzCharge5 = LatLng(3.184683, 101.746089)
    private val EzCharge6 = LatLng(3.146254, 101.695883)

    private var distance1: Float = 0.0f
    private var distance2: Float = 0.0f
    private var distance3: Float = 0.0f
    private var distance4: Float = 0.0f
    private var distance5: Float = 0.0f
    private var distance6: Float = 0.0f

    private var markerEzCharge1: Marker? = null
    private var markerEzCharge2: Marker? = null
    private var markerEzCharge3: Marker? = null
    private var markerEzCharge4: Marker? = null
    private var markerEzCharge5: Marker? = null
    private var markerEzCharge6: Marker? = null

    private lateinit var homeLocation: LatLng
    //val EzCharge1 = LatLng(3.203856, 101.736902)
    //val EzCharge2 = LatLng(3.215155, 101.729247)
    //val EzCharge3 = LatLng(3.201750, 101.717668)

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = FragmentLocationBinding.inflate(layoutInflater)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

        // Search locations
        binding.imageSearch.setOnClickListener {
            val intent =
                Intent(this@LocationFragment.requireContext(), LocationListActivity::class.java)
            //intent.putExtra(EXTRA_BMI, bmi)
            startActivity(intent)

        }

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()

        //Assign location
        markerEzCharge1 =
            mMap.addMarker(MarkerOptions().position(EzCharge1).title("Ez Charge Wangsa Maju"))
        markerEzCharge1 = mMap.addMarker(
            MarkerOptions().position(EzCharge1).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.icon_location)
            )
        )


        markerEzCharge2 =
            mMap.addMarker(MarkerOptions().position(EzCharge2).title("Ez Charge Taman Bunga Raya"))
        markerEzCharge2 = mMap.addMarker(
            MarkerOptions().position(EzCharge2).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.icon_location)
            )
        )

        markerEzCharge3 =
            mMap.addMarker(MarkerOptions().position(EzCharge3).title("Ez Charge Danau Kota"))
        markerEzCharge3 = mMap.addMarker(
            MarkerOptions().position(EzCharge3).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.icon_location)
            )
        )

        markerEzCharge4 =
            mMap.addMarker(MarkerOptions().position(EzCharge4).title("Ez Charge Ampang Jaya"))
        markerEzCharge4 = mMap.addMarker(
            MarkerOptions().position(EzCharge4).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.icon_location)
            )
        )

        markerEzCharge5 =
            mMap.addMarker(MarkerOptions().position(EzCharge5).title("Ez Charge Setiawangsa"))
        markerEzCharge5 = mMap.addMarker(
            MarkerOptions().position(EzCharge5).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.icon_location)
            )
        )

        markerEzCharge6 =
            mMap.addMarker(MarkerOptions().position(EzCharge6).title("Ez Charge City Centre"))
        markerEzCharge6 = mMap.addMarker(
            MarkerOptions().position(EzCharge6).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.icon_location)
            )
        )
//        mMap.addMarker(MarkerOptions().position(locationArrayList!![0]).title(String.format("Ez Charge Wangsa Maju")).snippet("Type"))
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!!.get(0)))
//
//        mMap.addMarker(MarkerOptions().position(locationArrayList!![1]).title(String.format("Ez Charge Taman Bunga Raya")))
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!!.get(1)))
//
//        mMap.addMarker(MarkerOptions().position(locationArrayList!![2]).title(String.format("Ez Charge Danau Kota")))
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!!.get(2)))

        //Display nearest locations


    }

//    override fun onMarkerClick(marker: Marker?): Boolean {
//        //handle click here
//        binding.btnOutputName.text = String.format("%s",marker.title)
//
//    }

    private fun setUpMap() {

        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->

            homeLocation = LatLng(location.latitude, location.longitude)


            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                displayDistance((currentLatLong))
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))

            }

        }

    }

    private fun displayDistance(currentLatLong: LatLng) {

        distance1 = calculateDistance(
            currentLatLong.latitude,
            currentLatLong.longitude,
            EzCharge1.latitude,
            EzCharge1.longitude
        )
        distance2 = calculateDistance(
            currentLatLong.latitude,
            currentLatLong.longitude,
            EzCharge2.latitude,
            EzCharge2.longitude
        )
        distance3 = calculateDistance(
            currentLatLong.latitude,
            currentLatLong.longitude,
            EzCharge3.latitude,
            EzCharge3.longitude
        )
        distance4 = calculateDistance(
            currentLatLong.latitude,
            currentLatLong.longitude,
            EzCharge4.latitude,
            EzCharge4.longitude
        )
        distance5 = calculateDistance(
            currentLatLong.latitude,
            currentLatLong.longitude,
            EzCharge5.latitude,
            EzCharge5.longitude
        )
        distance6 = calculateDistance(
            currentLatLong.latitude,
            currentLatLong.longitude,
            EzCharge6.latitude,
            EzCharge6.longitude
        )
        //var totalDistance: Float =  calculateDistance(currentLatLong.latitude,currentLatLong.longitude,EzCharge1.latitude, EzCharge1.longitude)

        db = FirebaseDatabase.getInstance().getReference("Location")
        db2 = FirebaseDatabase.getInstance().getReference("Location")
        db3 = FirebaseDatabase.getInstance().getReference("Location")
        db4 = FirebaseDatabase.getInstance().getReference("Location")
        db5 = FirebaseDatabase.getInstance().getReference("Location")
        db6 = FirebaseDatabase.getInstance().getReference("Location")

        db.child("L1").child("distance").setValue(distance1 / 1000)
        db2.child("L2").child("distance").setValue(distance2 / 1000)
        db3.child("L3").child("distance").setValue(distance3 / 1000)
        db4.child("L4").child("distance").setValue(distance4 / 1000)
        db5.child("L5").child("distance").setValue(distance5 / 1000)
        db6.child("L6").child("distance").setValue(distance6 / 1000)
        //Write distance to firebase

        //Calculate the shortest distance to current location
        val distanceList = listOf(distance1, distance2, distance3, distance4, distance5, distance6)

        val shortest = findMin(distanceList)

        binding.btnOutputKm.text = String.format("%.2fkm", shortest / 1000)

        binding.btnViewNearest.setOnClickListener {

            binding.btnOutputKm.text = String.format("%.2fkm", shortest / 1000)

            if (shortest == distance1) {

                binding.btnOutputName.text = String.format("Ez Charge Wangsa Maju")

            } else if (shortest == distance2) {
                binding.btnOutputName.text = String.format("Ez Charge Taman Bunga Raya")
            } else if (shortest == distance3) {
                binding.btnOutputName.text = String.format("Ez Charge Danau Kota")
            } else if (shortest == distance4) {
                binding.btnOutputName.text = String.format("Ez Charge Ampang Jaya")
            } else if (shortest == distance5) {
                binding.btnOutputName.text = String.format("Ez Charge Setiawangsa")
            } else if (shortest == distance6) {
                binding.btnOutputName.text = String.format("Ez Charge Batu Bahat")
            }
        }

        if (shortest == distance1) {

            //binding.btnOutputName.text = String.format("Ez Charge Wangsa Maju")

            binding.btnOutputName.text = String.format("Ez Charge Wangsa Maju")

        } else if (shortest == distance2) {
            binding.btnOutputName.text = String.format("Ez Charge Taman Bunga Raya")
        } else if (shortest == distance3) {
            binding.btnOutputName.text = String.format("Ez Charge Danau Kota")
        } else if (shortest == distance4) {
            binding.btnOutputName.text = String.format("Ez Charge Ampang Jaya")
        } else if (shortest == distance5) {
            binding.btnOutputName.text = String.format("Ez Charge Setiawangsa")
        } else if (shortest == distance6) {
            binding.btnOutputName.text = String.format("Ez Charge Batu Bahat")
        }
    }

    fun findMin(list: List<Float>): Float {
        return list.reduce { a: Float, b: Float -> a.coerceAtMost(b) };
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)

        markerOptions.title("Your are here")
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        binding.btnOutputName.text = String.format("%s", marker.title)

        binding.btnStation.text = getString(R.string.select_station)

        if (marker.title == "Ez Charge Wangsa Maju") {
            binding.btnOutputKm.text = String.format("%.2fkm", distance1 / 1000)

            binding.btnGostation.setOnClickListener {
                val intent = Intent(this@LocationFragment.requireContext(), ChargingSnackSelectorActivity::class.java)
                intent.putExtra("EXTRA_location", marker.title)
                startActivity(intent)
            }
        }

        if (marker.title == "Ez Charge Taman Bunga Raya") {
            binding.btnOutputKm.text = String.format("%.2fkm", distance2 / 1000)

            binding.btnGostation.setOnClickListener {
                val intent = Intent(this@LocationFragment.requireContext(), ChargingSnackSelectorActivity::class.java)
                intent.putExtra("EXTRA_location", marker.title)
                startActivity(intent)
            }
        }

        if (marker.title == "Ez Charge Danau Kota") {
            binding.btnOutputKm.text = String.format("%.2fkm", distance3 / 1000)

            binding.btnGostation.setOnClickListener {
                val intent = Intent(this@LocationFragment.requireContext(), ChargingSnackSelectorActivity::class.java)
                intent.putExtra("EXTRA_location", marker.title)
                startActivity(intent)
            }
        }
        if (marker.title == "Ez Charge Ampang Jaya") {
            binding.btnOutputKm.text = String.format("%.2fkm", distance4 / 1000)

            binding.btnGostation.setOnClickListener {
                val intent = Intent(this@LocationFragment.requireContext(), ChargingSnackSelectorActivity::class.java)
                intent.putExtra("EXTRA_location", marker.title)
                startActivity(intent)
            }
        }
        if (marker.title == "Ez Charge Setiawangsa") {
            binding.btnOutputKm.text = String.format("%.2fkm", distance5 / 1000)

            binding.btnGostation.setOnClickListener {
                val intent = Intent(this@LocationFragment.requireContext(), ChargingSnackSelectorActivity::class.java)
                intent.putExtra("EXTRA_location", marker.title)
                startActivity(intent)
            }
        }

        if (marker.title == "Ez Charge City Centre") {
            binding.btnOutputKm.text = String.format("%.2fkm", distance6 / 1000)

            binding.btnGostation.setOnClickListener {
                val intent = Intent(this@LocationFragment.requireContext(), ChargingSnackSelectorActivity::class.java)
                intent.putExtra("EXTRA_location", marker.title)
                startActivity(intent)
            }
        }

        if (marker.title == "Your are here") {
            binding.btnOutputKm.text = ""
        }

        return false
    }


    //Calculate distance between two points
    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        // distance in meter
        return results[0]
    }
}