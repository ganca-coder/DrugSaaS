<template>
  <div class="page">
    <el-card shadow="never">
      <template #header><span>采购入库单</span></template>

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
        <el-table-column prop="orderNo" label="入库单号" width="150" />
        <el-table-column prop="supplierName" label="供应商" min-width="140" />
        <el-table-column prop="warehouseName" label="入库仓库" min-width="120" />
        <el-table-column prop="handler" label="经手人" width="90" />
        <el-table-column prop="invoiceNo" label="发票号码" width="130" />
        <el-table-column prop="inboundDate" label="入库日期" width="110" />
        <el-table-column prop="totalQty" label="数量合计" width="100" />
        <el-table-column prop="totalAmount" label="金额合计" width="120" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.statusDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openView(row)">查看</el-button>
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑采购入库单' : '新增采购入库单'" width="96%" top="4vh">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="12">
          <el-col :span="8"><el-form-item label="入库单号" prop="orderNo"><el-input v-model="form.orderNo" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商名称" prop="supplierName"><el-input v-model="form.supplierName" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商ID"><el-input-number v-model="form.supplierId" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="入库仓库" prop="warehouseName"><el-input v-model="form.warehouseName" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="仓库ID"><el-input-number v-model="form.warehouseId" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发票类型"><el-input v-model="form.invoiceType" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发票号码"><el-input v-model="form.invoiceNo" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="经手人"><el-input v-model="form.handler" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="供应商业务员"><el-input v-model="form.supplierSalesman" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="付款方式"><el-input v-model="form.paymentMethod" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="结算方式"><el-input v-model="form.settlementMethod" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="预结算日期"><el-date-picker v-model="form.preSettlementDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="来货单号"><el-input v-model="form.arrivalNo" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="验收人"><el-input v-model="form.acceptor" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="来源验收单ID"><el-input-number v-model="form.sourceOrderId" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="入库日期"><el-date-picker v-model="form.inboundDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item></el-col>
        </el-row>
      </el-form>

      <div class="item-header">
        <span>入库明细</span>
        <el-button type="primary" link @click="addItem">+ 新增明细</el-button>
      </div>
      <el-table :data="form.items" border size="small">
        <el-table-column label="序号" width="60" fixed="left">
          <template #default="{ $index }">{{ $index + 1 }}</template>
        </el-table-column>
        <el-table-column label="商品ID" width="100">
          <template #default="{ row }"><el-input-number v-model="row.drugId" :controls="false" style="width: 100%" /></template>
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
        <el-table-column label="入库数量" width="110">
          <template #default="{ row }"><el-input-number v-model="row.inboundQty" :min="0" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="单价" width="110">
          <template #default="{ row }"><el-input-number v-model="row.price" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="金额" width="100">
          <template #default="{ row }">{{ calcAmount(row) }}</template>
        </el-table-column>
        <el-table-column label="零售价" width="110">
          <template #default="{ row }"><el-input-number v-model="row.retailPrice" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="会员价" width="110">
          <template #default="{ row }"><el-input-number v-model="row.memberPrice" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
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

    <el-dialog v-model="view.visible" title="采购入库单详情" width="96%" top="4vh">
      <el-descriptions :column="3" border size="small">
        <el-descriptions-item label="入库单号">{{ view.data?.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="供应商">{{ view.data?.supplierName }}</el-descriptions-item>
        <el-descriptions-item label="入库仓库">{{ view.data?.warehouseName }}</el-descriptions-item>
        <el-descriptions-item label="发票类型">{{ view.data?.invoiceType }}</el-descriptions-item>
        <el-descriptions-item label="发票号码">{{ view.data?.invoiceNo }}</el-descriptions-item>
        <el-descriptions-item label="经手人">{{ view.data?.handler }}</el-descriptions-item>
        <el-descriptions-item label="供应商业务员">{{ view.data?.supplierSalesman }}</el-descriptions-item>
        <el-descriptions-item label="付款方式">{{ view.data?.paymentMethod }}</el-descriptions-item>
        <el-descriptions-item label="结算方式">{{ view.data?.settlementMethod }}</el-descriptions-item>
        <el-descriptions-item label="预结算日期">{{ view.data?.preSettlementDate }}</el-descriptions-item>
        <el-descriptions-item label="来货单号">{{ view.data?.arrivalNo }}</el-descriptions-item>
        <el-descriptions-item label="验收人">{{ view.data?.acceptor }}</el-descriptions-item>
        <el-descriptions-item label="入库日期">{{ view.data?.inboundDate }}</el-descriptions-item>
        <el-descriptions-item label="单据状态">{{ view.data?.statusDesc }}</el-descriptions-item>
        <el-descriptions-item label="数量合计">{{ view.data?.totalQty }}</el-descriptions-item>
        <el-descriptions-item label="金额合计">{{ view.data?.totalAmount }}</el-descriptions-item>
        <el-descriptions-item label="过账时间">{{ view.data?.postTime }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="3">{{ view.data?.remark }}</el-descriptions-item>
      </el-descriptions>

      <div class="item-header"><span>入库明细</span></div>
      <el-table v-loading="view.loading" :data="view.data?.items || []" border size="small">
        <el-table-column label="序号" type="index" width="60" />
        <el-table-column prop="drugCode" label="商品编码" min-width="110" />
        <el-table-column prop="genericName" label="通用名称" min-width="120" />
        <el-table-column prop="drugName" label="商品名称" min-width="130" />
        <el-table-column prop="spec" label="规格" width="90" />
        <el-table-column prop="unit" label="单位" width="70" />
        <el-table-column prop="dosageForm" label="剂型" width="90" />
        <el-table-column prop="origin" label="产地" width="110" />
        <el-table-column prop="manufacturer" label="生产厂家" min-width="120" />
        <el-table-column prop="productionLicenseNo" label="生产许可证凭证号" width="140" />
        <el-table-column prop="marketingHolder" label="上市持有人" min-width="120" />
        <el-table-column prop="approvalNo" label="批准文号" width="110" />
        <el-table-column prop="batchNo" label="批号" width="110" />
        <el-table-column prop="productionDate" label="生产日期" width="110" />
        <el-table-column prop="expiryDate" label="有效期" width="110" />
        <el-table-column prop="inboundQty" label="入库数量" width="100" />
        <el-table-column prop="price" label="单价" width="100" />
        <el-table-column prop="amount" label="金额" width="100" />
        <el-table-column prop="retailPrice" label="零售价" width="100" />
        <el-table-column prop="memberPrice" label="会员价" width="100" />
        <el-table-column prop="remark" label="备注" min-width="120" />
      </el-table>

      <template #footer>
        <el-button @click="view.visible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pagePurchaseInboundOrder, getPurchaseInboundOrder, createPurchaseInboundOrder, updatePurchaseInboundOrder, deletePurchaseInboundOrder, postPurchaseInboundOrder } from '@/api/purchaseInboundOrder'

defineOptions({ name: 'pms-purchase-inbound-order' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', status: null })

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const view = reactive({ visible: false, loading: false, data: null })
const formRef = ref()

const createEmptyItem = () => ({
  drugId: null, drugCode: '', genericName: '', drugName: '', spec: '', unit: '', dosageForm: '', origin: '',
  manufacturer: '', productionLicenseNo: '', marketingHolder: '', marketingHolderAddress: '',
  approvalNo: '', batchNo: '', productionDate: null, expiryDate: null, inboundQty: 0,
  price: 0, amount: null, retailPrice: null, memberPrice: null, remark: ''
})
const createEmptyForm = () => ({
  id: null, orderNo: '', supplierId: null, supplierName: '', warehouseId: null, warehouseName: '',
  invoiceType: '', invoiceNo: '', handler: '', supplierSalesman: '', paymentMethod: '',
  settlementMethod: '', preSettlementDate: null, arrivalNo: '', acceptor: '', sourceOrderId: null,
  inboundDate: null, remark: '', items: [createEmptyItem()]
})
const form = ref(createEmptyForm())

const rules = {
  orderNo: [{ required: true, message: '请输入入库单号', trigger: 'blur' }],
  supplierName: [{ required: true, message: '请输入供应商名称', trigger: 'blur' }],
  warehouseName: [{ required: true, message: '请输入入库仓库', trigger: 'blur' }]
}

function calcAmount(row) {
  return ((Number(row.inboundQty) || 0) * (Number(row.price) || 0)).toFixed(2)
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
    const data = await pagePurchaseInboundOrder({ ...query })
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

async function openView(row) {
  view.visible = true
  view.loading = true
  view.data = null
  try {
    view.data = await getPurchaseInboundOrder(row.id)
  } finally {
    view.loading = false
  }
}

async function openEdit(row) {
  dialog.isEdit = true
  dialog.visible = true
  const data = await getPurchaseInboundOrder(row.id)
  form.value = {
    id: data.id, orderNo: data.orderNo, supplierId: data.supplierId, supplierName: data.supplierName,
    warehouseId: data.warehouseId, warehouseName: data.warehouseName, invoiceType: data.invoiceType,
    invoiceNo: data.invoiceNo, handler: data.handler, supplierSalesman: data.supplierSalesman,
    paymentMethod: data.paymentMethod, settlementMethod: data.settlementMethod,
    preSettlementDate: data.preSettlementDate, arrivalNo: data.arrivalNo, acceptor: data.acceptor,
    sourceOrderId: data.sourceOrderId, inboundDate: data.inboundDate, remark: data.remark,
    items: (data.items && data.items.length ? data.items : [createEmptyItem()]).map((i) => ({
      drugId: i.drugId, drugCode: i.drugCode, genericName: i.genericName, drugName: i.drugName, spec: i.spec, unit: i.unit,
      dosageForm: i.dosageForm, origin: i.origin, manufacturer: i.manufacturer,
      productionLicenseNo: i.productionLicenseNo, marketingHolder: i.marketingHolder,
      marketingHolderAddress: i.marketingHolderAddress, approvalNo: i.approvalNo, batchNo: i.batchNo,
      productionDate: i.productionDate, expiryDate: i.expiryDate, inboundQty: i.inboundQty ?? 0,
      price: i.price ?? 0, amount: i.amount, retailPrice: i.retailPrice, memberPrice: i.memberPrice,
      remark: i.remark
    }))
  }
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updatePurchaseInboundOrder(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createPurchaseInboundOrder(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除采购入库单「${row.orderNo}」？`, '提示', { type: 'warning' })
  await deletePurchaseInboundOrder(row.id)
  ElMessage.success('删除成功')
  load()
}

async function handlePost(row) {
  await ElMessageBox.confirm(`确认过账采购入库单「${row.orderNo}」？过账后不可修改。`, '提示', { type: 'warning' })
  await postPurchaseInboundOrder(row.id)
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
