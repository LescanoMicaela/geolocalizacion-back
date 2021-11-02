package com.daw.proyecto.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class FeedingRequest {

    @Schema(description = "Water available in colony", example = "true")
    private boolean waterAvailable;

    @Schema(description = "Food available in colony", example = "true")
    private boolean foodAvailable;

    @Schema(description = "Water provided", example = "false")
    private boolean water;

    @Schema(description = "Food provided", example = "true")
    private boolean food;

}
