package com.homeshoping.homeshoping.repository.Item;

import com.homeshoping.homeshoping.entity.Item.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemFileRepository extends JpaRepository<ItemImg,Long> {
}
