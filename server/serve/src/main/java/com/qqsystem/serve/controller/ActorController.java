package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
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
    public ResponseResult<Map<String, Object>> list(@RequestParam int page, @RequestParam int size) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", actorService.pageList(page, size));
        res.put("total", actorService.countList());
        return ResponseResult.success(res);
    }

    @GetMapping("/detail/{id}")
    public ResponseResult<Actor> detail(@PathVariable Long id) {
        Actor actor = actorService.getById(id);
        if (actor != null) {
            return ResponseResult.success(actor);
        } else {
            return ResponseResult.badRequest("演员不存在");
        }
    }

    @GetMapping("/detail/full/{id}")
    public ResponseResult<Actor> detailFull(@PathVariable Long id) {
        Actor actor = actorService.getDetailWithRelation(id);
        if (actor != null) {
            return ResponseResult.success(actor);
        } else {
            return ResponseResult.badRequest("演员不存在");
        }
    }

    @PostMapping("/add")
    public ResponseResult<?> add(@RequestBody Actor actor) {
        boolean success = actorService.add(actor);
        if (success) {
            return ResponseResult.success("添加成功");
        } else {
            return ResponseResult.badRequest("添加失败");
        }
    }

    @PutMapping("/update")
    public ResponseResult<?> update(@RequestBody Actor actor) {
        boolean success = actorService.update(actor);
        if (success) {
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.badRequest("更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable Long id) {
        boolean success = actorService.delete(id);
        if (success) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.badRequest("删除失败");
        }
    }
}
