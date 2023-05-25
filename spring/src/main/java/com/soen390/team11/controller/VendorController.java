package com.soen390.team11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soen390.team11.dto.VendorDto;
import com.soen390.team11.entity.Vendors;
import com.soen390.team11.service.VendorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

/**
 * Controller for the vendor
 */

/*
 * The VendorController class is a REST Controller in a Spring Boot application responsible for handling HTTP requests 
 * related to Vendor operations. These operations include getting all vendors, getting a vendor by its ID, and 
 * creating a new vendor.
 */
@RestController
@RequestMapping("/vendor")
public class VendorController {

      /*
     * The private field vendorsService is an instance of VendorsService. This field will hold all the business 
     * logic needed for vendor operations.
     */
    private final VendorsService vendorsService;

     /*
     * The constructor of VendorController class that takes an instance of VendorsService as parameter and initializes 
     * the vendorsService field. This follows the Dependency Injection principle, making the application easier to manage.
     */
    public VendorController(VendorsService vendorsService) {
        this.vendorsService = vendorsService;
    }

    /**
     * Gets all vendors
     *
     * @return List of all vendors
     */
       /*
     * The retrieveAllVendors method is mapped to the GET HTTP method at the "/vendor/" URL path. It retrieves all 
     * vendors by calling the getAllVendors method of vendorsService and returns the list as a part of the ResponseEntity 
     * with an HTTP status code of 200 (OK).
     */
    @GetMapping("/")
    public ResponseEntity<?> retrieveAllVendors(){
        return new ResponseEntity<>(vendorsService.getAllVendors(), HttpStatus.OK);
    }

    /**
     * Gets a specific vendor
     *
     * @param vid The vendor's ID
     * @return The vendor's information
     */
     /*
     * The getVendorById method is mapped to the GET HTTP method at the "/vendor/{vid}" URL path. It retrieves a 
     * specific vendor by its ID by calling the getVendor method of vendorsService. If the vendor is found, it returns 
     * a VendorDto object with the vendor's details with an HTTP status code of 200 (OK). If the vendor is not found, 
     * it returns a ResponseEntity with a Not Found (404) status code.
     */
    @GetMapping("/{vid}")
    public ResponseEntity getVendorById(@PathVariable String vid)
    {
        Optional<Vendors> vendor = vendorsService.getVendor(vid);
        if (vendor.isPresent())
        {
            VendorDto vendorDto = new VendorDto(
                    vendor.get().getVendorID(),
                    vendor.get().getCompanyname(),
                    vendor.get().getAddress(),
                    vendor.get().getPhone(),
                    vendor.get().getEmail()
                    );
            return ResponseEntity.ok(vendorDto);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Creates a new vendor
     *
     * @param vendorDto The vendor's information
     * @return The vendor's ID
     */
     /*
     * The createVendor method is mapped to the POST HTTP method at the "/vendor" URL path. It creates a new vendor 
     * with the information provided as a VendorDto object sent in the request body. It then returns the ID of the 
     * newly created vendor and an HTTP status code of 200 (OK).
     */
    @PostMapping
    public ResponseEntity createVendor(@RequestBody VendorDto vendorDto)
    {
        String id = vendorsService.createVendor(vendorDto);
        return ResponseEntity.ok(id);
    }
}
