<template>
  <div class="block">
    <el-dialog title="Upload Picture" :visible.sync="visualAdd">
      <el-upload
        ref="upload"
        style="text-align: center"
        drag
        accept=".jpg,.png"
        :auto-upload="false"
        :action="uploadUrl"
        :on-success="success"
        multiple
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">Drag files here, or<em>Click to upload</em></div>
        <div class="el-upload__tip" slot="tip">Only jpg/png files can be uploaded, and no more than 500kb</div>
      </el-upload>
      <div slot="footer">
        <el-button @click="cancel">Cancel</el-button>
        <el-button type="primary" @click="submit"> OK </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="visualComment">
      <div style="display: flex;flex-direction: row">
        
        <el-image style=";width: 80%;height: 480px" :src="getImage(currentImage.file)">
        </el-image>
        <el-upload
          style="width: 20%;margin-top: 80px"
          action="up"
          :auto-upload="false"
          :limit="1"
          ref="replace"
          list-type="picture-card"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
          :on-change="fileChange"
        >
          <i class="el-icon-plus">replace</i>
        </el-upload>
      </div>
      
      <div>
        <template v-if="currentImage.comments && currentImage.comments.length !== 0">
          <p v-for="item in currentImage.comments" :key="item.id">
            {{ item.createTime }} : {{ item.remark }}
          </p>
        </template>
        <template v-else>
          <p style="text-align: center">no comment yet</p>
        </template>
      </div>
      <el-form inline style="text-align: center">
        <el-form-item>
          <el-input v-model="comment"></el-input>
        </el-form-item>
        <el-button @click="addComment" type="primary">Comment</el-button>
      </el-form>
    </el-dialog>

    <el-form inline>
      <el-form-item label="image name">
        <el-input v-model="name"></el-input>
      </el-form-item>
      <el-button @click="search">Search</el-button>
      <el-form-item label="Comment">
        <el-input v-model="searchComment"></el-input>
      </el-form-item>
      <el-button @click="search">Search</el-button>
      <el-button type="primary" @click="visualAdd=true">Upload image</el-button>
    </el-form>
    <el-carousel :interval="4000" type="card" height="600px">
      <el-carousel-item v-for="item in images" :key="item.id">
        <div style="position: relative;background-color: black">
          <el-button @click="deleteImg(item.id)" style="z-index: 2499;float: right" type="danger">Delete</el-button>
          <el-image style="height: 100%" :src="getImage(item.file)" @click="clickImg(item)" fit="fill"></el-image>
        </div>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>

import userService from '@/services/userService'

export default {
  name: 'Index',
  data() {
    return {
      searchComment: '',
      currentImage: {},
      name: '',
      comment: '',
      visualComment: false,
      visualAdd: false,
      images: [],
      fileList: []
    }
  },
  computed: {
    uploadUrl() {
      return `${process.env.VUE_APP_BASE_API}/image/upload`
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fileChange(file, fileList) {
      this.fileList = fileList
      console.log(file)
      const formData = new FormData()
      formData.append('file', file.raw)
      formData.append('imageId', this.currentImage.id)
      await userService.updateImg(formData)
      this.$refs.replace.clearFiles()
      this.visualComment = false
      await this.fetchData()
    },
    search() {
      this.fetchData()
    },
    clickImg(image) {
      console.log('click', image)
      this.currentImage = image
      this.visualComment = true
    },
    async fetchData() {
      const { data } = await userService.getInfo({ name: this.name, comment: this.searchComment })
      this.images = data.images
    },
    getImage(url) {
      return `${process.env.VUE_APP_BASE_API}${url}`
    },
    cancel() {
      this.visualAdd = false
      this.$refs.upload.clearFiles()
    },
    submit() {
      this.$refs.upload.submit()
      this.$refs.upload.clearFiles()
      this.visualAdd = false
      setTimeout(() => {
        this.fetchData()
      }, 1000)
    },
    success() {
      this.visualAdd = false
      this.fetchData()
    },
    async addComment() {
      const comment = {
        imageId: this.currentImage.id,
        remark: this.comment
      }
      console.log('comment=', comment)
      await userService.addComment(comment)
      const { data } = await userService.getComments({ id: this.currentImage.id })
      this.currentImage.comments = data
      this.$message.info('Comment successfully!')
    },
    deleteImg(id) {
      this.$confirm('This action will permanently delete this image, do you want to continue?', 'Alert', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(async() => {
        await userService.deleteImg({ imageId: id })
        await this.fetchData()
      }).catch(() => {
      })
    }
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
