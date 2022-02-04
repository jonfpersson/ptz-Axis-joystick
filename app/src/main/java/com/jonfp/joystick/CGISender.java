package com.jonfp.joystick;

import java.util.concurrent.ExecutionException;

public class CGISender {
    private String ipAddress;
    private String cgiParams;
    private String PTZPassword;

    public CGISender(String ipAddress, String PTZPassword) {
        this.ipAddress = ipAddress;
        this.PTZPassword = PTZPassword;
    }

    public void setParams(String cgiParams){
        this.cgiParams = cgiParams;
    }

    public boolean sendCGIRequest() {
        if (ipAddress.isEmpty() || cgiParams.isEmpty() || PTZPassword.isEmpty())
            return false;

        String webPage = "http://" + ipAddress + "/axis-cgi/com/ptz.cgi?" + cgiParams;
        HttpRequester getRequest = new HttpRequester();

        try {
            String resultCode =
                    getRequest.execute(webPage, PTZPassword).get();

            if (resultCode.equals("Successful"))
                return true;
            else
                return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}

