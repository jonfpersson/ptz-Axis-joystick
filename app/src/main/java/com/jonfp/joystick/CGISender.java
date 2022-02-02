package com.jonfp.joystick;

import java.util.concurrent.ExecutionException;

public class CGISender {
    String ipAdress;
    String cgiParams;
    String PTZPassword;

    public CGISender(String ipAdress, String PTZPassword) {
        this.ipAdress = ipAdress;
        this.PTZPassword = PTZPassword;
    }

    public void setParams(String cgiParams){
        this.cgiParams = cgiParams;
    }

    public boolean sendCGIRequest() {
        if (ipAdress.isEmpty() || cgiParams.isEmpty() || PTZPassword.isEmpty())
            return false;

        String webPage = "http://" + ipAdress + "/axis-cgi/com/ptz.cgi?" + cgiParams;
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

