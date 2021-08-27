package org.liquibase.ext.persistence.titan;

public class TitanImage {
    public String image;
    public String tag;
    public String digest;

    public String getDigest() {
        return digest;
    }

    public String getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}