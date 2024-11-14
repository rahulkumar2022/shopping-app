package com.learn.ShopperStop.service.image;

import com.learn.ShopperStop.dto.ImageDto;
import com.learn.ShopperStop.exceptions.ResourceNotFoundException;
import com.learn.ShopperStop.model.Image;
import com.learn.ShopperStop.model.Product;
import com.learn.ShopperStop.repository.ImageRepository;
import com.learn.ShopperStop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IProductService iProductService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No Image found with this id "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new ResourceNotFoundException("No Image found with this id "+id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = iProductService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();

            for (MultipartFile file : files) {
                try {
                    Image image = new Image();
                    image.setFileName(file.getOriginalFilename());
                    image.setFileType(file.getContentType());
                    image.setImage(new SerialBlob(file.getBytes()));
                    image.setProduct(product);
                    String buildDownloadUrl = "/api/v1/images/download/";
                    String downloadUrl = buildDownloadUrl + image.getId();
                    image.setDownloadUrl(downloadUrl);
                    imageRepository.save(image);
                    Image savedImage = imageRepository.save(image);

                    savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                    imageRepository.save(savedImage);

                    ImageDto imageDto = new ImageDto();
                    imageDto.setImageId(savedImage.getId());
                    imageDto.setImageName(savedImage.getFileName());
                    imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                    savedImageDto.add(imageDto);

                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
