package com.homeshoping.homeshoping.service.itemCategory;


import com.homeshoping.homeshoping.Exception.CategoryAlreadyExists;
import com.homeshoping.homeshoping.Exception.CategoryNotFound;
import com.homeshoping.homeshoping.Exception.ParentCategoryAlreadyExists;
import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.repository.itemCategory.ItemCategoryRepository;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemCategory.ParentItemCategoryCreate;
import com.homeshoping.homeshoping.response.category.CategoryListResponse;
import com.homeshoping.homeshoping.response.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;
//
//    // 카테고리 생성
//    @Transactional
//    public void createCategory(ItemCategoryCreate itemCategoryCreate){
//        // 만약 이미 존재하는 이름의 카테고리를 생성하려고 하는 경우 에러를 리턴하는 로직이 필요함
//
//        // DTO -> entity
//        ItemCategory itemCategory = itemCategoryCreate.toEntity(itemCategoryCreate);
//
//        itemCategoryRepository.save(itemCategory);
//    }

    /**
     *  ( 카테고리 생성 로직)
     *
     *  -- 아예 처음 카테고리를 만드는 경우 ( 프론트에 카테고리 만들기 버튼이 존재할꺼임 )
     *    - 반드시 대분류와  + 소분류를 함께 입력해야 카테고리가 생성됨.
     *    - 만약 생성하려고 하는 대분류가 이미 존재하는 것이라면, 에러를 리턴함. ( 소분류는 중복 가능하게 함. <== 수정 할 수도 있음 )
     *
     *
     *  -- 이미 존재하는 대분류에 소분류를 추가하는 경우 ( 프론트에 소분류 추가 버튼이 존재할꺼임 )
     *    - select 박스로 대분류를 선택하게 하고 , 소분류를 input에 작성해서 서버로 전송.
     */
    @Transactional
    // (대분류) 카테고리 생성
    public void createParentCategory(ParentItemCategoryCreate parentItemCategoryCreate){

        // 이미 존재하는 대분류를 생성하려고 하는 경우 에러를 리턴
        if (itemCategoryRepository.existsByBranch(parentItemCategoryCreate.getBranch())) {
          throw new ParentCategoryAlreadyExists();
       }

        // DTO -> entity
        ItemCategory itemCategory = parentItemCategoryCreate.toEntity(parentItemCategoryCreate);

        itemCategoryRepository.save(itemCategory);

    }

    @Transactional
     // 카테고리 생성 ( 소분류 , 자식 )
     // ---> *** 완전 새로운 카테고리를 생성할 떄는 대분류 만들고 , 반드시 한개 이상의 소분류를 같이 만들어야함.
    public void createCategory(ItemCategoryCreate itemCategoryCreate){

        // 부모 카테고리 이름으로 부모 카테고리 찾아오기.
        /**
         *  branch("패션")
         *  name("ROOT")
         */
//        ItemCategory parentItemCategory = itemCategoryRepository.findByBranchAndName(itemCategoryCreate.getBranch(),"ROOT")
//                .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음 예외"));

        // 생성하려는 대분류가 이미 존재하단다면 "CategoryAlreadyExists" 에러를 리턴
        if (itemCategoryRepository.existsByBranch(itemCategoryCreate.getParentItemCategory().getBranch())) {
            throw new CategoryAlreadyExists();
        }
        else{
            // DTO -> entity
            ItemCategory itemCategory = itemCategoryCreate.toEntity(itemCategoryCreate);

            itemCategoryRepository.save(itemCategory);
        }

    }

    @Transactional
    // 대분류에 소분류 카테고리 추가하기
    public void addChildCategory(ItemCategoryCreate itemCategoryCreate){

        // 부모 카테고리와 연관관계가 셋팅이 안된 카테고리를 만들어서
        ItemCategory category = ItemCategory.createCategory(itemCategoryCreate);

        // 저장하고
        itemCategoryRepository.save(category);

        // 위에서 저장한 부모 카테고리와 연관관계가 셋팅이 안된 카테고리를 가져와서
        ItemCategory itemCategory = itemCategoryRepository.findByBranchAndName(itemCategoryCreate.getBranch(), itemCategoryCreate.getName())
                .orElseThrow(() -> new CategoryNotFound());

        // 부모 카테고리 가져오기
        ItemCategory parentCategory = itemCategoryRepository.findByBranchAndName(itemCategory.getBranch(), "ROOT").orElseThrow(() -> new CategoryNotFound());

        // 가져온 카테고리에 가져온 부모 카테고리를 셋팅해주기
//        itemCategory.setParentItemCategory(itemCategoryCreate.getParentItemCategory().toEntity(itemCategoryCreate.getParentItemCategory()));

        itemCategory.setParentItemCategory(parentCategory);


    }



    // 대분류 카테고리들의 이름을 모두 가져오기
    public List<String> getAllParentCategory(){

        // 부모 카테고리들 전부 가져오기
        List<ItemCategory> parentCategoryList = itemCategoryRepository.getAllParentCategories();

        // 부모 카테고리들의 이름만 빼서 리스트에 담기.
        List<String> parentCategoryNameList = parentCategoryList.stream().map(p -> p.getBranch()).collect(Collectors.toList());

        // 부모 카테고리들의 이름이 담긴 리스트 리턴
        return parentCategoryNameList;

    }


    // 부모 카테고리 아래에 있는 자식 카테고리들의 이름을 모두 가져오기
    public List<String> getChildCategory(String branch){

        // 부모 카테고리 아래에 있는 자식 카테고리들을 리스트 안에 담아서 가져옴.
        List<ItemCategory> childCategoryList = itemCategoryRepository.getAllChildCategories(branch);

        // 부모 카테고리 아래에 있는 자식 카테고리들의 이름만 빼서 리스트에 담기.
        List<String> childCategoryNameList = childCategoryList.stream().map(c -> c.getName()).collect(Collectors.toList());

        // 자식 카테고리들의 이름이 담겨있는 리스트를 리턴
        return childCategoryNameList;
        // 카테고리 entity -> 카테고리 DTO
//      CategoryDTO categoryDTO = new CategoryDTO(category);
    }

}

//    // 카테고리 저장
//    public Long saveCategory(CategoryDTO categoryDTO){
//
//        // DTO -> entity
//        Category category = categoryDTO.toEntity();
//
//        /**
//         *  대분류 등록 ( 최상위 카테고리 )
//         *  "ROOT": {
//         *  *         "categoryId": 1,
//         *  *         "branch": "coupang",
//         *  *         "code": "ROOT",
//         *  *         "name": "ROOT",
//         *  *         "parentCategoryName": "대분류",
//         *  *         "level": 0,
//         *  *         "children": {}
//         */
//        if (categoryDTO.getParentCategoryName() == null){
//
//            //JPA 사용하여 DB에서 branch와 name의 중복값을 검사. (대분류에서만 가능)
//            if (categoryRepository.existsByBranchAndName(categoryDTO.getBranch(), categoryDTO.getName())) {
//                throw new RuntimeException("해당 카테고리가 이미 존재함.");
//            }
//
//            // findByBranchAndName() 이 메서드로 ROOT 카테고리를 찾는데 null이면 최상위 ROOT Category를 생성하는 로직.
//            Category rootCategory = categoryRepository.findByBranchAndName(categoryDTO.getBranch(),"ROOT")
//                    .orElseGet( () ->
//                            Category.builder()
//                                    .name("ROOT")
//                                    .code("ROOT")
//                                    .branch(categoryDTO.getBranch())
//                                    .level(0)
//                                    .build()
//                    );
//
//            // 연관관계 맵핑
//            category.setParentCategory(rootCategory);
//
//            category.setLevel(1);
//
//            // DTO로 받아온 Branch 이름과 카테고리이름이 "ROOT"인 카테고리가 존재하지 않는지 확인하고 , 존재하지 않으면 위에서 생성한 ROOT Category 저장.
//            if (!categoryRepository.existsByBranchAndName(categoryDTO.getBranch(), "ROOT")) {
//                categoryRepository.save(rootCategory);
//            }
//
//        }
//
//        /**
//         *  중, 소분류 등록 ( categoryDTO.getParentCategoryName() == !null )
//         */
//        else { // categoryDTO.getParentCategoryName() 이 null 이 아닐 경우 => 하위 카테고리
//            String parentCategoryName = categoryDTO.getParentCategoryName();
//
//            // 부모 카테고리 이름으로 부모 카테고리 찾아오기.
//            Category parentCategory = categoryRepository.findByBranchAndName(categoryDTO.getBranch(), parentCategoryName)
//                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음 예외"));
//
//            // 자식 카테고리이기 때문에 부모 카테고리의 레벨에서 + 1
//            category.setLevel(parentCategory.getLevel() + 1);
//
//            // 부모카테고리 셋팅( 자식을 포함 )
//            category.setParentCategory(parentCategory);
//
//            // 부모 카테고리에서 자식 카테고리를 list에 넣어주기
//            parentCategory.getSubCategory().add(category);
//        }
//        categoryRepository.save(category);
//        return category.getId();
//    }
//
//    // branch 로 검색했을 때, 가장 최상단 경로부터 가장 하위까지 조회하기
//    public Map<String , CategoryDTO> getCategoryByBranch(String branch){
//
//        // 최상위 카테고리 찾기 ( 엔티티 )
//        Category category = categoryRepository.findByBranchAndCode(branch, "ROOT").orElseThrow(() -> new RuntimeException("찾는 대분류가 없습니다"));
//
//        // 카테고리 entity -> 카테고리 DTO
//        CategoryDTO categoryDTO = new CategoryDTO(category);
//
//        Map<String, CategoryDTO> data = new HashMap<>();
//        data.put(categoryDTO.getName(), categoryDTO);
//
//        return data;
//    }
//
//    // branch 로 검색했을 때, 가장 최상단 경로부터 가장 하위까지 조회하기
//    public List<Category> getCategory(String branch){
//
//        // 최상위 카테고리 찾기 ( 엔티티 )
//        return categoryRepository.getAllCategoryByBranch(branch);
//
//    }




