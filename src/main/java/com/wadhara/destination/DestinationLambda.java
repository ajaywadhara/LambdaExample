package com.wadhara.destination;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.LambdaDestinationEvent;

public class DestinationLambda {

    public void handle(LambdaDestinationEvent event, Context context){
        final LambdaLogger logger = context.getLogger();

        logger.log("Lambda Destination event received");

        logger.log("\nDestination event details:" + event.getRequestPayload().toString());
    }
}
