package com.homeshoping.homeshoping.service.itemCategory;


import com.homeshoping.homeshoping.repository.itemCategory.ItemCategoryRepository;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemCategory.ParentItemCategoryCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class ItemCategoryServiceTest {


    @Autowired
    ItemCategoryService itemCategoryService;
    @Autowired
    ItemCategoryRepository itemCategoryRepository;


    @Test
    @Rollback(value = false)
    @DisplayName("카테고리 생성 테스트 ( 대분류 )")
    public void createBigCategoryTest(){
        ParentItemCategoryCreate parentItemCategoryCreate1 = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        itemCategoryService.createParentCategory(parentItemCategoryCreate1);

        ParentItemCategoryCreate parentItemCategoryCreate2 = ParentItemCategoryCreate.builder()
                .branch("PANTS")
                .name("ROOT")
                .build();

        itemCategoryService.createParentCategory(parentItemCategoryCreate2);


    }

    @Test
    @Rollback(value = false)
    @DisplayName("카테고리 생성 테스트 ( 소분류 )")
    public void createCategoryTest(){

        // 첫번쨰 (대분류) 생성
        ParentItemCategoryCreate parentItemCategory1 = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        // 두번쨰 (대분류) 생성.
        ParentItemCategoryCreate parentItemCategory2 = ParentItemCategoryCreate.builder()
                .branch("PANTS")
                .name("ROOT")
                .build();

        // 첫번째 (소분류) 생성.
        ItemCategoryCreate childCategory1 = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("맨투맨")
                .parentItemCategory(parentItemCategory1)
                .build();

        itemCategoryService.createCategory(childCategory1);

        // 두번쨰 (소분류) 생성.
        ItemCategoryCreate childCategory2 = ItemCategoryCreate.builder()
                .branch("PANTS")
                .name("청바지")
                .parentItemCategory(parentItemCategory2)
                .build();

        itemCategoryService.createCategory(childCategory2);

    }

    @Test
    @Rollback(value = false)
    @DisplayName("부모 카테고리에 자식카테고리를 추가 테스트")
    public void addChildCategoryTest(){

        // given
        // 카테고리 하나 생성
        ParentItemCategoryCreate parentItemCategory = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        ItemCategoryCreate childCategory = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("맨투맨")
                .parentItemCategory(parentItemCategory)
                .build();

        itemCategoryService.createCategory(childCategory);

        // 추가하고 싶은 새로운 소분류 생성.
        ItemCategoryCreate newChildCategory = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("티셔츠")
                .parentItemCategory(parentItemCategory)
                .build();

        itemCategoryService.addChildCategory(newChildCategory);
    }

//    @Test
//    @Rollback(value = false)
//    @DisplayName("같은 branch 있는 카테고리들의 이름을 리스트형태로 가져오기.")
//    public void getCategorysNameByBranchTest(){
//
//        // 대분류 생성
//        ItemCategoryCreate bigItemCategoryCreate = ItemCategoryCreate.builder()
//                .branch("패션")
//                .name("ROOT")
//                .build();
//
//        itemCategoryService.createBigCategory(bigItemCategoryCreate);
//
//        // 소분류 생성
//        ItemCategoryCreate smallItemCategoryCreate = ItemCategoryCreate.builder()
//                .branch("패션")
//                .name("PANTS")
//                .build();
//
//        itemCategoryService.createCategory(smallItemCategoryCreate);
//
//        List<String> categoryList = itemCategoryService.getCategoriesNameByBranch("패션");
//
//        System.out.println("categoryList= " + categoryList);
//    }

    // 전체 카테고리 다 가져오기 테스트
//    @Test
//    @Rollback(value = false)
//    @DisplayName("카테고리 종류 전부다 가져오기")
//    public void getAllCategoryTest(){
//        CategoryCreate.builder()
//                .name("OUTER")
//                .build();
//
//        CategoryCreate.builder()
//                .name("TOP")
//                .build();
//    }
    // 카테고리 별로 Item을 가져오기 테스트



//    @Test
//    @Rollback(value = false)
//    public void 카테고리_저장_테스트 () {
//        //given
//        CategoryDTO categoryDTO1 = createCategoryDTO("패션", "패션", "남성패션");
//        Long savedId = categoryService.saveCategory(categoryDTO1);
//
//        CategoryDTO categoryDTO2 = createCategoryDTO("패션", "패션", "여성패션");
//        categoryService.saveCategory(categoryDTO2);
//
//        CategoryDTO categoryDTO3 = createCategoryDTO("패션", "패션", "아동패션");
//        categoryService.saveCategory(categoryDTO3);
//
//        //when
//        Category category = findCategory(savedId);
//        System.out.println("category.toString() = " + category.toString());
//
//        //then
//        assertThat(category.getCode()).isEqualTo("패션");
//
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 카테고리_조회_테스트 () {
//        //given
//        CategoryDTO categoryDTO1 = createCategoryDTO("패션", "패션", "남성패션");
//        categoryService.saveCategory(categoryDTO1);
//
//        CategoryDTO categoryDTO2 = createCategoryDTO("패션", "패션", "여성패션");
//        categoryService.saveCategory(categoryDTO2);
//
//        CategoryDTO categoryDTO3 = createCategoryDTO("패션", "패션", "아동패션");
//        categoryService.saveCategory(categoryDTO3);
//
//        //when
//        Map<String, CategoryDTO> testBranch = categoryService.getCategoryByBranch("패션");
//
//        //then
//        System.out.println(testBranch.toString());
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 카테고리_조회_테스트2 () {
//        //given
//        CategoryDTO categoryDTO1 = createCategoryDTO("패션", "패션", "남성패션");
//        categoryService.saveCategory(categoryDTO1);
//
//        CategoryDTO categoryDTO2 = createCategoryDTO("패션", "패션", "여성패션");
//        categoryService.saveCategory(categoryDTO2);
//
//        CategoryDTO categoryDTO3 = createCategoryDTO("패션", "패션", "아동패션");
//        categoryService.saveCategory(categoryDTO3);
//
//        //when
//        List<Category> categoryList = categoryService.getCategory("패션");
//
//
//        //then
//        System.out.println("categoryList = " + categoryList.toString());
//    }
//
//
//

    // categoryDTO 생성.
//    private CategoryCreate createCategoryDTO(String branch , String name) {
//        CategoryDTO categoryDTO = new CategoryDTO();
//        categoryDTO.setBranch(testBranch);
//        categoryDTO.setCode(testCode);
//        categoryDTO.setName(testName);
//        return categoryDTO;
//    }
//
//
//
//    // 카테고리 Id로 카테고리 찾아오기
//    private Category findCategory(Long categoryId) {
//        return categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
//    }
}