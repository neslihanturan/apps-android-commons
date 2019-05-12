package fr.free.nrw.commons.nearby.mvp;

/**
 * General View and UserAction methods are defined under
 * this interface. This interface can be considered parent
 * of both NearbyMapContract and NearbyListContract
 */
public interface NearbyContract {

    interface View {
        void showPlaces();
    }

    interface UserActions {
        void uploadImageGallery();
        void uploadImageCamera();
        void bookmarkItem();
        void getDirections();
        void seeWikidataItem();
        void seeWikipediaArticle();
        void seeCommonsFilePage();
        void rotateScreen();
    }
}
