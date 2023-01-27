package com.homeshoping.homeshoping.service.Item;

import com.homeshoping.homeshoping.Exception.ItemNotFound;

import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.itemCategory.ItemItemCategoryRepository;
import com.homeshoping.homeshoping.repository.itemInfo.ItemInfoRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;

import com.homeshoping.homeshoping.request.itemInfo.ItemInfoCreate;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionCreate;
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
    ItemItemCategoryRepository itemCategoryRepository;

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
    void itemRegistrationTest(){

        // given
        // 아이템 info 생성.
        ItemInfoCreate itemInfoCreate = createItemInfo();

        // 대분류 아이템 category 생성. ( 대분류(branch) = 패션 , 소분류(name) = ROOT )
        ItemCategoryCreate bigItemCategory = createBigItemCategory();

        // 소분류 아이템 category 에 위에서 만든 부모 아이템 카테고리 셋팅
        ItemCategoryCreate smallItemCategory = ItemCategoryCreate.builder()
                .branch("패션")
                .name("PANTS")
                .parentItemCategory(bigItemCategory)
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

        // when
        // 상품 등록
        itemService.itemRegistration(itemCreate);

        System.out.println("itemCreate.toString() = " + itemCreate.toString());

        // then
        // 상품 검증
//        assertEquals("savage",itemCreate.getName());
//        assertEquals(10000,itemCreate.getPrice());
//        assertEquals(10000,itemCreate.getStockQuantity());
//        assertEquals("에스파",itemCreate.getAlbum().getArtist());
    }


    @Test
    @DisplayName("등록된 상품 1개 가져오기 테스트")
    @Rollback(value = false)
    @Transactional
    void getRegistratedItemTest(){

        // 상품 생성후, 생성한 아이템의 id 가져오기
        Long itemId = createItem();

        ItemResponse registratedItem = itemService.getRegistratedItem(itemId);

        System.out.println("registratedItem = " + registratedItem.toString());
    }

//
//    @Test
//    @DisplayName("카테고리별 아이템 가져오기 테스트")
//    @Rollback(value = false)
//    void findAllItemByCategoryTest(){
//
//        // given
//        // 상품 생성
//        Album album = Album.builder()
//                .artist("에스파")
//                .build();
//
//        // 카테고리 생성.
//        ItemCategoryCreate itemCategoryCreate = createCategory();
//
//        // 생성한 카테고리 찾아오기                                        // 패션                         // PANTS
//        ItemCategory itemCategory = categoryRepository.findByBranchAndName(itemCategoryCreate.getBranch(), itemCategoryCreate.getName()).orElseThrow(() -> new ItemNotFound());
//
//        // 생성할 아이템 셋팅
//        ItemCreate itemCreate = ItemCreate.builder()
//                .name("savage")
//                .price(10000)
//                .stockQuantity(10000)
//                .category(itemCategory)
//                .createdAt(LocalDateTime.now())
//                .album(album)
//                .build();
//
//        // 상품 등록
//        itemService.itemRegistration(itemCreate);
//
//        // when                                                          // 패션
//        CategoryListResponse items = itemService.findAllItemByCategoryBranch(itemCategoryCreate.getBranch());
//
//        System.out.println("items.getData().toString() = " + items.getData().toString());;
//    }
//
//    @Test
//    @DisplayName("등록된 제품 하나 가져오기 테스트(앨범)")
//    @Rollback(value = false)
//    void getRegistratedItemTest(){
//
//        // given
//        // 제품 등록
//        Album album = Album.builder()
//                .artist("에스파")
//                .build();
//
//        ItemCreate itemCreate = ItemCreate.builder()
//                .name("savage")
//                .price(10000)
//                .stockQuantity(10000)
//
//                .album(album)
//                .build();
//
//        itemService.itemRegistration(itemCreate);
//
//        // when
//        // 제품 가져오기
//        ItemResponse foundedItem = itemService.getRegistratedItem(1L);
//
//        // then
//        // 가져온 제품 검증
//        assertEquals("savage",foundedItem.getName());
//        assertEquals(10000,foundedItem.getPrice());
//        assertEquals(10000,foundedItem.getStockQuantity());
//
//    }
//
//    @Test
//    @DisplayName("등록한 상품을 최신순으로 20개 가져오기 테스트(제일 처음 페이지)")
//    @Rollback(value = false)
//    void getAllRegistratedItemFirstPageTest(){
//
//        // given
//        // 제품 300개 등록
//        for(int i = 1; i<=300; i++){
//            Album album = Album.builder()
//                    .artist("에스파")
//                    .build();
//
//            ItemCreate itemCreate = ItemCreate.builder()
//                    .name("savage" + i)
//                    .price(10000)
//                    .stockQuantity(10000)
//
//                    .album(album)
//                    .build();
//
//            itemService.itemRegistration(itemCreate);
//        }
//
//        ItemSearch itemSearch = new ItemSearch();
//
//        // when
//        // 제~일 최신제품 20개 가져오기
//        List<ItemResponse> items = itemService.getAllRegistratedItem(itemSearch); // [{},{},{}]
//
//        // then
//        // 가져온 제품 검증
//        assertEquals("savage300",items.get(0).getName());
//        assertEquals(20,items.size());
//    }
//
//    @Test
//    @DisplayName("등록한 상품을 최신순으로 20개 가져오기 테스트(두번쨰 페이지)")
//    @Rollback(value = false)
//    void getAllRegistratedItemSecondPageTest(){
//
//        // given
//        // 제품 300개 등록
//        for(int i = 1; i<=300; i++){
//            Album album = Album.builder()
//                    .artist("에스파")
//                    .build();
//
//            ItemCreate itemCreate = ItemCreate.builder()
//                    .name("savage" + i)
//                    .price(10000)
//                    .stockQuantity(10000)
//
//                    .album(album)
//                    .build();
//
//            itemService.itemRegistration(itemCreate);
//        }
//
//        // 2번째 페이지
//        ItemSearch itemSearch = ItemSearch.builder()
//                .size(20)
//                .page(2)
//                .build();
//
//        // when
//        // 2번째 페이지의 상품 20개 가져오기
//        List<ItemResponse> items = itemService.getAllRegistratedItem(itemSearch); // [{},{},{}]
//
//        // then
//        // 가져온 제품 검증
//        assertEquals("savage280",items.get(0).getName());
//        assertEquals(20,items.size());
//    }
//
//    @Test
//    @DisplayName("상품 변경 테스트(앨범)")
//    void editItemTest(){
//
//        // given
//        // 제품 한개 등록 ( 앨범 )
//        Album album = Album.builder()
//                .artist("에스파")
//                .build();
//
//        Item item = Item.builder()
//                .name("savage")
//                .price(10000)
//                .stockQuantity(10000)
//
//                .album(album)
//                .build();
//
//        itemRepository.save(item);
//
//        // 변경된 제품
//        Album editedAlbum = Album.builder()
//                .artist("뉴진스")
//                .build();
//
//        ItemEdit itemEdit = ItemEdit.builder()
//                .name("hypeboy")
//                .price(10000)
//                .stockQuantity(10000)
//
//                .album(editedAlbum)
//                .build();
//
//        // when
//        // 제품 변경
//        itemService.editItem(item.getId(),itemEdit);
//
//        // then
//        // 변경전 제품의 id 로 제품을 찾아와서
//        Item updatedItem = itemRepository.findById(item.getId()).orElseThrow(ItemNotFound::new);
//
//        // 변경이 제대로 되었는지 검증
//        assertEquals("hypeboy",updatedItem.getName());
//    }
//
//    @Test
//    @DisplayName("상품 삭제 테스트(앨범)")
//    void deleteItemTest(){
//
//        // given
//        // 제품 한개 등록 ( 앨범 )
//        Album album = Album.builder()
//                .artist("에스파")
//                .build();
//
//        Item item = Item.builder()
//                .name("savage")
//                .price(10000)
//                .stockQuantity(10000)
//
//                .album(album)
//                .build();
//
//        itemRepository.save(item);
//
//        // when
//        // 제품 삭제
//        itemService.deleteItem(item.getId()); // [{},{},{}]
//
//        // then
//        assertEquals(0,itemRepository.count());
//    }

    // 상품 등록 메서드
    private Long createItem(){

        // given
        // 아이템 info 생성.
        ItemInfoCreate itemInfoCreate = createItemInfo();

        // 대분류 아이템 category 생성. ( 대분류(branch) = 패션 , 소분류(name) = ROOT )
        ItemCategoryCreate bigItemCategory = createBigItemCategory();

        // 소분류 아이템 category 에 위에서 만든 부모 아이템 카테고리 셋팅
        ItemCategoryCreate smallItemCategory = ItemCategoryCreate.builder()
                .branch("패션")
                .name("PANTS")
                .parentItemCategory(bigItemCategory)
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

        // when
        // 상품 등록
        Long itemId = itemService.itemRegistration(itemCreate);
        return itemId;
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
    private ItemCategoryCreate createBigItemCategory(){

        // 대분류 생성
        ItemCategoryCreate bigItemCategoryCreate = ItemCategoryCreate.builder()
                .branch("패션")
                .name("ROOT")
                .build();

        return bigItemCategoryCreate;
    }



}