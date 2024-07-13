package io.github.heitorleonny.imageliteapi.application.images;

import io.github.heitorleonny.imageliteapi.domain.enity.Image;
import io.github.heitorleonny.imageliteapi.domain.enums.ImageExtension;
import io.github.heitorleonny.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/images")
@Slf4j // adiciona um objeto de log na classe
@RequiredArgsConstructor
@CrossOrigin("*")
public class ImagesController {
    private static final Logger log = LoggerFactory.getLogger(ImagesController.class);
    private final ImageService service;
    private final ImageMapper mapper;

    //*
    // multi-part/formdata
    //*
    @PostMapping
    public ResponseEntity save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags
    ) throws IOException {
        log.info("Imagem recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());
        log.info("Content Type: {}", file.getContentType());

        Image image = mapper.mapToImage(file, name, tags);
        Image savedImage = service.save(image);

        // http://localhost:8080/v1/images/jkashdkjsahd
        URI imageUri = buildImageURL(savedImage);



        return ResponseEntity.created(imageUri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id){
        var possibleImage = service.getById(id);
        if(possibleImage.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"", image.getFileName());

        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);


    }

    //v1/images?extension=PNG&&query=Nature
    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(
            @RequestParam(value = "extension", required = false, defaultValue = "") String extension,
            @RequestParam(value = "query", required = false) String query){

        var result = service.search(ImageExtension.ofName(extension), query);

        var images = result.stream().map(image -> {
            var url = buildImageURL(image);
            return mapper.imageToDTO(image, url.toString());
        }).collect(Collectors.toList());

        return ResponseEntity.of(Optional.of(images));
    }

    private URI buildImageURL(Image image){
    String imagePath = "/" + image.getId();
    return ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .path(imagePath)
            .build()
            .toUri();
    }
}
