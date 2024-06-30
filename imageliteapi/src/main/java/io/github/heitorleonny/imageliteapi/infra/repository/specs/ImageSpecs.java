package io.github.heitorleonny.imageliteapi.infra.repository.specs;

import io.github.heitorleonny.imageliteapi.domain.enity.Image;
import io.github.heitorleonny.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;

public class ImageSpecs {

    private ImageSpecs(){}

    public static Specification<Image> extensionEqual(ImageExtension extension){
        return (root, q, cb) -> cb.equal(root.get("extension"), extension);
    }

    public static Specification<Image> nameLike(String name){
        return (root, q, cb) -> cb.like(cb.upper(root.get("name")),"%" + name.toUpperCase() + "%");

    }

    public static Specification<Image> tagsLike(String tags){
        return (root, q, cb) -> cb.like(cb.upper(root.get("tags")),"%" + tags.toUpperCase() + "%");

    }
}
