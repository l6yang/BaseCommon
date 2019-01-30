package com.sample.base.beans;

import com.google.gson.annotations.SerializedName;

public class UpdateBean {
    @SerializedName("bbh")
    private String version;
    @SerializedName("gxurl")
    private String url;
    @SerializedName("gxsj")
    private String gxsj;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGxsj() {
        return gxsj;
    }

    public void setGxsj(String gxsj) {
        this.gxsj = gxsj;
    }
}
