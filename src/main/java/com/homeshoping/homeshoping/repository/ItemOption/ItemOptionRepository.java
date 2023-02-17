package com.homeshoping.homeshoping.repository.ItemOption;

import com.homeshoping.homeshoping.entity.itemOption.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption,Long> , ItemOptionRepositoryCustom {
}
