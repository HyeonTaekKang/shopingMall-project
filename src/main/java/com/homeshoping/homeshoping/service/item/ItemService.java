package com.homeshoping.homeshoping.service.item;

import com.homeshoping.homeshoping.Exception.CategoryNotFound;
import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import com.homeshoping.homeshoping.entity.itemOption.ItemOption;
import com.homeshoping.homeshoping.repository.Item.ItemFileRepository;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.ItemOption.ItemOptionRepository;
import com.homeshoping.homeshoping.repository.itemCategory.ItemCategoryRepository;
import com.homeshoping.homeshoping.repository.itemInfo.ItemInfoRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.homeshoping.homeshoping.request.Item.ItemEdit;

import com.homeshoping.homeshoping.response.Item.ItemListResponse;
import com.homeshoping.homeshoping.response.Item.ItemResponse;
import com.homeshoping.homeshoping.response.category.CategoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemInfoRepository itemInfoRepository;

    private final ItemOptionRepository itemOptionRepository;

    private final ItemCategoryRepository itemCategoryRepository;

    private final ItemFileRepository itemFileRepository;
    // 상품 등록하기 ( create )
    @Transactional
    public Long itemRegistration(ItemCreate itemCreate) throws IOException {
/*
         - 첨부 파일 처리
            1. DTO에 담긴 파일을 꺼냄
            2. 파일의 이름을 가져옴
            3. 서버 저장용 이름을 만듦 : 내사진.jpg => 581290_내사진.jpg
            4. 저장 경로 설정
            5. 해당 경로에 파일 저장
            6. item_table 에 해당 데이터 save 처리
            7. item_file_table 에 해당 데이터 save 처리
         */

        // 상품 DTO -> 상품 Entity
        Item newItem = Item.createItem(itemCreate);

        // 상품 저장
        itemRepository.save(newItem).getId();

        // 상품의 카테고리를 지정해주기 위한 코드들
        ItemCategory itemCategory = itemCategoryRepository.findByBranchAndName(itemCreate.getItemCategoryCreate().getBranch(), itemCreate.getItemCategoryCreate().getName())
                .orElseThrow(() -> new CategoryNotFound());

        Item item = itemRepository.findById(newItem.getId()).orElseThrow(() -> new ItemNotFound());

        item.setItemCategory(itemCategory);


//        Item item = itemRepository.findById(newItem.getId()).orElseThrow(() -> new ItemNotFound());
//
//        ItemCategory itemCategory = itemCategoryRepository.findByBranchAndName(item.getItemCategory().getBranch(), item.getItemCategory().getName()).orElseThrow(() -> new CategoryNotFound());

//        // 상품 이미지가 포함된 item을 찾아옴.
//        Item includeFileItem = itemRepository.findById(saveId).get();
//
//        for(MultipartFile itemFile : itemCreate.getItemFile()){
////            MultipartFile itemFile = itemCreate.getItemFile(); // 1
//
//            String originalFilename = itemFile.getOriginalFilename(); // 2
//
//            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3
//
//            // 파일이 설정해둔 경로에 설정해준 이름으로 저장이 되게끔 셋팅해주는 작업.
//            String savePath = "C:/springboot_img/" + storedFileName; // C:/springboot_img/74982760943_내사진.jpg  // 4
//
//            itemFile.transferTo(new File(savePath)); // 5
//
//
//            // 부모 ( item )가 셋팅된 ItemImg
//            ItemImg setItemItemFile = ItemImg.toItemFile(includeFileItem, originalFilename, storedFileName);
//
//            // 부모 ( item ) 가 셋팅된 ItemImg 저장.
//            itemFileRepository.save(setItemItemFile);
        return newItem.getId();
    }

    // 등록한 상품 하나만 가져오기 ( read one )
    public ItemResponse getRegistratedItem(Long itemId){

        // 상품 하나 찾아오고,
        Item item = itemRepository.getItem(itemId);

        // 엔티티 -> DTO로 변경해서 리턴
        return ItemResponse.createItemResponse(item);
    }

    // 등록한 상품 최신순으로 20개씩 가져오기 (read 20 )
    public List<ItemResponse> getLatestItems(ItemSearch itemSearch){

        // 등록한 상품 20개를 최신순으로 리스트형태로 가져오고,
        List<Item> items = itemRepository.getLatestItems(itemSearch); // [{},{}] -> Item

        // 리스트안에 있는 item 객체를 itemResponse객체로 변경한다음, 그 변경한 객체를 리스트 안에 담아서 리턴
        return items.stream().map(item -> ItemResponse.createItemResponse(item)).collect(Collectors.toList());
    }

    // ( 대분류 ) 카테고리별로 상품 가져오기
    public ItemListResponse findAllItemByCategoryBranch(String categoryBranch){

        // 같은 categoryBranch인 Item들을 리스트에 담아옴
        List<Item> items = itemRepository.getAllItemByCategoryBranch(categoryBranch); //[{} , {}]

        // 엔티티 -> DTO
        // 리스트안에 있는 item 객체를 itemResponse객체로 변경한다음, 그 변경한 객체를 리스트 안에 담기.
        List<ItemResponse> itemDto = items.stream().map(item -> ItemResponse.createItemResponse(item)).collect(Collectors.toList());

        return new ItemListResponse(itemDto);
    }

    // ( 소분류 ) 카테고리별로 상품 가져오기
    public CategoryListResponse findAllItemByCategoryName(String categoryName){

        // 같은 categoryName인 Item들을 리스트에 담아옴
        List<Item> items = itemRepository.getAllItemByCategoryName(categoryName); //[{} , {}]

        // 엔티티 -> DTO
        // 리스트안에 있는 item 객체를 itemResponse객체로 변경한다음, 그 변경한 객체를 리스트 안에 담아서 리턴
        List<ItemResponse> itemDto = items.stream().map(item -> ItemResponse.createItemResponse(item)).collect(Collectors.toList());

        return new CategoryListResponse(itemDto);
    }
//
//
    // 상품 수정
    @Transactional  // (변경할 상품id , 변경된 상품)
    public Long editItem(Long itemId, ItemEdit itemEdit){

        // 변경할 상품 가져오기.
//        Item item = itemRepository.getItem(itemId);
        Item item = itemRepository.findById(itemId).get();

        // 변경할 상품의 상세정보 가져오기
        ItemInfo itemInfo = itemInfoRepository.findById(item.getItemInfo().getId()).get();

        // 변경할 상품의 옵션 가져오기
        List<ItemOption> itemOptions = itemOptionRepository.getItemOptions(item.getId());

        // 변경할 상품의 카테고리 가져오기
        ItemCategory itemCategory = itemCategoryRepository.findById(item.getItemCategory().getId()).get();

        // ------------------------------------------------------------------------------------
        // 상품 변경
        item.editItem(itemEdit);

        // 상품 정보 변경
        itemInfo.editItemInfo(itemEdit.getEditedItemInfo());

        // 상품 옵션 변경
        for(int i = 0; i<itemOptions.size(); i++) {
            itemOptions.get(i).editItemOption(itemEdit.getEditedItemOptionList().get(i));
//            itemOptions.set(i,itemEdit.getEditedItemOptionList().get(i));
        }


        return item.getId();


    }

    // 등록한 상품 삭제하기 ( delete )
    @Transactional
    public void deleteItem(Long id){

        // 삭제할 게시물을 찾아와서
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFound());
        // 삭제
        itemRepository.delete(item);
    }
}
