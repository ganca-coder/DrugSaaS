<template>
  <div class="page">
    <el-card shadow="never">
      <template #header><span>采购订单</span></template>

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
        <el-table-column prop="orderNo" label="采购订单号" width="150" />
        <el-table-column prop="supplierName" label="供应商" min-width="140" />
        <el-table-column prop="handler" label="经手人" width="90" />
        <el-table-column prop="purchaser" label="采购员" width="90" />
        <el-table-column prop="orderDate" label="下单日期" width="110" />
        <el-table-column prop="totalQty" label="数量合计" width="100" />
        <el-table-column prop="totalAmount" label="金额合计" width="120" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.statusDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="info" @click="goDetail(row)">详情</el-button>
            <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑采购订单' : '新增采购订单'" width="96%" top="4vh">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="12">
          <el-col :span="8"><el-form-item label="订单号" prop="orderNo"><el-input v-model="form.orderNo" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商名称" prop="supplierName"><el-input v-model="form.supplierName" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商ID"><el-input-number v-model="form.supplierId" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商业务员"><el-input v-model="form.supplierSalesman" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="经手人"><el-input v-model="form.handler" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="采购员"><el-input v-model="form.purchaser" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="下单日期"><el-date-picker v-model="form.orderDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="付款方式"><el-input v-model="form.paymentMethod" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="结算方式"><el-input v-model="form.settlementMethod" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="预结算日期"><el-date-picker v-model="form.preSettlementDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发票类型"><el-input v-model="form.invoiceType" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="送货方式"><el-input v-model="form.deliveryMethod" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="预计到货时间"><el-date-picker v-model="form.estimatedArrivalTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item></el-col>
        </el-row>
      </el-form>

      <div class="item-header">
        <span>采购明细</span>
        <el-button type="primary" link @click="addItem">+ 新增明细</el-button>
      </div>
      <el-table :data="form.items" border size="small" max-height="360">
        <el-table-column label="序号" width="60" fixed="left">
          <template #default="{ $index }">{{ $index + 1 }}</template>
        </el-table-column>
        <el-table-column label="药品编码" min-width="110">
          <template #default="{ row }"><el-input v-model="row.drugCode" placeholder="编码" /></template>
        </el-table-column>
        <el-table-column label="通用药品名称" min-width="120">
          <template #default="{ row }"><el-input v-model="row.genericName" /></template>
        </el-table-column>
        <el-table-column label="药品名称" min-width="130">
          <template #default="{ row }"><el-input v-model="row.drugName" placeholder="名称" /></template>
        </el-table-column>
        <el-table-column label="单位" width="70">
          <template #default="{ row }"><el-input v-model="row.unit" /></template>
        </el-table-column>
        <el-table-column label="规格" width="90">
          <template #default="{ row }"><el-input v-model="row.spec" /></template>
        </el-table-column>
        <el-table-column label="产地" min-width="110">
          <template #default="{ row }"><el-input v-model="row.origin" /></template>
        </el-table-column>
        <el-table-column label="生产厂家" min-width="120">
          <template #default="{ row }"><el-input v-model="row.manufacturer" /></template>
        </el-table-column>
        <el-table-column label="数量" width="110">
          <template #default="{ row }"><el-input-number v-model="row.qty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="单价" width="120">
          <template #default="{ row }"><el-input-number v-model="row.price" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="金额" width="100">
          <template #default="{ row }">{{ calcAmount(row) }}</template>
        </el-table-column>
        <el-table-column label="收货数量" width="110">
          <template #default="{ row }"><el-input-number v-model="row.receivedQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="收货金额" width="110">
          <template #default="{ row }"><el-input-number v-model="row.receivedAmount" :min="0" :precision="2" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="收货拒收数量" width="120">
          <template #default="{ row }"><el-input-number v-model="row.receivedRejectQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="收货拒收金额" width="120">
          <template #default="{ row }"><el-input-number v-model="row.receivedRejectAmount" :min="0" :precision="2" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="源单据类型" width="110">
          <template #default="{ row }"><el-input v-model="row.sourceType" /></template>
        </el-table-column>
        <el-table-column label="源单号" width="120">
          <template #default="{ row }"><el-input v-model="row.sourceOrderNo" /></template>
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
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pagePurchaseOrder, getPurchaseOrder, createPurchaseOrder, updatePurchaseOrder, deletePurchaseOrder, postPurchaseOrder } from '@/api/purchaseOrder'

defineOptions({ name: 'pms-purchase-order' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', status: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()

const createEmptyItem = () => ({
  drugCode: '', genericName: '', drugName: '', spec: '', unit: '', origin: '', manufacturer: '',
  qty: 0, price: 0, receivedQty: 0, receivedAmount: 0, receivedRejectQty: 0, receivedRejectAmount: 0,
  sourceType: '', sourceOrderNo: '', remark: ''
})
const createEmptyForm = () => ({
  id: null, orderNo: '', supplierId: null, supplierName: '', supplierSalesman: '',
  orderDate: null, handler: '', purchaser: '', paymentMethod: '', settlementMethod: '',
  preSettlementDate: null, invoiceType: '', deliveryMethod: '', estimatedArrivalTime: null,
  cancelReason: '', cancelNote: '', remark: '', items: [createEmptyItem()]
})
const form = ref(createEmptyForm())

const rules = {
  orderNo: [{ required: true, message: '请输入采购订单号', trigger: 'blur' }],
  supplierName: [{ required: true, message: '请输入供应商名称', trigger: 'blur' }]
}

function calcAmount(row) {
  return ((Number(row.qty) || 0) * (Number(row.price) || 0)).toFixed(2)
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
    const data = await pagePurchaseOrder({ ...query })
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
  const data = await getPurchaseOrder(row.id)
  form.value = {
    id: data.id, orderNo: data.orderNo, supplierId: data.supplierId, supplierName: data.supplierName,
    supplierSalesman: data.supplierSalesman, orderDate: data.orderDate, handler: data.handler,
    purchaser: data.purchaser, paymentMethod: data.paymentMethod, settlementMethod: data.settlementMethod,
    preSettlementDate: data.preSettlementDate, invoiceType: data.invoiceType,
    deliveryMethod: data.deliveryMethod, estimatedArrivalTime: data.estimatedArrivalTime,
    cancelReason: data.cancelReason, cancelNote: data.cancelNote, remark: data.remark,
    items: (data.items && data.items.length ? data.items : [createEmptyItem()]).map((i) => ({
      drugCode: i.drugCode, genericName: i.genericName, drugName: i.drugName, spec: i.spec,
      unit: i.unit, origin: i.origin, manufacturer: i.manufacturer,
      qty: i.qty ?? 0, price: i.price ?? 0, receivedQty: i.receivedQty ?? 0,
      receivedAmount: i.receivedAmount ?? 0, receivedRejectQty: i.receivedRejectQty ?? 0,
      receivedRejectAmount: i.receivedRejectAmount ?? 0, sourceType: i.sourceType,
      sourceOrderNo: i.sourceOrderNo, remark: i.remark
    }))
  }
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updatePurchaseOrder(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createPurchaseOrder(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除采购订单「${row.orderNo}」？`, '提示', { type: 'warning' })
  await deletePurchaseOrder(row.id)
  ElMessage.success('删除成功')
  load()
}

async function handlePost(row) {
  await ElMessageBox.confirm(`确认过账采购订单「${row.orderNo}」？过账后不可修改。`, '提示', { type: 'warning' })
  await postPurchaseOrder(row.id)
  ElMessage.success('过账成功')
  load()
}

const router = useRouter()

function goDetail(row) {
  router.push(`/pms/purchase-order/detail/${row.id}`)
}

onMounted(load)
</script>

<style scoped>
.search-form { margin-bottom: 12px; }
.toolbar { margin-bottom: 12px; }
.pagination { display: flex; justify-content: flex-end; margin-top: 16px; }
.item-header { display: flex; justify-content: space-between; align-items: center; margin: 16px 0 8px; font-weight: 600; }
</style>
