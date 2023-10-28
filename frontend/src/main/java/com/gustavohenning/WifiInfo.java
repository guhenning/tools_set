package com.gustavohenning;

class WifiInfo {
    private String ssid;
    private String password;

    public WifiInfo(String ssid, String password) {
        this.ssid = ssid;
        this.password = password;
    }

    public String getSsid() {
        return ssid;
    }

    public String getPassword() {
        return password;
    }
}

