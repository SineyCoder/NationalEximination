<template>
    <div class="addQuestion">

      <Modal v-model="refresh" :mask-closable="false" width="360">
        <p slot="header" style="color:#f60;text-align:center">
          <Icon type="ios-information-circle"></Icon>
          <span>刷新提示</span>
        </p>
        <div style="text-align:center">
          <p>点击按钮后页面将会刷新，重置所有数据</p>
        </div>
        <div slot="footer">
          <Button type="error" size="large" long @click="$router.go(0)">刷新</Button>
        </div>
      </Modal>

      <Input class="item" search @on-search="search" v-model="form.cTitle" size="large" placeholder="题目" style="width: 100%" />

      <!--<Table v-if="table.data.length > 0" :columns="table.col" :data="table.data" class="item"></Table>-->

      <Upload
        multiple
        ref="upload"
        type="drag"
        :on-remove="handleRemove"
        :on-success="handleSuccess"
        :on-error="handleError"
        :before-upload="handleUpload"
        :format="['jpg','jpeg','png']"
        :max-size="1024"
        action="http://127.0.0.1:8088/exam/uploadPicture">
        <div style="padding: 20px 0">
          <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
          <p>上传图片（可选）</p>
        </div>
      </Upload>
      <div>
        <Select class="item" @on-select="examTypeSelect" v-model="form.cExamType" style="width:200px;margin-top: 10px;">
          <Option v-for="item in examTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </div>
      <div>
        <Select  class="item" v-model="form.cExamProject" style="width:200px;margin-top: 10px;">
          <Option v-for="item in projectTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </div>
      <div>
        <Select @on-change="selectChange" class="item" v-model="form.cType" style="width:200px">
          <Option v-for="item in types" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </div>
      <div class="item option">
        <RadioGroup v-if="optionContent.length > 0 && optionType === 0" v-model="form.cAnswer" vertical>
          <Radio  v-for="(item, index) in optionContent" :key="index" :label="index">
            <span>{{item}}</span>
          </Radio>
        </RadioGroup>
        <CheckboxGroup v-if="optionContent.length > 0 && optionType === 1" v-model="form.cAnswer" vertical>
          <Checkbox style="width:100%;margin-bottom: 10px;" v-for="(item, index) in optionContent" :key="index" :label="index">
            <span>{{item}}</span>
          </Checkbox>
        </CheckboxGroup>
        <div>
          <Button type="error" v-if="form.cAnswer !== '' && optionType === 0 || form.cAnswer.length > 0 && optionType === 1" @click="deleteOption" style="margin:20px 0 10px 0;">删除</Button>
        </div>
        <Input search enter-button="添加选项" v-model="optionValue" @on-search="addOption" placeholder="请输入选项内容" />
      </div>
      <Input class="item" v-model="form.cAnalyse" type="textarea" :rows="5" size="large" placeholder="题目解析" style="width: 100%" />
      <Button @click="submit">提交</Button>
      <div>{{form.cAnswer}}</div>
      <div>{{optionContent}}</div>
    </div>
</template>

<script>
  import {add, getExamProject, getExamType, searchTitleContent} from "../../assets/api/request";

    export default {
      name: "AddQuestion",
      data(){
        return{
          form:{
            cTitle:'',
            cType:'单选题',
            cContent:'',
            cAnswer:'',
            cAnalyse:'',
            cPicture:[],
            cExamType:undefined,
            cExamProject:undefined,
          },
          types:[{
            value: '单选题',
            label: '单选题'
          },{
            value: '判断题',
            label: '判断题'
          },{
            value: '不定项选择题',
            label: '不定项选择题'
          }],
          table:{
            col:[{
              title:'cid',
              key:'cid'
            },{
              title:'ctitle',
              key:'ctitle'
            },{
              title:'ctype',
              key:'ctype'
            },{
              title:'canswer',
              key:'canswer'
            },{
              title:'canalyse',
              key:'canalyse'
            },{
              title:'cpicture',
              key:'cpicture'
            },{
              title:'cexamType',
              key:'cexamType'
            },],
            data:[]
          },
          examTypes:[],
          projectTypes:[],
          optionType:0,//默认是radio
          optionContent:[],
          optionValue:'',
          filenames:[],
          refresh:false,
        }
      },
      mounted(){
        this.getExamType();
      },
      methods:{
        search(value){
          searchTitleContent(value)
            .then(data => {
              if(data.length > 0)
                this.$Message.info(`有${data.length}条可能的记录。`)
              else
                this.$Message.success('没有记录');
            })
        },
        getExamType(){
          getExamType()
            .then(data => {
              for(let i = 0;i < data.length;i++){
                this.examTypes.push({
                  value:data[i].cid,
                  label:data[i].ctype
                })
              }
            });
        },
        examTypeSelect(obj){
          getExamProject(obj.value)
            .then(data => {
              this.projectTypes = [];
              for(let i = 0;i < data.length;i++){
                this.projectTypes.push({
                  value:data[i].cid,
                  label:data[i].ctype
                })
              }
            })
        },
        selectChange(name){
          if(name === '单选题' || name === '判断题'){
            this.form.cAnswer = '';
            this.optionType = 0;
          }else{
            this.form.cAnswer = [];
            this.optionType = 1;
          }
        },
        addOption(){
          if(this.optionValue === ''){
            this.$Message.error("输入内容为空");
            return;
          }else if(this.optionContent.indexOf(this.optionValue) !== -1){
            this.$Message.error("请勿重复添加");
            return;
          }
          this.optionContent.push(this.optionValue);
          this.optionValue = '';
        },
        deleteOption(){
          if(this.optionType === 1){
            for(let i = 0;i < this.form.cAnswer.length;i++){
              let obj = this.form.cAnswer[i];
              let index = this.optionContent.indexOf(obj);
              this.optionContent.splice(index, 1);
            }
            this.form.cAnswer = [];
          }else{
            let index = this.optionContent.indexOf(this.form.cAnswer);
            this.optionContent.splice(index, 1);
            this.form.cAnswer = '';
          }
        },
        handleUpload(file){
          if(this.filenames.indexOf(file.name) === -1){
            this.filenames.push(file.name);
            return true;
          }
          this.$Message.error("已经上传过该图片");
          return false;
        },
        handleError(error, errorInfo,file){
          this.filenames.splice(this.filenames.indexOf(file.name), 1);
          this.$Message.error(file.name + " 上传失败");
        },
        handleSuccess(path){
          this.form.cPicture.push(path)
        },
        handleRemove(file){
          let index = this.filenames.indexOf(file.name);
          if(index !== -1) this.filenames.splice(index, 1);
          index = this.form.cPicture.indexOf(file.name);
          if(index !== -1) this.form.cPicture.splice(index, 1);
        },
        submit(){
          console.log(this.form);
          let form = this.form;
          if(this.optionContent.length < 2){
            this.$Message.error("你添加的选项数量不足");
            return;
          }else if(form.cAnswer instanceof Array){
            if(form.cAnswer.length === 0){
              this.$Message.error("你还没选择不定项的答案");
              return;
            }
            form.cAnswer.sort();
          }else if(this.form.cAnswer === ''){
            this.$Message.error("你还没选择一个正确答案");
            return;
          }else if(form.cExamType == null || form.cExamType <= 0){
            this.$Message.error("你还没选择类型");
            return;
          }else if(form.cExamProject == null || form.cExamProject <= 0){
            this.$Message.error("你还没选择具体科目");
            return;
          }
          if(form.cTitle === '' || form.cAnalyse === ''){
            this.$Message.error("有未填写的区域");
            return;
          }
          let params = {
            cTitle: form.cTitle,
            cType: form.cType,
            cContent: JSON.stringify(this.optionContent),
            cAnswer: JSON.stringify(form.cAnswer),
            cAnalyse: form.cAnalyse,
            cPicture: JSON.stringify(form.cPicture),
            cExamType:form.cExamType,
            cExamProject:form.cExamProject,
          };
          add(params)
            .then(data => {
              if('success' === data){
                this.$Message.success("添加成功");
                this.refresh = true;
              }
            });
        }
      }
    }
</script>

<style scoped>
  .addQuestion{
    padding:20px;
  }
  .item{
    margin-bottom: 20px;
  }
  .option{
    border: 1px dashed gray;
    padding:10px;
  }
</style>
