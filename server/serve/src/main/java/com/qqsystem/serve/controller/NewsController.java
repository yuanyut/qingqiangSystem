package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.config.AppConfig;
import com.qqsystem.serve.config.UploadConfig;
import com.qqsystem.serve.entity.News;
import com.qqsystem.serve.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private AppConfig appConfig;

    @GetMapping("/list")
    public ResponseResult<Map<String, Object>> list(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) Integer category, @RequestParam(required = false) String keyword) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", newsService.pageList(page, size, category, keyword));
        res.put("total", newsService.countList(category, keyword));
        return ResponseResult.success(res);
    }

    @GetMapping("/admin/list")
    public ResponseResult<Map<String, Object>> adminList(@RequestParam int page,
                                                         @RequestParam int size,
                                                         @RequestParam(required = false) String category,
                                                         @RequestParam(required = false) String keyword,
                                                         @RequestParam(required = false) Integer status) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", newsService.pageListWithCondition(page, size, category, keyword, status));
        res.put("total", newsService.countListWithCondition(category, keyword, status));
        return ResponseResult.success(res);
    }

    @GetMapping("/detail/{id}")
    public ResponseResult<News> detail(@PathVariable Long id) {
        News news = newsService.getById(id);
        if (news != null) {
            return ResponseResult.success(news);
        } else {
            return ResponseResult.badRequest("新闻不存在");
        }
    }

    @PostMapping("/add")
    public ResponseResult<?> add(@RequestBody News news) {
        boolean success = newsService.add(news);
        if (success) {
            return ResponseResult.success("添加成功");
        } else {
            return ResponseResult.badRequest("添加失败");
        }
    }

    @PutMapping("/update")
    public ResponseResult<?> update(@RequestBody News news) {
        boolean success = newsService.update(news);
        if (success) {
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.badRequest("更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable Long id) {
        boolean success = newsService.delete(id);
        if (success) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.badRequest("删除失败");
        }
    }

    @DeleteMapping("/batch")
    public ResponseResult<?> batchDelete(@RequestBody List<Long> ids) {
        boolean success = newsService.batchDelete(ids);
        if (success) {
            return ResponseResult.success("批量删除成功");
        } else {
            return ResponseResult.badRequest("批量删除失败");
        }
    }

    // 获取新闻分类统计
    @GetMapping("/categories")
    public ResponseResult<List<Map<String, Object>>> getCategories() {
        List<Map<String, Object>> categories = newsService.getNewsCategories();
        return ResponseResult.success(categories);
    }

    // 获取头条资讯
    @GetMapping("/top")
    public ResponseResult<News> getTop() {
        News topNews = newsService.getTopNews();
        if (topNews != null) {
            return ResponseResult.success(topNews);
        } else {
            return ResponseResult.badRequest("暂无头条资讯");
        }
    }

    // 获取热门排行
    @GetMapping("/hot")
    public ResponseResult<List<News>> getHot(@RequestParam int size) {
        List<News> hotList = newsService.getHotNews(size);
        return ResponseResult.success(hotList);
    }

    // 获取推荐阅读
    @GetMapping("/recommend")
    public ResponseResult<List<News>> getRecommend(@RequestParam int size, @RequestParam(required = false) String category) {
        List<News> recommendList = newsService.getRecommendNews(size, category);
        return ResponseResult.success(recommendList);
    }

    // 获取媒体聚焦（视频类）
    @GetMapping("/media")
    public ResponseResult<List<News>> getMedia(@RequestParam int size) {
        List<News> mediaList = newsService.getMediaNews(size);
        return ResponseResult.success(mediaList);
    }

    // 封面上传接口
    @PostMapping("/upload")
    public ResponseResult<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseResult.badRequest("文件为空");
        }

        String uploadDir = uploadConfig.getFullPath("news");
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        String filePath = uploadDir + File.separator + fileName;

        try {
            file.transferTo(new File(filePath));

            String url;
            if (uploadConfig.isUseFullUrl()) {
                url = appConfig.getDomain() + uploadConfig.getUrlPrefix() + "/news/" + fileName;
            } else {
                url = uploadConfig.getUrlPrefix() + "/news/" + fileName;
            }

            Map<String, String> result = new HashMap<>();
            result.put("url", url);
            return ResponseResult.success(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.badRequest("上传失败");
        }
    }
}
