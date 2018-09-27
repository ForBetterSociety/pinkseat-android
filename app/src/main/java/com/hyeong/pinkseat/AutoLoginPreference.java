package com.hyeong.pinkseat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by JY on 2018-09-27.
 */

// <자동 로그인 관련 함수를 정의한 class>
public class AutoLoginPreference {

    static final String PREF_USER_ID = "id";
    static final String PREF_USER_PW = "pw";
    static final String PREF_USER_HO = "hospital";
    static final String PREF_USER_NAME = "name";
    static final String PREF_USER_DATE = "date";
    static final String PREF_USER_IDX = "user_idx";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUser(Context ctx, String id, String pw, String hospital, String name, String date, String idx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID, id);
        editor.putString(PREF_USER_PW, pw);
        editor.putString(PREF_USER_HO, hospital);
        editor.putString(PREF_USER_NAME, name);
        editor.putString(PREF_USER_DATE, date);
        editor.putString(PREF_USER_IDX, idx);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getID(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }
    public static String getPW(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_PW, "");
    }
    public static String getHospital(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_HO, "");
    }
    public static String getName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
    public static String getDate(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_DATE, "");
    }
    public static String getIdx(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_IDX, "");
    }

    // 로그아웃
    public static void clearLogin(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }

}
