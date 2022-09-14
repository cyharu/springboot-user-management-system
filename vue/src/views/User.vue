<template>
  <div>

    <div style="margin: 10px 0">
      <el-input style="width: 250px" placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input style="width: 250px; margin-left: 5px" placeholder="请输入地址" suffix-icon="el-icon-location" v-model="address"></el-input>
      <el-input style="width: 250px; margin-left: 5px" placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="email"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search" style="margin-right: 3px"></i>
        搜索</el-button>
      <el-button style="margin-left: 5px" type="primary" @click="reset"><i class="el-icon-delete" style="margin-right: 3px"></i>
        清空</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">添加<i class="el-icon-circle-plus-outline" style="margin-left: 3px"></i></el-button>
      <el-popconfirm
          style="margin-left: 5px"
          confirm-button-text='确定'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="确认删除这些数据吗？"
          @confirm="delBatch">
        <el-button type="danger" style="margin-left: 20px" slot="reference">批量删除<i class="el-icon-remove-outline" style="margin-left: 3px"></i></el-button>
      </el-popconfirm>
      <el-upload
          action="http://localhost:9090/user/import"
          :show-file-list="false"
          :on-success="handleImportSuccess"
          style="display: inline-block">
        <el-button type="success" style="margin-left: 20px" >导入<i class="el-icon-add-location" style="margin-left: 3px"></i></el-button>
      </el-upload>
      <el-button type="success" style="margin-left: 20px" @click="exp">导出<i class="el-icon-delete-location" style="margin-left: 3px"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="140">
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120">
      </el-table-column>
      <el-table-column prop="phone" label="电话">
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column prop="address" label="地址">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)">编辑<i class="el-icon-s-management" style="margin-left: 3px"></i> </el-button>
          <el-popconfirm
              style="margin-left: 5px"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确认删除本条数据吗？"
              @confirm="handleDel(scope.row.id)">
            <el-button type="danger" slot="reference">删除<i class="el-icon-remove" style="margin-left: 3px"></i> </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page = "pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form label-width="90px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return{
      tableData: [],
      total: 0,
      pageNum:1,
      pageSize:2,
      username:"",
      address:"",
      email:"",
      form:{},
      multipleSelection:[],
      dialogFormVisible:false,
      headerBg: 'headerBg'
    }
  },

  created() {
    //请求分页查询数据
    this.load()
  },


  methods:{
    load(){
      this.request.get("/user/page", {
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          address:this.address,
          email:this.email
        }
      }).then(res =>{
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },

    save(){
      this.request.post("/user", this.form).then(res =>{
        if(res.data){
          this.$message.success("保存成功 O(∩_∩)O~")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("操作失败 ┭┮﹏┭┮")
        }
      })
    },

    handleAdd(){
      this.dialogFormVisible = true
      this.form={}
    },

    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true
    },

    handleDel(id){
      this.request.delete("/user/" + id).then(res =>{
        if(res.data){
          this.$message.success("删除成功 (#^.^#)")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("删除失败 ┭┮﹏┭┮")
        }
      })
    },

    handleSelectionChange(val){
      this.multipleSelection=val
    },

    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/user/del/batch" , ids).then(res =>{
        if(res.data){
          this.$message.success("批量删除成功 (#^.^#)")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("批量删除失败 ┭┮﹏┭┮")
        }
      })
    },

    reset(){
      this.username=""
      this.address=""
      this.email=""
      this.load()
    },

    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleImportSuccess(){
      this.$message.success("导入成功！")
      this.load()
    }
  }
}
</script>

<style>
.headerBg{
  background-color: #cccccc !important;
}
</style>