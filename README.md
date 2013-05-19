# Qype API SDK for Android

[Register a new application](http://www.qype.co.uk/developers/api) on this site to get a **App key** and a **App Secret**.

## Download and install the Android SDK

* Install Eclipse if you don't have it already
* Install Android SDK, [See the documentation](http://developer.android.com/sdk/index.html)
* Install the Eclipse Plugin, [See the documentation](http://developer.android.com/sdk/eclipse-adt.html)
* For Emulator testing, create Virtual devices, [See the documentation](http://developer.android.com/guide/developing/devices/managing-avds.html)
* Clone the project or download the repository as a zip file

## Create a new Android Project

* Open Eclipse
* Create a new Android Project : File > New > Project > Android Project
* Set a project name, for example "QypeSample"
* Select : "Create project from existing source"
* Specify the directory where you have unzip the QypeSample downloaded project
* Click "Finish"

## Add INTERNET permission

If it's not already done in your project, you must add the INTERNET permission in your **AndroidManifest** file. 

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Authentication

You need to provide to your users a system to connect to Qype. To do that, you just need to instantiate a Qype object with your **Application key** and **Application Secret** and call **authorize()**. Authorize method open a webpage in a dialog to prompt the user to login to his Qype account and grant the application to access to his account. This method is asynchronous and you have a callback interface for notifying the calling application when the authentication dialog has completed, failed, or been canceled.

```java
Qype mQype = new Qype(mContext, QYPE_APP_KEY, QYPE_APP_SECRET);
mQype.authorize(new QypeOAuthListener() {
	@Override public void onQypeOAuthComplete() {}

	@Override public void onQypeOAuthCancel() {}

	@Override public void onQypeOAuthError(int errorCode, String description, String failingUrl) {}
});
```

## Test if user is already logged in

To provide a more useful user experience and doesn't prompt for credentials all the time, you can use the **isLoggedIn()** method on the Qype object.

```java
if(mQype.isLoggedIn()) {
	// do something
}
```

