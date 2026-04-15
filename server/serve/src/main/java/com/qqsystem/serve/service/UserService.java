package com.qqsystem.serve.service;

import com.qqsystem.serve.dto.RegisterDTO;
import com.qqsystem.serve.dto.UserDTO;
import com.qqsystem.serve.dto.UserStatsDTO;
import com.qqsystem.serve.entity.Behavior;
import com.qqsystem.serve.entity.User;
import com.qqsystem.serve.mapper.ActorMapper;
import com.qqsystem.serve.mapper.BehaviorMapper;
import com.qqsystem.serve.mapper.ContentMapper;
import com.qqsystem.serve.mapper.DramaMapper;
import com.qqsystem.serve.mapper.loginlogmapper;
import com.qqsystem.serve.mapper.NewsMapper;
import com.qqsystem.serve.mapper.UserMapper;
import com.qqsystem.serve.vo.FavoriteVO;
import com.qqsystem.serve.vo.HistoryVO;
import com.qqsystem.serve.vo.LikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private BehaviorMapper behaviorMapper;
    
    @Autowired
    private DramaMapper dramaMapper;
    
    @Autowired
    private ActorMapper actorMapper;
    
    @Autowired
    private NewsMapper newsMapper;
    
    @Autowired
    private ContentMapper contentMapper;
    
    @Autowired
    private loginlogmapper loginLogMapper;

    public User login(String username, String password) {

        // 1. 根据用户名查用户
        User user = userMapper.selectByUsername(username);

        // 2. 用户不存在
        if (user == null) {
            return null;
        }

        // 3. 校验密码（当前：明文）
        if (!user.getPassword().equals(password)) {
            return null;
        }

        // 4. 登录成功
        return user;
    }
    //    查登录用户的明细
    public UserDTO getUserInfo(Long userId) {

        User user = userMapper.selectById(userId);

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setAvatar(user.getAvatar());
        dto.setAddress(user.getAddress());
        dto.setCreateTime(user.getCreateTime() != null ? user.getCreateTime().toString() : null);
        return dto;
    }
    //注册
    public void register(RegisterDTO dto) {

        // 1. 用户名是否存在
        User exist = userMapper.selectByUsername(dto.getUsername());
        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }



        // 3. 封装用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());
        user.setSex(dto.getSex());
        user.setBirthday(dto.getBirthday());
        user.setStatus(1);

        // 4. 入库
        userMapper.insert(user);
    }
    
    // 获取用户行为统计数据
    public UserStatsDTO getUserStats(Long userId) {
        UserStatsDTO stats = new UserStatsDTO();
        
        // 统计收藏数量
        Long favoriteCount = behaviorMapper.countUserBehavior(userId, null, "favorite");
        stats.setFavoriteCount(favoriteCount != null ? favoriteCount.intValue() : 0);
        
        // 统计浏览数量
        Long viewCount = behaviorMapper.countUserBehavior(userId, null, "view");
        stats.setViewCount(viewCount != null ? viewCount.intValue() : 0);
        
        // 统计点赞数量
        Long likeCount = behaviorMapper.countUserBehavior(userId, null, "like");
        stats.setLikeCount(likeCount != null ? likeCount.intValue() : 0);
        
        return stats;
    }
    
    // 获取用户收藏列表
    public Map<String, Object> getUserFavorites(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<Behavior> behaviors = behaviorMapper.selectUserBehaviorList(userId, null, "favorite", offset, size);
        Long total = behaviorMapper.countUserBehavior(userId, null, "favorite");
        
        List<FavoriteVO> favoriteList = new ArrayList<>();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (Behavior behavior : behaviors) {
            FavoriteVO vo = new FavoriteVO();
            vo.setId(behavior.getId());
            vo.setUserId(behavior.getUserId());
            vo.setTargetType(behavior.getTargetType());
            vo.setTargetId(behavior.getTargetId());
            vo.setAction(behavior.getAction());
            vo.setCreateTime(behavior.getCreateTime());
            vo.setTime(behavior.getCreateTime() != null ? formatter.format(behavior.getCreateTime()) : "");
            
            // 设置默认值
            vo.setTitle("未知内容");
            vo.setCoverUrl("/default-cover.jpg");
            vo.setSubtitle("暂无描述");
            
            // 根据targetType查询对应的数据
            if ("drama".equals(behavior.getTargetType())) {
                var drama = dramaMapper.selectById(behavior.getTargetId());
                if (drama != null) {
                    vo.setTitle(drama.getName() != null ? drama.getName() : "未知剧目");
                    vo.setCoverUrl(drama.getCover() != null ? drama.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(drama.getIntro() != null ? drama.getIntro() : "暂无描述");
                }
            } else if ("actor".equals(behavior.getTargetType())) {
                var actor = actorMapper.selectById(behavior.getTargetId());
                if (actor != null) {
                    vo.setTitle(actor.getName() != null ? actor.getName() : "未知演员");
                    vo.setCoverUrl(actor.getAvatar() != null ? actor.getAvatar() : "/default-cover.jpg");
                    vo.setSubtitle(actor.getIntro() != null ? actor.getIntro() : "暂无描述");
                }
            } else if ("news".equals(behavior.getTargetType())) {
                var news = newsMapper.selectById(behavior.getTargetId());
                if (news != null) {
                    vo.setTitle(news.getTitle() != null ? news.getTitle() : "未知资讯");
                    vo.setCoverUrl(news.getCover() != null ? news.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(news.getContent() != null ? news.getContent() : "暂无描述");
                }
            } else if ("culture".equals(behavior.getTargetType())) {
                var content = contentMapper.selectById(behavior.getTargetId());
                if (content != null) {
                    vo.setTitle(content.getTitle() != null ? content.getTitle() : "未知文化内容");
                    vo.setCoverUrl(content.getCover() != null ? content.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(content.getContent() != null ? content.getContent() : "暂无描述");
                }
            }
            
            favoriteList.add(vo);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", favoriteList);
        result.put("total", total);
        return result;
    }
    
    // 获取用户浏览历史
    public Map<String, Object> getUserHistory(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<Behavior> behaviors = behaviorMapper.selectUserBehaviorList(userId, null, "view", offset, size);
        Long total = behaviorMapper.countUserBehavior(userId, null, "view");
        
        List<HistoryVO> historyList = new ArrayList<>();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (Behavior behavior : behaviors) {
            HistoryVO vo = new HistoryVO();
            vo.setId(behavior.getId());
            vo.setUserId(behavior.getUserId());
            vo.setTargetType(behavior.getTargetType());
            vo.setTargetId(behavior.getTargetId());
            vo.setAction(behavior.getAction());
            vo.setCreateTime(behavior.getCreateTime());
            vo.setTime(behavior.getCreateTime() != null ? formatter.format(behavior.getCreateTime()) : "");
            
            // 设置默认值
            vo.setTitle("未知内容");
            vo.setCoverUrl("/default-cover.jpg");
            vo.setSubtitle("暂无描述");
            
            // 根据targetType查询对应的数据
            if ("drama".equals(behavior.getTargetType())) {
                var drama = dramaMapper.selectById(behavior.getTargetId());
                if (drama != null) {
                    vo.setTitle(drama.getName() != null ? drama.getName() : "未知剧目");
                    vo.setCoverUrl(drama.getCover() != null ? drama.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(drama.getIntro() != null ? drama.getIntro() : "暂无描述");
                }
            } else if ("actor".equals(behavior.getTargetType())) {
                var actor = actorMapper.selectById(behavior.getTargetId());
                if (actor != null) {
                    vo.setTitle(actor.getName() != null ? actor.getName() : "未知演员");
                    vo.setCoverUrl(actor.getAvatar() != null ? actor.getAvatar() : "/default-cover.jpg");
                    vo.setSubtitle(actor.getIntro() != null ? actor.getIntro() : "暂无描述");
                }
            } else if ("news".equals(behavior.getTargetType())) {
                var news = newsMapper.selectById(behavior.getTargetId());
                if (news != null) {
                    vo.setTitle(news.getTitle() != null ? news.getTitle() : "未知资讯");
                    vo.setCoverUrl(news.getCover() != null ? news.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(news.getContent() != null ? news.getContent() : "暂无描述");
                }
            } else if ("culture".equals(behavior.getTargetType())) {
                var content = contentMapper.selectById(behavior.getTargetId());
                if (content != null) {
                    vo.setTitle(content.getTitle() != null ? content.getTitle() : "未知文化内容");
                    vo.setCoverUrl(content.getCover() != null ? content.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(content.getContent() != null ? content.getContent() : "暂无描述");
                }
            }
            
            historyList.add(vo);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", historyList);
        result.put("total", total);
        return result;
    }
    
    // 获取用户点赞列表
    public Map<String, Object> getUserLikes(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<Behavior> behaviors = behaviorMapper.selectUserBehaviorList(userId, null, "like", offset, size);
        Long total = behaviorMapper.countUserBehavior(userId, null, "like");
        
        List<LikeVO> likeList = new ArrayList<>();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (Behavior behavior : behaviors) {
            LikeVO vo = new LikeVO();
            vo.setId(behavior.getId());
            vo.setUserId(behavior.getUserId());
            vo.setTargetType(behavior.getTargetType());
            vo.setTargetId(behavior.getTargetId());
            vo.setAction(behavior.getAction());
            vo.setCreateTime(behavior.getCreateTime());
            vo.setTime(behavior.getCreateTime() != null ? formatter.format(behavior.getCreateTime()) : "");
            
            // 设置默认值
            vo.setTitle("未知内容");
            vo.setCoverUrl("/default-cover.jpg");
            vo.setSubtitle("暂无描述");
            
            // 根据targetType查询对应的数据
            if ("drama".equals(behavior.getTargetType())) {
                var drama = dramaMapper.selectById(behavior.getTargetId());
                if (drama != null) {
                    vo.setTitle(drama.getName() != null ? drama.getName() : "未知剧目");
                    vo.setCoverUrl(drama.getCover() != null ? drama.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(drama.getIntro() != null ? drama.getIntro() : "暂无描述");
                }
            } else if ("actor".equals(behavior.getTargetType())) {
                var actor = actorMapper.selectById(behavior.getTargetId());
                if (actor != null) {
                    vo.setTitle(actor.getName() != null ? actor.getName() : "未知演员");
                    vo.setCoverUrl(actor.getAvatar() != null ? actor.getAvatar() : "/default-cover.jpg");
                    vo.setSubtitle(actor.getIntro() != null ? actor.getIntro() : "暂无描述");
                }
            } else if ("news".equals(behavior.getTargetType())) {
                var news = newsMapper.selectById(behavior.getTargetId());
                if (news != null) {
                    vo.setTitle(news.getTitle() != null ? news.getTitle() : "未知资讯");
                    vo.setCoverUrl(news.getCover() != null ? news.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(news.getContent() != null ? news.getContent() : "暂无描述");
                }
            } else if ("culture".equals(behavior.getTargetType())) {
                var content = contentMapper.selectById(behavior.getTargetId());
                if (content != null) {
                    vo.setTitle(content.getTitle() != null ? content.getTitle() : "未知文化内容");
                    vo.setCoverUrl(content.getCover() != null ? content.getCover() : "/default-cover.jpg");
                    vo.setSubtitle(content.getContent() != null ? content.getContent() : "暂无描述");
                }
            }
            
            likeList.add(vo);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", likeList);
        result.put("total", total);
        return result;
    }
    
    // 根据id删除行为
    public int deleteBehaviorById(Long id) {
        return behaviorMapper.updateBehaviorStatusById(id, 0);
    }
    
    // 取消行为（收藏/点赞）
    public int cancelBehavior(Long userId, String targetType, Long targetId, String action) {
        return behaviorMapper.updateBehaviorStatus(userId, targetType, targetId, action, 0);
    }
    
    // 清空行为
    public int clearBehavior(Long userId, String action) {
        return behaviorMapper.batchUpdateBehaviorStatus(userId, action, 0);
    }
    
    // 获取用户详细信息
    public User getUserProfile(Long userId) {
        return userMapper.selectById(userId);
    }
    
    // 更新用户资料
    public int updateUserProfile(Long userId, String nickname, String info, String address, String sex, String birthday) {
        return userMapper.updateUserProfile(userId, nickname, info, address, sex, birthday);
    }
    
    // 更新用户头像
    public int updateUserAvatar(Long userId, String avatar) {
        return userMapper.updateUserAvatar(userId, avatar);
    }
    
    // 修改密码
    public int changePassword(Long userId, String oldPassword, String newPassword) {
        // 先查询用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 校验旧密码
        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("旧密码错误");
        }
        
        // 更新密码
        return userMapper.updatePassword(userId, newPassword);
    }
    
    // 记录登录日志
    public void recordLoginLog(Long userId, String ip, String device) {
        com.qqsystem.serve.entity.loginLog loginLog = new com.qqsystem.serve.entity.loginLog();
        loginLog.setUserId(userId);
        loginLog.setIp(ip);
        loginLog.setDevice(device);
        loginLog.setLoginTime(new java.util.Date());
        loginLogMapper.insertLoginLog(loginLog);
    }
}