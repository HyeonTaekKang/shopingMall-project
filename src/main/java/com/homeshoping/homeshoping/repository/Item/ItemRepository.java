package com.homeshoping.homeshoping.repository.Item;


import com.homeshoping.homeshoping.entity.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> , ItemRepositoryCustom {

}
