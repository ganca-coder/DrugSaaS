<template>
  <div class="page">
    <el-card shadow="never">
      <template #header><span>POS零售单</span></template>

      <el-form :inline="true" :model="query" class="search-form" @submit.prevent>
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="单号/会员姓名/手机号" clearable style="width: 200px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="单据日期">
          <el-date-picker v-model="dateRange" type="daterange" value-format="YYYY-MM-DD" start-placeholder="开始" end-placeholder="结束" style="width: 240px" />
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
        <el-table-column prop="orderNo" label="单据编号" width="140" />
        <el-table-column prop="billType" label="单据类型" width="90" />
        <el-table-column prop="billDate" label="单据日期" width="110" />
        <el-table-column prop="posSerialNo" label="POS流水号" width="120" />
        <el-table-column prop="warehouseName" label="仓库" width="120" />
        <el-table-column prop="cashier" label="收银员" width="90" />
        <el-table-column prop="memberName" label="会员" width="90" />
        <el-table-column prop="totalQty" label="合计数量" width="90" />
        <el-table-column prop="discountedAmount" label="折后金额" width="100" />
        <el-table-column label="积分已打印" width="100">
          <template #default="{ row }">
            <el-tag :type="row.pointsPrinted === 1 ? 'success' : 'info'">{{ row.pointsPrintedDesc }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button link type="info" @click="goDetail(row)">详情</el-button>
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

    <el-dialog v-model="dialog.visible" :title="dialog.isEdit ? '编辑POS零售单' : '新增POS零售单'" width="96%" top="3vh">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="6"><el-form-item label="单据编号" prop="orderNo"><el-input v-model="form.orderNo" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="单据类型"><el-input v-model="form.billType" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="单据日期"><el-date-picker v-model="form.billDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="销售类型"><el-input v-model="form.saleType" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="POS流水号"><el-input v-model="form.posSerialNo" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="仓库"><el-input v-model="form.warehouseName" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="营业时间"><el-date-picker v-model="form.businessTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="POS编号"><el-input v-model="form.posNo" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="班次"><el-input v-model="form.shift" /></el-form-item></el-col>
          <el-col :span="6">
            <el-form-item label="积分已打印">
              <el-select v-model="form.pointsPrinted" style="width: 100%">
                <el-option label="否" :value="0" />
                <el-option label="是" :value="1" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6"><el-form-item label="收银员"><el-input v-model="form.cashier" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="营业员"><el-input v-model="form.salesman" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="优惠金额"><el-input-number v-model="form.discountAmount" :min="0" :precision="2" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="整单折扣率"><el-input-number v-model="form.wholeDiscountRate" :min="0" :precision="4" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="找零金额"><el-input-number v-model="form.changeAmount" :min="0" :precision="2" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="零钱包金额"><el-input-number v-model="form.walletAmount" :min="0" :precision="2" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="抹零金额"><el-input-number v-model="form.roundAmount" :min="0" :precision="2" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="手工抹零金额"><el-input-number v-model="form.manualRoundAmount" :min="0" :precision="2" :controls="false" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="会员"><el-input v-model="form.memberName" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="会员身份证号"><el-input v-model="form.memberIdCardNo" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="会员手机号"><el-input v-model="form.memberPhone" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="POS来源单号"><el-input v-model="form.posSourceOrderNo" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="摘要"><el-input v-model="form.summary" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="业务平台"><el-input v-model="form.businessPlatform" /></el-form-item></el-col>
          <el-col :span="6"><el-form-item label="零售价类型"><el-input v-model="form.retailPriceType" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item></el-col>
        </el-row>
      </el-form>

      <div class="item-header">
        <span>零售明细</span>
        <el-button type="primary" link @click="addItem">+ 新增明细</el-button>
      </div>
      <el-table :data="form.items" border size="small" max-height="360">
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
        <el-table-column label="单位" width="70">
          <template #default="{ row }"><el-input v-model="row.unit" /></template>
        </el-table-column>
        <el-table-column label="规格" width="90">
          <template #default="{ row }"><el-input v-model="row.spec" /></template>
        </el-table-column>
        <el-table-column label="产地" min-width="110">
          <template #default="{ row }"><el-input v-model="row.origin" /></template>
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
        <el-table-column label="数量" width="110">
          <template #default="{ row }"><el-input-number v-model="row.qty" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="单价" width="120">
          <template #default="{ row }"><el-input-number v-model="row.price" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="金额" width="100">
          <template #default="{ row }">{{ calcAmount(row) }}</template>
        </el-table-column>
        <el-table-column label="折扣率" width="110">
          <template #default="{ row }"><el-input-number v-model="row.discountRate" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="优惠金额" width="120">
          <template #default="{ row }"><el-input-number v-model="row.discountAmount" :min="0" :precision="2" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="折后单价" width="120">
          <template #default="{ row }"><el-input-number v-model="row.discountedPrice" :min="0" :precision="4" :controls="false" style="width: 100%" /></template>
        </el-table-column>
        <el-table-column label="应收金额" width="110">
          <template #default="{ row }">{{ calcReceivable(row) }}</template>
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
import { pagePosRetailOrder, getPosRetailOrder, createPosRetailOrder, updatePosRetailOrder, deletePosRetailOrder } from '@/api/posRetailOrder'

defineOptions({ name: 'oms-pos-retail-order' })

const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 20, keyword: '', billDateStart: null, billDateEnd: null })
const dateRange = ref(null)

const dialog = reactive({ visible: false, isEdit: false, saving: false })
const formRef = ref()

const createEmptyItem = () => ({
  drugCode: '', genericName: '', drugName: '', unit: '', spec: '', origin: '',
  batchNo: '', productionDate: null, expiryDate: null, qty: 0, price: 0,
  discountRate: null, discountAmount: 0, discountedPrice: null, remark: ''
})
const createEmptyForm = () => ({
  id: null, orderNo: '', billType: '', billDate: null, saleType: '',
  posSerialNo: '', warehouseName: '', businessTime: null, posNo: '', shift: '',
  pointsPrinted: 0, cashier: '', salesman: '', discountAmount: 0, wholeDiscountRate: null,
  changeAmount: 0, walletAmount: 0, roundAmount: 0, manualRoundAmount: 0,
  memberName: '', memberIdCardNo: '', memberPhone: '', posSourceOrderNo: '', summary: '',
  businessPlatform: '', retailPriceType: '', remark: '', items: [createEmptyItem()]
})
const form = ref(createEmptyForm())

const rules = {
  orderNo: [{ required: true, message: '请输入单据编号', trigger: 'blur' }]
}

function calcAmount(row) {
  return ((Number(row.qty) || 0) * (Number(row.price) || 0)).toFixed(2)
}

function calcReceivable(row) {
  return ((Number(row.qty) || 0) * (Number(row.price) || 0) - (Number(row.discountAmount) || 0)).toFixed(2)
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
    if (dateRange.value && dateRange.value.length === 2) {
      query.billDateStart = dateRange.value[0]
      query.billDateEnd = dateRange.value[1]
    } else {
      query.billDateStart = null
      query.billDateEnd = null
    }
    const data = await pagePosRetailOrder({ ...query })
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
  dateRange.value = null
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
  const data = await getPosRetailOrder(row.id)
  form.value = {
    id: data.id, orderNo: data.orderNo, billType: data.billType, billDate: data.billDate,
    saleType: data.saleType, posSerialNo: data.posSerialNo, warehouseName: data.warehouseName,
    businessTime: data.businessTime, posNo: data.posNo, shift: data.shift,
    pointsPrinted: data.pointsPrinted ?? 0, cashier: data.cashier, salesman: data.salesman,
    discountAmount: data.discountAmount ?? 0, wholeDiscountRate: data.wholeDiscountRate,
    changeAmount: data.changeAmount ?? 0, walletAmount: data.walletAmount ?? 0,
    roundAmount: data.roundAmount ?? 0, manualRoundAmount: data.manualRoundAmount ?? 0,
    memberName: data.memberName, memberIdCardNo: data.memberIdCardNo, memberPhone: data.memberPhone,
    posSourceOrderNo: data.posSourceOrderNo, summary: data.summary,
    businessPlatform: data.businessPlatform, retailPriceType: data.retailPriceType, remark: data.remark,
    items: (data.items && data.items.length ? data.items : [createEmptyItem()]).map((i) => ({
      drugCode: i.drugCode, genericName: i.genericName, drugName: i.drugName, unit: i.unit,
      spec: i.spec, origin: i.origin, batchNo: i.batchNo, productionDate: i.productionDate,
      expiryDate: i.expiryDate, qty: i.qty ?? 0, price: i.price ?? 0,
      discountRate: i.discountRate, discountAmount: i.discountAmount ?? 0,
      discountedPrice: i.discountedPrice, remark: i.remark
    }))
  }
}

async function handleSave() {
  await formRef.value.validate()
  dialog.saving = true
  try {
    if (dialog.isEdit) {
      await updatePosRetailOrder(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await createPosRetailOrder(form.value)
      ElMessage.success('新增成功')
    }
    dialog.visible = false
    load()
  } finally {
    dialog.saving = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除POS零售单「${row.orderNo}」？`, '提示', { type: 'warning' })
  await deletePosRetailOrder(row.id)
  ElMessage.success('删除成功')
  load()
}

const router = useRouter()

function goDetail(row) {
  router.push(`/oms/pos-retail-order/detail/${row.id}`)
}

onMounted(load)
</script>

<style scoped>
.search-form { margin-bottom: 12px; }
.toolbar { margin-bottom: 12px; }
.pagination { display: flex; justify-content: flex-end; margin-top: 16px; }
.item-header { display: flex; justify-content: space-between; align-items: center; margin: 16px 0 8px; font-weight: 600; }
</style>
