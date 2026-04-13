package com.qqsystem.serve.controller;

import com.qqsystem.serve.entity.Actor;
import com.qqsystem.serve.service.ActorService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/actor")
public class ActorController {

    @Resource
    private ActorService actorService;

    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam int page, @RequestParam int size) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", actorService.pageList(page, size));
        res.put("total", actorService.countList());
        return res;
    }

    @GetMapping("/detail/{id}")
    public Actor detail(@PathVariable Long id) {
        return actorService.getById(id);
    }

    @GetMapping("/detail/full/{id}")
    public Actor detailFull(@PathVariable Long id) {
        return actorService.getDetailWithRelation(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Actor actor) {
        return actorService.add(actor);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Actor actor) {
        return actorService.update(actor);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return actorService.delete(id);
    }
}
