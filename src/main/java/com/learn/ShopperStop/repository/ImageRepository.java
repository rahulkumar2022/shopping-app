package com.learn.ShopperStop.repository;

import com.learn.ShopperStop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
