<template>
  <div class="page">
    <el-card shadow="never">
      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="角色名称/编码" clearable style="width: 200px" @keyup.enter="handleSearch" />
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
        <el-button v-permission="'system:role:create'" type="primary" @click="openAdd">新增</el-button>
      </div>

      <el-table v-loading="loading" :data="list" border stripe>
        <el-table-column prop="roleCode" label="角色编码" width="160" />
        <el-table-column prop="roleName" label="角色名称" min-width="160" />
        <el-table-column label="数据范围" width="120">
          <template #default="{ row }">{{ row.dataScopeDesc }}</template>
        </el-table-column>
        <el-table-column label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.statusDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="140" />
        <el-table-column label="操作" width="140" fixed="right">
          <template #default="{ row }">
            <el-button v-permission="'system:role:update'" link type="primary" @click="openEdit(row)">编辑</el-button>
            <el-button v-permission="'system:role:delete'" link type="danger" @click="handleDelete(row)">删除</el-button>
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑角色' : '新增角色'" width="680px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="角色编码" prop="roleCode"><el-input v-model="form.roleCode" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="角色名称" prop="roleName"><el-input v-model="form.roleName" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-select v-model="form.status" style="width: 100%">
                <el-option label="启用" :value="1" />
                <el-option label="停用" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据范围">
              <el-select v-model="form.dataScope" style="width: 100%">
                <el-option label="全部数据" :value="1" />
                <el-option label="多机构及下级" :value="2" />
                <el-option label="本机构及下级" :value="3" />
                <el-option label="本机构" :value="4" />
                <el-option label="仅本人" :value="5" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item v-if="form.dataScope === 2" label="机构">
          <div class="org-tree">
            <el-tree
              ref="orgTreeRef"
              :data="orgTree"
              show-checkbox
              node-key="id"
              check-strictly
              :props="{ label: 'orgName', children: 'children' }"
              default-expand-all
            />
          </div>
        </el-form-item>

        <el-form-item label="权限">
          <el-checkbox-group v-model="selectedPermissions" class="perm-group">
            <el-checkbox v-for="p in availablePermissions" :key="p.permission" :value="p.permission">
              <span>{{ p.menuName }}</span>
              <span class="perm-code">{{ p.permission }}</span>
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialog.visible = false">取消</el-button>
        <el-button type="primary" :loading="dialog.saving" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, nextTick, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageRole, createRole, updateRole, deleteRole, saveRolePermissions, getRolePermissions, saveRoleDataOrgs, getRoleDataOrgs } from '@/api/permission'
import { pageOrg } from '@/api/org'
import { useMenuStore } from '@/stores/menu'

defineOptions({ name: 'system-role' })

const menuStore = useMenuStore()

const loading = ref(false)
const list = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', status: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()
const orgTreeRef = ref()
const createEmptyForm = () => ({ id: null, roleCode: '', roleName: '', status: 1, dataScope: 4, remark: '' })
const form = ref(createEmptyForm())
const selectedPermissions = ref([])
const orgTree = ref([])
const rules = {
  roleCode: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
  roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }]
}

// 从当前租户菜单树收集可用权限标识
function collectPermissions(tree, acc = []) {
  for (const node of tree || []) {
    if (node.permission) acc.push({ permission: node.permission, menuName: node.menuName })
    if (node.children && node.children.length) collectPermissions(node.children, acc)
  }
  return acc
}
const availablePermissions = computed(() => collectPermissions(menuStore.menus))

function buildOrgTree(list) {
  const map = {}
  const roots = []
  ;(list || []).forEach((item) => { map[item.id] = { ...item, children: [] } })
  ;(list || []).forEach((item) => {
    const node = map[item.id]
    if (item.parentId && map[item.parentId]) map[item.parentId].children.push(node)
    else roots.push(node)
  })
  return roots
}

async function loadOrgTree() {
  try {
    const data = await pageOrg({ pageNum: 1, pageSize: 1000 })
    orgTree.value = buildOrgTree(data.records || [])
  } catch {
    orgTree.value = []
  }
}

async function load() {
  loading.value = true
  try {
    const data = await pageRole({ ...query })
    list.value = data.records || []
    total.value = data.total || 0
  } finally {
    loading.value = false
  }
}

function handleSearch() { query.pageNum = 1; load() }
function handleReset() { query.keyword = ''; query.status = null; handleSearch() }

function openAdd() {
  dialog.isEdit = false
  form.value = createEmptyForm()
  selectedPermissions.value = []
  orgTree.value = []
  dialog.visible = true
  loadOrgTree()
}

async function openEdit(row) {
  dialog.isEdit = true
  form.value = { id: row.id, roleCode: row.roleCode, roleName: row.roleName, status: row.status, dataScope: row.dataScope, remark: row.remark }
  try {
    selectedPermissions.value = await getRolePermissions(row.id) || []
  } catch {
    selectedPermissions.value = []
  }
  orgTree.value = []
  dialog.visible = true
  await loadOrgTree()
  if (form.value.dataScope === 2) {
    try {
      const orgIds = await getRoleDataOrgs(row.id) || []
      await nextTick()
      orgTreeRef.value?.setCheckedKeys(orgIds)
    } catch { /* ignore */ }
  }
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    let roleId
    if (dialog.isEdit) {
      roleId = form.value.id
      await updateRole(roleId, form.value)
      ElMessage.success('修改成功')
    } else {
      roleId = await createRole(form.value)
      ElMessage.success('新增成功')
    }
    await saveRolePermissions(roleId, selectedPermissions.value || [])
    const dataOrgIds = form.value.dataScope === 2
      ? (orgTreeRef.value?.getCheckedKeys() || [])
      : []
    await saveRoleDataOrgs(roleId, dataOrgIds)
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除角色「${row.roleName}」？`, '提示', { type: 'warning' })
  await deleteRole(row.id)
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped>
.search-form { margin-bottom: 12px; }
.toolbar { margin-bottom: 12px; }
.pagination { display: flex; justify-content: flex-end; margin-top: 16px; }
.perm-group {
  max-height: 200px;
  overflow: auto;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  padding: 8px 12px;
  width: 100%;
  display: flex;
  flex-direction: column;
}
.org-tree {
  width: 100%;
  max-height: 200px;
  overflow: auto;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  padding: 8px;
}
.perm-code {
  margin-left: 8px;
  color: #999;
  font-size: 12px;
}
</style>
