<template>
  <el-container class="layout-container">
    <el-aside :width="appStore.collapsed ? '64px' : '220px'" class="layout-aside">
      <div class="layout-logo">
        <span v-if="!appStore.collapsed">医药SaaS</span>
        <span v-else>ERP</span>
      </div>
      <el-scrollbar class="layout-menu-scroll">
        <el-menu
          :default-active="activeMenu"
          :collapse="appStore.collapsed"
          :collapse-transition="false"
          :unique-opened="true"
          background-color="#001529"
          text-color="#b7bdc3"
          active-text-color="#ffffff"
          router
        >
          <sidebar-item
            v-for="item in menuStore.menus"
            :key="item.menuCode"
            :item="item"
          />
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container class="layout-body">
      <el-header class="layout-header" height="50px">
        <navbar />
      </el-header>
      <tabs-bar />
      <el-main class="layout-main">
        <router-view v-slot="{ Component, route }">
          <keep-alive :include="tabsStore.cachedViews">
            <component :is="Component" :key="route.fullPath" />
          </keep-alive>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { useMenuStore } from '@/stores/menu'
import { useTabsStore } from '@/stores/tabs'
import SidebarItem from './components/SidebarItem.vue'
import Navbar from './components/Navbar.vue'
import TabsBar from './components/TabsBar.vue'

const route = useRoute()
const appStore = useAppStore()
const menuStore = useMenuStore()
const tabsStore = useTabsStore()

const activeMenu = computed(() => route.path)

onMounted(() => {
  menuStore.loadMenus()
})

// 路由变化时追加页签
watch(
  () => route.path,
  () => tabsStore.addTab(route),
  { immediate: true }
)
</script>

<style scoped>
.layout-container {
  height: 100vh;
  width: 100%;
}

.layout-aside {
  background-color: #001529;
  transition: width 0.2s;
  overflow: hidden;
}

.layout-logo {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  letter-spacing: 1px;
  white-space: nowrap;
}

.layout-menu-scroll {
  height: calc(100vh - 50px);
}

.layout-body {
  display: flex;
  flex-direction: column;
}

.layout-header {
  background-color: #fff;
  border-bottom: 1px solid #e8e8e8;
  padding: 0 16px;
  display: flex;
  align-items: center;
}

.layout-main {
  background-color: #f0f2f5;
  padding: 16px;
  overflow: auto;
}

/* 折叠时隐藏滚动条宽度变化造成的抖动 */
.layout-aside :deep(.el-menu) {
  border-right: none;
}
</style>
