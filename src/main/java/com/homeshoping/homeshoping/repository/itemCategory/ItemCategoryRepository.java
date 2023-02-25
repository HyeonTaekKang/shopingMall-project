package com.homeshoping.homeshoping.repository.itemCategory;


import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> , ItemCategoryRepositoryCustom {

    // 카테고리의 branch로 카테고리를 찾아오기
    Optional<ItemCategory> findByBranch(String branch);

    // 카테고리의 name으로 카테고리를 찾아오기
//    Optional<ItemCategory> findByName (String name);

    // 카테고리의 branch와 이름으로 해당 카테고리를 찾아오기
    Optional<ItemCategory> findByBranchAndName (String branch, String name);

//    // 해당 카테고리가 존재하는지 branch와 name으로 검색하여 true or false를 반환.
//    Boolean existsByBranchAndName(String branch, String name);
//
//    //
//    Optional<Category> findByBranchAndCode (String branch, String code);

    // 카테고리의 이름으로 해당 카테고리가 존재하는지 판단하는 메서드
    Boolean existsByBranchAndName (String branch, String name);

    // 해당 대분류가 존재하는지 판단하는 메서드 ( 매개변수로 대분류 이름을 받음 )
    Boolean existsByBranch (String branch);
}
