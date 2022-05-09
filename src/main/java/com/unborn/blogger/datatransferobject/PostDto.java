package com.unborn.blogger.datatransferobject;

import com.unborn.blogger.entities.Category;
import com.unborn.blogger.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private String imageUrl;

    private Date addedDate;

    private Date updatedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();
}
