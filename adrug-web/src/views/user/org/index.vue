<template>
  <div class="page">
    <el-card shadow="never">
      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="机构名称/编码" clearable style="width: 200px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="query.orgType" placeholder="全部" clearable style="width: 110px">
            <el-option label="总部" :value="1" />
            <el-option label="药店" :value="2" />
            <el-option label="门店" :value="3" />
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
        <el-table-column prop="orgCode" label="机构编码" width="140" />
        <el-table-column prop="orgName" label="机构名称" min-width="160" />
        <el-table-column label="类型" width="90">
          <template #default="{ row }">{{ row.orgTypeDesc }}</template>
        </el-table-column>
        <el-table-column prop="parentId" label="上级机构ID" width="110" />
        <el-table-column label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.statusDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="120" />
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑机构' : '新增机构'" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="机构编码" prop="orgCode"><el-input v-model="form.orgCode" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="机构名称" prop="orgName"><el-input v-model="form.orgName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="上级机构ID"><el-input-number v-model="form.parentId" :min="0" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="类型">
              <el-select v-model="form.orgType" style="width: 100%">
                <el-option label="总部" :value="1" />
                <el-option label="药店" :value="2" />
                <el-option label="门店" :value="3" />
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
import { pageOrg, createOrg, updateOrg, deleteOrg } from '@/api/org'

defineOptions({ name: 'user-org' })

const loading = ref(false)
const list = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', orgType: null, status: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()
const createEmptyForm = () => ({ id: null, parentId: 0, orgCode: '', orgName: '', orgType: 1, status: 1, remark: '' })
const form = ref(createEmptyForm())
const rules = {
  orgCode: [{ required: true, message: '请输入机构编码', trigger: 'blur' }],
  orgName: [{ required: true, message: '请输入机构名称', trigger: 'blur' }]
}

async function load() {
  loading.value = true
  try {
    const data = await pageOrg({ ...query })
    list.value = data.records || []
    total.value = data.total || 0
  } finally {
    loading.value = false
  }
}

function handleSearch() { query.pageNum = 1; load() }
function handleReset() { query.keyword = ''; query.orgType = null; query.status = null; handleSearch() }

function openAdd() { dialog.isEdit = false; form.value = createEmptyForm(); dialog.visible = true }

function openEdit(row) {
  dialog.isEdit = true
  form.value = { id: row.id, parentId: row.parentId, orgCode: row.orgCode, orgName: row.orgName, orgType: row.orgType, status: row.status, remark: row.remark }
  dialog.visible = true
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updateOrg(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createOrg(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除机构「${row.orgName}」？`, '提示', { type: 'warning' })
  await deleteOrg(row.id)
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
