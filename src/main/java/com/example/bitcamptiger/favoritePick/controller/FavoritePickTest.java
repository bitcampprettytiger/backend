package com.example.bitcamptiger.favoritePick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavoritePickTest {

    @GetMapping("/favorite-Test")
    public String FavoritePickPage() {
        return "FavoritePickAndTop8";
    }
}
