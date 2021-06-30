package com.lilcode.aop.p3c07.airbnb

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val mapView: MapView by lazy {
        findViewById(R.id.mapView)
    }

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.houseViewPager)
    }
    private val viewPagerAdapter = HouseViewPagerAdapter()
    private val recyclerViewAdapter = HouseListAdapter()

    private val recyclerView:RecyclerView by lazy{
        findViewById(R.id.recyclerView)
    }

    private val currentLocationButton:LocationButtonView by lazy{
        findViewById(R.id.currentLocationButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // onCreate 연결
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        viewPager.adapter = viewPagerAdapter
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        // page 변경시 처리
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val selectedHouseModel = viewPagerAdapter.currentList[position]
                val cameraUpdate = CameraUpdate.scrollTo(LatLng(selectedHouseModel.lat,selectedHouseModel.lng))
                    .animate(CameraAnimation.Easing)
                naverMap.moveCamera(cameraUpdate)
            }
        })
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.497801, 127.027591))
        naverMap.moveCamera(cameraUpdate)

        // 현위치 기능
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false

        currentLocationButton.map = naverMap

        locationSource =
            FusedLocationSource(this@MainActivity, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource

//        val marker = Marker()
//        marker.position = LatLng(37.500493, 127.029740)
//        marker.map = naverMap
//        marker.icon = MarkerIcons.BLACK
//        marker.iconTintColor = Color.RED

        // 지도 다 로드 이후에 가져오기
        getHouseListFromAPI()
    }

    private fun getHouseListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()

        retrofit.create(HouseService::class.java).also {
            it.getHouseList()
                .enqueue(object : Callback<HouseDto> {
                    override fun onResponse(call: Call<HouseDto>, response: Response<HouseDto>) {
                        if (response.isSuccessful.not()) {
                            // fail
                            Log.d("Retrofit", "실패1")

                            return
                        }

                        response.body()?.let { dto ->
                            updateMarker(dto.items)
                            viewPagerAdapter.submitList(dto.items)
                            recyclerViewAdapter.submitList(dto.items)
                        }
                    }

                    override fun onFailure(call: Call<HouseDto>, t: Throwable) {
                        // 실패 처리 구현;
                        Log.d("Retrofit", "실패2")
                        Log.d("Retrofit", t.stackTraceToString())
                    }

                })
        }
    }

    private fun updateMarker(houses: List<HouseModel>) {
        houses.forEach { house ->

            val marker = Marker()
            marker.position = LatLng(house.lat, house.lng)
            // todo marker.onClickListener
            marker.map = naverMap
            marker.tag = house.id
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE)
            return

        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}