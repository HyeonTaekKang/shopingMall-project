package com.homeshoping.homeshoping.controller.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.itemCategory.ItemCategoryRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemCategory.ParentItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoCreate;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionCreate;
import com.homeshoping.homeshoping.service.item.ItemService;
import com.homeshoping.homeshoping.service.itemCategory.ItemCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    ItemController itemController;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemCategoryService itemCategoryService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("제품 등록 테스트")
    @Rollback(value = false)
    @Transactional
    void createProductTest() throws Exception {

        // given
        // 새로운 상품 등록
        ItemCreate newItem = createItem();

        // 객체 -> JSON
        String productJson = objectMapper.writeValueAsString(newItem);

        // then
        // 검증
        mockMvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @Test
//    @DisplayName("등록된 제품 1개 가져오기 테스트")
//    @Rollback(value = false)
//    void readItemTest() throws Exception{
//
//        // given
//        // 상품 생성후, 생성한 아이템의 id 가져오기
//        Long itemId = createItem();
//
//        // expected
//        // 상품 가져오기
//        mockMvc.perform(get("/item/{itemId}",itemId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }

//    @Test
//    @DisplayName("대분류 카테고리별로 등록된 아이템들 가져오기 테스트")
//    @Rollback(value = false)
//    void readItemsByCategoryBranchTest1() throws Exception{
//        // given
//        // 상품 생성
//
//        // 카테고리 생성1.
//        ItemCategoryCreate createdCategory1 = createCategory1();
//
//        // 카테고리 생성2.
////        CategoryCreate createdCategory2 = createCategory2();
//
//        // 생성한 카테고리 찾아오기                                        // OUTER              // 패딩
//        ItemCategory itemCategory1 = itemCategoryRepository.findByBranchAndName(createdCategory1.getBranch(), createdCategory1.getName()).orElseThrow(() -> new CategoryNotFound());
//
//        // 생성한 카테고리 찾아오기                                        // OUTER              // 더플코트
////        Category category2 = categoryRepository.findByBranchAndName(createdCategory1.getBranch(), createdCategory1.getName()).orElseThrow(() -> new CategoryNotFound());
//
//        // 생성할 아이템 셋팅1
//        ItemCreate itemCreate1 = ItemCreate.builder()
//                .name("유광패딩")
//                .price(10000)
//                .stockQuantity(10000)
//                .category(itemCategory1)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        // 상품 등록1
//        itemService.itemRegistration(itemCreate1);
//
//        // 생성할 아이템 셋팅2 ( 위 아이템이랑 같은 category 임 )
//        ItemCreate itemCreate2 = ItemCreate.builder()
//                .name("무광패딩")
//                .price(10000)
//                .stockQuantity(10000)
//                .category(itemCategory1)
//                .createdAt(LocalDateTime.now())
//
//                .build();
//
//        // 상품 등록2
//        itemService.itemRegistration(itemCreate2);
//
//        mockMvc.perform(get("/items/{categoryBranch}", itemCategory1.getBranch())
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("대분류 카테고리별로 등록된 아이템들 가져오기 테스트 ( category의 branch는 동일하고 , category의 name은 다른 두개의 아이템 생성 )" )
//    @Rollback(value = false)
//    void readItemsByCategoryBranchTest2() throws Exception{
//        // given
//        // 상품 생성
//
//        // 카테고리 생성1.
//        ItemCategoryCreate createdCategory1 = createCategory1();
//
//        // 카테고리 생성2.
//        ItemCategoryCreate createdCategory2 = createCategory2();
//
//        // 생성한 카테고리 찾아오기                                        // OUTER                       // 패딩
//        ItemCategory itemCategory1 = itemCategoryRepository.findByBranchAndName(createdCategory1.getBranch(), createdCategory1.getName()).orElseThrow(() -> new CategoryNotFound());
//
//        // 생성한 카테고리 찾아오기                                        // OUTER                       // 더플코트
//        ItemCategory itemCategory2 = itemCategoryRepository.findByBranchAndName(createdCategory2.getBranch(), createdCategory2.getName()).orElseThrow(() -> new CategoryNotFound());
//
//        // 생성할 아이템 셋팅1
//        ItemCreate itemCreate1 = ItemCreate.builder()
//                .name("유광패딩")
//                .price(10000)
//                .stockQuantity(10000)
//                .category(itemCategory1)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        // 상품 등록1
//        itemService.itemRegistration(itemCreate1);
//
//        // 생성할 아이템 셋팅2
//        ItemCreate itemCreate2 = ItemCreate.builder()
//                .name("롱더플코트")
//                .price(10000)
//                .stockQuantity(10000)
//                .category(itemCategory2)
//                .createdAt(LocalDateTime.now())
//
//                .build();
//
//        // 상품 등록2
//        itemService.itemRegistration(itemCreate2);
//
//        mockMvc.perform(get("/items/{categoryBranch}", itemCategory1.getBranch())
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("소분류 카테고리별로 등록된 아이템들 가져오기 테스트" )
//    @Rollback(value = false)
//    void readItemsByCategoryNameTest() throws Exception{
//        // given
//
//        // 카테고리 생성1.
//        ItemCategoryCreate createdCategory1 = createCategory1();
//
//        // 카테고리 생성2.
//        ItemCategoryCreate createdCategory2 = createCategory2();
//
//        // 생성한 카테고리 찾아오기                                        // OUTER                       // 패딩
//        ItemCategory itemCategory1 = itemCategoryRepository.findByBranchAndName(createdCategory1.getBranch(), createdCategory1.getName()).orElseThrow(() -> new CategoryNotFound());
//
//        // 생성한 카테고리 찾아오기                                        // OUTER                       // 더플코트
//        ItemCategory itemCategory2 = itemCategoryRepository.findByBranchAndName(createdCategory2.getBranch(), createdCategory2.getName()).orElseThrow(() -> new CategoryNotFound());
//
//        // 생성할 아이템 셋팅1 ( 찾아올 아이템 )
//        ItemCreate itemCreate1 = ItemCreate.builder()
//                .name("유광패딩")
//                .price(10000)
//                .stockQuantity(10000)
//                .category(itemCategory1)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        // 상품 등록1
//        itemService.itemRegistration(itemCreate1);
//
//        // 생성할 아이템 셋팅2
//        ItemCreate itemCreate2 = ItemCreate.builder()
//                .name("롱더플코트")
//                .price(10000)
//                .stockQuantity(10000)
//                .category(itemCategory2)
//                .createdAt(LocalDateTime.now())
//
//                .build();
//
//        // 상품 등록2
//        itemService.itemRegistration(itemCreate2);
//
//        mockMvc.perform(get("/category/{categoryName}", itemCategory1.getName())
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("등록한 제품 최신순으로 20개 가져오기 테스트")
//    void readItemsTest() throws Exception {
//
//        // given
//        // 제품 300개 등록
//        for(int i = 1; i<=300; i++){
//            Album album = Album.builder()
//                    .artist("에스파")
//                    .build();
//
//            ItemCreate newProduct = ItemCreate.builder()
//                    .name("savage" + i)
//                    .price(10000)
//                    .stockQuantity(10000)
//
//                    .album(album)
//                    .build();
//
//            itemService.itemRegistration(newProduct);
//        }
//
//        // item 리스트 가져오기 ( 최신순으로 20개 )
//        ItemSearch itemSearch = new ItemSearch();  // page = 0 , size = 20
//        List<ItemResponse> items = itemService.getAllRegistratedItem(itemSearch);
//
//
//        // expected
//        mockMvc.perform(get("/items")
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("savage300"))
//                .andExpect(jsonPath("$[0].price").value(10000))
//                .andExpect(jsonPath("$[0].stockQuantity").value(10000))
//
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("제품 변경 테스트(앨범)")
//    void updateItemTest() throws Exception {
//
//        // given
//        // 제품 한개 등록 ( 앨범 )
//        Item item = saveItemWithAlbum();
//
//        // 변경된 제품
//
//        ItemEdit itemEdit = ItemEdit.builder()
//                .name("hypeboy")
//                .price(10000)
//                .stockQuantity(10000)
//                .build();
//
//        // 변경된 제품 객체를 JSON형태로 변경
//        String editedItem = objectMapper.writeValueAsString(itemEdit);
//
//        // expected
//        // 제품 삭제
//        mockMvc.perform(patch("/item/{itemId}",item.getId())
//                        .content(editedItem)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("제품 삭제 테스트(앨범)")
//    void deleteItemsTest() throws Exception {
//
//        // given
//        // 제품 한개 등록 ( 앨범 )
//        Item item = saveItemWithAlbum();
//
//        // expected
//        // 제품 삭제
//        mockMvc.perform(delete("/item/{itemId}",item.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }



    // 상품 등록 메서드
    private ItemCreate createItem() throws IOException {

        // given
        // 아이템 info 생성.
        ItemInfoCreate itemInfoCreate = createItemInfo();

        // (대분류) 아이템 category 생성. ( 대분류(branch) = TOP , 소분류(name) = ROOT )
        ParentItemCategoryCreate parentItemCategory = createParentItemCategory();

        // (소분류) 아이템 category 에 위에서 만든 부모 아이템 카테고리 셋팅
        ItemCategoryCreate childItemCategory = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("맨투맨")
                .parentItemCategory(parentItemCategory)
                .build();

        // 카테고리 생성.
        itemCategoryService.createCategory(childItemCategory);

        // 아이템 option 생성.
        List<ItemOptionCreate> itemOptionCreateList = createItemOption();

        // 생성할 아이템 셋팅
        ItemCreate itemCreate = ItemCreate.builder()
                .name("와이드팬츠")
                .price(10000)
                .itemInfoCreate(itemInfoCreate)
                .itemOptionCreateList(itemOptionCreateList)
                .itemCategoryCreate(childItemCategory) // 부모가 셋팅된 자식 카테고리
                .stockQuantity(10000)
                .itemOptionCreateList(itemOptionCreateList)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        // 상품 등록
        Long itemId = itemService.itemRegistration(itemCreate);
        return itemCreate;
    }

    // 새로운 ItemInfo 생성
    private ItemInfoCreate createItemInfo(){
        return ItemInfoCreate.builder()
                .manufactureCountry("대한민국")
                .material("울")
                .color("검정,아이보리,청색")
                .size("S,M,L")
                .maker("(주)한국꺼")
                .washingMethod("드라이클리닝")
                .yearAndMonthofManufacture(LocalDate.now())
                .manager("강현택")
                .qualityStandard("잘 보존하세요")
                .build();
    }

    // 새로운 ItemOption 생성
    private List<ItemOptionCreate> createItemOption(){

        List<ItemOptionCreate> itemOptionCreateList = new ArrayList();

        // itemOption 2개 생성.
        ItemOptionCreate itemOptionCreate1 = ItemOptionCreate.builder()
                .optionName("Size")
                .optionalItem1("S")
                .optionalItem2("M")
                .optionalItem3("L")
                .optionalItem4("XL")
                .build();

        ItemOptionCreate itemOptionCreate2 = ItemOptionCreate.builder()
                .optionName("Color")
                .optionalItem1("Black")
                .optionalItem2("Red")
                .optionalItem3("Green")
                .optionalItem4("Yellow")
                .build();

        itemOptionCreateList.add(itemOptionCreate1);
        itemOptionCreateList.add(itemOptionCreate2);

        return itemOptionCreateList;
    }


    // ( 대분류 ) ItemCategory 리턴
    private ParentItemCategoryCreate createParentItemCategory() {

        // 대분류 생성
        ParentItemCategoryCreate parentItemCategory = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        return parentItemCategory;
    }


}

































