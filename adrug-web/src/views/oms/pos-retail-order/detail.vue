<template>
  <div class="page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>POS零售单详情</span>
          <el-button @click="goBack">返回</el-button>
        </div>
      </template>

      <el-descriptions :column="3" border>
        <el-descriptions-item label="单据编号">{{ order.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="单据类型">{{ order.billType }}</el-descriptions-item>
        <el-descriptions-item label="单据日期">{{ order.billDate }}</el-descriptions-item>
        <el-descriptions-item label="销售类型">{{ order.saleType }}</el-descriptions-item>
        <el-descriptions-item label="POS流水号">{{ order.posSerialNo }}</el-descriptions-item>
        <el-descriptions-item label="仓库">{{ order.warehouseName }}</el-descriptions-item>
        <el-descriptions-item label="营业时间">{{ order.businessTime }}</el-descriptions-item>
        <el-descriptions-item label="POS编号">{{ order.posNo }}</el-descriptions-item>
        <el-descriptions-item label="班次">{{ order.shift }}</el-descriptions-item>
        <el-descriptions-item label="积分已打印">
          <el-tag :type="order.pointsPrinted === 1 ? 'success' : 'info'">{{ order.pointsPrintedDesc }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="收银员">{{ order.cashier }}</el-descriptions-item>
        <el-descriptions-item label="营业员">{{ order.salesman }}</el-descriptions-item>
        <el-descriptions-item label="合计数量">{{ order.totalQty }}</el-descriptions-item>
        <el-descriptions-item label="折后金额">{{ order.discountedAmount }}</el-descriptions-item>
        <el-descriptions-item label="优惠金额">{{ order.discountAmount }}</el-descriptions-item>
        <el-descriptions-item label="整单折扣率">{{ order.wholeDiscountRate }}</el-descriptions-item>
        <el-descriptions-item label="找零金额">{{ order.changeAmount }}</el-descriptions-item>
        <el-descriptions-item label="零钱包金额">{{ order.walletAmount }}</el-descriptions-item>
        <el-descriptions-item label="抹零金额">{{ order.roundAmount }}</el-descriptions-item>
        <el-descriptions-item label="手工抹零金额">{{ order.manualRoundAmount }}</el-descriptions-item>
        <el-descriptions-item label="会员">{{ order.memberName }}</el-descriptions-item>
        <el-descriptions-item label="会员身份证号">{{ order.memberIdCardNo }}</el-descriptions-item>
        <el-descriptions-item label="会员手机号">{{ order.memberPhone }}</el-descriptions-item>
        <el-descriptions-item label="POS来源单号">{{ order.posSourceOrderNo }}</el-descriptions-item>
        <el-descriptions-item label="摘要">{{ order.summary }}</el-descriptions-item>
        <el-descriptions-item label="业务平台">{{ order.businessPlatform }}</el-descriptions-item>
        <el-descriptions-item label="零售价类型">{{ order.retailPriceType }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="3">{{ order.remark }}</el-descriptions-item>
      </el-descriptions>

      <div class="item-header">零售明细</div>
      <el-table :data="order.items || []" border stripe>
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="drugCode" label="商品编码" width="120" />
        <el-table-column prop="genericName" label="通用名称" min-width="120" />
        <el-table-column prop="drugName" label="商品名称" min-width="140" />
        <el-table-column prop="unit" label="单位" width="70" />
        <el-table-column prop="spec" label="规格" width="90" />
        <el-table-column prop="origin" label="产地" min-width="100" />
        <el-table-column prop="batchNo" label="批号" width="110" />
        <el-table-column prop="productionDate" label="生产日期" width="110" />
        <el-table-column prop="expiryDate" label="有效期" width="110" />
        <el-table-column prop="qty" label="数量" width="90" />
        <el-table-column prop="price" label="单价" width="90" />
        <el-table-column prop="amount" label="金额" width="90" />
        <el-table-column prop="discountRate" label="折扣率" width="90" />
        <el-table-column prop="discountAmount" label="优惠金额" width="100" />
        <el-table-column prop="discountedPrice" label="折后单价" width="100" />
        <el-table-column prop="receivableAmount" label="应收金额" width="100" />
        <el-table-column prop="remark" label="备注" min-width="100" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getPosRetailOrder } from '@/api/posRetailOrder'

defineOptions({ name: 'oms-pos-retail-order-detail' })

const route = useRoute()
const router = useRouter()

const order = ref({})

function goBack() {
  router.back()
}

onMounted(async () => {
  order.value = await getPosRetailOrder(route.params.id)
})
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.item-header { margin: 20px 0 10px; font-weight: 600; }
</style>
