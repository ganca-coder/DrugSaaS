<template>
  <div class="page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>菜单管理（当前租户，由顶部「租户 ID」决定）</span>
          <div class="card-tools">
            <el-button type="primary" size="small" @click="openAddRoot">新增根菜单</el-button>
          </div>
        </div>
      </template>

      <el-table
        v-loading="loading"
        :data="tableData"
        row-key="id"
        :tree-props="{ children: 'children' }"
        default-expand-all
        border
      >
        <el-table-column prop="menuName" label="菜单名称" min-width="180" />
        <el-table-column prop="menuCode" label="菜单编码" min-width="140" />
        <el-table-column label="类型" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.menuType === 1" size="small">目录</el-tag>
            <el-tag v-else-if="row.menuType === 2" size="small" type="success">菜单</el-tag>
            <el-tag v-else size="small" type="info">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路由地址" min-width="140" />
        <el-table-column prop="component" label="组件" min-width="160" />
        <el-table-column prop="icon" label="图标" width="110" />
        <el-table-column prop="sortNo" label="排序" width="70" />
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'" size="small">{{ row.statusDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="190" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.menuType !== 3" link type="primary" @click="openAddChild(row)">新增下级</el-button>
            <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑菜单' : '新增菜单'" width="640px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="菜单名称" prop="menuName"><el-input v-model="form.menuName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="菜单编码" prop="menuCode"><el-input v-model="form.menuCode" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="上级菜单">
              <el-tree-select
                v-model="form.parentId"
                :data="parentOptions"
                :props="{ label: 'menuName', children: 'children' }"
                node-key="id"
                check-strictly
                default-expand-all
                :render-after-expand="false"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型">
              <el-select v-model="form.menuType" style="width: 100%">
                <el-option label="目录" :value="1" />
                <el-option label="菜单" :value="2" />
                <el-option label="按钮" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="路由地址"><el-input v-model="form.path" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="组件路径"><el-input v-model="form.component" placeholder="如 bus/drug/index" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="图标"><el-input v-model="form.icon" placeholder="Element Plus 图标名" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="排序号"><el-input-number v-model="form.sortNo" :min="0" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="权限标识"><el-input v-model="form.permission" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="显示"><el-switch v-model="form.visible" :active-value="1" :inactive-value="0" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="启用"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item></el-col>
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
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMenuTree, createMenu, updateMenu, deleteMenu } from '@/api/menu'

defineOptions({ name: 'system-menu' })

const loading = ref(false)
const tableData = ref([])

const parentOptions = computed(() => [{ id: 0, menuName: '顶级菜单', children: tableData.value }])

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()

const createEmptyForm = () => ({
  id: null, menuName: '', menuCode: '', parentId: 0, menuType: 2, path: '', component: '',
  icon: '', sortNo: 0, visible: 1, status: 1, permission: '', remark: ''
})
const form = ref(createEmptyForm())

const rules = {
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  menuCode: [{ required: true, message: '请输入菜单编码', trigger: 'blur' }]
}

function buildTree(list) {
  const map = {}
  const roots = []
  ;(list || []).forEach((item) => { map[item.id] = { ...item, children: [] } })
  ;(list || []).forEach((item) => {
    const node = map[item.id]
    if (item.parentId && map[item.parentId]) {
      map[item.parentId].children.push(node)
    } else {
      roots.push(node)
    }
  })
  return roots
}

async function load() {
  loading.value = true
  try {
    tableData.value = await getMenuTree() || []
  } finally {
    loading.value = false
  }
}

function openAddRoot() {
  dialog.isEdit = false
  form.value = createEmptyForm()
  form.value.parentId = 0
  dialog.visible = true
}

function openAddChild(row) {
  dialog.isEdit = false
  form.value = createEmptyForm()
  form.value.parentId = row.id
  dialog.visible = true
}

function openEdit(row) {
  dialog.isEdit = true
  form.value = {
    id: row.id, menuName: row.menuName, menuCode: row.menuCode, parentId: row.parentId,
    menuType: row.menuType, path: row.path, component: row.component, icon: row.icon,
    sortNo: row.sortNo, visible: row.visible, status: row.status, permission: row.permission,
    remark: row.remark
  }
  dialog.visible = true
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updateMenu(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createMenu(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除菜单「${row.menuName}」？`, '提示', { type: 'warning' })
  await deleteMenu(row.id)
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped>
.card-header { display: flex; align-items: center; justify-content: space-between; }
.card-tools { display: flex; align-items: center; gap: 8px; }
.tenant-label { font-size: 13px; color: #666; }
</style>
