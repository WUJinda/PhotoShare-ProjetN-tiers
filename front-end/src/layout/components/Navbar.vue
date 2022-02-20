<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <el-dialog title="Edit password" append-to-body :visible.sync="visualForm">
      <el-form :model="passForm" ref="password-form" :rules="rules">
        <el-form-item type="password" label="Old pass" prop="oldPass">
          <el-input v-model="passForm.oldPass" :type="passwordType" auto-complete="off" />
        </el-form-item>
        <el-form-item label="New Pass" prop="newPass">
          <el-input v-model="passForm.newPass" :type="passwordType" auto-complete="off" />
        </el-form-item>
        <el-form-item label="Confirm New Pass" prop="surePass">
          <el-input v-model="passForm.surePass" :type="passwordType" auto-complete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visualForm = false">Cancel</el-button>
        <el-button type="primary" @click="updatePassword()">Submit</el-button>
      </div>
    </el-dialog>
    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <el-image :src="head_image" class="user-avatar" />
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <el-dropdown-item @click.native="visualForm = true">
            Modify Password
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">Logout</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="right-menu"><span style="font-size: 13px;margin-right: 50px">{{ 'Welcome, ' + this.$store.getters.name }}</span></div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data() {
    return {
      passwordType: 'password',
      passForm: {
        oldPass: '',
        newPass: '',
        surePass: ''
      },
      rules: {
        oldPass: [
          { required: true, message: 'Please enter your old password', trigger: 'blur' }
        ],
        newPass: [
          { required: true, message: 'Please enter your new password', trigger: 'blur' },
          { min: 6, max: 12, message: '6 to 12 characters long', trigger: 'blur' }
        ],
        surePass: [
          { required: true, message: 'Please enter your new password again', trigger: 'blur' },
          { min: 6, max: 12, message: '6 to 12 characters long', trigger: 'blur' }
        ]
      },
      visualForm: false,
      head_image: require('@/assets/images/head.jpg')
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'headPortrait'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    async updatePassword() {
      await this.$refs['password-form'].validate(valid => {
        if (valid) {
          if (this.passForm.newPass !== this.passForm.surePass) {
            this.$message.error('The passwords entered twice do not match!')
          } else {
            this.$store.dispatch('user/updatePassword', this.passForm).then(() => {
              this.$message.info('Password reset complete!')
              this.visualForm = false
            })
          }
        } else {
          this.$message.error('Form validation failed!')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
