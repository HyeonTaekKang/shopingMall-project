package com.homeshoping.homeshoping.service.item;

import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.itemCategory.ItemCategoryRepository;
import com.homeshoping.homeshoping.repository.itemInfo.ItemInfoRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemEdit;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;

import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryEdit;
import com.homeshoping.homeshoping.request.itemCategory.ParentItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoCreate;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoEdit;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionCreate;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionEdit;
import com.homeshoping.homeshoping.response.Item.ItemListResponse;
import com.homeshoping.homeshoping.response.Item.ItemResponse;
import com.homeshoping.homeshoping.service.itemCategory.ItemCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemCategoryService itemCategoryService;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    ItemInfoRepository itemInfoRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;


    @BeforeEach
    void clean() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @Rollback(value = false)
    @Transactional
    void itemRegistrationTest() throws IOException {

        createItem();
//        // given
//        // 아이템 info 생성.
//        ItemInfoCreate itemInfoCreate = createItemInfo();
//
//        // 대분류 아이템 category 생성. ( 대분류(branch) = 패션 , 소분류(name) = ROOT )
//        ParentItemCategoryCreate parentItemCategory = createParentItemCategory();
//
//        // 소분류 아이템 category 에 위에서 만든 부모 아이템 카테고리 셋팅
//        ItemCategoryCreate smallItemCategory = ItemCategoryCreate.builder()
//                .branch("패션")
//                .name("PANTS")
//                .parentItemCategory(parentItemCategory)
//                .build();
//
//        // 아이템 option 생성.
//        List<ItemOptionCreate> itemOptionCreateList = createItemOption();
//
//        // 생성할 아이템 셋팅
//        ItemCreate itemCreate = ItemCreate.builder()
//                .name("와이드팬츠")
//                .price(10000)
//                .itemInfoCreate(itemInfoCreate)
//                .itemOptionCreateList(itemOptionCreateList)
//                .itemCategoryCreate(smallItemCategory) // 부모가 셋팅된 자식 카테고리
//                .stockQuantity(10000)
//                .itemOptionCreateList(itemOptionCreateList)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        // when
//        // 상품 등록
//        itemService.itemRegistration(itemCreate);

        // then
        // 상품 검증
//        assertEquals("와이드팬츠",itemCreate.getName());
//        assertEquals(10000,itemCreate.getPrice());
//        assertEquals(,itemCreate.getItemInfoCreate());
//        assertEquals(10000,itemCreate.getStockQuantity());
//        assertEquals("에스파",itemCreate.getAlbum().getArtist());
    }


    @Test
    @DisplayName("등록된 상품 1개 가져오기 테스트")
    @Rollback(value = false)
    @Transactional
    void getRegistratedItemTest() throws IOException {

        // 상품 생성후, 생성한 아이템의 id 가져오기
        Long itemId = createItem();

        ItemResponse registratedItem = itemService.getRegistratedItem(itemId);

        System.out.println("registratedItem = " + registratedItem.toString());
    }

    @Test
    @DisplayName("등록한 상품을 최신순으로 20개 가져오기 테스트(제일 처음 페이지)")
    @Rollback(value = false)
    void getLatestItemsTest() throws IOException {

        // given
        // 상품 300개 등록
        for (int i = 1; i <= 300; i++) {

            // 아이템 info 생성.
            ItemInfoCreate itemInfoCreate = createItemInfo();

            // 대분류 아이템 category 생성. ( 대분류(branch) = 패션 , 소분류(name) = ROOT )
            ParentItemCategoryCreate parentItemCategory = createParentItemCategory();

            // 소분류 아이템 category 에 위에서 만든 부모 아이템 카테고리 셋팅
            ItemCategoryCreate smallItemCategory = ItemCategoryCreate.builder()
                    .branch("패션")
                    .name("PANTS")
                    .parentItemCategory(parentItemCategory)
                    .build();

            // 아이템 option 생성.
            List<ItemOptionCreate> itemOptionCreateList = createItemOption();

            // 생성할 아이템 셋팅
            ItemCreate itemCreate = ItemCreate.builder()
                    .name("와이드팬츠" + i)
                    .price(10000)
                    .itemInfoCreate(itemInfoCreate)
                    .itemOptionCreateList(itemOptionCreateList)
                    .itemCategoryCreate(smallItemCategory) // 부모가 셋팅된 자식 카테고리
                    .stockQuantity(10000)
                    .itemOptionCreateList(itemOptionCreateList)
                    .createdAt(LocalDateTime.now())
                    .build();

            // 상품 등록
            itemService.itemRegistration(itemCreate);
        }

        ItemSearch itemSearch = new ItemSearch();

        // when
        // 제~일 최신제품 20개 가져오기
        List<ItemResponse> items = itemService.getLatestItems(itemSearch); // [{},{},{}]

        // then
        // 가져온 제품 검증
        assertEquals("와이드팬츠300", items.get(0).getName());
        assertEquals(20, items.size());
    }

    @Test
    @DisplayName("등록한 상품을 최신순으로 20개 가져오기 테스트(두번쨰 페이지)")
    @Rollback(value = false)
    void getAllRegistratedItemSecondPageTest() throws IOException {

        // given
        // 상품 300개 등록
        for (int i = 1; i <= 300; i++) {

            // 아이템 info 생성.
            ItemInfoCreate itemInfoCreate = createItemInfo();

            // 대분류 아이템 category 생성. ( 대분류(branch) = 패션 , 소분류(name) = ROOT )
            ParentItemCategoryCreate parentItemCategory = createParentItemCategory();

            // 소분류 아이템 category 에 위에서 만든 부모 아이템 카테고리 셋팅
            ItemCategoryCreate smallItemCategory = ItemCategoryCreate.builder()
                    .branch("패션")
                    .name("PANTS")
                    .parentItemCategory(parentItemCategory)
                    .build();

            // 아이템 option 생성.
            List<ItemOptionCreate> itemOptionCreateList = createItemOption();

            // 생성할 아이템 셋팅
            ItemCreate itemCreate = ItemCreate.builder()
                    .name("와이드팬츠" + i)
                    .price(10000)
                    .itemInfoCreate(itemInfoCreate)
                    .itemOptionCreateList(itemOptionCreateList)
                    .itemCategoryCreate(smallItemCategory) // 부모가 셋팅된 자식 카테고리
                    .stockQuantity(10000)
                    .itemOptionCreateList(itemOptionCreateList)
                    .createdAt(LocalDateTime.now())
                    .build();

            // 상품 등록
            itemService.itemRegistration(itemCreate);
        }

        // 2번째 페이지
        ItemSearch itemSearch = ItemSearch.builder()
                .size(20)
                .page(2)
                .build();

        // when
        // 2번째 페이지의 상품 20개 가져오기
        List<ItemResponse> items = itemService.getLatestItems(itemSearch); // [{},{},{}]

        // then
        // 가져온 제품 검증
        assertEquals("와이드팬츠280", items.get(0).getName());
        assertEquals(20, items.size());
    }

    @Test
    @DisplayName("대분류 카테고리별 아이템 가져오기 테스트 ( 대분류가 TOP인 아이템 가져오기 )")
    @Rollback(value = false)
    @Transactional
    void findAllItemByCategoryTest() throws IOException {

        // given

        // 대분류 아이템 category 생성. ( 대분류(branch) = TOP , 소분류(name) = ROOT )
        ParentItemCategoryCreate parentItemCategory = createParentItemCategory();

        // 대분류 카테고리가 "TOP" 인 아이템 10개 생성
        for (int i = 1; i <= 10; i++) {

            // 아이템 info 생성.
            ItemInfoCreate itemInfoCreate = createItemInfo();

            // 소분류 아이템 category 에 위에서 만든 부모 아이템 카테고리 셋팅
            ItemCategoryCreate smallItemCategory = ItemCategoryCreate.builder()
                    .branch("TOP")
                    .name("맨투맨")
                    .parentItemCategory(parentItemCategory)
                    .build();

            // 아이템 option 생성.
            List<ItemOptionCreate> itemOptionCreateList = createItemOption();

            // 생성할 아이템 셋팅
            ItemCreate itemCreate = ItemCreate.builder()
                    .name("와이드팬츠")
                    .price(10000)
                    .itemInfoCreate(itemInfoCreate)
                    .itemOptionCreateList(itemOptionCreateList)
                    .itemCategoryCreate(smallItemCategory) // 부모가 셋팅된 자식 카테고리
                    .stockQuantity(10000)
                    .itemOptionCreateList(itemOptionCreateList)
                    .createdAt(LocalDateTime.now())
                    .build();

            // 상품 등록
            Long itemId = itemService.itemRegistration(itemCreate);
        }


        // when
        // 대분류 카테고리가 "OUTER"인 제품만 찾아오기
        ItemListResponse items = itemService.findAllItemByCategoryBranch(parentItemCategory.getBranch());

        System.out.println("items = " + items.getData().toString());
    }

    @Test
    @DisplayName("상품 변경 테스트 ")
    @Rollback(value = false)
    @Transactional
    void ItemEditTest() throws IOException {

        // given
        // 제품 한개 등록
        Long itemId = createItem();

        // 등록한 제품 찾아오기
        Item item = itemRepository.getItem(itemId);

        // 변경된 상품 상세정보.
        ItemInfoEdit editedItemInfo = createEditedItemInfo();

        // 변경된 대분류 아이템 category 생성. ( 대분류(branch) = 패션 , 소분류(name) = ROOT )
        ItemCategoryEdit editedBigItemCategory = createEditedBigItemCategory();

        // 변경된 소분류 아이템 category 에 위에서 만든 변경된 부모 아이템 카테고리 셋팅
        ItemCategoryEdit editedSmallItemCategory = ItemCategoryEdit.builder()
                .branch("PANTS")
                .name("검정색 슬렉스")
                .parentItemCategory(editedBigItemCategory)
                .build();
//
        // 변경된 상품 옵션
        List<ItemOptionEdit> editedItemOptionList = createEditedItemOption();

        // 변경된 아이템 셋팅
        ItemEdit editedItem = ItemEdit.builder()
                .id(item.getItemInfo().getId())
                .name("hypeboy")
                .price(10000)
                .editedItemInfo(editedItemInfo)
                .editedItemCategory(editedSmallItemCategory) // 부모가 셋팅된 수정된 자식 카테고리
                .editedItemOptionList(editedItemOptionList)
                .stockQuantity(10000)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

//         변경된 제품
//        ItemEdit editedItem = createEditedItem();

        // when
        // 제품 변경
        itemService.editItem(item.getId(), editedItem);

        // then
        // 변경전 제품의 이름과 변경 후 제품의 이름이 같은지 검증
        assertEquals("hypeboy", item.getName());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    /**
     * 상품과 연관관계가 있는 itemInfo 와 itemOption 도 같이 삭제가 되야함.
     * 대신 'itemCateogry' 상품과 연관관계가 있지만  상품이 삭제되어도 그대로 남아 있어야 함.
     */
    void deleteItemTest() throws IOException {

        // given
        // 제품 한개 등록
        Long itemId = createItem();

        // when
        itemRepository.deleteById(itemId);

        // then

    }

    // 상품 등록 메서드
    private Long createItem() throws IOException {

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

//        // 생성한 카테고리를 branch 와 name 으로 찾아오기
//        ItemCategory smallItemCategory = itemCategoryRepository.findByBranchAndName(childItemCategory.getBranch(), childItemCategory.getName()).orElseThrow(() -> new CategoryNotFound());

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
        return itemId;
    }

    // 변경된 상품 리턴 메서드 ( 위에 상품등록 메서드가 리턴하는 상품과 이름만 다른 상품을 리턴하는 메서드임 )
    private ItemEdit createEditedItem() {

        // given
        // 변경된 아이템 info 생성.
        ItemInfoEdit editedItemInfo = createEditedItemInfo();

        // 변경된 대분류 아이템 category 생성. ( 대분류(branch) = 패션 , 소분류(name) = ROOT )
        ItemCategoryEdit editedBigItemCategory = createEditedBigItemCategory();

        // 변경된 소분류 아이템 category 에 위에서 만든 변경된 부모 아이템 카테고리 셋팅
        ItemCategoryEdit editedSmallItemCategory = ItemCategoryEdit.builder()
                .branch("PANTS")
                .name("검정색 슬렉스")
                .parentItemCategory(editedBigItemCategory)
                .build();

        // 변경된 아이템 option 생성.
        List<ItemOptionEdit> editedItemOptionList = createEditedItemOption();

        // 변경된 아이템 셋팅
        ItemEdit editedItem = ItemEdit.builder()
                .name("hypeboy")
                .price(10000)
                .editedItemInfo(editedItemInfo)
                .editedItemCategory(editedSmallItemCategory)
                .editedItemOptionList(editedItemOptionList) // 부모가 셋팅된 수정된 자식 카테고리
                .stockQuantity(10000)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        // 변경된 아이템 return
        return editedItem;

    }


    // 새로운 ItemInfo 생성
    private ItemInfoCreate createItemInfo() {
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

    // 변경된 ItemInfo 생성
    private ItemInfoEdit createEditedItemInfo() {
        return ItemInfoEdit.builder()
                .manufactureCountry("중국")
                .material("울")
                .color("하얀색")
                .size("S,M,L")
                .maker("(주)중국")
                .washingMethod("드라이클리닝")
                .yearAndMonthofManufacture(LocalDate.now())
                .manager("강현택")
                .qualityStandard("잘 보존하세요")
                .build();
    }


    // 새로운 ItemOption 생성
    private List<ItemOptionCreate> createItemOption() {

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

    // 변경된 ItemOption 생성
    private List<ItemOptionEdit> createEditedItemOption() {

        List<ItemOptionEdit> editedItemOptionList = new ArrayList();

        // 변경된 itemOption 2개 생성.
        ItemOptionEdit editedItemOptionCreate1 = ItemOptionEdit.builder()
                .optionName("Size")
                .optionalItem1("S")
                .optionalItem2("M")
                .optionalItem3("L")
                .optionalItem4("XL")
                .optionalItem5("XXL")
                .build();

        ItemOptionEdit editedItemOptionCreate2 = ItemOptionEdit.builder()
                .optionName("Color")
                .optionalItem1("Black")
                .optionalItem2("Red")
                .optionalItem3("Green")
                .optionalItem4("Yellow")
                .optionalItem5("White")
                .build();

        editedItemOptionList.add(editedItemOptionCreate1);
        editedItemOptionList.add(editedItemOptionCreate2);

        return editedItemOptionList;
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

    // 변경된 ( 대분류 ) ItemCategory 리턴
    private ItemCategoryEdit createEditedBigItemCategory() {

        // 대분류 생성
        ItemCategoryEdit editedBigItemCategory = ItemCategoryEdit.builder()
                .branch("PANTS")
                .name("ROOT")
                .build();

        return editedBigItemCategory;
    }

    // ( 대분류 ) 이름이 "OUTER"인 대분류 ItemCategory 리턴
    private ItemCategoryCreate createBigItemCategoryNameOuter() {

        // 대분류 생성
        ItemCategoryCreate bigItemCategoryCreate = ItemCategoryCreate.builder()
                .branch("OUTER")
                .name("ROOT")
                .build();

        return bigItemCategoryCreate;
    }

    // ( 대분류 ) 이름이 "TOP"인 대분류 ItemCategory 리턴
    private ItemCategoryCreate createBigItemCategoryNameTop() {

        // 대분류 생성
        ItemCategoryCreate bigItemCategoryCreate = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        return bigItemCategoryCreate;
    }

    // 카테고리 생성메서드 ( 같은 branch인데 name이 다른 두 카테고리 생성. )
    public void createCategory(){

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

    // ( 대분류 = 컴퓨터 , 이름 = 그래픽 카드 ) 인 카테고리 생성.
//    private ItemCategoryCreate createCategory(){
//        // 대분류 생성
//        ItemCategoryCreate bigItemCategoryCreate = ItemCategoryCreate.builder()
//                .branch("컴퓨터")
//                .name("ROOT")
//                .build();
//
//        // 소분류 생성
//        ItemCategoryCreate smallItemCategoryCreate = ItemCategoryCreate.builder()
//                .branch("컴퓨터")
//                .name("그래픽카드")
//                .parentItemCategory(bigItemCategoryCreate)
//                .build();
//
//        itemCategoryService.createCategory(smallItemCategoryCreate);
//        return smallItemCategoryCreate;
//    }



}