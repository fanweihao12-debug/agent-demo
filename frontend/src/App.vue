<template>
  <div id="app">
    <!-- 根据登录状态显示不同页面 -->
    <Login v-if="!isLoggedIn"/>
    <MainIndex v-else />
  </div>
</template>

<script setup>
import Login from './components/Login.vue'
import MainIndex from './components/MainIndex.vue'
import {computed, ref} from "vue";
import {useUserStore} from "./api/User.js";

const userStore = useUserStore();

const isLoggedIn = computed(() => userStore.isLoggedIn)

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
