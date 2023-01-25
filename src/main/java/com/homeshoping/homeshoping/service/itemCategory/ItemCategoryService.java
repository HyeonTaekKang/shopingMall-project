package com.homeshoping.homeshoping.service.itemCategory;


import com.homeshoping.homeshoping.Exception.CategoryAlreadyExists;
import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.repository.itemCategory.ItemItemCategoryRepository;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
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

    private final ItemItemCategoryRepository itemCategoryRepository;

    // 카테고리 생성
    @Transactional
    public void createCategory(ItemCategoryCreate itemCategoryCreate){
        // 만약 이미 존재하는 이름의 카테고리를 생성하려고 하는 경우 에러를 리턴하는 로직이 필요함

        // DTO -> entity
        ItemCategory itemCategory = itemCategoryCreate.toEntity(itemCategoryCreate);

        itemCategoryRepository.save(itemCategory);
    }

    // 존재하는 모든 카테고리 가져오기
    public CategoryListResponse getAllCategory(){

        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();

        // entity -> DTO
        List<CategoryResponse> categoryDto = itemCategoryList.stream().map(c -> new CategoryResponse(c)).collect(Collectors.toList());

        return new CategoryListResponse(categoryDto);
    }

    @Transactional
    // 카테고리 생성 ( 대분류 , 부모  )
    public void createBigCategory(ItemCategoryCreate itemCategoryCreate){

        // 이미 존재하는 이름의 카테고리를 생성하려고 하는 경우 에러를 리턴
        if (itemCategoryRepository.existsByBranchAndName(itemCategoryCreate.getBranch() , itemCategoryCreate.getName())) {
          throw new CategoryAlreadyExists();
       }

        // DTO -> entity
        ItemCategory itemCategory = itemCategoryCreate.toEntity(itemCategoryCreate);

        itemCategoryRepository.save(itemCategory);

    }

    @Transactional
     // 카테고리 생성 ( 소분류 , 자식 )
    public void createSmallCategory(ItemCategoryCreate itemCategoryCreate){

        // 부모 카테고리 이름으로 부모 카테고리 찾아오기.
        /**
         *  branch("패션")
         *  name("ROOT")
         */
        ItemCategory parentItemCategory = itemCategoryRepository.findByBranchAndName(itemCategoryCreate.getBranch(),"ROOT")
                .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음 예외"));

        // DTO -> entity
        ItemCategory itemCategory = itemCategoryCreate.toEntity(itemCategoryCreate);

        // 자식 카테고리에 부모카테고리 셋팅
        itemCategory.setParentItemCategory(parentItemCategory);

        itemCategoryRepository.save(itemCategory);
    }

    //branch 로 검색했을 때, 같은 branch의 카테고리들의 이름을 리스트 형태로 가져오기.
    public List<String> getCategoriesNameByBranch(String branch){

        // 같은 branch 있는 카테고리들을 리스트 형태로 가져오기.
        List<ItemCategory> itemCategoryList = itemCategoryRepository.getAllCategoryByBranch(branch);

        // 같은 branch 있는 카테고리들의 이름을 리스트형태로 가져오기.
        List<String> categoryNameList = itemCategoryList.stream().map(c -> c.getName()).collect(Collectors.toList());

        return categoryNameList;
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




