<template>
  <div class="page">
    <el-card shadow="never">
      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="姓名/工号" clearable style="width: 180px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="query.gender" placeholder="全部" clearable style="width: 100px">
            <el-option label="男" :value="1" />
            <el-option label="女" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="在职状态">
          <el-select v-model="query.status" placeholder="全部" clearable style="width: 110px">
            <el-option label="在职" :value="1" />
            <el-option label="离职" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否药师">
          <el-select v-model="query.pharmacist" placeholder="全部" clearable style="width: 100px">
            <el-option label="是" :value="1" />
            <el-option label="否" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <div class="toolbar">
        <el-button v-permission="'user:employee:create'" type="primary" @click="openAdd">新增</el-button>
      </div>

      <el-table v-loading="loading" :data="list" border stripe>
        <el-table-column prop="empNo" label="工号" width="120" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column label="性别" width="70">
          <template #default="{ row }">{{ row.genderDesc }}</template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="phone" label="账号" width="130" />
        <el-table-column label="入职日期" width="110">
          <template #default="{ row }">{{ row.entryDate }}</template>
        </el-table-column>
        <el-table-column label="是否药师" width="90">
          <template #default="{ row }">{{ row.pharmacistDesc }}</template>
        </el-table-column>
        <el-table-column label="在职状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.statusDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button v-permission="'user:employee:update'" link type="primary" @click="openEdit(row)">编辑</el-button>
            <el-button v-permission="'user:employee:assign'" link type="primary" @click="openAssignRoles(row)">分配角色</el-button>
            <el-button v-permission="'user:employee:delete'" link type="danger" @click="handleDelete(row)">删除</el-button>
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑员工' : '新增员工'" width="680px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="工号" prop="empNo"><el-input v-model="form.empNo" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="姓名" prop="name"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="性别">
              <el-select v-model="form.gender" style="width: 100%">
                <el-option label="未知" :value="0" />
                <el-option label="男" :value="1" />
                <el-option label="女" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="出生日期"><el-date-picker v-model="form.birthDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="手机号" prop="phone"><el-input v-model="form.phone" placeholder="登录账号" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="身份证号"><el-input v-model="form.idCardNo" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="岗位ID"><el-input-number v-model="form.positionId" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="入职日期"><el-date-picker v-model="form.entryDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="离职日期"><el-date-picker v-model="form.resignDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="在职状态">
              <el-select v-model="form.status" style="width: 100%">
                <el-option label="在职" :value="1" />
                <el-option label="离职" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否药师">
              <el-select v-model="form.pharmacist" style="width: 100%">
                <el-option label="是" :value="1" />
                <el-option label="否" :value="0" />
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

    <el-dialog v-model="roleDialog.visible" title="分配角色" width="480px">
      <el-checkbox-group v-model="selectedRoleIds">
        <el-checkbox v-for="r in allRoles" :key="r.id" :value="r.id" style="display: block; margin: 4px 0">
          {{ r.roleName }}（{{ r.roleCode }}）
        </el-checkbox>
      </el-checkbox-group>
      <template #footer>
        <el-button @click="roleDialog.visible = false">取消</el-button>
        <el-button type="primary" :loading="roleDialog.saving" @click="handleAssignRoles">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageEmployee, createEmployee, updateEmployee, deleteEmployee } from '@/api/employee'
import { getAccountByEmployeeId } from '@/api/account'
import { pageRole, getAccountRoleIds, saveAccountRoles } from '@/api/permission'

defineOptions({ name: 'user-employee' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', gender: null, status: null, pharmacist: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()

const createEmptyForm = () => ({
  id: null, empNo: '', name: '', gender: 1, birthDate: null, idCardNo: '', phone: '',
  email: '', positionId: null, entryDate: null, resignDate: null, status: 1, pharmacist: 0, remark: ''
})
const form = ref(createEmptyForm())

const rules = {
  empNo: [{ required: true, message: '请输入工号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号（作为登录账号）', trigger: 'blur' }]
}

async function load() {
  loading.value = true
  try {
    const data = await pageEmployee({ ...query })
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
  query.gender = null
  query.status = null
  query.pharmacist = null
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
    id: row.id, empNo: row.empNo, name: row.name, gender: row.gender, birthDate: row.birthDate,
    idCardNo: row.idCardNo, phone: row.phone, email: row.email, positionId: row.positionId,
    entryDate: row.entryDate, resignDate: row.resignDate, status: row.status,
    pharmacist: row.pharmacist, remark: row.remark
  }
  dialog.visible = true
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updateEmployee(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createEmployee(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除员工「${row.name}」？`, '提示', { type: 'warning' })
  await deleteEmployee(row.id)
  ElMessage.success('删除成功')
  load()
}

const roleDialog = reactive({ visible: false, accountId: null, saving: false })
const allRoles = ref([])
const selectedRoleIds = ref([])

async function openAssignRoles(row) {
  roleDialog.visible = true
  try {
    const account = await getAccountByEmployeeId(row.id)
    if (!account) {
      ElMessage.warning('该员工还没有账号')
      roleDialog.visible = false
      return
    }
    roleDialog.accountId = account.id
    const [roles, roleIds] = await Promise.all([
      pageRole({ pageNum: 1, pageSize: 1000 }),
      getAccountRoleIds(account.id)
    ])
    allRoles.value = roles.records || []
    selectedRoleIds.value = roleIds || []
  } catch {
    /* ignore */
  }
}

async function handleAssignRoles() {
  roleDialog.saving = true
  try {
    await saveAccountRoles(roleDialog.accountId, selectedRoleIds.value || [])
    ElMessage.success('分配成功')
    roleDialog.visible = false
  } finally {
    roleDialog.saving = false
  }
}

onMounted(load)
</script>

<style scoped>
.search-form { margin-bottom: 12px; }
.toolbar { margin-bottom: 12px; }
.pagination { display: flex; justify-content: flex-end; margin-top: 16px; }
</style>
