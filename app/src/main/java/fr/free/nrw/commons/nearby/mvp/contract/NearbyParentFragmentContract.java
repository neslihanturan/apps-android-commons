package fr.free.nrw.commons.nearby.mvp.contract;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import fr.free.nrw.commons.location.LatLng;
import fr.free.nrw.commons.location.LocationServiceManager;

public interface NearbyParentFragmentContract {

    interface View {
        void setListFragmentExpanded();
        void refreshView();
        void registerLocationUpdates(LocationServiceManager locationServiceManager);
        boolean isNetworkConnectionEstablished();
        void addNetworkBroadcastReceiver();
        void listOptionMenuItemClicked();
        void  populatePlaces(LatLng curlatLng, LatLng searchLatLng);
        boolean isBottomSheetExpanded();
        void addSearchThisAreaButtonAction();
        void setSearchThisAreaButtonVisibility(boolean isVisible);
        void setSearchThisAreaProgressVisibility(boolean isVisible);
        void checkPermissionsAndPerformAction(Runnable runnable);
        void resumeFragment();
    }

    interface UserActions {
        void displayListFragmentExpanded();
        void onTabSelected();
        void initializeNearbyOperations();
        void updateMapAndList(LocationServiceManager.LocationChangeType locationChangeType, LatLng cameraTarget);
        void lockNearby(boolean isNearbyLocked);
        MapboxMap.OnCameraMoveListener onCameraMove(MapboxMap mapboxMap);
    }
    
    interface ViewsAreReadyCallback {
        void nearbyFragmentsAreReady();
        void nearbyMapViewReady();
    }
}
