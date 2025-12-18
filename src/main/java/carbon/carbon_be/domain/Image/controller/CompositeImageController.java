package carbon.carbon_be.domain.Image.controller;

import carbon.carbon_be.domain.Image.dto.request.CompositeImageRequestDto;
import carbon.carbon_be.domain.Image.dto.response.CompositeImageResponseDto;
import carbon.carbon_be.domain.Image.service.outfit.CompositeImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/outfit")
public class CompositeImageController {

    private final CompositeImageService service;

    @PostMapping("/composite-url")
    public CompositeImageResponseDto compositeUrl(@Valid @RequestBody CompositeImageRequestDto request) {
        return CompositeImageResponseDto.builder()
                .imageUrl(service.getCompositeUrl(request))
                .build();
    }
}

