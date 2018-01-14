package com.astro.dto;

import lombok.Data;

import java.io.InputStream;

/**
 * Created by astro on 2018/1/11.
 */
@Data
public class ImageHolder {

    private String imageName;
    private InputStream image;

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }
}
