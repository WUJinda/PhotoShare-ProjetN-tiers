<template>
  <div class="login-container" :style="backgroundDiv">
    <el-dialog title="Register an account" :visible.sync="dialogVisible">
      <el-form :model="registerForm" ref="registerForm" >
        <el-form-item prop="username" label="username">
          <el-input v-model="registerForm.username" ></el-input>
        </el-form-item>
        <el-form-item prop="password" label="password">
          <el-input v-model="registerForm.password"></el-input>
        </el-form-item>
        <el-form-item prop="password1" label="confirm password">
          <el-input v-model="registerForm.password1"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addUser">Submit</el-button>
      </div>
    </el-dialog>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <div v-if="loginForm.isAdmin" style="text-align: center;font-size: 1.5em;padding-bottom: 10px">
        Admin login
      </div>
      <div v-else style="text-align: center;font-size: 1.5em;padding-bottom: 10px">
        Login
      </div>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username.."
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password" >
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password.."
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <span style="font-weight:bold color: blue">Don't have an account yet?<span style="color: red" @click="dialogVisible = true"> Register here.</span></span>
      <el-row :gutter="10" style="margin-top: 10px">
        <el-col :span="12">
            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">Login</el-button>
        </el-col>
        <el-col :span="12">
          <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleReset">Reset</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import userService from '@/services/userService'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import '@/styles/index.scss' // global css

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please input Username'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 2) {
        callback(new Error('Password must be at least 2 characters'))
      } else {
        callback()
      }
    }
    return {
      backgroundDiv: {
        background: 'url(' + require('@/assets/images/back.jpg') + ') no-repeat',
        backgroundSize: 'cover'
      },
      loginForm: {
        username: '',
        password: ''
      },
      registerForm: {
        username: '',
        password: '',
        password1: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      dialogVisible: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    async addUser() {
      console.log(this.registerForm)
      if (this.registerForm.password != this.registerForm.password1) {
        this.$message.warning('The passwords entered twice do not match!')
        return
      }
      await userService.addUser(this.registerForm)
      this.dialogVisible = false
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleReset() {
      this.$refs.loginForm.resetFields()
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          console.log(this.loginForm)
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* Fix input background incongruity and cursor discoloration */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #878e92;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      color: black;
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #ffffff;
$dark_gray: #2a84b7;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 20px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: rgba(0,0,0,.85);
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
