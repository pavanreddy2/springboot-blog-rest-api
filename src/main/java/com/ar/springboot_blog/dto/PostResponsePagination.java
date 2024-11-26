package com.ar.springboot_blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponsePagination {

    private List<PostResponse> content;
    private int pageNo;
    private int pageSize;
    private long  totalElements;
    private int totalPages;
    private boolean last;
}
