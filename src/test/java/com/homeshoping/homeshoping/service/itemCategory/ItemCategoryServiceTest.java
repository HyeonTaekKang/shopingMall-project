package com.homeshoping.homeshoping.service.itemCategory;


import com.homeshoping.homeshoping.repository.itemCategory.ItemCategoryRepository;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemCategory.ParentItemCategoryCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("부모 카테고리에 자식카테고리 1개 이상 추가 테스트")
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

    @Test
    @Rollback(value = false)
    @DisplayName("존재하는 대분류 카테고리들의 이름을 모두 가져오는 기능 테스트")
    public void getAllParentCategoryNameTest(){

        // given
        // 카테고리 2개 생성 ( 대분류 2개 + 소분류 2개 )
        ParentItemCategoryCreate parentItemCategory1 = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        ParentItemCategoryCreate parentItemCategory2 = ParentItemCategoryCreate.builder()
                .branch("PANTS")
                .name("ROOT")
                .build();

        ItemCategoryCreate childCategory1 = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("맨투맨")
                .parentItemCategory(parentItemCategory1)
                .build();


        ItemCategoryCreate childCategory2 = ItemCategoryCreate.builder()
                .branch("PANTS")
                .name("카고팬츠")
                .parentItemCategory(parentItemCategory2)
                .build();

        itemCategoryService.createCategory(childCategory1);
        itemCategoryService.createCategory(childCategory2);

        // when
        // 존재하는 모든 대분류 카테고리의 이름을 가져오기 ( TOP , PANTS )
        List<String> allParentCategoryNameList = itemCategoryService.getAllParentCategoryName();


        // then
        assertEquals(allParentCategoryNameList.get(0),"TOP");
        assertEquals(allParentCategoryNameList.get(1),"PANTS");

    }

    @Test
    @Rollback(value = false)
    @DisplayName("부모가 주어지면(branch) 그 부모 밑에 있는 자식들의 이름을 가져와야 한다.")
    void getChildCategoryName(){

        /**
         *  테스트 설명
         *   - 부모가 같은 자식카테고리를 3개 만듬.
         *   - 위에서 만든 카테고리와 부모 카테고리가 다른 자식카테고리 1개를 만듬
         *   - 메서드 (getChildCategoryName) 를 실행했을 떄 첫번쨰로 만든 부모가 같은 자식카테고리 3개의 이름만 정확히 가져와야함.
         *   - 만약 부모가 같은 자식카테고리 3개와 부모 카테고리가 다른 자식 카테고리의 이름을 가져올 시 테스트는 실패함.
         */

        // given
        // 부모가 같은 자식 카테고리 3개 생성.
        ParentItemCategoryCreate parentItemCategory1 = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        ItemCategoryCreate childCategory = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("맨투맨")
                .parentItemCategory(parentItemCategory1)
                .build();

        itemCategoryService.createCategory(childCategory);

        // 부모에 자식 카테고리 추가
        ItemCategoryCreate newChildCategory1 = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("셔츠")
                .parentItemCategory(parentItemCategory1)
                .build();

        itemCategoryService.addChildCategory(newChildCategory1);

        // 부모에 자식 카테고리 추가
        ItemCategoryCreate newChildCategory2 = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("반팔")
                .parentItemCategory(parentItemCategory1)
                .build();

        itemCategoryService.addChildCategory(newChildCategory2);


        // 부모 카테고리가 "PANTS" 인 카테고리 생성.
        ParentItemCategoryCreate parentItemCategory2 = ParentItemCategoryCreate.builder()
                .branch("PANTS")
                .name("ROOT")
                .build();

        ItemCategoryCreate newChildCategory3 = ItemCategoryCreate.builder()
                .branch("PANTS")
                .name("카고팬츠")
                .parentItemCategory(parentItemCategory2)
                .build();

        // when
        // 부모 카테고리가 "TOP" 인 자식 카테고리들의 이름을 가져옴.
        List<String> childCategoryNameList = itemCategoryService.getChildCategoryName("TOP");

        // then
        assertEquals(childCategoryNameList.size(), 4);
        assertEquals(childCategoryNameList.get(0), "ROOT");
        assertEquals(childCategoryNameList.get(1), "맨투맨");
        assertEquals(childCategoryNameList.get(2), "셔츠");
        assertEquals(childCategoryNameList.get(3), "반팔");
    }

//    @Test
//    @Rollback(value = false)
//    @DisplayName("대분류 카테고리 삭제 테스트 - 대분류 카테고리 삭제시 그 아래에 있는 소분류 카테고리도 같이 삭제된다")
//    public void deleteParentCategoryTest() {
//
//        /**
//         *  테스트 설명
//         *   - 카테고리 1개 생성 ( branch = "TOP" , name = "맨투맨" )
//         *   -
//         */
//
//        // given
//        ParentItemCategoryCreate parentItemCategory1 = ParentItemCategoryCreate.builder()
//                .branch("TOP")
//                .name("ROOT")
//                .build();
//
//        ItemCategoryCreate childCategory = ItemCategoryCreate.builder()
//                .branch("TOP")
//                .name("맨투맨")
//                .parentItemCategory(parentItemCategory1)
//                .build();
//    }
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