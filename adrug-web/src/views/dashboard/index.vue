<template>
  <div class="dashboard">
    <el-card shadow="never">
      <h2>医药SaaS</h2>
      <p class="tip">当前为骨架搭建阶段：左侧菜单、顶部页签、中间内容区已就绪。</p>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="前端">Vue 3 + Vite + Element Plus</el-descriptions-item>
        <el-descriptions-item label="后端">Spring Cloud 微服务</el-descriptions-item>
        <el-descriptions-item label="菜单服务">/svc/common/menu</el-descriptions-item>
        <el-descriptions-item label="租户菜单">默认菜单 + 租户自定义覆盖</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
defineOptions({ name: 'home' })
</script>

<style scoped>
.tip {
  color: #666;
  margin: 8px 0 16px;
}
</style>
