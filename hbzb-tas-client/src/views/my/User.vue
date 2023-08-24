<template>
  <div>    
    <div class="container">
      <h2 style="width:560px; margin-bottom:30px; padding:20px; color:#fff; background:#E6A23C">个人认证</h2>
      <div class="form-box">
        <el-form ref="form" :model="form" label-width="100px">
          <el-form-item label="姓名">
            <el-input v-model="form.bidTitle" ></el-input>
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="form.bidBonds"></el-input>
          </el-form-item>
          <el-form-item label="身份证号正反面照片">
            <el-input v-model="form.idFrontPic"></el-input>
          </el-form-item>
          <el-form-item label="个人简介">
            <el-input type="textarea" rows="5" v-model="form.memo"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">提交申请</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'user',
    data: function() {
      return {
        form: {}
      }
    },
    methods: {
      handleChange(value) {
        console.log(value);
      },
      onSubmit() {
        this.$message.success('提交成功！');
      }
    }
  }
</script>