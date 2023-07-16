package com.csemosip.bookingservice.controller;

import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.service.Impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> findResource(@PathVariable Long id) {
        Resource resource = resourceService.findResource(id);
        return sendSuccessResponse(resource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createResource(@RequestBody ResourceDTO resourceDTO) {
        Resource resource = resourceService.createResource(resourceDTO);
        return sendSuccessResponse(resource, HttpStatus.CREATED);
    }
}
