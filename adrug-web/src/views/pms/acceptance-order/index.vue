<template>
  <div class="page">
    <el-card shadow="never">
      <template #header><span>验收单</span></template>

      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="单号/供应商" clearable style="width: 180px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable style="width: 120px">
            <el-option label="制单保存" :value="0" />
            <el-option label="制单完成" :value="1" />
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
        <el-table-column prop="orderNo" label="验收单号" width="150" />
        <el-table-column prop="supplierName" label="供应商" min-width="140" />
        <el-table-column prop="warehouseName" label="验收仓库" min-width="120" />
        <el-table-column prop="handler" label="经手人" width="90" />
        <el-table-column prop="acceptDate" label="验收日期" width="110" />
        <el-table-column prop="totalQty" label="数量合计" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.statusDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
            <el-button v-if="row.status === 0 && !row.acceptTime" link type="warning" @click="handleAccept(row)">验收</el-button>
            <el-button v-if="row.status === 0" link type="success" @click="handlePost(row)">过账</el-button>
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑验收单' : '新增验收单'" width="96%" top="4vh">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="8"><el-form-item label="验收单号" prop="orderNo"><el-input v-model="form.orderNo" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商名称" prop="supplierName"><el-input v-model="form.supplierName" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商ID"><el-input-number v-model="form.supplierId" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="验收仓库" prop="warehouseName"><el-input v-model="form.warehouseName" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="仓库ID"><el-input-number v-model="form.warehouseId" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="数据来源"><el-input v-model="form.sourceType" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="经手人"><el-input v-model="form.handler" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="来源收货单ID"><el-input-number v-model="form.sourceOrderId" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="验收日期"><el-date-picker v-model="form.acceptDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item></el-col>
        </el-row>
      </el-form>

      <div class="item-header">
        <span>验收明细</span>
        <el-button type="primary" link @click="addItem">+ 新增明细</el-button>
      </div>
      <el-table :data="form.items" border size="small">
        <el-table-column label="序号" width="60" fixed="left">
          <template #default="{ $index }">{{ $index + 1 }}</template>
        </el-table-column>
        <el-table-column label="商品编码" min-width="110">
          <template #default="{ row }"><el-input v-model="row.drugCode" placeholder="编码" /></template>
        </el-table-column>
        <el-table-column label="通用名称" min-width="120">
          <template #default="{ row }"><el-input v-model="row.genericName" /></template>
        </el-table-column>
        <el-table-column label="商品名称" min-width="130">
          <template #default="{ row }"><el-input v-model="row.drugName" placeholder="名称" /></template>
        </el-table-column>
        <el-table-column label="规格" width="90">
          <template #default="{ row }"><el-input v-model="row.spec" /></template>
        </el-table-column>
        <el-table-column label="单位" width="70">
          <template #default="{ row }"><el-input v-model="row.unit" /></template>
        </el-table-column>
        <el-table-column label="剂型" width="90">
          <template #default="{ row }"><el-input v-model="row.dosageForm" /></template>
        </el-table-column>
        <el-table-column label="产地" width="110">
          <template #default="{ row }"><el-input v-model="row.origin" /></template>
        </el-table-column>
        <el-table-column label="生产厂家" min-width="120">
          <template #default="{ row }"><el-input v-model="row.manufacturer" /></template>
        </el-table-column>
        <el-table-column label="生产许可证凭证号" width="140">
          <template #default="{ row }"><el-input v-model="row.productionLicenseNo" /></template>
        </el-table-column>
        <el-table-column label="上市持有人" min-width="120">
          <template #default="{ row }"><el-input v-model="row.marketingHolder" /></template>
        </el-table-column>
        <el-table-column label="批准文号" width="110">
          <template #default="{ row }"><el-input v-model="row.approvalNo" /></template>
        </el-table-column>
        <el-table-column label="批号" width="110">
          <template #default="{ row }"><el-input v-model="row.batchNo" /></template>
        </el-table-column>
        <el-table-column label="生产日期" width="130">
          <template #default="{ row }"><el-date-picker v-model="row.productionDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="有效期" width="130">
          <template #default="{ row }"><el-date-picker v-model="row.expiryDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="货位" width="90">
          <template #default="{ row }"><el-input v-model="row.location" /></template>
        </el-table-column>
        <el-table-column label="收货数量" width="100">
          <template #default="{ row }"><el-input-number v-model="row.receivedQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="验收数量" width="100">
          <template #default="{ row }"><el-input-number v-model="row.acceptQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="单价" width="110">
          <template #default="{ row }"><el-input-number v-model="row.price" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="金额" width="100">
          <template #default="{ row }">{{ calcAmount(row) }}</template>
        </el-table-column>
        <el-table-column label="有药检报告" width="100">
          <template #default="{ row }"><el-switch v-model="row.hasInspectionReport" :active-value="1" :inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="监管码可识别" width="110">
          <template #default="{ row }"><el-switch v-model="row.supervisionCodeRecognizable" :active-value="1" :inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="包装情况" width="100">
          <template #default="{ row }"><el-input v-model="row.packagingCondition" /></template>
        </el-table-column>
        <el-table-column label="外观质量" width="100">
          <template #default="{ row }"><el-input v-model="row.appearanceQuality" /></template>
        </el-table-column>
        <el-table-column label="验收状态" width="110">
          <template #default="{ row }"><el-input v-model="row.acceptanceStatus" /></template>
        </el-table-column>
        <el-table-column label="验收时间" width="150">
          <template #default="{ row }"><el-date-picker v-model="row.acceptTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="抽样数量" width="100">
          <template #default="{ row }"><el-input-number v-model="row.sampleQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="拒收数量" width="100">
          <template #default="{ row }"><el-input-number v-model="row.rejectQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="拒收原因" min-width="120">
          <template #default="{ row }"><el-input v-model="row.rejectReason" /></template>
        </el-table-column>
        <el-table-column label="拒收天数" width="90">
          <template #default="{ row }"><el-input-number v-model="row.rejectDays" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="不合格数量" width="100">
          <template #default="{ row }"><el-input-number v-model="row.unqualifiedQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="不合格原因" min-width="120">
          <template #default="{ row }"><el-input v-model="row.unqualifiedReason" /></template>
        </el-table-column>
        <el-table-column label="处理方式" width="110">
          <template #default="{ row }">
            <el-select v-model="row.handleType" placeholder="请选择" clearable style="width: 100%">
              <el-option label="退货" :value="1" />
              <el-option label="换货" :value="2" />
              <el-option label="让步接收" :value="3" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="待处理数量" width="100">
          <template #default="{ row }"><el-input-number v-model="row.pendingQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="收货员" width="90">
          <template #default="{ row }"><el-input v-model="row.receiver" /></template>
        </el-table-column>
        <el-table-column label="备注" min-width="120">
          <template #default="{ row }"><el-input v-model="row.remark" /></template>
        </el-table-column>
        <el-table-column label="操作" width="60" fixed="right">
          <template #default="{ $index }"><el-button link type="danger" @click="removeItem($index)">删除</el-button></template>
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
import { pageAcceptanceOrder, getAcceptanceOrder, createAcceptanceOrder, updateAcceptanceOrder, deleteAcceptanceOrder, acceptAcceptanceOrder, postAcceptanceOrder } from '@/api/acceptanceOrder'

defineOptions({ name: 'pms-acceptance-order' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', status: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()

const createEmptyItem = () => ({
  drugCode: '', genericName: '', drugName: '', spec: '', unit: '', dosageForm: '', origin: '',
  manufacturer: '', productionLicenseNo: '', marketingHolder: '', marketingHolderAddress: '',
  approvalNo: '', batchNo: '', productionDate: null, expiryDate: null, location: '',
  receivedQty: 0, acceptQty: 0, price: 0, amount: null, hasInspectionReport: 0,
  supervisionCodeRecognizable: 0, packagingCondition: '', appearanceQuality: '', acceptanceStatus: '',
  acceptTime: null, sampleQty: 0, rejectQty: 0, rejectReason: '', rejectDays: null,
  unqualifiedQty: 0, unqualifiedReason: '', handleType: null, pendingQty: 0, receiver: '', remark: ''
})
const createEmptyForm = () => ({
  id: null, orderNo: '', supplierId: null, supplierName: '', warehouseId: null, warehouseName: '',
  sourceType: '', handler: '', sourceOrderId: null, acceptDate: null, remark: '', items: [createEmptyItem()]
})
const form = ref(createEmptyForm())

const rules = {
  orderNo: [{ required: true, message: '请输入验收单号', trigger: 'blur' }],
  supplierName: [{ required: true, message: '请输入供应商名称', trigger: 'blur' }],
  warehouseName: [{ required: true, message: '请输入验收仓库', trigger: 'blur' }]
}

function calcAmount(row) {
  return ((Number(row.acceptQty) || 0) * (Number(row.price) || 0)).toFixed(2)
}

function addItem() {
  form.value.items.push(createEmptyItem())
}

function removeItem(index) {
  form.value.items.splice(index, 1)
}

async function load() {
  loading.value = true
  try {
    const data = await pageAcceptanceOrder({ ...query })
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
  const data = await getAcceptanceOrder(row.id)
  form.value = {
    id: data.id, orderNo: data.orderNo, supplierId: data.supplierId, supplierName: data.supplierName,
    warehouseId: data.warehouseId, warehouseName: data.warehouseName, sourceType: data.sourceType,
    handler: data.handler, sourceOrderId: data.sourceOrderId, acceptDate: data.acceptDate, remark: data.remark,
    items: (data.items && data.items.length ? data.items : [createEmptyItem()]).map((i) => ({
      drugCode: i.drugCode, genericName: i.genericName, drugName: i.drugName, spec: i.spec, unit: i.unit,
      dosageForm: i.dosageForm, origin: i.origin, manufacturer: i.manufacturer,
      productionLicenseNo: i.productionLicenseNo, marketingHolder: i.marketingHolder,
      marketingHolderAddress: i.marketingHolderAddress, approvalNo: i.approvalNo, batchNo: i.batchNo,
      productionDate: i.productionDate, expiryDate: i.expiryDate, location: i.location,
      receivedQty: i.receivedQty ?? 0, acceptQty: i.acceptQty ?? 0, price: i.price ?? 0, amount: i.amount,
      hasInspectionReport: i.hasInspectionReport ?? 0, supervisionCodeRecognizable: i.supervisionCodeRecognizable ?? 0,
      packagingCondition: i.packagingCondition, appearanceQuality: i.appearanceQuality,
      acceptanceStatus: i.acceptanceStatus, acceptTime: i.acceptTime, sampleQty: i.sampleQty ?? 0,
      rejectQty: i.rejectQty ?? 0, rejectReason: i.rejectReason, rejectDays: i.rejectDays,
      unqualifiedQty: i.unqualifiedQty ?? 0, unqualifiedReason: i.unqualifiedReason, handleType: i.handleType,
      pendingQty: i.pendingQty ?? 0, receiver: i.receiver, remark: i.remark
    }))
  }
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updateAcceptanceOrder(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createAcceptanceOrder(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除验收单「${row.orderNo}」？`, '提示', { type: 'warning' })
  await deleteAcceptanceOrder(row.id)
  ElMessage.success('删除成功')
  load()
}

async function handleAccept(row) {
  await ElMessageBox.confirm(`确认验收验收单「${row.orderNo}」？`, '提示', { type: 'warning' })
  await acceptAcceptanceOrder(row.id)
  ElMessage.success('验收完成')
  load()
}

async function handlePost(row) {
  await ElMessageBox.confirm(`确认过账验收单「${row.orderNo}」？过账后不可修改。`, '提示', { type: 'warning' })
  await postAcceptanceOrder(row.id)
  ElMessage.success('过账成功')
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
