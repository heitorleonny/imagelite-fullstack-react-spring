package io.github.heitorleonny.imageliteapi.application.images;

import io.github.heitorleonny.imageliteapi.domain.enity.Image;
import io.github.heitorleonny.imageliteapi.domain.enums.ImageExtension;
import io.github.heitorleonny.imageliteapi.domain.service.ImageService;
import io.github.heitorleonny.imageliteapi.infra.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository repository;


    @Override
    @Transactional
    public Image save(Image image) {
        return repository.save(image);
    }

    @Override
    public Optional<Image> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Image> search(ImageExtension extension, String query) {
        return repository.findByExtensionAndNameOrTagsLike(extension, query);
    }


}
