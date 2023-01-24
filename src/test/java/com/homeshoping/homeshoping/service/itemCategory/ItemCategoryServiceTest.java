package com.homeshoping.homeshoping.service.itemCategory;


import com.homeshoping.homeshoping.repository.itemCategory.ItemItemCategoryRepository;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
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
    ItemItemCategoryRepository itemCategoryRepository;


    @Test
    @Rollback(value = false)
    @DisplayName("카테고리 저장 테스트 ( 대분류 )")
    public void createBigCategoryTest(){
        ItemCategoryCreate itemCategoryCreate = ItemCategoryCreate.builder()
                .branch("패션")
                .name("ROOT")
                .build();

        itemCategoryService.createBigCategory(itemCategoryCreate);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("카테고리 저장 테스트 ( 소분류 )")
    public void createSmallCategoryTest(){

        // 대분류 생성
        ItemCategoryCreate bigItemCategoryCreate = ItemCategoryCreate.builder()
                .branch("패션")
                .name("ROOT")
                .build();

        itemCategoryService.createBigCategory(bigItemCategoryCreate);

        // 소분류 생성
        ItemCategoryCreate smallItemCategoryCreate = ItemCategoryCreate.builder()
                .branch("패션")
                .name("PANTS")
                .build();

        itemCategoryService.createSmallCategory(smallItemCategoryCreate);

        // 대분류 생성
        ItemCategoryCreate bigItemCategoryCreate1 = ItemCategoryCreate.builder()
                .branch("컴퓨터")
                .name("ROOT")
                .build();

        itemCategoryService.createBigCategory(bigItemCategoryCreate1);

        // 소분류 생성
        ItemCategoryCreate smallItemCategoryCreate2 = ItemCategoryCreate.builder()
                .branch("컴퓨터")
                .name("그래픽카드")
                .build();

        itemCategoryService.createSmallCategory(smallItemCategoryCreate2);

    }

    @Test
    @Rollback(value = false)
    @DisplayName("같은 branch 있는 카테고리들의 이름을 리스트형태로 가져오기.")
    public void getCategorysNameByBranchTest(){

        // 대분류 생성
        ItemCategoryCreate bigItemCategoryCreate = ItemCategoryCreate.builder()
                .branch("패션")
                .name("ROOT")
                .build();

        itemCategoryService.createBigCategory(bigItemCategoryCreate);

        // 소분류 생성
        ItemCategoryCreate smallItemCategoryCreate = ItemCategoryCreate.builder()
                .branch("패션")
                .name("PANTS")
                .build();

        itemCategoryService.createSmallCategory(smallItemCategoryCreate);

        List<String> categoryList = itemCategoryService.getCategoriesNameByBranch("패션");

        System.out.println("categoryList= " + categoryList);
    }
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