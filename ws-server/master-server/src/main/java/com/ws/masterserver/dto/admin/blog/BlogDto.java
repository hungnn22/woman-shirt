package com.ws.masterserver.dto.admin.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogDto {
    private String id;
    private String title;
    private String content;
    private String topicId;
}
