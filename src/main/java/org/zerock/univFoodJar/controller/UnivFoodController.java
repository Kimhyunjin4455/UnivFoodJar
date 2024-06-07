package org.zerock.univFoodJar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class UnivFoodController {
    // post 방식으로 전달된 파라미터들을 UnivFoodDTO로 수집해서 UnivFoodService 타입 객체의 register()를 호출하도록

    private final UnivFoodService univFoodService;
    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(UnivFoodDTO univFoodDTO, RedirectAttributes redirectAttributes){
        log.info("univFoodDTO: " + univFoodDTO);
        Long uno = univFoodService.register(univFoodDTO);
        redirectAttributes.addFlashAttribute("msg", uno);

        return "redirect:/univFood/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", univFoodService.getList(pageRequestDTO));
    }

    @GetMapping({"","/"})
    public String index(){
        return "redirect:/univFood/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long uno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("uno: " + uno);
        UnivFoodDTO univFoodDTO = univFoodService.getUnivFood(uno);
        model.addAttribute("dto", univFoodDTO);
    }


    @GetMapping("/delete/{uno}")
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
