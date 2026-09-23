<template>
  <div class="navbar">
    <div class="navbar-left">
      <el-icon class="navbar-collapse" @click="appStore.toggleCollapsed()">
        <Expand v-if="appStore.collapsed" />
        <Fold v-else />
      </el-icon>
      <span class="navbar-title">{{ route.meta?.title || '' }}</span>
    </div>

    <div class="navbar-right">
      <span class="navbar-user">{{ userStore.userInfo?.username || '' }}</span>
      <el-dropdown @command="onCommand">
        <el-avatar :size="28" class="navbar-avatar">{{ avatarText }}</el-avatar>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()
const userStore = useUserStore()

const avatarText = computed(() => (userStore.userInfo?.username || '?').slice(0, 1))

function onCommand(cmd) {
  if (cmd === 'logout') {
    userStore.logout()
    router.push('/login')
  }
}
</script>

<style scoped>
.navbar {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.navbar-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.navbar-collapse {
  font-size: 18px;
  cursor: pointer;
  color: #333;
}

.navbar-title {
  font-size: 15px;
  font-weight: 500;
  color: #333;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

.navbar-avatar {
  background-color: #409eff;
  font-size: 13px;
  cursor: pointer;
}

.navbar-user {
  font-size: 13px;
  color: #333;
}
</style>
