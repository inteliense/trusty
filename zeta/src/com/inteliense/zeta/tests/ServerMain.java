package com.inteliense.zeta.tests;

import com.inteliense.zeta.server.*;
import org.json.simple.JSONObject;

public class ServerMain {

    public static void main(String[] args) throws APIException {

        APIServerConfig config = new APIServerConfig("127.0.0.1", 8181, "/api");
        config.setApiServerKeyPassword("password");
        config.setApiServerKeystorePath("/home/ryan/testkey.jks");
        config.setServerType(APIServerType.ZERO_TRUST_SYNC);
        config.setCorsPolicy(new CORSPolicy(true));
        config.setRateLimit(25);
        config.useDynamicApiKey(true);
        config.setSessionResourcePaths(
                "session/init",
                "session/keys",
                "session/close");

        APIKeyPair testKeyPair = APIKeyPair.generateNewPair();
        final String API_KEY = "apikey_JnM8qqLoh2CjEM773mMmEUQS3in5cq9VHRiw5iNnXcNeEbuPhqpDZG0OJR9hbtb1Hi8QBkaE";
        final String API_SECRET = "secret_cYM64CuorsmEkjXGNvmmdhtoSWMlrGq7zCD8Eo0O0jeKrfJ02GsAIwXr";
        System.out.println("API KEY = " + API_KEY);
        System.out.println("SECRET = " + API_SECRET);

        API api = new API(config) {

            @Override
            public APIKeyPair lookupApiKey(String apiKey) {


                    return new APIKeyPair(API_KEY, API_SECRET);

                //return null;

            }

        };

        api.start();

        api.addResource("query/new", new String[]{"sql", "parameters"}, new APIResource() {
            @Override
            public APIResponse execute(ClientSession clientSession, Parameters params, RequestHeaders headers) {

                if(params.getString("sql").equals("SQL") && params.getString("parameters").equals("PARAMS")) {

                    JSONObject obj = new JSONObject();
                    obj.put("response", "RESPONSE");

                    return new APIResponse(clientSession, obj, ResponseCode.SUCCESSFUL);

                }

                return new APIResponse(clientSession, "fSDFASDF", ResponseCode.REQUEST_FAILED);
            }

        });

    }

}