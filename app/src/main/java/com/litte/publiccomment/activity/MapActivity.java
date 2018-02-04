package com.litte.publiccomment.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.litte.publiccomment.R;
import com.litte.publiccomment.bean.BusinessBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends Activity {

    BusinessBean.BusinessesBean business;

    @BindView(R.id.mapView)
    MapView mapView;

    BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        business = (BusinessBean.BusinessesBean) getIntent().getSerializableExtra("business");
        ButterKnife.bind(this);
        baiduMap = mapView.getMap();
        //更改地图默认的比例尺（5km---->100m）
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomBy(16);
        showAddress();
    }

    //在百度地图上显示地址 //经纬度定位
    private void showAddress() {
        //1、根据商户地址查询其所在的经纬度（地理编码查询）
        //（根据经纬度反查地址 ---称为反向地理编码查询）
        GeoCoder geoCoder = GeoCoder.newInstance();//地理编码
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            /**
             * 地理编码查询
             * @param geoCodeResult
             */
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                //TODO
                if (geoCodeResult == null && geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR){
                    Toast.makeText(MapActivity.this, "服务器繁忙，请稍后再试！", Toast.LENGTH_SHORT).show();
                }else {
                    //地址所对应的经纬度
                    LatLng location = geoCodeResult.getLocation();
                    //2、在location 所对应的经纬度上插一个标志物
                    MarkerOptions option = new MarkerOptions();
                    option.position(location);
                    option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_locate));
                    baiduMap.addOverlay(option);
                    //3、移动屏幕中心点和location所对应的位置
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(location);
                    baiduMap.animateMapStatus(mapStatusUpdate);
                    //4、添加一个信息窗+
                    TextView tv = new TextView(MapActivity.this);
                    tv.setPadding(8,8,8,8);
                    tv.setTextColor(Color.GRAY);
                    tv.setBackgroundColor(Color.BLUE);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP);
                    InfoWindow infoWindow = new InfoWindow(tv,location,-40);
                    baiduMap.showInfoWindow(infoWindow);
                }
            }

            /**
             * 反向地理编码查询
             * @param reverseGeoCodeResult
             */
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });
        //真正发起地理编码查询
        GeoCodeOption option = new GeoCodeOption();
        option.address(business.getAddress());
        option.city(business.getCity());
        geoCoder.geocode(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
}
