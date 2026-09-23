<template>
  <div class="page">
    <el-card shadow="never">
      <template #header><span>供应商管理</span></template>

      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="名称/编码" clearable style="width: 180px" @keyup.enter="handleSearch" />
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
        <el-table-column prop="supplierCode" label="供应商编码" width="130" />
        <el-table-column prop="supplierName" label="供应商名称" min-width="180" />
        <el-table-column prop="enterpriseType" label="企业类型" width="100" />
        <el-table-column prop="contact" label="联系人" width="90" />
        <el-table-column prop="contactPhone" label="联系人手机" width="130" />
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑供应商' : '新增供应商'" width="96%" top="4vh">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="12">
          <el-col :span="8"><el-form-item label="供应商编码" prop="supplierCode"><el-input v-model="form.supplierCode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商名称" prop="supplierName"><el-input v-model="form.supplierName" /></el-form-item></el-col>
          <el-col :span="8">
            <el-form-item label="状态">
              <el-select v-model="form.status" style="width: 100%">
                <el-option label="启用" :value="1" />
                <el-option label="停用" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8"><el-form-item label="企业类型"><el-input v-model="form.enterpriseType" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商分类"><el-input v-model="form.supplierCategory" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="税号"><el-input v-model="form.taxNo" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="结算方式"><el-input v-model="form.settlementMethod" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="法人代表"><el-input v-model="form.legalRepresentative" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="企业负责人"><el-input v-model="form.enterpriseLeader" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="质量负责人"><el-input v-model="form.qualityLeader" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="联系人"><el-input v-model="form.contact" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="联系人手机"><el-input v-model="form.contactPhone" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="联系人身份证"><el-input v-model="form.contactIdCard" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="公司地址"><el-input v-model="form.companyAddress" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="仓库地址"><el-input v-model="form.warehouseAddress" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="区域"><el-input v-model="form.region" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="省份"><el-input v-model="form.province" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="城市"><el-input v-model="form.city" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="区/县"><el-input v-model="form.district" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="传真"><el-input v-model="form.fax" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="EMAIL"><el-input v-model="form.email" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="邮政编码"><el-input v-model="form.postcode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="预计送货天数"><el-input-number v-model="form.expectedDeliveryDays" :min="0" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="进项税率(%)"><el-input-number v-model="form.inputTaxRate" :min="0" :precision="2" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发票类型"><el-input v-model="form.invoiceType" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="药监往来单位"><el-input v-model="form.drugSupervisionUnit" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="受托机构"><el-input v-model="form.entrustedOrg" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="助记码"><el-input v-model="form.mnemonicCode" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="拟供应品种"><el-input v-model="form.plannedVariety" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="上一年度报告"><el-input v-model="form.lastYearReport" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="印章样式"><el-input v-model="form.sealStyle" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="质量体系调查表"><el-input v-model="form.qualitySystemSurvey" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="随货同行单样式"><el-input v-model="form.withGoodsBillStyle" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item></el-col>
        </el-row>
      </el-form>

      <div class="item-header">
        <span>证件信息</span>
        <el-button type="primary" link @click="addCert">+ 新增明细</el-button>
      </div>
      <el-table :data="form.certificates" border size="small">
        <el-table-column label="序号" width="60">
          <template #default="{ $index }">{{ $index + 1 }}</template>
        </el-table-column>
        <el-table-column label="证件类型" min-width="120">
          <template #default="{ row }"><el-input v-model="row.certType" /></template>
        </el-table-column>
        <el-table-column label="证件号码" min-width="140">
          <template #default="{ row }"><el-input v-model="row.certNo" /></template>
        </el-table-column>
        <el-table-column label="开始日期" width="140">
          <template #default="{ row }"><el-date-picker v-model="row.certStartDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="有效期" width="140">
          <template #default="{ row }"><el-date-picker v-model="row.certExpiryDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="经营范围" min-width="160">
          <template #default="{ row }"><el-input v-model="row.businessScope" /></template>
        </el-table-column>
        <el-table-column label="发证机关" min-width="140">
          <template #default="{ row }"><el-input v-model="row.issuingAuthority" /></template>
        </el-table-column>
        <el-table-column label="备注" min-width="120">
          <template #default="{ row }"><el-input v-model="row.remark" /></template>
        </el-table-column>
        <el-table-column label="操作" width="60" fixed="right">
          <template #default="{ $index }"><el-button link type="danger" @click="removeCert($index)">删除</el-button></template>
        </el-table-column>
      </el-table>

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
import { pageSupplier, getSupplier, createSupplier, updateSupplier, deleteSupplier } from '@/api/supplier'

defineOptions({ name: 'bus-supplier' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', status: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()

const createEmptyCert = () => ({ certType: '', certNo: '', certStartDate: null, certExpiryDate: null, businessScope: '', issuingAuthority: '', remark: '' })
const createEmptyForm = () => ({
  id: null, supplierCode: '', supplierName: '', status: 1, enterpriseType: '', supplierCategory: '',
  taxNo: '', settlementMethod: '', legalRepresentative: '', phone: '', enterpriseLeader: '', qualityLeader: '',
  contact: '', contactPhone: '', contactIdCard: '', companyAddress: '', warehouseAddress: '', region: '',
  province: '', city: '', district: '', fax: '', email: '', postcode: '', expectedDeliveryDays: null,
  inputTaxRate: null, invoiceType: '', drugSupervisionUnit: '', entrustedOrg: '', mnemonicCode: '',
  plannedVariety: '', lastYearReport: '', sealStyle: '', qualitySystemSurvey: '', withGoodsBillStyle: '',
  remark: '', certificates: [createEmptyCert()]
})
const form = ref(createEmptyForm())

const rules = {
  supplierCode: [{ required: true, message: '请输入供应商编码', trigger: 'blur' }],
  supplierName: [{ required: true, message: '请输入供应商名称', trigger: 'blur' }]
}

function addCert() {
  form.value.certificates.push(createEmptyCert())
}

function removeCert(index) {
  form.value.certificates.splice(index, 1)
}

async function load() {
  loading.value = true
  try {
    const data = await pageSupplier({ ...query })
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
  query.status = null
  handleSearch()
}

function openAdd() {
  dialog.isEdit = false
  form.value = createEmptyForm()
  dialog.visible = true
}

async function openEdit(row) {
  dialog.isEdit = true
  dialog.visible = true
  const data = await getSupplier(row.id)
  form.value = {
    id: data.id, supplierCode: data.supplierCode, supplierName: data.supplierName, status: data.status,
    enterpriseType: data.enterpriseType, supplierCategory: data.supplierCategory, taxNo: data.taxNo,
    settlementMethod: data.settlementMethod, legalRepresentative: data.legalRepresentative, phone: data.phone,
    enterpriseLeader: data.enterpriseLeader, qualityLeader: data.qualityLeader, contact: data.contact,
    contactPhone: data.contactPhone, contactIdCard: data.contactIdCard, companyAddress: data.companyAddress,
    warehouseAddress: data.warehouseAddress, region: data.region, province: data.province, city: data.city,
    district: data.district, fax: data.fax, email: data.email, postcode: data.postcode,
    expectedDeliveryDays: data.expectedDeliveryDays, inputTaxRate: data.inputTaxRate, invoiceType: data.invoiceType,
    drugSupervisionUnit: data.drugSupervisionUnit, entrustedOrg: data.entrustedOrg, mnemonicCode: data.mnemonicCode,
    plannedVariety: data.plannedVariety, lastYearReport: data.lastYearReport, sealStyle: data.sealStyle,
    qualitySystemSurvey: data.qualitySystemSurvey, withGoodsBillStyle: data.withGoodsBillStyle, remark: data.remark,
    certificates: (data.certificates && data.certificates.length ? data.certificates : [createEmptyCert()]).map((c) => ({
      certType: c.certType, certNo: c.certNo, certStartDate: c.certStartDate, certExpiryDate: c.certExpiryDate,
      businessScope: c.businessScope, issuingAuthority: c.issuingAuthority, remark: c.remark
    }))
  }
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updateSupplier(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createSupplier(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除供应商「${row.supplierName}」？`, '提示', { type: 'warning' })
  await deleteSupplier(row.id)
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped>
.search-form { margin-bottom: 12px; }
.toolbar { margin-bottom: 12px; }
.pagination { display: flex; justify-content: flex-end; margin-top: 16px; }
.item-header { display: flex; justify-content: space-between; align-items: center; margin: 16px 0 8px; font-weight: 600; }
</style>
