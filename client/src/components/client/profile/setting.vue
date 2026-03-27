<script setup lang="ts">
</script>
<template>
      <div class="account-settings">
    <!-- 头部 -->
    <div class="settings-header">
      <h3 class="header-title">
        <span class="title-icon">⚙️</span>
        账户设置
      </h3>
      <button class="save-all-btn">保存全部设置</button>
    </div>

    <!-- 基本信息 -->
    <div class="settings-section">
      <div class="section-title">
        <span class="section-icon">📝</span>
        基本信息
      </div>
      <div class="section-content">
        <!-- 头像 -->
        <div class="setting-item avatar-item">
          <div class="setting-label">
            <span class="label-text">头像</span>
          </div>
          <div class="setting-value">
            <div class="avatar-wrapper">
              <img  class="avatar-img" alt="头像">
              <button class="upload-btn" >更换头像</button>
              <input 
                type="file" 
                ref="avatarInput" 
                class="hidden-input" 
                accept="image/*"
                
              >
            </div>
          </div>
        </div>

        <!-- 昵称 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">昵称</span>
          </div>
          <div class="setting-value">
            <span class="value-text"></span>
            <button class="edit-btn">修改</button>
          </div>
        </div>

        <!-- 简介 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">简介</span>
          </div>
          <div class="setting-value">
            <span class="value-text bio-text">'暂无简介'</span>
            <button class="edit-btn" >修改</button>
          </div>
        </div>

        <!-- 地区 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">地区</span>
          </div>
          <div class="setting-value">
            <span class="value-text"></span>
            <button class="edit-btn">修改</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 安全设置 -->
    <div class="settings-section">
      <div class="section-title">
        <span class="section-icon">🔒</span>
        安全设置
      </div>
      <div class="section-content">
        <!-- 手机号 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">手机号</span>
          </div>
          <div class="setting-value">
            <span class="value-text">159</span>
            <button class="edit-btn" >更换</button>
          </div>
        </div>

        <!-- 邮箱 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">邮箱</span>
          </div>
          <div class="setting-value">
            <span class="value-text"></span>
            <button class="edit-btn">更换</button>
          </div>
        </div>

        <!-- 密码 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">密码</span>
          </div>
          <div class="setting-value">
            <span class="value-text password-placeholder">········</span>
            <button class="edit-btn" >修改密码</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 隐私设置 -->
    <div class="settings-section">
      <div class="section-title">
        <span class="section-icon">🛡️</span>
        隐私设置
      </div>
      <div class="section-content">
        <div class="setting-item switch-item">
          <div class="setting-label">
            <span class="label-text">允许他人查看我的收藏</span>
          </div>
          <div class="setting-value">
            <label class="switch">
              <input type="checkbox" >
              <span class="slider"></span>
            </label>
          </div>
        </div>

        <div class="setting-item switch-item">
          <div class="setting-label">
            <span class="label-text">允许他人查看我的浏览历史</span>
          </div>
          <div class="setting-value">
            <label class="switch">
              <input type="checkbox" >
              <span class="slider"></span>
            </label>
          </div>
        </div>

        <div class="setting-item switch-item">
          <div class="setting-label">
            <span class="label-text">允许通过手机号搜索到我</span>
          </div>
          <div class="setting-value">
            <label class="switch">
              <input type="checkbox" >
              <span class="slider"></span>
            </label>
          </div>
        </div>
      </div>
    </div>

    <!-- 通知设置 -->
    <div class="settings-section">
      <div class="section-title">
        <span class="section-icon">🔔</span>
        通知设置
      </div>
      <div class="section-content">
        <div class="setting-item switch-item">
          <div class="setting-label">
            <span class="label-text">系统通知</span>
          </div>
          <div class="setting-value">
            <label class="switch">
              <input type="checkbox" >
              <span class="slider"></span>
            </label>
          </div>
        </div>

        <div class="setting-item switch-item">
          <div class="setting-label">
            <span class="label-text">互动通知</span>
          </div>
          <div class="setting-value">
            <label class="switch">
              <input type="checkbox" >
              <span class="slider"></span>
            </label>
          </div>
        </div>

        <div class="setting-item switch-item">
          <div class="setting-label">
            <span class="label-text">活动通知</span>
          </div>
          <div class="setting-value">
            <label class="switch">
              <input type="checkbox" >
              <span class="slider"></span>
            </label>
          </div>
        </div>

        <div class="setting-item switch-item">
          <div class="setting-label">
            <span class="label-text">邮件通知</span>
          </div>
          <div class="setting-value">
            <label class="switch">
              <input type="checkbox" >
              <span class="slider"></span>
            </label>
          </div>
        </div>
      </div>
    </div>

    <!-- 编辑弹窗 -->
    
  </div>
    </template>
    <style scoped>
</style>