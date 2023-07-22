package com.csemosip.bookingservice.controller;

import com.csemosip.bookingservice.dto.ResourceAvailabilityDTO;
import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resources")
public class ResourcesController extends AbstractController {
    @Autowired
    ResourceService resourceService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER', 'RESOURCE_USER')")
    public ResponseEntity<Map<String, Object>> findAllResources() {
        List<Resource> resources = resourceService.findAllResources();
        return sendSuccessResponse(resources, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER', 'RESOURCE_USER')")
    public ResponseEntity<Map<String, Object>> findResource(@PathVariable Long id) {
        Resource resource = resourceService.findResource(id);
        return sendSuccessResponse(resource, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER')")
    public ResponseEntity<Map<String, Object>> createResource(@RequestBody ResourceDTO resourceDTO) {
        Resource resource = resourceService.createResource(resourceDTO);
        return sendSuccessResponse(resource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER')")
    public ResponseEntity<Map<String, Object>> updateResource(@PathVariable Long id, @RequestBody ResourceDTO resourceDTO) {
        Resource resource = resourceService.updateResource(id, resourceDTO);
        return sendSuccessResponse(resource, HttpStatus.OK);
    }

    @GetMapping("/{id}/available")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER', 'RESOURCE_USER')")
    public ResponseEntity<Map<String, Object>> getAvailableCountByResourceIdAndTimeslot(
            @PathVariable("id") Long resourceId,
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime end
    ) {
        try {
            List<ResourceAvailabilityDTO> availabilityList = resourceService.getAvailabilityByResourceIdAndTimeslot(
                    resourceId,
                    start,
                    end
            );
            return sendSuccessResponse(availabilityList, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return sendBadRequestResponse(e.getMessage());
        }
    }
}
