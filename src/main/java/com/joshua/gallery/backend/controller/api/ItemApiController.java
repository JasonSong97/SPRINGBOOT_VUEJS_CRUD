package com.joshua.gallery.backend.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joshua.gallery.backend.model.item.Item;
import com.joshua.gallery.backend.model.item.ItemRepository;

@RequestMapping("/api")
@RestController
public class ItemApiController {

     @Autowired
     ItemRepository itemRepository;

     @GetMapping("/items")
     public List<Item> getItems() {
          List<Item> items = itemRepository.findAll();
          return items;
     }
}
