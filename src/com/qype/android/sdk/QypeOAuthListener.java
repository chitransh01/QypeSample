package com.qype.android.sdk;

/**
* Callback interface for the OAuth login process
*/
public interface QypeOAuthListener {
	/**
	 * Called when the user is logged in to Qype
	 */
	public void onQypeOAuthComplete();

	/**
	 * Called when the user has canceled the login process
	 */
	public void onQypeOAuthCancel();

	/**
	 * Called when the login process has failed
	 *
	 * @param errorCode
	 * @param description
	 * @param failingUrl
	 */
	public void onQypeOAuthError(int errorCode, String description, String failingUrl);
}
