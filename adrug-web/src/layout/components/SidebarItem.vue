<template>
  <template v-if="show">
    <el-sub-menu v-if="isDirectory" :index="item.path || String(item.id)">
      <template #title>
        <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
        <span>{{ item.menuName }}</span>
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.menuCode"
        :item="child"
      />
    </el-sub-menu>

    <el-menu-item v-else :index="item.path">
      <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
      <template #title>{{ item.menuName }}</template>
    </el-menu-item>
  </template>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  item: { type: Object, required: true }
})

// 按钮(3) 与 隐藏(visible=0)/停用(status=0) 项不进入侧边栏
const show = computed(
  () => props.item.menuType !== 3 && props.item.visible !== 0 && props.item.status !== 0
)
const isDirectory = computed(() => props.item.menuType === 1)
</script>
