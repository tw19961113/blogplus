<template>
  <div>
    <nav class="nav nav-top">
      <div class="nav-header">
        <div class="logo-wrapper clearfix">
          <router-link :to="{ name: 'index'}" class="logo">博客</router-link>
        </div>
      </div>
    </nav>
    <div class="login-container">
      <div class="login-box">
        <div class="login-title">
          <h2>BlogPlus 登录平台</h2>
          <p>基于element-ui的极致体验</p>
      </div>
        <div class="login-form-wrapper">
          <form>
            <el-form ref="loginForm" :model="loginForm" :rules="rules">
              <el-form-item prop="username">
                <el-input
                  class="login-input"
                  placeholder="用户名"
                  prefix-icon="fa-user fa"
                  v-model="loginForm.username">
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  type="password"
                  class="login-input"
                  placeholder="密码"
                  prefix-icon="fa-lock fa"
                  v-model="loginForm.password">
                </el-input>
              </el-form-item>
              <el-form-item prop="verificationCode">
                <el-input
                  type="text"
                  class="verification-code-input"
                  placeholder="验证码"
                  :prefix-icon="'fa fa-image'"
                  v-model="loginForm.verificationCode">
                </el-input>
                <a class="verification-code-wrapper" href="#"><img title="点击刷新" :src="imgUrl" class="verification-code-img" @click="refreshImg"/></a>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="doLogin('loginForm')" class="login-button">登录</el-button>
              </el-form-item>
            </el-form>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import md5 from 'js-md5';
import {getVerification,doLogin} from "../../../api/user/login.js";
export default {
  name: 'login',
  data () {
    return {
      loginForm: {
        username: '',
        password: '',
        verificationCode:''
      },
      imgUrl: null,
      rules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'change' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'change' }
        ],
        verificationCode: [
          { required: true, message: '验证码不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    //页面初次加载图形验证码
    this.refreshImg();
  },
  methods: {
    /**
     * 获取验证码
     */
    refreshImg () {
      getVerification()
        .catch(err=>{
          console.log(err)
          this.imgUrl = 'data:image/jpeg;base64,' +
          btoa(new Uint8Array(err.data).reduce((data, byte) => data + String.fromCharCode(byte), ''));
      });
    },
    doLogin: function (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          //对密码进行MD5加密
          doLogin({"userName":this.loginForm.username,"password":md5(this.loginForm.password),
            "verificationCode":this.loginForm.verificationCode}).then(response => {
              if(response.data.status == 200){
                this.$message({
                  showClose: true,
                  message: response.data.msg,
                  type: 'success'
                });
                //将后端生成的token存入session
                sessionStorage.setItem('token',response.data.data);
                this.$router.push({name: 'index'});
              }else {
                this.$message({
                  showClose: true,
                  message: response.data.msg,
                  type: 'error'
                });
              }
          }).catch(error => {
            this.$message({
              showClose: true,
              message: "系统故障，请联系管理员！",
              type: 'error'
            });
          })
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
  @import '../../../common/css/theme';
  .login-container{
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    width: 100%;
    margin-top: 150px;
    display: flex;
    .login-box{
      width: 375px;
      margin: 0 auto;
      box-sizing: border-box;
      .login-title{
        text-align: center;
        h2{
          margin-bottom: 10px;
          font-weight: 300;
          font-size: 30px;
          color: #333;
        }
        p{
          color: #999;
          font-size: 16px;
        }
      }
      .login-form-wrapper{
        padding: 20px;
        .login-input{
          width: 100%;
        }
        .verification-code-input{
          width: 180px;
          margin-right: 20px;
        }
        .verification-code-wrapper{
          display: inline-block;
          vertical-align: bottom;
          height: 40px;
          width: 120px;
          border-radius: 4px;
          border: 1px solid #eee;
          .verification-code-img{
            outline: none;
            border: 0;
            height: 100%;
            width: 100%;
          }
        }
        .login-button{
          color: #FFF;
          background-color: @btn-color;
          border-color: @btn-color;
          opacity: .75;
          &.is-disabled{
            background-color: @btn-disabled-color;
            border-color: @btn-disabled-color;
            opacity: 1;
          }
          width: 100%;
          &:focus{
            background-color: @btn-color;
            border-color: @btn-color;
            opacity: .75;
          }
          &:active{
            background-color: @btn-color;
            border-color: #fb1b1b;
            opacity: 1;
          }
          &:hover{
            background-color: @btn-color;
            border-color: @btn-color;
            opacity: 1;
          }
          &.is-disabled:hover {
            color: #fff;
            background-color: @btn-disabled-color;
            border-color: @btn-disabled-color;
            opacity: 1;
          }
        }
      }
    }
  }
</style>
