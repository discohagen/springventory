package com.discohagen.springventory.dto.image;

import com.discohagen.springventory.model.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImageDTO {
    private String fileName;
    private byte[] data;

    public Image toImage() {
        Image image = new Image();

        image.setFileName(fileName);
        image.setData(data);

        return image;
    }
}

