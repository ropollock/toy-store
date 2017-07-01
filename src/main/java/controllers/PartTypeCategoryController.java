package controllers;

import authorization.AuthenticationService;
import domain.PartTypeCategoryService;
import models.PartTypeCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import responses.ErrorResponse;
import responses.GetPartTypeCategoriesResponse;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/part-type-category")
public class PartTypeCategoryController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
    private AuthenticationService authenticationService;
    private PartTypeCategoryService partTypeCategoryService;

    public PartTypeCategoryController() { init(); }

    public void init() {
        this.authenticationService = new AuthenticationService();
        this.partTypeCategoryService = new PartTypeCategoryService();
    }

    @RequestMapping(method = RequestMethod.GET, produces ="application/json")
    public ResponseEntity<?> getPartTypeCategories(@RequestHeader(value = "x-session-token") String token) {
        LOGGER.info("Getting all part type categories");
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Validate session
        if(!authenticationService.authorized(token)
                || !authenticationService.hasPermission(token, "read")) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrors(Collections.singletonList("Unauthorized session."));
            LOGGER.warn("Failed to get all part type categories unauthorized.");
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNAUTHORIZED);
        }

        List<PartTypeCategory> partTypeCategories = partTypeCategoryService.getPartTypeCategories();
        return new ResponseEntity<>(new GetPartTypeCategoriesResponse(partTypeCategories), headers, HttpStatus.OK);
    }
}
