package com.homeshoping.homeshoping.repository.category;


import com.homeshoping.homeshoping.entity.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category , Long> , CategoryRepositoryCustom {

//    // 카테고리의 이름으로 해당 카테고리를 찾아오기
//    Optional<Category> findByName (String name);
//
    // 카테고리의 branch와 이름으로 해당 카테고리를 찾아오기
    Optional<Category> findByBranchAndName (String branch, String name);

//    // 해당 카테고리가 존재하는지 branch와 name으로 검색하여 true or false를 반환.
//    Boolean existsByBranchAndName(String branch, String name);
//
//    //
//    Optional<Category> findByBranchAndCode (String branch, String code);

    // 카테고리의 이름으로 해당 카테고리가 존재하는지 판단하는 메서드
    Boolean existsByBranchAndName (String branch , String name);
}
