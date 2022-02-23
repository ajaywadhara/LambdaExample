package com.wadhara.errorhandling;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpStatus;

import static com.wadhara.errorhandling.Constants.*;

public class LambdaFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        final LambdaLogger logger = context.getLogger();
        //log the entire event
        logger.log("API Full event:" + requestEvent.toString());

        //gets user details from POST call and saves that to DB

        //fetch the request body
        String body = requestEvent.getBody();

        final User user = gson.fromJson(body, User.class);

        //client check - if username and id are not null

        if(StringUtils.isNullOrEmpty(user.getUsername()) || user.getId() == null){
            //throw new RuntimeException("Details are not valid");
            //sanitize the error
            return returnApiResponse(HttpStatus.SC_BAD_REQUEST, "Request body not valid",
                    CLIENT_ERROR_MESSAGE, CLIENT_ERROR_CODE, logger);
        }

        //server error
        try {
            performOperation(user);
        } catch (Exception e){
            return returnApiResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "DB Call failed",
                    SERVER_ERROR_MESSAGE + user.getUsername(), SERVER_ERROR_CODE, logger);
        }


        //success reponse
        return returnApiResponse(HttpStatus.SC_OK, "SUCCESS", null, null, logger);
    }

    void performOperation(User user){
        //DB operation or calling a new API
        if(user.getId() == 101){
            //error
            throw new RuntimeException("User is not valid");
        }
    }

    public APIGatewayProxyResponseEvent returnApiResponse(int statusCode, String responseBody,
                                                          String errorMessage, String errorCode, LambdaLogger logger){
        final Error error = new Error();
        if(!StringUtils.isNullOrEmpty(errorCode)){
            error.setErrorCode(errorCode);
            error.setErrorMessage(errorMessage);
        }

        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withBody(gson.toJson(new Response<String>(statusCode, responseBody, error)));
        logger.log("\n" + responseEvent.toString());
        return responseEvent;

    }
}
