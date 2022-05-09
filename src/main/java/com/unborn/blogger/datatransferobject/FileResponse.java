package com.unborn.blogger.datatransferobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {
    PostDto postDto;
    String fileName;
    String message;
}
