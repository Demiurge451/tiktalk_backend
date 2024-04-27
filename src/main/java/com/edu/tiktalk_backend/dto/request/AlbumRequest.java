package com.edu.tiktalk_backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequest {
    private String title;
    private String description;
}
