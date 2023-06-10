package com.canwinthey.aws.sqs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsConsumerService {

    private Logger logger= LoggerFactory.getLogger(SqsConsumerService.class);
/*
    @SqsListener("${sqs-queue-name}")
    public void loadMessageFromSQS(String message)  {
        logger.info("Message from SQS Queue: {}", message);
    }
*/
}
