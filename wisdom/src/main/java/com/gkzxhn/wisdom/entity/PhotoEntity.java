package com.gkzxhn.wisdom.entity;

/**
 * Created by Raleigh.Luo on 17/8/4.
 */

public class PhotoEntity {
    private String localPath;//图片本地地址
    private String imageUrl;//图片下载地址

    public PhotoEntity(String localPath) {
        this.localPath = localPath;
    }

    public PhotoEntity(String localPath, String imageUrl) {
        this.localPath = localPath;
        this.imageUrl = imageUrl;
    }

    public void setLocalPath(String localPath) {
        if (localPath != null && !localPath.equals("null"))
            this.localPath = localPath;
    }

    public String getLocalPath() {
        return localPath == null ? "" : localPath;
    }

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.equals("null"))
            this.imageUrl = imageUrl;
    }
}
