package com.discohagen.springventory.dto.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetImageDTO {
    private Long id;
    private String fileName;
    private byte[] data;
}
