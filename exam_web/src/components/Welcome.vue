<template>
    <div class="welcome">
      <div class="form">
        <div style="text-align: center;">
          <h2>登录</h2>
        </div>
        <Form ref="form" :model="form" :rules="formRules">
          <FormItem prop="user">
            <Input type="text" v-model="form.username" placeholder="用户名">
              <Icon type="ios-person-outline" slot="prepend"></Icon>
            </Input>
          </FormItem>
          <FormItem prop="password">
            <Input type="password" v-model="form.password" placeholder="密码">
              <Icon type="ios-lock-outline" slot="prepend"></Icon>
            </Input>
          </FormItem>
          <FormItem prop="code">
            <Input type="text" style="width: 50%;float: left;" maxlength="6" v-model="form.code" placeholder="验证码">
              <Icon type="ios-ionitron-outline" slot="prepend"></Icon>
            </Input>
            <img @click="updateImg" :src="codeSrc" alt="#" class="img"/>
          </FormItem>
          <FormItem>
            <RadioGroup v-model="form.type">
              <Radio label="0">普通用户</Radio>
              <Radio label="1">管理员</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem>
            <Button type="primary" style="width:100%;margin-bottom: 20px;" @click="handleSubmit('form')">登录</Button>
            <Button type="primary" style="width:100%;" @click="handleSubmit('formInline')">注册</Button>
          </FormItem>
        </Form>
      </div>
    </div>
</template>

<script>
  import R from '../assets/api/R';
  import {login} from "../assets/api/request";
  import {mapMutations} from 'vuex';
    export default {
      name: "Welcome",
      data(){
        return{
          codeSrc:R.BASE_URL + '/getCode',
          form:{
            username:'',
            password:'',
            type:'0',
            code:'',
          },
          formRules:{
            username:[{required:true, message:'用户名未填写', trigger:'blur'}],
            password:[{required:true, message:'密码未填写', trigger:'blur'}],
            code:[{required:true, message:'验证码未填写', trigger:'blur'}],
          }
        }
      },
      methods:{
        ...mapMutations(['changeUserState']),
        handleSubmit(tableRef){
          this.$refs[tableRef].validate((valid) => {
            if (valid) {
              let form = this.form;
              let user = {
                cAccount:form.username,
                cPassword:form.password,
                cGrant:form.type,
                code:form.code,
              };
              login(user)
                .then(data => {
                  if(data.code === -1){
                    this.$Message.error(data.msg);
                  }else{
                    let state = parseInt(form.type);
                    this.changeUserState(state);
                    this.$Message.success(data.msg);
                    console.log(form.type);
                    if(state === 0){
                      this.$router.push("/userInfo");
                    }else if(state === 1){
                      this.$router.push("/admin/adminInfo");
                    }

                  }
                });
            } else {
              this.$Message.error('信息未填写完全');
            }
          })
        },
        updateImg(){
          this.codeSrc = R.BASE_URL + '/getCode?' +  parseInt(Math.random() * 100007)
        }
      }
    }
</script>

<style scoped>
.welcome{
  width:100%;
  height:100vh;
  background-image: url("../assets/img/background.png");
  background-size: cover;
}
.form{
  width:400px;
  height:400px;
  position: absolute;
  left:50%;
  transform: translate(-50%, -50%);
  top:50%;
  background-color: rgba(255,255,255,0.1);
  padding:10px 30px 10px 30px;
}
.img{
  height: 33px;
  margin-left: 10px;
}
.img:hover{
  cursor: pointer;
}
</style>
