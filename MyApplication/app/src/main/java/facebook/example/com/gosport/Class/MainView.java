package facebook.example.com.gosport.Class;

import com.facebook.AccessToken;

public interface MainView {
    void initializeFBSdk();

    void initializeView();

    void showFriendsList();

    void showFBLoginResult(AccessToken fbAccessToken);

    void loginUsingFBManager();
}
