package com.klontong.mobileapp.apihelper;

/**
 * Created by Rifky on 29-Oct-18.
 */

public class UtilsApi {

    private static final String BASE_URL_API = "https://api-kl.000webhostapp.com/api/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
