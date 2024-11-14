package com.learn.ShopperStop.service.image;

import com.learn.ShopperStop.dto.ImageDto;
import com.learn.ShopperStop.model.Image;
import com.learn.ShopperStop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDto> saveImage(List<MultipartFile> image, Long productId);

    void updateImage(MultipartFile file, Long imageId);



}
