package com.inteliense.trusty.server;

import com.sun.net.httpserver.Headers;

import java.util.ArrayList;

public class ClientInfo {

    private String remoteIp;
    private int remotePort;
    private ArrayList<String> remoteHostname;
    private ArrayList<String> userAgent;

    private boolean hostnameFlag = false;
    private boolean userAgentFlag = false;

    private int numIpRequests = 0;

    public ClientInfo(Headers headers, String remoteIp, int remotePort, String remoteHostname) {
        userAgent.add(headers.getFirst("User-Agent"));
        this.remoteHostname.add(remoteHostname);
        this.remotePort = remotePort;
        this.remoteIp = remoteIp;
    }

    public boolean verifyHostname(String remoteHostname) {
        boolean res = this.remoteHostname.contains(remoteHostname);
        if(!res) {
            this.remoteHostname.add(remoteHostname);
            hostnameFlag = true;
        }
        return res;
    }

    public boolean verifyUserAgent(String userAgent) {
        boolean res = this.userAgent.contains(userAgent);
        if(!res) {
            this.userAgent.add(userAgent);
            userAgentFlag = true;
        }
        return res;
    }

    public int getNumIpRequests() {
        return numIpRequests;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public String getLastUserAgent() {
        return userAgent.get(userAgent.size() - 1);
    }

    public String getUserAgent(int index) {
        return userAgent.get(index);
    }

    public int numUserAgentEntries() {
        return userAgent.size();
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public String getLastHostname() {
        return remoteHostname.get(remoteHostname.size() - 1);
    }

    public String getHostname(int index){
        return remoteHostname.get(index);
    }

    public int numHostnameEntries() {
        return remoteHostname.size();
    }

    public boolean isFlagged() {
        return (hostnameFlag || userAgentFlag);
    }

}