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
              <img class="avatar-img" alt="头像">
              <button class="upload-btn">更换头像</button>
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
            <span class="value-text bio-text">暂无简介</span>
            <button class="edit-btn">修改</button>
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
            <span class="value-text">159****1234</span>
            <button class="edit-btn">更换</button>
          </div>
        </div>

        <!-- 邮箱 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">邮箱</span>
          </div>
          <div class="setting-value">
            <span class="value-text">user@example.com</span>
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
            <button class="edit-btn">修改密码</button>
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
              <input type="checkbox">
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
              <input type="checkbox">
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
              <input type="checkbox">
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
              <input type="checkbox">
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
              <input type="checkbox">
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
              <input type="checkbox">
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
              <input type="checkbox">
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
.account-settings {
  max-width: 900px;
  margin: 0 auto;
  padding: 32px 28px;
  background: linear-gradient(135deg, #fefaf5 0%, #fff9f2 100%);
  min-height: 100vh;
  border-radius: 0;
}

/* 头部样式 */
.settings-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 20px;
  border-bottom: 2px solid #f0e2d0;
}

.header-title {
  font-size: 28px;
  font-weight: 600;
  margin: 0;
  background: linear-gradient(135deg, #3a2c21, #8b5a3a);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  display: flex;
  align-items: center;
  gap: 12px;
}

.title-icon {
  font-size: 28px;
}

.save-all-btn {
  padding: 10px 28px;
  background: linear-gradient(135deg, #b87c4e, #9b623c);
  border: none;
  border-radius: 40px;
  color: white;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(184, 124, 78, 0.2);
}

.save-all-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(184, 124, 78, 0.3);
}

/* 设置区块 */
.settings-section {
  background: white;
  border-radius: 24px;
  margin-bottom: 24px;
  overflow: hidden;
  border: 1px solid #f0e6dc;
  transition: all 0.3s ease;
}

.settings-section:hover {
  box-shadow: 0 8px 24px -12px rgba(98, 67, 41, 0.12);
  border-color: #e6d5c4;
}

.section-title {
  padding: 20px 24px;
  background: #fefaf5;
  border-bottom: 1px solid #f0e2d0;
  font-size: 18px;
  font-weight: 600;
  color: #3a2c21;
  display: flex;
  align-items: center;
  gap: 10px;
}

.section-icon {
  font-size: 20px;
}

.section-content {
  padding: 8px 0;
}

/* 设置项 */
.setting-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 24px;
  transition: background 0.2s ease;
  border-bottom: 1px solid #f8f2ea;
}

.setting-item:last-child {
  border-bottom: none;
}

.setting-item:hover {
  background: #fefbf7;
}

.setting-label {
  flex-shrink: 0;
  width: 140px;
}

.label-text {
  font-size: 15px;
  font-weight: 500;
  color: #5e4c38;
}

.setting-value {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 16px;
}

.value-text {
  font-size: 15px;
  color: #9b8570;
  transition: color 0.2s;
}

.bio-text {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.password-placeholder {
  letter-spacing: 2px;
  font-size: 16px;
  font-weight: 500;
  color: #b5a084;
}

/* 头像样式 */
.avatar-item .setting-value {
  justify-content: flex-start;
}

.avatar-wrapper {
  display: flex;
  align-items: center;
  gap: 20px;
}

.avatar-img {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  object-fit: cover;
  background: #f5ede2;
  border: 3px solid #f0e2d0;
  transition: all 0.3s ease;
}

.avatar-img:hover {
  border-color: #b87c4e;
  transform: scale(1.02);
}

.upload-btn {
  padding: 8px 20px;
  background: transparent;
  border: 1px solid #e6d5c4;
  border-radius: 40px;
  color: #b87c4e;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.upload-btn:hover {
  background: #b87c4e;
  border-color: #b87c4e;
  color: white;
  transform: scale(1.02);
}

.hidden-input {
  display: none;
}

/* 编辑按钮 */
.edit-btn {
  padding: 6px 18px;
  background: transparent;
  border: 1px solid #e6d5c4;
  border-radius: 40px;
  color: #9b8570;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.edit-btn:hover {
  background: #f5ede2;
  border-color: #b87c4e;
  color: #b87c4e;
  transform: scale(1.02);
}

/* 开关样式 */
.switch-item {
  justify-content: space-between;
}

.switch-item .setting-label {
  width: auto;
  flex: 1;
}

.switch-item .setting-value {
  flex-shrink: 0;
  width: auto;
}

.switch {
  position: relative;
  display: inline-block;
  width: 52px;
  height: 28px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #e6d5c4;
  transition: 0.3s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 24px;
  width: 24px;
  left: 2px;
  bottom: 2px;
  background-color: white;
  transition: 0.3s;
  border-radius: 50%;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

input:checked + .slider {
  background-color: #b87c4e;
}

input:checked + .slider:before {
  transform: translateX(24px);
}

input:focus + .slider {
  box-shadow: 0 0 1px #b87c4e;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .account-settings {
    padding: 20px 16px;
  }

  .settings-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .header-title {
    font-size: 24px;
  }

  .title-icon {
    font-size: 24px;
  }

  .save-all-btn {
    width: 100%;
    text-align: center;
  }

  .setting-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
    padding: 16px 20px;
  }

  .setting-label {
    width: 100%;
  }

  .setting-value {
    width: 100%;
    justify-content: space-between;
  }

  .avatar-wrapper {
    width: 100%;
    justify-content: space-between;
  }

  .bio-text {
    max-width: 200px;
  }

  .section-title {
    padding: 16px 20px;
    font-size: 16px;
  }
}

@media (max-width: 480px) {
  .avatar-wrapper {
    flex-wrap: wrap;
    justify-content: center;
    gap: 12px;
  }

  .avatar-img {
    width: 56px;
    height: 56px;
  }

  .upload-btn {
    padding: 6px 16px;
    font-size: 12px;
  }

  .edit-btn {
    padding: 4px 14px;
    font-size: 12px;
  }

  .value-text {
    font-size: 14px;
  }

  .bio-text {
    max-width: 150px;
  }
}
</style>