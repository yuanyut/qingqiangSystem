import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  // ----- 加下面这一行，这是解决问题的关键 -----
  base: '/manage',
  // ---------------------------------------
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    proxy: {
      '/actor': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      '/drama': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      '/user': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      '/content': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      '/upload': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
    },
  },
})
