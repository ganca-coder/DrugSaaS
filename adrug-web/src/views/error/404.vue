<template>
  <div class="not-found">
    <el-result icon="warning" title="404" sub-title="页面不存在或路由未注册">
      <template #extra>
        <el-button type="primary" @click="$router.replace('/')">回到首页</el-button>
      </template>
    </el-result>
  </div>
</template>

<script setup>
defineOptions({ name: '404' })
</script>

<style scoped>
.not-found {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f2f5;
}
</style>
