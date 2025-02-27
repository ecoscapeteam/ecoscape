package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.ListingImagesGetResponse;
import com.java2024.ecoscape.dto.ListingImagesRequest;
import com.java2024.ecoscape.dto.ListingImagesUploadResponse;
import com.java2024.ecoscape.models.ListingImages;
import com.java2024.ecoscape.services.ListingImagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ListingImagesController {
    private final ListingImagesService listingImagesService;

    public ListingImagesController(ListingImagesService listingImagesService) {
        this.listingImagesService = listingImagesService;
    }

    @PostMapping
    public ResponseEntity<?> createListingImage(@RequestBody ListingImagesRequest request) {
        if (listingImagesService.existsByImageUrl(request.getImageUrl())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("That image is already uploaded.");
        }

        ListingImages listingImages = listingImagesService.createListingImages(request);

        ListingImagesUploadResponse listingImagesUploadResponse = new ListingImagesUploadResponse(
                "Image got uploaded successfully!",
                listingImages.getImageUrl()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(listingImagesUploadResponse);
    }

    @GetMapping
    public ResponseEntity<List<ListingImagesGetResponse>> getAllListingImages() {
        List<ListingImagesGetResponse> images = listingImagesService.findAllListingImages();

        return ResponseEntity.ok(images);
    }

    @GetMapping("{id}")
    public ResponseEntity<ListingImagesGetResponse> getImageById(@PathVariable Long id) {
        ListingImages listingImages = listingImagesService.findImageById(id);

        ListingImagesGetResponse listingImagesGetResponse = new ListingImagesGetResponse(
                listingImages.getListing().getId(),
                listingImages.getImageUrl()
        );

        return ResponseEntity.ok(listingImagesGetResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteListingImage(@PathVariable Long id) {
        listingImagesService.deleteListingImage(id);

        return ResponseEntity.ok("Listing image successfully deleted!");
    }
}
