<template>
  <div class="ucenter-wrap"  v-wechat-title="$t('m.navheader.rechargeRecord')">
    <NavHeader activeTab="4"></NavHeader>

    <div class="ucenter-content">
        <div class="ucenter-row">
            <div class="ucenter-main no-sidebar">
                <div class="ucenter-header">
                    <h3 class="title">{{$t('m.accountAssets.rechargeRecord')}}</h3>
                    <div class="right-bar">
                      <el-select v-model="coinSelectValue" :placeholder="$t('m.accountAssets.coinName')" size="small" @change="onMarketChange">
                        <el-option :label="$t('m.all')" :value="0">{{$t('m.all')}}</el-option>
                        <el-option
                          v-for="item in marketSelectOptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </div>
                </div>
                <div class="ucenter-body">
                  <el-table
                    :data="recordTbData"
                    header-cell-class-name="table-header"
                    slot="empty"
                  >
                    <el-table-column
                      prop="created"
                      :label="$t('m.trade.time')"
                      sortable>
                      >
                    </el-table-column>

                    <el-table-column
                      prop="coinName"
                      :label="$t('m.accountAssets.coinName')"
                      :formatter="formatCoinName"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="address"
                      :label="$t('m.patch.address')"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="num"
                      :label="$t('m.exchange.amount')"
                      align="center"
                      sortable
                    >
                    </el-table-column>

<!--                    <el-table-column
                      prop="fee"
                      :label="$t('m.accountAssets.rechargeFee')"
                      align="center"
                    >
                    </el-table-column>-->
                    <el-table-column
                      prop="mum"
                      :label="$t('m.patch.withdrawMum')"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="status"
                      :label="$t('m.accountAssets.status')"
                      align="center"
                      >
                      <template slot-scope="scope">
                        <span v-if="scope.row.confirm < 1">
                          {{$t(`m.trade.confirm`) + Math.abs(scope.row.confirm) + $t(`m.trade.confirmNumber`)}}
                        </span>
                        <span v-else>
                          {{$t(`m.trade.coinStatus[${scope.row.status}]`)}}
                        </span>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-pagination
                    class="pager-container"
                    background
                    layout="prev, pager, next"
                    :current-page.sync="page"
                    :page-size="rows"
                    :total="totalRecords"
                    @current-change="handlePageChange"
                  >
                  </el-pagination>
                </div>
            </div>
        </div>
    </div>

    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>
  </div>
</template>

<script>
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import financeHeader from 'components/finance/finance-header'
  import { orderApi } from 'api/order'
  import { OK } from 'api/config'
  import { homeApi } from 'api/home'
  import { financeApi } from 'api/finance'
  import { util } from 'common/js/util'
  import { mapGetters } from 'vuex'

  export default {
    data() {
      return {
        totalRecords       : 0,
        // ?????????btc
        coinId             : 0,
        rows               : 10,
        type               : 0,
        page               : 1,
        marketSelectOptions: [],
        coinSelectValue    : '',
        recordTbData       : []
      }
    },
    computed:{
      ...mapGetters(['token'])
    },
    mounted() {
      // ????????????
      this._getBaseCoinList();
      // get the child page
      let id = this.$route.params.id
      console.log("????????????", id);
      if (id != undefined) {
        this.coinId = id
        this.coinSelectValue = id
      }

      this._inWalletRecord();

    },
    methods: {
      formatCoinName(row){
        return row.coinName.toUpperCase()
      },
      formatStatus(row, column){
        return util.rechargeCoinStatusFormat(row.confirm, row.status)
      },
      handlePageChange(currentPage){
        console.log("handlePageChange:", currentPage);
        this.page = currentPage
        this._inWalletRecord()
      },
      formatDate(row, column){
        return util.date2DateTimeString(new Date(row.created))
      },
      onMarketChange(marketId){
        console.log("change:", marketId);
        this.coinId = marketId
        this._inWalletRecord()

      },
      _inWalletRecord(){
        financeApi.serverInWalletRecord(this.rows, this.page, this.coinId, this.token).then(res => {
            console.log("????????????", res.data)
            this.totalRecords = res.data.total
            this.recordTbData = res.data.records
        }).catch(()=>{

        })
      },
      _getBaseCoinList(){
        financeApi.getBaseCoinList().then((res) => {
          for (let i = 0; i < res.data.length; i++) {
            let item      = res.data[i]
            let newItem   = {}
            newItem.label = item.title
            newItem.value = item.id
            //????????????????????????
            if(item.id == this.coinId ){
              this.coinSelectValue = newItem.label
            }
            this.marketSelectOptions.push(newItem)
          }
          console.log("????????????", this.marketSelectOptions);
        }).catch(()=>{

        })
      }
    },

    components: {
      NavHeader,
      mFooter,
      financeHeader
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
