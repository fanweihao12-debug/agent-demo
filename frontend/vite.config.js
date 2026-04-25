import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173, // 前端端口
    proxy: {
      // 这里的 /api 是一个标识，告诉 Vite 哪些请求需要转发
      '/api': {
        target: 'http://localhost:8081', // 你的后端地址和端口
        changeOrigin: true,             // 允许跨域
        rewrite: (path) => path.replace(/^\/api/, '') // 关键：转发时去掉路径里的 /api
      }
    }
  }
})