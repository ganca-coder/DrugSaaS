<template>
  <div class="page">
    <el-card shadow="never">
      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="药品名称/编码" clearable style="width: 200px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="query.drugType" placeholder="全部" clearable style="width: 130px">
            <el-option label="处方药" :value="1" />
            <el-option label="非处方药" :value="2" />
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
        <el-table-column prop="drugCode" label="药品编码" width="140" />
        <el-table-column prop="drugName" label="药品名称" min-width="160" />
        <el-table-column prop="genericName" label="通用名" min-width="120" />
        <el-table-column prop="mainBarcode" label="主条形码" width="130" />
        <el-table-column label="分类" width="100">
          <template #default="{ row }">{{ row.categoryDesc }}</template>
        </el-table-column>
        <el-table-column prop="spec" label="规格" width="100" />
        <el-table-column prop="unit" label="单位" width="70" />
        <el-table-column prop="manufacturer" label="生产厂家" min-width="120" />
        <el-table-column label="类型" width="90">
          <template #default="{ row }">{{ row.drugTypeDesc }}</template>
        </el-table-column>
        <el-table-column label="状态" width="90">
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑药品' : '新增药品'" width="96%" top="4vh">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="12">
          <el-col :span="8"><el-form-item label="药品编码" prop="drugCode"><el-input v-model="form.drugCode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="药品名称" prop="drugName"><el-input v-model="form.drugName" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="通用名称"><el-input v-model="form.genericName" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="规格"><el-input v-model="form.spec" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="包装规格"><el-input v-model="form.packageSpec" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="剂型"><el-input v-model="form.dosageForm" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="单位"><el-input v-model="form.unit" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="生产厂家"><el-input v-model="form.manufacturer" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="产地"><el-input v-model="form.origin" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="批准文号"><el-input v-model="form.approvalNo" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="批文有效期"><el-date-picker v-model="form.approvalExpiryDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="主条形码"><el-input v-model="form.mainBarcode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="条码一"><el-input v-model="form.barcode1" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="本位码"><el-input v-model="form.standardCode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="药监统一编码"><el-input v-model="form.drugSupervisionCode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="国家医保编码"><el-input v-model="form.nationalMedicalInsuranceCode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="助记码"><el-input v-model="form.mnemonicCode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="上市持有人"><el-input v-model="form.marketingHolder" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="上市持有人地址"><el-input v-model="form.marketingHolderAddress" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="储存条件"><el-input v-model="form.storageCondition" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="经营范围"><el-input v-model="form.businessScope" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="拆零规格"><el-input v-model="form.splitSpec" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="拆零单位"><el-input v-model="form.splitUnit" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="进价"><el-input-number v-model="form.purchasePrice" :min="0" :precision="4" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="零售价"><el-input-number v-model="form.retailPrice" :min="0" :precision="4" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="会员价"><el-input-number v-model="form.memberPrice" :min="0" :precision="4" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="拆零价"><el-input-number v-model="form.splitPrice" :min="0" :precision="4" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="建议零售价"><el-input-number v-model="form.suggestedRetailPrice" :min="0" :precision="4" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8">
            <el-form-item label="商品分类">
              <el-select v-model="form.category" clearable style="width: 100%">
                <el-option label="普通药品" :value="1" />
                <el-option label="中药饮片" :value="2" />
                <el-option label="精制饮片" :value="3" />
                <el-option label="医疗器械" :value="4" />
                <el-option label="保健食品" :value="5" />
                <el-option label="赠品" :value="6" />
                <el-option label="其他" :value="7" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="类型">
              <el-select v-model="form.drugType" style="width: 100%">
                <el-option label="处方药" :value="1" />
                <el-option label="非处方药" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
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
import { pageDrug, createDrug, updateDrug, deleteDrug } from '@/api/drug'

defineOptions({ name: 'bus-drug' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', drugType: null, status: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()

const createEmptyForm = () => ({
  id: null, drugCode: '', drugName: '', genericName: '', spec: '', dosageForm: '', unit: '',
  manufacturer: '', approvalNo: '', mainBarcode: '', category: null, packageSpec: '', origin: '',
  marketingHolder: '', marketingHolderAddress: '', approvalExpiryDate: null, drugSupervisionCode: '',
  barcode1: '', standardCode: '', storageCondition: '', businessScope: '', splitSpec: '', splitUnit: '',
  purchasePrice: null, retailPrice: null, memberPrice: null, splitPrice: null, suggestedRetailPrice: null,
  nationalMedicalInsuranceCode: '', mnemonicCode: '', drugType: 1, status: 1, remark: ''
})
const form = ref(createEmptyForm())

const rules = {
  drugCode: [{ required: true, message: '请输入药品编码', trigger: 'blur' }],
  drugName: [{ required: true, message: '请输入药品名称', trigger: 'blur' }]
}

async function load() {
  loading.value = true
  try {
    const data = await pageDrug({ ...query })
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
  query.drugType = null
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
    id: row.id, drugCode: row.drugCode, drugName: row.drugName, genericName: row.genericName,
    spec: row.spec, dosageForm: row.dosageForm, unit: row.unit, manufacturer: row.manufacturer,
    approvalNo: row.approvalNo, mainBarcode: row.mainBarcode, category: row.category,
    packageSpec: row.packageSpec, origin: row.origin, marketingHolder: row.marketingHolder,
    marketingHolderAddress: row.marketingHolderAddress, approvalExpiryDate: row.approvalExpiryDate,
    drugSupervisionCode: row.drugSupervisionCode, barcode1: row.barcode1, standardCode: row.standardCode,
    storageCondition: row.storageCondition, businessScope: row.businessScope, splitSpec: row.splitSpec,
    splitUnit: row.splitUnit, purchasePrice: row.purchasePrice, retailPrice: row.retailPrice,
    memberPrice: row.memberPrice, splitPrice: row.splitPrice, suggestedRetailPrice: row.suggestedRetailPrice,
    nationalMedicalInsuranceCode: row.nationalMedicalInsuranceCode, mnemonicCode: row.mnemonicCode,
    drugType: row.drugType, status: row.status, remark: row.remark
  }
  dialog.visible = true
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updateDrug(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createDrug(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除药品「${row.drugName}」？`, '提示', { type: 'warning' })
  await deleteDrug(row.id)
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
