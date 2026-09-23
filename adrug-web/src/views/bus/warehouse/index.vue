<template>
  <div class="page">
    <el-card shadow="never">
      <template #header><span>仓库信息</span></template>

      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="名称/编码" clearable style="width: 180px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="query.warehouseType" placeholder="全部" clearable style="width: 120px">
            <el-option label="合格仓" :value="1" />
            <el-option label="不合格仓" :value="2" />
            <el-option label="待验区" :value="3" />
            <el-option label="退货区" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable style="width: 110px">
            <el-option label="启用" :value="1" />
            <el-option label="停用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <div class="toolbar">
        <el-button type="primary" @click="openAdd">新增</el-button>
      </div>

      <el-table v-loading="loading" :data="list" border stripe>
        <el-table-column prop="warehouseCode" label="仓库编码" width="130" />
        <el-table-column prop="warehouseName" label="仓库名称" min-width="160" />
        <el-table-column label="类型" width="100">
          <template #default="{ row }">{{ row.warehouseTypeDesc }}</template>
        </el-table-column>
        <el-table-column prop="address" label="地址" min-width="160" />
        <el-table-column prop="manager" label="负责人" width="90" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.statusDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="140" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑仓库' : '新增仓库'" width="640px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="仓库编码" prop="warehouseCode"><el-input v-model="form.warehouseCode" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="仓库名称" prop="warehouseName"><el-input v-model="form.warehouseName" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="类型">
              <el-select v-model="form.warehouseType" style="width: 100%">
                <el-option label="合格仓" :value="1" />
                <el-option label="不合格仓" :value="2" />
                <el-option label="待验区" :value="3" />
                <el-option label="退货区" :value="4" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-select v-model="form.status" style="width: 100%">
                <el-option label="启用" :value="1" />
                <el-option label="停用" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="负责人"><el-input v-model="form.manager" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="联系电话"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="地址"><el-input v-model="form.address" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialog.visible = false">取消</el-button>
        <el-button type="primary" :loading="dialog.saving" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageWarehouse, createWarehouse, updateWarehouse, deleteWarehouse } from '@/api/warehouse'

defineOptions({ name: 'bus-warehouse' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', warehouseType: null, status: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()

const createEmptyForm = () => ({
  id: null, warehouseCode: '', warehouseName: '', warehouseType: 1, status: 1,
  address: '', manager: '', phone: '', remark: ''
})
const form = ref(createEmptyForm())

const rules = {
  warehouseCode: [{ required: true, message: '请输入仓库编码', trigger: 'blur' }],
  warehouseName: [{ required: true, message: '请输入仓库名称', trigger: 'blur' }]
}

async function load() {
  loading.value = true
  try {
    const data = await pageWarehouse({ ...query })
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
  query.warehouseType = null
  query.status = null
  handleSearch()
}

function openAdd() {
  dialog.isEdit = false
  form.value = createEmptyForm()
  dialog.visible = true
}

function openEdit(row) {
  dialog.isEdit = true
  form.value = {
    id: row.id, warehouseCode: row.warehouseCode, warehouseName: row.warehouseName,
    warehouseType: row.warehouseType, status: row.status, address: row.address,
    manager: row.manager, phone: row.phone, remark: row.remark
  }
  dialog.visible = true
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updateWarehouse(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createWarehouse(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除仓库「${row.warehouseName}」？`, '提示', { type: 'warning' })
  await deleteWarehouse(row.id)
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped>
.search-form { margin-bottom: 12px; }
.toolbar { margin-bottom: 12px; }
.pagination { display: flex; justify-content: flex-end; margin-top: 16px; }
</style>
