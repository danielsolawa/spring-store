package com.danielsolawa.storeauth.controllers;

import com.danielsolawa.storeauth.dtos.ProductListDto;
import com.danielsolawa.storeauth.services.ProductSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProductSearchController.BASE_URL)
public class ProductSearchController {

    public static final String BASE_URL = "/api/v1/product-search";

    private final ProductSearchService productSearchService;

    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }


    @GetMapping("/{word}")
    @ResponseStatus(HttpStatus.OK)
    public ProductListDto searchForProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "0") Integer size,
            @PathVariable("word") String word){

        if(page.equals(0) && size.equals(0)){
            return new ProductListDto((productSearchService.searchForProductByKeyword(word)),
                    productSearchService.countSearchForProductByKeyword(word));
        }

        return new ProductListDto(productSearchService.searchForProductByKeyword(word, page, size),
                productSearchService.countSearchForProductByKeyword(word));
    }



}
