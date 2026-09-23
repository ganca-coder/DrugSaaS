<template>
  <div class="page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>采购订单详情</span>
          <el-button @click="goBack">返回</el-button>
        </div>
      </template>

      <el-descriptions :column="3" border>
        <el-descriptions-item label="采购订单号">{{ order.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="供应商">{{ order.supplierName }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="order.status === 1 ? 'success' : 'info'">{{ order.statusDesc }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="供应商业务员">{{ order.supplierSalesman }}</el-descriptions-item>
        <el-descriptions-item label="经手人">{{ order.handler }}</el-descriptions-item>
        <el-descriptions-item label="采购员">{{ order.purchaser }}</el-descriptions-item>
        <el-descriptions-item label="下单日期">{{ order.orderDate }}</el-descriptions-item>
        <el-descriptions-item label="付款方式">{{ order.paymentMethod }}</el-descriptions-item>
        <el-descriptions-item label="结算方式">{{ order.settlementMethod }}</el-descriptions-item>
        <el-descriptions-item label="预结算日期">{{ order.preSettlementDate }}</el-descriptions-item>
        <el-descriptions-item label="发票类型">{{ order.invoiceType }}</el-descriptions-item>
        <el-descriptions-item label="送货方式">{{ order.deliveryMethod }}</el-descriptions-item>
        <el-descriptions-item label="预计到货时间">{{ order.estimatedArrivalTime }}</el-descriptions-item>
        <el-descriptions-item label="采购数量合计">{{ order.totalQty }}</el-descriptions-item>
        <el-descriptions-item label="采购金额合计">{{ order.totalAmount }}</el-descriptions-item>
        <el-descriptions-item label="过账时间">{{ order.postTime }}</el-descriptions-item>
        <el-descriptions-item label="取消理由">{{ order.cancelReason }}</el-descriptions-item>
        <el-descriptions-item label="取消说明">{{ order.cancelNote }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="3">{{ order.remark }}</el-descriptions-item>
      </el-descriptions>

      <div class="item-header">采购明细</div>
      <el-table :data="order.items || []" border stripe>
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="drugCode" label="药品编码" width="120" />
        <el-table-column prop="genericName" label="通用药品名称" min-width="120" />
        <el-table-column prop="drugName" label="药品名称" min-width="140" />
        <el-table-column prop="spec" label="规格" width="90" />
        <el-table-column prop="unit" label="单位" width="70" />
        <el-table-column prop="origin" label="产地" min-width="100" />
        <el-table-column prop="manufacturer" label="生产厂家" min-width="120" />
        <el-table-column prop="qty" label="数量" width="100" />
        <el-table-column prop="price" label="单价" width="100" />
        <el-table-column prop="amount" label="金额" width="100" />
        <el-table-column prop="receivedQty" label="收货数量" width="100" />
        <el-table-column prop="receivedAmount" label="收货金额" width="100" />
        <el-table-column prop="receivedRejectQty" label="收货拒收数量" width="110" />
        <el-table-column prop="receivedRejectAmount" label="收货拒收金额" width="110" />
        <el-table-column prop="sourceType" label="源单据类型" width="100" />
        <el-table-column prop="sourceOrderNo" label="源单号" width="110" />
        <el-table-column prop="remark" label="备注" min-width="100" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getPurchaseOrder } from '@/api/purchaseOrder'

defineOptions({ name: 'pms-purchase-order-detail' })

const route = useRoute()
const router = useRouter()

const order = ref({})

function goBack() {
  router.back()
}

onMounted(async () => {
  order.value = await getPurchaseOrder(route.params.id)
})
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.item-header { margin: 20px 0 10px; font-weight: 600; }
</style>
