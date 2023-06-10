package com.canwinthey.aws.sns.utils;

public class SnsUtils {

    public static String buildEmailBody(){
        return "Dear Employee ,\n" +
                "\n" +
                "\n" +
                "Connection down Mumbai."+"\n"+
                "All the servers in Mumbai Data center are not accessible. We are working on it ! \n" +
                "Notification will be sent out as soon as the issue is resolved. For any questions regarding this message please feel free to contact IT Service Support team";
    }

    public static String buildSMSBody(){
        return "Dear Customer ,\n" +
                "\n" +
                "\n" +
                "Connection down in Mumbai."+"\n"+
                "All the servers in Mumbai Data center are not accessible. We are working on it ! \n";

    }

}
