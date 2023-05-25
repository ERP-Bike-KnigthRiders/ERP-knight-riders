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

    private final VendorsService vendorsService;

    public VendorController(VendorsService vendorsService) {
        this.vendorsService = vendorsService;
    }

    /**
     * Gets all vendors
     *
     * @return List of all vendors
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
    
    
    //This method fetches a vendor by its ID from a service, then maps the vendor's data into a DTO (Data Transfer Object) if it exists, or returns an HTTP 404 error response if the vendor does not exist.
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
    @PostMapping
    public ResponseEntity createVendor(@RequestBody VendorDto vendorDto)
    {
        String id = vendorsService.createVendor(vendorDto);
        return ResponseEntity.ok(id);
    }
}
