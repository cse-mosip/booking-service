package com.csemosip.bookingservice.controller;

import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.service.Impl.ResourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ResourcesTest {

    @Mock
    private ResourceServiceImpl resourceService;

    @InjectMocks
    private Resources resources;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllResources_ReturnsListOfResources() {
        // Arrange
        List<Resource> expectedResources = Arrays.asList(
                new Resource(1, "Resource 1", 1),
                new Resource(2, "Resource 2", 1)
        );
        when(resourceService.findAllResources()).thenReturn(expectedResources);

        // Act
        ResponseEntity<Map<String, Object>> response = resources.findAllResources();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResources, response.getBody());
        verify(resourceService, times(1)).findAllResources();
    }

    @Test
    void findResource_ValidId_ReturnsResource() {
        // Arrange
        int resourceId = 1;
        Resource expectedResource = new Resource(resourceId, "Resource 1", 1);
        when(resourceService.findResource(resourceId)).thenReturn(expectedResource);

        // Act
        ResponseEntity<Map<String, Object>> response = resources.findResource(resourceId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResource, response.getBody());
        verify(resourceService, times(1)).findResource(resourceId);
    }

    @Test
    void createResource_ValidResourceDTO_ReturnsCreatedResource() {
        // Arrange
        ResourceDTO resourceDTO = new ResourceDTO("Resource 1", 1);
        Resource expectedResource = new Resource(1, "Resource 1", 1);
        when(resourceService.createResource(resourceDTO)).thenReturn(expectedResource);

        // Act
        ResponseEntity<Map<String, Object>> response = resources.createResource(resourceDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResource, response.getBody());
        verify(resourceService, times(1)).createResource(resourceDTO);
    }

}
