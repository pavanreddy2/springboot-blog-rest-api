package com.ar.springboot_blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String description;

    private String content;

    private LocalDateTime dateCreated;

    private LocalDateTime lastUpdated;
}
