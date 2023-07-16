package com.csemosip.bookingservice.controller;

import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.service.Impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resources")
public class Resources extends AbstractController {
    @Autowired
    ResourceServiceImpl resourceService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllResources() {
        List<Resource> resources = resourceService.findAllResources();
        return sendSuccessResponse(resources, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findResource(@PathVariable Integer id) {
        Resource resource = resourceService.findResource(id);
        return sendSuccessResponse(resource, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','','RESOURCE_MANAGER')")
    public ResponseEntity<Map<String, Object>> createResource(@RequestBody ResourceDTO resourceDTO) {
        Resource resource = resourceService.createResource(resourceDTO);
        return sendSuccessResponse(resource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateResource(@PathVariable Integer id, @RequestBody ResourceDTO resourceDTO) {
        Resource resource = resourceService.updateResource(id, resourceDTO);
        return sendSuccessResponse(resource, HttpStatus.OK);
    }
}
