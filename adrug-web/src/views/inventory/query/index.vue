<template>
  <div class="page">
    <el-card shadow="never">
      <template #header><span>库存查询</span></template>

      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="药品名称/编码" clearable style="width: 200px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="仓库ID">
          <el-input-number v-model="query.warehouseId" :controls="false" placeholder="仓库" style="width: 140px" />
        </el-form-item>
        <el-form-item label="批号">
          <el-input v-model="query.batchNo" placeholder="批号" clearable style="width: 140px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="list" border stripe>
        <el-table-column prop="drugCode" label="药品编码" width="140" />
        <el-table-column prop="drugName" label="药品名称" min-width="160" />
        <el-table-column prop="spec" label="规格" width="100" />
        <el-table-column prop="unit" label="单位" width="70" />
        <el-table-column prop="warehouseName" label="仓库" min-width="120" />
        <el-table-column prop="batchNo" label="批号" width="120" />
        <el-table-column prop="productionDate" label="生产日期" width="110" />
        <el-table-column prop="expiryDate" label="有效期" width="110" />
        <el-table-column prop="quantity" label="可用库存" width="110" />
      </el-table>

      <el-pagination
        class="pagination"
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="load"
        @size-change="handleSearch"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { pageInventory } from '@/api/inventory'

defineOptions({ name: 'inventory-query' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', warehouseId: null, batchNo: '' })

async function load() {
  loading.value = true
  try {
    const data = await pageInventory({ ...query })
    list.value = data.records || []
    total.value = data.total || 0
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  query.pageNum = 1
  load()
}

function handleReset() {
  query.keyword = ''
  query.warehouseId = null
  query.batchNo = ''
  handleSearch()
}

onMounted(load)
</script>

<style scoped>
.search-form { margin-bottom: 12px; }
.pagination { display: flex; justify-content: flex-end; margin-top: 16px; }
</style>
