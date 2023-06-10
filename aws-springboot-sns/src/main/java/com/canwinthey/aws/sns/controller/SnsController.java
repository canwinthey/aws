package com.canwinthey.aws.sns.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import com.canwinthey.aws.sns.utils.SnsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/sns")
public class SnsController {

    private Logger logger= LoggerFactory.getLogger(SnsController.class);

    @Autowired
    private AmazonSNSClient snsClient;

    @Value("${topic-arn}")
    private String TOPIC_ARN;

    @Value("${sqs-arn}")
    private String sqsArn;

    // protocol = email, endpoint = email
    @GetMapping("/subscribe/{protocol}/{endpoint}")
    public String addSubscriptionToTopic(@PathVariable String protocol, @PathVariable String endpoint) {
        SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, protocol, endpoint);
        SubscribeResult subscribeResult = snsClient.subscribe(request);

        String msg = "";

        if (subscribeResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
            logger.info("Subscriber creation successful");
            msg = "Subscription request is pending. To confirm the subscription, check your email : " + endpoint;
            logger.info(msg);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Subscription request failed!!");
        }
        return msg;
    }

    @GetMapping("/sendEmail")
    public String sendEmail(){
        PublishRequest publishRequest=new PublishRequest(TOPIC_ARN, SnsUtils.buildEmailBody(),"Notification: Network connectivity issue");
        snsClient.publish(publishRequest);
        return "Notification send successfully !!";
    }

    @RequestMapping("/sendSMS/{phone}")
    private String sendSMS(@PathVariable String phone) throws URISyntaxException {

        //PublishRequest publishRequest=new PublishRequest(TOPIC_ARN, SnsUtils.buildSMSBody());
        PublishRequest publishRequest=new PublishRequest();
        publishRequest.setPhoneNumber(phone);
        publishRequest.setMessage(SnsUtils.buildSMSBody());
        publishRequest.setSubject("SNS: Connection Outage in Mumbai");

        PublishResult publishResult = snsClient.publish(publishRequest);

        if (publishResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
            System.out.println("Message publishing to phone successful !!");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Message publishing to phone failed !!");
        }
        return "SMS sent to " + phone + ". Message-ID: " + publishResult.getMessageId();
    }

    @RequestMapping("/sqs")
    private String sendMsgToSQS() throws URISyntaxException {

        PublishRequest publishRequest=new PublishRequest();
        publishRequest.setTargetArn(sqsArn);
        publishRequest.setMessage(SnsUtils.buildSMSBody());
        publishRequest.setSubject("SQS: Connection Outage in Mumbai");

        PublishResult publishResult = snsClient.publish(publishRequest);

        if (publishResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
            System.out.println("Message publishing to phone successful !!");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Message publishing to phone failed !!");
        }
        return "SNs sent to SQS!! " + ". Message-ID: " + publishResult.getMessageId();
    }

}