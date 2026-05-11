package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.config.AppConfig;
import com.qqsystem.serve.config.UploadConfig;
import com.qqsystem.serve.entity.Actor;
import com.qqsystem.serve.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actor")
public class ActorController {

    @Resource
    private ActorService actorService;

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private AppConfig appConfig;

    @GetMapping("/list")
    public ResponseResult<Map<String, Object>> list(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String style) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", actorService.pageList(page, size, style));
        res.put("total", actorService.countList(style));
        return ResponseResult.success(res);
    }
    @GetMapping("/admin/list")
    public ResponseResult<Map<String, Object>> adminList(@RequestParam int page,
                                                         @RequestParam int size,
                                                         @RequestParam(required = false) String style,
                                                         @RequestParam(required = false) String keyword,
                                                         @RequestParam(required = false) Integer status) {

        Map<String, Object> res = new HashMap<>();
        res.put("list", actorService.pageListWithRelation(page, size, style, keyword, status));
        res.put("total", actorService.countList(style, keyword, status));
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

    @DeleteMapping("/batch")
    public ResponseResult<?> batchDelete(@RequestBody List<Long> ids) {
        boolean success = actorService.batchDelete(ids);
        if (success) {
            return ResponseResult.success("批量删除成功");
        } else {
            return ResponseResult.badRequest("批量删除失败");
        }
    }
    
    @PostMapping("/upload")
    public ResponseResult<Map<String, String>> upload(@RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseResult.badRequest("文件为空");
        }

        String uploadDir = uploadConfig.getFullPath("actor");
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = java.util.UUID.randomUUID() + suffix;
        String filePath = uploadDir + File.separator + fileName;

        try {
            file.transferTo(new File(filePath));

            String url;
            if (uploadConfig.isUseFullUrl()) {
                url = appConfig.getDomain() + uploadConfig.getUrlPrefix() + "/actor/" + fileName;
            } else {
                url = uploadConfig.getUrlPrefix() + "/actor/" + fileName;
            }

            Map<String, String> result = new HashMap<>();
            result.put("url", url);
            return ResponseResult.success(result);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return ResponseResult.badRequest("上传失败");
        }
    }

}
