/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.mindorks.framework.mvvm.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.di.PreferenceInfo;
import com.mindorks.framework.mvvm.utils.AppConstants;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";

    private static final String PREF_KEY_CURRENT_USER_FNAME = "PREF_KEY_CURRENT_USER_FNAME";

    private static final String PREF_KEY_CURRENT_USER_LNAME = "PREF_KEY_CURRENT_USER_LNAME";

    private static final String PREF_KEY_CURRENT_USER_BIRTH = "PREF_KEY_CURRENT_USER_BIRTH";

    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private static final String PREF_KEY_CURRENT_USER_PASS = "PREF_KEY_USER_PASS_IN_MODE";
    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public Long getCurrentUserId() {
        long userId = mPrefs.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX);
        return userId == AppConstants.NULL_INDEX ? null : userId;
    }

    @Override
    public void setCurrentUserId(Long userId) {
        long id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putLong(PREF_KEY_CURRENT_USER_ID, id).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getCurrentUserFName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_FNAME, null);
    }

    @Override
    public void setCurrentUserFName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_FNAME, userName).apply();
    }

    @Override
    public String getCurrentUserLName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_LNAME, null);
    }

    @Override
    public void setCurrentUserLName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_LNAME, userName).apply();
    }

    @Override
    public String getCurrentUserBirth() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_BIRTH, null);
    }

    @Override
    public void setCurrentUserBirth(String date) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_BIRTH, date).apply();
    }

    @Override
    public String getCurrentUserPass() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PASS, null);

    }

    @Override
    public void setCurrentUserPass(String Pass) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PASS, Pass).apply();
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }
}
