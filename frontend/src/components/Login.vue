<template>
  <div class="page-wrapper">
    <div class="background-container">
      <div class="glow-1"></div>
      <div class="glow-2"></div>
      <div class="bg-grid"></div>
    </div>

    <div class="main-card">
      <div class="visual-section">
        <div class="visual-content">
          <svg viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg" class="hero-svg">
            <path fill="#ffffff" d="M44.7,-76.4C58.1,-69.2,69.2,-58.1,76.4,-44.7C83.7,-31.3,87,-15.7,85.6,-0.8C84.3,14.1,78.2,28.2,70.2,40.8C62.1,53.3,52,64.4,39.5,71.8C27.1,79.2,12.3,82.9,-1.9,86.2C-16.1,89.5,-32.2,92.3,-45.8,86.8C-59.5,81.2,-70.7,67.3,-78.6,52.3C-86.4,37.3,-90.9,21.1,-90.4,5.2C-89.8,-10.8,-84.2,-26.4,-75.4,-40.1C-66.6,-53.8,-54.6,-65.7,-40.8,-72.7C-27,-79.7,-11.5,-81.8,2.2,-85.5C15.9,-89.3,31.3,-83.6,44.7,-76.4Z" transform="translate(100 100)" />
          </svg>
          <h1 class="visual-title">极简 · 高效</h1>
          <p class="visual-desc">让每一行代码都富有生命力</p>
        </div>
      </div>

      <div class="form-section">
        <div class="form-container">
          <div class="header">
            <h2 class="form-title">{{ isRegister ? '创建新账号' : '欢迎回来' }}</h2>
            <p class="form-subtitle">{{ isRegister ? '只需几秒，开启您的旅程' : '请输入您的详细信息以访问系统' }}</p>
          </div>

          <el-form :model="isRegister ? registerForm : loginForm" label-position="top" class="custom-form">
            <el-form-item label="用户名">
              <el-input v-model="(isRegister ? registerForm : loginForm).name" placeholder="请输入用户名" prefix-icon="User" />
            </el-form-item>

            <template v-if="isRegister">
              <el-form-item label="手机号">
                <el-input v-model="registerForm.phoneNumber" placeholder="请输入手机号" prefix-icon="Iphone" />
              </el-form-item>
              <el-form-item label="年龄 (选填)">
                <el-input v-model.number="registerForm.age" type="number" placeholder="请输入年龄" prefix-icon="TrendCharts" />
              </el-form-item>
            </template>

            <el-form-item label="密码">
              <el-input v-model="(isRegister ? registerForm : loginForm).passWord" type="password" show-password placeholder="请输入密码" prefix-icon="Lock" />
            </el-form-item>

            <el-button
                :type="isRegister ? 'success' : 'primary'"
                class="action-btn"
                @click="isRegister ? handleRegister() : handleLogin()"
                :loading="loading"
            >
              {{ isRegister ? '确认注册' : '登录' }}
            </el-button>
          </el-form>

          <div class="switch-mode">
            {{ isRegister ? '已有账号？' : '还没有账号？' }}
            <span @click="toggleMode">{{ isRegister ? '返回登录' : '立即注册' }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';




const api = axios.create({ baseURL: '/api/user' });
const isRegister = ref(false);
const loading = ref(false);

const loginForm = reactive({ name: '', passWord: '' });
const registerForm = reactive({ name: '', passWord: '', phoneNumber: '', age: null });

const toggleMode = () => {
  isRegister.value = !isRegister.value;
};

const handleLogin = async () => {
  if (!loginForm.name || !loginForm.passWord) return ElMessage.warning('请填写完整信息');
  loading.value = true;
  try {
    const res = await api.post('/login', loginForm);
    const { code, msg, data } = res.data;
    if (code === 1) ElMessage.success('登录成功！');
    else ElMessage.error(msg || '登录失败');
  } catch (err) { ElMessage.error('网络错误'); } finally { loading.value = false; }
};

const handleRegister = async () => {
  if (!registerForm.name || !registerForm.passWord) return ElMessage.warning('用户名和密码不能为空');
  loading.value = true;
  try {
    const res = await api.post('/login', loginForm);
    const { code, msg, data } = res.data;
    if (code=== 1) { ElMessage.success('注册成功'); isRegister.value = false; }
    else ElMessage.error(msg || '注册失败');
  } catch (err) { ElMessage.error('接口异常'); } finally { loading.value = false; }
};
</script>

<style scoped>
/* 核心布局 */
.page-wrapper {
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  position: relative;
  background-color: #f0f4f8; /* 基础底色 */
}

/* 背景层样式 */
.background-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.glow-1 {
  position: absolute;
  top: -10%;
  left: -10%;
  width: 50%;
  height: 50%;
  background: radial-gradient(circle, rgba(79, 70, 229, 0.2) 0%, transparent 70%);
  filter: blur(80px);
  animation: move 20s infinite alternate;
}

.glow-2 {
  position: absolute;
  bottom: -10%;
  right: -10%;
  width: 60%;
  height: 60%;
  background: radial-gradient(circle, rgba(99, 102, 241, 0.15) 0%, transparent 70%);
  filter: blur(100px);
  animation: move 15s infinite alternate-reverse;
}

.bg-grid {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: radial-gradient(#d1d5db 1px, transparent 1px);
  background-size: 32px 32px; /* 网格间距 */
  opacity: 0.3;
  mask-image: radial-gradient(circle, black, transparent 80%);
}

@keyframes move {
  from { transform: translate(0, 0); }
  to { transform: translate(10%, 10%); }
}

/* 卡片主样式 */
.main-card {
  width: 950px;
  height: 620px;
  display: flex;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 28px;
  box-shadow: 0 40px 100px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  z-index: 10;
  border: 1px solid rgba(255, 255, 255, 0.5);
}

/* 左侧插画区 */
.visual-section {
  flex: 1.1;
  background: linear-gradient(135deg, #4f46e5 0%, #6366f1 100%);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  padding: 40px;
  position: relative;
}

.hero-svg {
  width: 260px;
  margin-bottom: 24px;
  filter: drop-shadow(0 10px 20px rgba(0,0,0,0.2));
  animation: float 6s ease-in-out infinite;
}

.visual-title {
  font-size: 26px;
  font-weight: 700;
  letter-spacing: 2px;
  margin-bottom: 12px;
}

.visual-desc {
  font-size: 14px;
  opacity: 0.9;
  font-weight: 300;
}

/* 右侧表单区 */
.form-section {
  width: 480px;
  padding: 60px;
  background: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-title {
  font-size: 28px;
  font-weight: 800;
  color: #1e293b;
  margin-bottom: 8px;
}

.form-subtitle {
  font-size: 15px;
  color: #94a3b8;
  margin-bottom: 35px;
}

.action-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 14px;
  margin-top: 25px;
  box-shadow: 0 10px 15px -3px rgba(79, 70, 229, 0.3);
}

.switch-mode {
  margin-top: 35px;
  text-align: center;
  color: #64748b;
  font-size: 14px;
}

.switch-mode span {
  color: #4f46e5;
  cursor: pointer;
  font-weight: 700;
  text-decoration: underline;
  text-underline-offset: 4px;
}

/* 输入框微调 */
:deep(.el-input__wrapper) {
  background-color: #f8fafc !important;
  border: 1px solid #e2e8f0;
  box-shadow: none !important;
  border-radius: 12px;
  transition: all 0.3s;
}

:deep(.el-input__wrapper.is-focus) {
  background-color: white !important;
  border-color: #4f46e5;
  box-shadow: 0 0 0 4px rgba(79, 70, 229, 0.1) !important;
}

@keyframes float {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-15px) rotate(2deg); }
}

@media (max-width: 950px) {
  .main-card { width: 95%; height: auto; flex-direction: column; }
  .visual-section { padding: 40px 20px; }
  .hero-svg { width: 150px; }
  .form-section { width: 100%; padding: 40px 30px; }
}
</style>