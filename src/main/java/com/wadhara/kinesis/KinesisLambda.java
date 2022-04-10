package com.wadhara.kinesis;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;

public class KinesisLambda {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public void handle(KinesisEvent event, Context context){
        final LambdaLogger logger = context.getLogger();
        for(KinesisEvent.KinesisEventRecord record : event.getRecords()){
            //200 records
            /*{
                "orderId": 1493740222,
                    "product": "jeans",
                    "quantity": 5
            }*/

            String orderData = StandardCharsets.UTF_8.decode(record.getKinesis().getData()).toString();
            logger.log("Order data :" + orderData);

            final Order order = gson.fromJson(orderData, Order.class);

            if(order.getQuantity() > 15){
                throw new RuntimeException("Quantity is large.....possible fraud");
            }
        }
    }
}
