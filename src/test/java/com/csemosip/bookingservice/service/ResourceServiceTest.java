package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.repository.ResourceRepository;
import com.csemosip.bookingservice.service.ResourceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ResourceService resourceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllResources() {
        // Arrange
        List<Resource> expectedResources = new ArrayList<>();
        when(resourceRepository.findAll()).thenReturn(expectedResources);

        // Act
        List<Resource> actualResources = resourceService.findAllResources();

        // Assert
        Assertions.assertNotNull(actualResources);
        verify(resourceRepository, times(1)).findAll();
    }

    @Test
    public void testCreateResource() {
        // Arrange
        ResourceDTO resourceDTO = new ResourceDTO();
        Resource expectedResource = new Resource();
        when(modelMapper.map(resourceDTO, Resource.class)).thenReturn(expectedResource);
        when(resourceRepository.save(expectedResource)).thenReturn(expectedResource);

        // Act
        Resource actualResource = resourceService.createResource(resourceDTO);

        // Assert
        Assertions.assertNotNull(actualResource);
        verify(modelMapper, times(1)).map(resourceDTO, Resource.class);
        verify(resourceRepository, times(1)).save(expectedResource);
    }

    @Test
    public void testFindResource_existingResource() {
        // Arrange
        Integer resourceId = 1;
        Resource expectedResource = new Resource();
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(expectedResource));

        // Act
        Resource actualResource = resourceService.findResource(resourceId);

        // Assert
        Assertions.assertNotNull(actualResource);
        verify(resourceRepository, times(1)).findById(resourceId);
    }

    @Test
    public void testFindResource_nonExistingResource() {
        // Arrange
        Integer resourceId = 1;
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            resourceService.findResource(resourceId);
        });
        verify(resourceRepository, times(1)).findById(resourceId);
    }

}





