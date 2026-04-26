<template>
  <div id="app">
    <!-- 根据登录状态显示不同页面 -->
    <Login v-if="!isLoggedIn" @login-success="handleLoginSuccess" />
    <MainIndex v-else />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Login from './components/Login.vue'
import MainIndex from './components/MainIndex.vue'


const isLoggedIn = ref(false)

// 处理登录成功
const handleLoginSuccess = () => {
  isLoggedIn.value = true
}

</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  background-color: #f7f7f7;
}
</style>