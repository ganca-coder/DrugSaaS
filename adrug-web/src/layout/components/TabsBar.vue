<template>
  <div class="tabs-bar">
    <el-tabs
      v-model="activeTab"
      type="card"
      class="tabs"
      @tab-click="onTabClick"
      @tab-remove="onTabRemove"
    >
      <el-tab-pane
        v-for="tab in tabsStore.tabs"
        :key="tab.path"
        :label="tab.title"
        :name="tab.path"
        :closable="tab.closable"
      />
    </el-tabs>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useTabsStore } from '@/stores/tabs'

const route = useRoute()
const router = useRouter()
const tabsStore = useTabsStore()

const activeTab = computed(() => route.path)

function onTabClick(pane) {
  const path = pane.paneName
  if (path && path !== route.path) {
    router.push(path)
  }
}

function onTabRemove(path) {
  const next = tabsStore.removeTab(path)
  // 关闭的是当前页时，跳转到相邻页签
  if (next && path === route.path) {
    router.push(next)
  }
}
</script>

<style scoped>
.tabs-bar {
  background-color: #fff;
  border-bottom: 1px solid #e8e8e8;
  padding: 6px 16px 0;
}

.tabs :deep(.el-tabs__header) {
  margin: 0;
}

.tabs :deep(.el-tabs__nav) {
  border: none;
}
</style>
