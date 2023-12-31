package org.iptime.yoon.blog.image;

import org.iptime.yoon.blog.dto.internal.ImageDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author rival
 * @since 2023-08-17
 */
public interface ImageService {
    void uploadImage(MultipartFile multipartFile, String filename, Long userId) throws Exception;

    ImageDto downloadImage(Long id) throws Exception;

    void deleteImage(Long id);

    String getImageUrl(Long id);

    ImageDto downloadImageThumbnail(Long id) throws Exception;
}
