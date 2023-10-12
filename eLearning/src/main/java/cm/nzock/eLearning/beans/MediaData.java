package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class MediaData implements Serializable {

    private String image;
    private String code;
    private String downloadUrl;
    private String format;
    private Boolean confidential;
    private Short width;

    public MediaData() {
    }

    public String getImage() {
        return image;
    }

    public MediaData setImage(String image) {
        this.image = image;
        return this;
    }

    public String getCode() {
        return code;
    }

    public MediaData setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public MediaData setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public MediaData setFormat(String format) {
        this.format = format;
        return this;
    }

    public Boolean getConfidential() {
        return confidential;
    }

    public MediaData setConfidential(Boolean confidential) {
        this.confidential = confidential;
        return this;
    }

    public Short getWidth() {
        return width;
    }

    public MediaData setWidth(Short width) {
        this.width = width;
        return this;
    }
}
