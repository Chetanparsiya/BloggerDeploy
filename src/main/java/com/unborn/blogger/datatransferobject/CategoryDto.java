package com.unborn.blogger.datatransferobject;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class CategoryDto {

    private Long categoryId;
    @NotEmpty
    @Size(min=4,message = "Category Title Length Should be 4 Character")
    private String categoryTitle;
    @NotEmpty
    @Size(min=10,message = "Category Title Length Should be 4 Character")
    private String categoryDescription;
}
