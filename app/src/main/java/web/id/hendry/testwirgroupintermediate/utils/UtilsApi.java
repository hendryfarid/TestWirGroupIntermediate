package web.id.hendry.testwirgroupintermediate.utils;


public class UtilsApi {


    public static final String BASE_URL_API = "http://recruitment.devmind2.net/api/";


    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
