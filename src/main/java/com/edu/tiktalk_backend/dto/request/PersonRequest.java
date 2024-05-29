package com.edu.tiktalk_backend.dto.request;

import com.edu.tiktalk_backend.enums.RolesEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRequest {
    @NotNull
    @Size(min = 2, max = 255)
    private String name;
}
