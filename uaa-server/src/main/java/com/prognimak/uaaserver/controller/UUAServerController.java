package com.prognimak.uaaserver.controller;


import com.prognimak.uaaserver.service.UUAServerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @Info(
                title = "This controller end points for management with JWT token",
                contact = @Contact(url = "", name = "Oleksandr Prognimak", email = "ol.prognimak@gmail.com")
        )
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Log4j2
public class UUAServerController {
    private final UUAServerService service;

    /**
     * Creates JWT token with using secret key with username and password delimited with semicolon
     * and transferred over custom HTTP header <code>Secret</code>
     *
     * @param secret encoded String with username and password.
     * @return ResponseEntity with token
     */
    @Operation(summary = "Create JWT token",
               description = "Successfully retrieved JWT token"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "text/plain"),
            required = false
    )
    @ApiResponse(responseCode = "200", description = "Retrieve JWT token")
    @ApiResponse(responseCode = "400", description = "Error creation and retrieves od JWT Token. ")
    @PostMapping(path = "/createtoken", produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createToken(@RequestHeader("Secret") @Parameter(
            in = ParameterIn.HEADER,
            description = "Secret for token creation (Base64 encoded)",
            required = true,
            schema = @Schema(type = "string", example = "dGVzdFVzZXJOYW1lOnRlc3RQYXNzd29yZA==")
    )  String secret) {
        try {
            String token = service.requestToken(secret);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.error("UUAServer. Can not create JWT token", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UUAServer. JWT token is not created.");
        }
    }
}
