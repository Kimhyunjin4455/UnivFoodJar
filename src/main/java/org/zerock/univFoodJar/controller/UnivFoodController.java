package org.zerock.univFoodJar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.univFoodJar.dto.PageRequestDTO;
import org.zerock.univFoodJar.dto.UnivFoodDTO;
import org.zerock.univFoodJar.service.UnivFoodService;

@Controller
@RequestMapping("/univFood")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "UnivFoodController", description = "univFood 관련 API")
public class UnivFoodController {
    // post 방식으로 전달된 파라미터들을 UnivFoodDTO로 수집해서 UnivFoodService 타입 객체의 register()를 호출하도록

    private final UnivFoodService univFoodService;
    @GetMapping("/register")
    @Operation(summary = "음식점 등록 페이지", description = "가게를 등록하는 페이지를 반환합니다.")
    public void register(){

    }

    @PostMapping("/register")
    @Operation(summary = "음식점 등록", description = "새로운 가게를 등록합니다.")
    @Parameter(name="uno", description ="등록하는 게시글의 순번")
    @Parameter(name="restaurantName", description = "등록하는 식당의 이름")
    @Parameter(name = "foodField", description = "등록하는 식당 음식의 종류(ex: 일식, 양식, 한식, 중식, 동남아)")
    @Parameter(name = "signatureMenu", description = "식당의 대표 음식")
    @Parameter(name = "contact", description = "식당의 연락처")
    @Parameter(name = "address", description = "식당의 주소")
    @Parameter(name = "imageDTOList", description = "이미지DTO의 리스트")
    @Parameter(name = "avg", description = "식당 별점의 평균 점수")
    @Parameter(name = "reviewCnt", description = "식당 게시글 댓글의 개수")
    @Parameter(name = "regDate", description = "게시글을 처음 등록한 일자")
    @Parameter(name = "modDate", description = "게시글을 마지막으로 수정한 일자")
    public String register(UnivFoodDTO univFoodDTO, RedirectAttributes redirectAttributes){
        log.info("univFoodDTO: " + univFoodDTO);
        Long uno = univFoodService.register(univFoodDTO);
        redirectAttributes.addFlashAttribute("msg", uno);

        return "redirect:/univFood/list";
    }

    @GetMapping("/list")
    @Operation(summary = "음식점 목록 조회", description = "식당 목록을 반환합니다.")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", univFoodService.getList(pageRequestDTO));
    }

    @GetMapping({"","/"})
    @Operation(summary = "리다이렉트", description = "기본 페이지에서 식당 목록 페이지로 리다이렉트합니다.")
    public String index(){
        return "redirect:/univFood/list";
    }

    @GetMapping({"/read", "/modify"})
    @Operation(summary = "음식점 상세보기 및 수정 페이지", description = "등록된 식당의 상세정보 또는 수정 페이지를 반환합니다.")
    public void read(Long uno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("uno: " + uno);
        UnivFoodDTO univFoodDTO = univFoodService.getUnivFood(uno);
        model.addAttribute("dto", univFoodDTO);
    }


    @GetMapping("/delete/{uno}")
    @Operation(summary = "음식점 삭제", description = "특정 음식점을 삭제합니다.")
    public String removeUnivFood(@PathVariable Long uno, RedirectAttributes redirectAttributes){ //
        log.info("--------------delete univFood-------------------");
        log.info("uno: " + uno);

        univFoodService.remove(uno);

        redirectAttributes.addFlashAttribute("msg", uno);

        log.info("--------------successly delete------------------");

        return "redirect:/univFood";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    @Operation(summary = "음식점 수정", description = "기존 음식점 정보를 수정합니다.")
    public String modify(UnivFoodDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){
        log.info("-----------------post modify------------------");
        log.info("dto: "+dto);

        Long uno = univFoodService.modify(dto);
        log.info(dto.getImageDTOList());

        redirectAttributes.addAttribute("uno", uno);
//        redirectAttributes.addAttribute("restaurantName", dto.getRestaurantName());
//        redirectAttributes.addAttribute("signatureMenu", dto.getSignatureMenu());
//        redirectAttributes.addAttribute("address", dto.getAddress());
//        redirectAttributes.addAttribute("contact", dto.getContact());
//        redirectAttributes.addAttribute("imgList", dto.getImageDTOList());
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());




        return "redirect:/univFood/read";
    }




}
