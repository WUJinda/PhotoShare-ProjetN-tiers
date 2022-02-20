<template >
    <div   class="dashboard-container">
      <img :src="index_image" style="width:200px;height:200px" alt="">
      
    </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
      background: '',
      index_image: require('@/assets/images/head.jpg')
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    text-align: center;
    margin-top: 15%;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
