package com.joshua.gallery.backend.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ItemApiController {

     @GetMapping("/items")
     public List<String> getItems() {
          List<String> items = new ArrayList<>();
          items.add("alpha");
          items.add("bata");
          items.add("gamma");
          return items;
     }
}
