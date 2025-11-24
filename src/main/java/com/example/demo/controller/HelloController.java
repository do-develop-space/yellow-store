package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Operation(summary = "Hello API", description = "hello 문자열을 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 hello 문자열 반환",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "hello")
                    )
            )
    })
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}

