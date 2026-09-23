import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    host: '0.0.0.0',
    port: 5173,
    // 开发期将后端接口前缀代理到网关（adrug-gateway，端口 8081）
    proxy: {
      '/app': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
