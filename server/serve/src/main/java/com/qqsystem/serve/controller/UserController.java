package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.common.utils.JwtUtil;
import com.qqsystem.serve.dto.RegisterDTO;
import com.qqsystem.serve.dto.UserDTO;
import com.qqsystem.serve.dto.UserStatsDTO;
import com.qqsystem.serve.entity.User;
import com.qqsystem.serve.service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    //登录
    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody User user, HttpServletRequest request) {

        User result = userService.login(user.getUsername(), user.getPassword());

        if (result == null) {
            return ResponseResult.badRequest("登录失败");
        }
        //生成这个用户的token
        String token = JwtUtil.generateToken(
                result.getId(),
                result.getUsername(),
                result.getRole()
        );
        
        // 记录登录日志
        String ip = request.getRemoteAddr();
        String device = request.getHeader("User-Agent");
        userService.recordLoginLog(result.getId(), ip, device);
        
        System.out.println("token=" + token);
        return ResponseResult.success(token);
    }
    
    /**
     * 获取当前登录用户信息
     * 必须携带token
     * 后端通过token解析userId
     * 查询数据库返回用户信息
     */
    @GetMapping("/me")
    public ResponseResult<UserDTO> me(HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 查数据库
            UserDTO userDTO = userService.getUserInfo(userId);
            if (userDTO != null) {
                return ResponseResult.success(userDTO);
            } else {
                return ResponseResult.badRequest("用户不存在");
            }
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }

    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody RegisterDTO dto) {
        userService.register(dto);
        return ResponseResult.success("注册成功");
    }
    
    /**
     * 获取用户行为统计数据
     * 必须携带token
     * 后端通过token解析userId
     * 基于user_behavior表进行统计
     */
    @GetMapping("/stats")
    public ResponseResult<UserStatsDTO> stats(HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 获取统计数据
            UserStatsDTO stats = userService.getUserStats(userId);
            return ResponseResult.success(stats);
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 获取用户收藏列表
     * 必须携带token
     */
    @GetMapping("/favorite")
    public ResponseResult<?> favorite(HttpServletRequest request, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 获取收藏列表
            return ResponseResult.success(userService.getUserFavorites(userId, page, size));
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 获取用户浏览历史
     * 必须携带token
     */
    @GetMapping("/history")
    public ResponseResult<?> history(HttpServletRequest request, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 获取浏览历史
            return ResponseResult.success(userService.getUserHistory(userId, page, size));
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 获取用户点赞列表
     * 必须携带token
     */
    @GetMapping("/like")
    public ResponseResult<?> like(HttpServletRequest request, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 获取点赞列表
            return ResponseResult.success(userService.getUserLikes(userId, page, size));
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 根据id删除行为
     * 必须携带token
     */
    @DeleteMapping("/behavior/{id}")
    public ResponseResult<?> deleteBehavior(@PathVariable Long id, HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 删除行为
            int result = userService.deleteBehaviorById(id);
            if (result > 0) {
                return ResponseResult.success("删除成功");
            } else {
                return ResponseResult.badRequest("删除失败");
            }
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 取消行为（收藏/点赞）
     * 必须携带token
     */
    @PostMapping("/behavior/cancel")
    public ResponseResult<?> cancelBehavior(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 获取参数
            String targetType = (String) params.get("targetType");
            Long targetId = Long.valueOf(params.get("targetId").toString());
            String action = (String) params.get("action");

            // 5. 取消行为
            int result = userService.cancelBehavior(userId, targetType, targetId, action);
            if (result > 0) {
                return ResponseResult.success("取消成功");
            } else {
                return ResponseResult.badRequest("取消失败");
            }
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 清空行为
     * 必须携带token
     */
    @DeleteMapping("/behavior/clear")
    public ResponseResult<?> clearBehavior(@RequestParam String action, HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 清空行为
            int result = userService.clearBehavior(userId, action);
            if (result > 0) {
                return ResponseResult.success("清空成功");
            } else {
                return ResponseResult.badRequest("清空失败");
            }
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 获取用户详细信息
     * 必须携带token
     */
    @GetMapping("/profile")
    public ResponseResult<?> getUserProfile(HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 获取用户信息
            User user = userService.getUserProfile(userId);
            if (user != null) {
                // 构建返回数据
                Map<String, Object> data = new HashMap<>();
                data.put("id", user.getId());
                data.put("username", user.getUsername());
                data.put("nickname", user.getNickname() != null ? user.getNickname() : "");
                data.put("avatar", user.getAvatar() != null ? user.getAvatar() : "");
                data.put("sex", user.getSex() != null ? user.getSex() : 0);
                data.put("birthday", user.getBirthday() != null ? user.getBirthday() : "");
                data.put("address", user.getAddress() != null ? user.getAddress() : "");
                data.put("info", user.getInfo() != null ? user.getInfo() : "");
                data.put("createTime", user.getCreateTime() != null ? user.getCreateTime().toString() : "");
                return ResponseResult.success(data);
            } else {
                return ResponseResult.badRequest("用户不存在");
            }
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 更新用户资料
     * 必须携带token
     */
    @PostMapping("/updateProfile")
    public ResponseResult<?> updateUserProfile(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 获取参数
            String nickname = (String) params.get("nickname");
            String info = (String) params.get("info");
            String address = (String) params.get("address");
            String sex = (String) params.get("sex");
            String birthday = (String) params.get("birthday");

            // 5. 更新用户资料
            int result = userService.updateUserProfile(userId, nickname, info, address, sex, birthday);
            if (result > 0) {
                // 返回更新后的用户信息
                User user = userService.getUserProfile(userId);
                Map<String, Object> data = new HashMap<>();
                data.put("id", user.getId());
                data.put("username", user.getUsername());
                data.put("nickname", user.getNickname() != null ? user.getNickname() : "");
                data.put("avatar", user.getAvatar() != null ? user.getAvatar() : "");
                data.put("sex", user.getSex() != null ? user.getSex() : 0);
                data.put("birthday", user.getBirthday() != null ? user.getBirthday() : "");
                data.put("address", user.getAddress() != null ? user.getAddress() : "");
                data.put("info", user.getInfo() != null ? user.getInfo() : "");
                return ResponseResult.success(data);
            } else {
                return ResponseResult.badRequest("更新失败");
            }
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
    
    /**
     * 上传头像
     * 必须携带token
     */
    @PostMapping("/avatar/upload")
    public ResponseResult<?> uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 校验文件
            if (file == null || file.isEmpty()) {
                return ResponseResult.badRequest("请选择文件");
            }

            // 5. 校验文件类型
            String contentType = file.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                return ResponseResult.badRequest("只支持jpg和png格式");
            }

            // 6. 校验文件大小
            if (file.getSize() > 2 * 1024 * 1024) {
                return ResponseResult.badRequest("文件大小不能超过2MB");
            }

            // 7. 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            // 8. 保存文件
            String uploadPath = "D:/qin-opera-promotion-system/upload/avatar/";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File dest = new File(uploadPath + filename);
            file.transferTo(dest);

            // 9. 生成头像URL
            String avatarUrl = "http://localhost:8081/upload/avatar/" + filename;

            // 10. 更新用户头像
            int result = userService.updateUserAvatar(userId, avatarUrl);
            if (result > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("url", avatarUrl);
                return ResponseResult.success(response);
            } else {
                return ResponseResult.badRequest("上传失败");
            }
        } catch (Exception e) {
            System.out.println("上传头像失败: " + e.getMessage());
            return ResponseResult.badRequest("上传失败");
        }
    }
    
    /**
     * 修改密码
     * 必须携带token
     */
    @PostMapping("/changePassword")
    public ResponseResult<?> changePassword(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 获取参数
            String oldPassword = (String) params.get("oldPassword");
            String newPassword = (String) params.get("newPassword");

            // 5. 修改密码
            try {
                int result = userService.changePassword(userId, oldPassword, newPassword);
                if (result > 0) {
                    return ResponseResult.success("修改成功，请重新登录");
                } else {
                    return ResponseResult.badRequest("修改失败");
                }
            } catch (RuntimeException e) {
                return ResponseResult.badRequest(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }
}
