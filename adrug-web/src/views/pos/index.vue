<template>
  <div class="pos-container">
    <!-- 顶部：操作栏 -->
    <header class="pos-header">
      <div class="pos-title">POS收银台</div>
      <div class="pos-header-form">
        <span class="pos-cashier">收银员：{{ cashier || '—' }}　{{ shift }}</span>
      </div>
      <div class="pos-header-actions">
        <el-button class="pos-action-btn" @click="holdOrder">挂单</el-button>
        <el-button class="pos-action-btn" @click="openHoldList">取单</el-button>
        <el-button class="pos-action-btn" @click="shiftHandover">交班</el-button>
      </div>
      <el-button link class="pos-logout" @click="logout">退出登录</el-button>
    </header>

    <!-- 第二行：药品搜索 -->
    <div class="pos-search">
      <div class="pos-drug-search">
        <el-input
          ref="drugInputRef"
          v-model="drugKeyword"
          placeholder="输入药品编码 / 名称 （回车搜索）"
          size="large"
          clearable
          @keyup.enter="searchDrug"
        />
        <el-button type="primary" size="large" @click="searchDrug">药品搜索</el-button>
        <div v-if="drugList.length" class="drug-dropdown">
          <div v-for="drug in drugList" :key="drug.id" class="drug-item" @click="addDrug(drug)">
            <div class="drug-name">{{ drug.drugName }}</div>
            <div class="drug-sub">{{ drug.drugCode }} · {{ drug.spec }} · {{ drug.unit }}</div>
            <div class="drug-price">￥{{ formatMoney(drug.retailPrice) }}</div>
          </div>
        </div>
      </div>

      <div class="pos-member-search">
        <el-input v-model="memberKeyword" placeholder="姓名 / 手机号 / 卡号  （回车搜索）" clearable size="large" @keyup.enter="searchMember" />
        <el-button type="primary" size="large" @click="searchMember">会员搜索</el-button>
        <div v-if="memberList.length" class="member-dropdown">
          <div v-for="m in memberList" :key="m.id" class="member-item" @click="selectMember(m)">
            {{ m.name }}（{{ m.memberNo || m.phone }}）
          </div>
        </div>
      </div>
    </div>

    <!-- 中间：药品明细 -->
    <main class="pos-cart">
      <el-table :data="cart" border size="small" height="100%">
        <el-table-column type="index" label="#" width="48" />
        <el-table-column prop="drugCode" label="编码" width="110" />
        <el-table-column prop="drugName" label="商品名称" min-width="160" />
        <el-table-column prop="spec" label="规格" width="90" />
        <el-table-column prop="unit" label="单位" width="64" />
        <el-table-column label="数量" width="130">
          <template #default="{ row }">
            <el-input-number v-model="row.qty" :min="0" :precision="4" :controls="false" size="small" style="width: 100%" />
          </template>
        </el-table-column>
        <el-table-column label="单价" width="130">
          <template #default="{ row }">
            <el-input-number v-model="row.price" :min="0" :precision="4" :controls="false" size="small" style="width: 100%" />
          </template>
        </el-table-column>
        <el-table-column label="优惠金额" width="120">
          <template #default="{ row }">
            <el-input-number v-model="row.discountAmount" :min="0" :precision="2" :controls="false" size="small" style="width: 100%" />
          </template>
        </el-table-column>
        <el-table-column label="应收金额" width="110">
          <template #default="{ row }">{{ formatMoney(calcReceivable(row)) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="70" fixed="right">
          <template #default="{ $index }"><el-button link type="danger" @click="removeItem($index)">删除</el-button></template>
        </el-table-column>
      </el-table>
    </main>

    <!-- 底部：结算 -->
    <footer class="pos-footer">
      <div class="footer-content">
        <div class="settle-inputs">
          <span>收款</span><el-input-number v-model="cashReceived" :min="0" :precision="2" :controls="false" style="width: 110px" />
          <span class="total-item">数量 <b>{{ totalQty }}</b></span>
        </div>
        <div class="footer-member-info">
          <span class="member-info-text">会员 <b class="member-info-val">{{ selectedMember ? selectedMember.name : '非会员' }}</b></span>
          <el-button v-if="selectedMember" link type="danger" @click="clearMember">清除</el-button>
        </div>
        <div class="footer-member-discount">
          <span class="member-info-text">会员折扣 <b class="member-info-val">{{ formatDiscount(selectedMember ? selectedMember.discountRate : null) }}</b></span>
        </div>
        <div class="settle-totals">
          <div class="total-item">优惠 <b>￥{{ formatMoney(discountAmount) }}</b></div>
          <div class="total-item total-amount">应收 <b>￥{{ formatMoney(discountedAmount) }}</b></div>
          <div class="total-item">找零 <b>￥{{ formatMoney(changeAmount) }}</b></div>
        </div>
      </div>
      <div class="settle-buttons">
        <el-button size="large" @click="clearAll">清空</el-button>
        <el-button size="large" type="primary" :loading="settling" @click="checkout">结算</el-button>
      </div>
    </footer>

    <el-dialog v-model="holdDialogVisible" title="取单" width="560px">
      <el-table :data="heldOrders" border size="small">
        <el-table-column label="挂单号" width="120">
          <template #default="{ row }">{{ row.id }}</template>
        </el-table-column>
        <el-table-column label="商品数" width="80">
          <template #default="{ row }">{{ row.items.length }}</template>
        </el-table-column>
        <el-table-column label="会员">
          <template #default="{ row }">{{ row.member ? row.member.name : '—' }}</template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link type="primary" @click="restoreHold(row)">取单</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { searchDrugPage, searchMemberPage, createPosRetailOrder } from '@/api/pos'

defineOptions({ name: 'pos' })

const router = useRouter()
const userStore = useUserStore()

const cashier = computed(() => userStore.userInfo?.username || '')

// 收银台参数
const shift = ref('早班')

// 挂单（内存态，骨架阶段）
const heldOrders = ref([])
const holdDialogVisible = ref(false)

// 商品搜索
const drugInputRef = ref()
const drugKeyword = ref('')
const drugList = ref([])
const drugLoading = ref(false)

// 购物车
const cart = ref([])

// 会员
const memberKeyword = ref('')
const memberList = ref([])
const selectedMember = ref(null)

// 结算
const cashReceived = ref(0)
const settling = ref(false)

const totalQty = computed(() => cart.value.reduce((s, i) => s + (Number(i.qty) || 0), 0))
const discountAmount = computed(() => cart.value.reduce((s, i) => s + (Number(i.discountAmount) || 0), 0))
const discountedAmount = computed(() => cart.value.reduce((s, i) => s + calcReceivable(i), 0))
const changeAmount = computed(() => Number(cashReceived.value) - discountedAmount.value)

function formatMoney(val) {
  return (Number(val) || 0).toFixed(2)
}

function formatDiscount(rate) {
  const v = rate == null || rate === '' ? 0 : Number(rate)
  return (v * 100).toFixed(2) + '%'
}

function calcAmount(row) {
  return (Number(row.qty) || 0) * (Number(row.price) || 0)
}

function calcReceivable(row) {
  return calcAmount(row) - (Number(row.discountAmount) || 0)
}

async function searchDrug() {
  if (!drugKeyword.value.trim()) return
  drugLoading.value = true
  try {
    const data = await searchDrugPage({ keyword: drugKeyword.value.trim(), pageNum: 1, pageSize: 20 })
    drugList.value = data.records || []
  } finally {
    drugLoading.value = false
  }
}

function addDrug(drug) {
  const exist = cart.value.find((i) => i.drugId === drug.id)
  if (exist) {
    exist.qty = (Number(exist.qty) || 0) + 1
  } else {
    cart.value.push({
      drugId: drug.id,
      drugCode: drug.drugCode,
      genericName: drug.genericName,
      drugName: drug.drugName,
      unit: drug.unit,
      spec: drug.spec,
      origin: drug.origin,
      qty: 1,
      price: drug.retailPrice ?? 0,
      discountRate: null,
      discountAmount: 0,
      discountedPrice: drug.retailPrice ?? 0,
      remark: ''
    })
  }
  // 加入后清空搜索，便于下一次扫码
  drugKeyword.value = ''
  drugList.value = []
  drugInputRef.value?.focus()
}

function removeItem(index) {
  cart.value.splice(index, 1)
}

async function searchMember() {
  if (!memberKeyword.value.trim()) return
  const data = await searchMemberPage({ keyword: memberKeyword.value.trim(), pageNum: 1, pageSize: 10 })
  memberList.value = data.records || []
}

function selectMember(m) {
  selectedMember.value = m
  memberList.value = []
  memberKeyword.value = ''
}

function clearMember() {
  selectedMember.value = null
}

function clearAll() {
  cart.value = []
  selectedMember.value = null
  memberList.value = []
  cashReceived.value = 0
  drugKeyword.value = ''
  drugList.value = []
  drugInputRef.value?.focus()
}

function holdOrder() {
  if (!cart.value.length) {
    ElMessage.warning('购物车为空，无法挂单')
    return
  }
  heldOrders.value.push({
    id: Date.now(),
    items: cart.value.map((i) => ({ ...i })),
    member: selectedMember.value ? { ...selectedMember.value } : null
  })
  clearAll()
  ElMessage.success('已挂单')
}

function openHoldList() {
  if (!heldOrders.value.length) {
    ElMessage.info('暂无挂单')
    return
  }
  holdDialogVisible.value = true
}

function restoreHold(h) {
  cart.value = h.items.map((i) => ({ ...i }))
  selectedMember.value = h.member ? { ...h.member } : null
  heldOrders.value = heldOrders.value.filter((x) => x.id !== h.id)
  holdDialogVisible.value = false
  ElMessage.success('取单成功')
}

function shiftHandover() {
  ElMessageBox.confirm('确认交班？交班后将清空当前购物车与挂单。', '交班', { type: 'warning' })
    .then(() => {
      clearAll()
      heldOrders.value = []
      ElMessage.success('交班成功')
    })
    .catch(() => {})
}

function genOrderNo() {
  const d = new Date()
  const pad = (n) => String(n).padStart(2, '0')
  const ts = `${d.getFullYear()}${pad(d.getMonth() + 1)}${pad(d.getDate())}${pad(d.getHours())}${pad(d.getMinutes())}${pad(d.getSeconds())}`
  return `POS${ts}${Math.floor(Math.random() * 900 + 100)}`
}

async function checkout() {
  if (!cart.value.length) {
    ElMessage.warning('购物车为空')
    return
  }
  await ElMessageBox.confirm(`确认结算本单？应收 ￥${formatMoney(discountedAmount.value)}`, '结算', { type: 'warning' })

  const now = new Date()
  const pad = (n) => String(n).padStart(2, '0')
  const billDate = `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())}`
  const businessTime = `${billDate} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`

  const payload = {
    orderNo: genOrderNo(),
    billType: '零售',
    billDate,
    saleType: '零售',
    posSerialNo: genOrderNo(),
    warehouseName: '',
    businessTime,
    posNo: '',
    shift: shift.value,
    pointsPrinted: 0,
    cashier: cashier.value,
    salesman: '',
    discountAmount: discountAmount.value,
    wholeDiscountRate: null,
    changeAmount: changeAmount.value,
    walletAmount: 0,
    roundAmount: 0,
    manualRoundAmount: 0,
    memberId: selectedMember.value?.id ?? null,
    memberName: selectedMember.value?.name ?? null,
    memberIdCardNo: selectedMember.value?.idCardNo ?? null,
    memberPhone: selectedMember.value?.phone ?? null,
    posSourceOrderNo: '',
    summary: '',
    businessPlatform: 'web',
    retailPriceType: '零售价',
    remark: '',
    items: cart.value.map((i) => ({
      drugId: i.drugId,
      drugCode: i.drugCode,
      genericName: i.genericName,
      drugName: i.drugName,
      unit: i.unit,
      spec: i.spec,
      origin: i.origin,
      batchNo: i.batchNo,
      productionDate: i.productionDate,
      expiryDate: i.expiryDate,
      qty: i.qty,
      price: i.price,
      amount: calcAmount(i),
      discountRate: i.discountRate,
      discountAmount: i.discountAmount,
      discountedPrice: i.discountedPrice,
      receivableAmount: calcReceivable(i),
      remark: i.remark
    }))
  }

  settling.value = true
  try {
    await createPosRetailOrder(payload)
    ElMessage.success(`结算成功：${payload.orderNo}`)
    clearAll()
  } finally {
    settling.value = false
  }
}

function logout() {
  userStore.logout()
  router.push('/login')
}

onMounted(() => {
  drugInputRef.value?.focus()
})
</script>

<style scoped>
.pos-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f0f2f5;
}

/* 顶部操作栏 */
.pos-header {
  height: 56px;
  background: #001529;
  color: #fff;
  display: flex;
  align-items: center;
  padding: 0 16px;
  gap: 24px;
  flex-shrink: 0;
}
.pos-title {
  font-size: 20px;
  font-weight: 700;
  letter-spacing: 1px;
}
.pos-header-form {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  flex: 1;
}
.pos-header-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}
.pos-cashier {
  color: #e6a23c;
  font-size: 14px;
}
.pos-action-btn {
  background-color: #334155;
  border-color: #475569;
  color: #fff;
}
.pos-action-btn:hover,
.pos-action-btn:focus {
  background-color: #475569;
  border-color: #64748b;
  color: #fff;
}
.pos-logout {
  color: #fff;
}

/* 第二行：药品搜索 */
.pos-search {
  position: relative;
  display: flex;
  gap: 16px;
  padding: 12px;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  flex-shrink: 0;
}
.pos-drug-search {
  position: relative;
  flex: 1;
  display: flex;
  gap: 8px;
  min-width: 0;
}
.pos-drug-search :deep(.el-input) {
  flex: 1;
}
.drug-dropdown {
  position: absolute;
  top: calc(100% + 8px);
  left: 0;
  width: 100%;
  max-height: 320px;
  overflow: auto;
  background: #fff;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
  z-index: 100;
}
.drug-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 14px;
  cursor: pointer;
  border-bottom: 1px solid #f0f2f5;
}
.drug-item:hover {
  background: #ecf5ff;
}
.drug-name {
  font-weight: 600;
  min-width: 160px;
}
.drug-sub {
  color: #909399;
  font-size: 12px;
  flex: 1;
  margin: 0 12px;
}
.drug-price {
  color: #e6a23c;
  font-weight: 700;
}

/* 中间：药品明细 */
.pos-cart {
  flex: 1;
  padding: 12px;
  overflow: hidden;
}
.pos-cart .el-table {
  background: #fff;
  border-radius: 4px;
}

/* 底部：结算及会员信息 */
.pos-footer {
  height: 120px;
  background: #fff;
  border-top: 1px solid #e8e8e8;
  display: flex;
  align-items: center;
  padding: 0 16px;
  gap: 24px;
  flex-shrink: 0;
}
.footer-member-info {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
  white-space: nowrap;
}
.footer-member-discount {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  white-space: nowrap;
}
.member-info-text {
  color: #606266;
  font-size: 14px;
}
.member-info-val {
  color: #303133;
  font-weight: 700;
}
.pos-member-search {
  position: relative;
  flex: 1;
  display: flex;
  align-items: center;
  gap: 8px;
  min-width: 0;
}
.pos-member-search :deep(.el-input) {
  flex: 1;
}
.member-label {
  font-weight: 600;
  white-space: nowrap;
}
.member-dropdown {
  position: absolute;
  top: 40px;
  right: 0;
  width: 320px;
  max-height: 200px;
  overflow: auto;
  background: #fff;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
  z-index: 100;
}
.member-item {
  padding: 8px 12px;
  cursor: pointer;
}
.member-item:hover {
  background: #ecf5ff;
}
.member-selected {
  color: #67c23a;
  font-size: 13px;
  white-space: nowrap;
}

.footer-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
}
.settle-inputs {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #606266;
  font-size: 13px;
  flex-shrink: 0;
}
.settle-totals {
  display: flex;
  align-items: center;
  gap: 20px;
  flex-shrink: 0;
}
.total-item {
  color: #606266;
  font-size: 13px;
  white-space: nowrap;
}
.total-item b {
  display: inline;
  margin-left: 6px;
  font-size: 16px;
  color: #303133;
}
.total-amount b {
  color: #f56c6c;
  font-size: 24px;
}
.settle-buttons {
  display: flex;
  gap: 12px;
  flex-shrink: 0;
}
.settle-buttons .el-button {
  min-width: 88px;
}
</style>
