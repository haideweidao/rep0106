package com.sibd.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sibd.mp.mapper.UserMapper;
import com.sibd.mp.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class Demo1 {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testSelect() {
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            System.out.println(user);
        }
    }
    @Test
    public void testInsert(){
        User user = new User();
        user.setAge(20);
        user.setEmail("test@sibd.cn");
        user.setName("曹操");
        user.setUserName("caocao");
        user.setPassword("123456");
        int result = userMapper.insert(user); //返回的result是受影响的行数，并不是自增
        System.out.println("result = " + result);
        System.out.println(user.getId()); //自增后的id会回填到对象中
    }
    @Test
    public void testUpdateById() {
        User user = userMapper.selectById(5);
        user.setAge(84);
        this.userMapper.updateById(user);
    }
    @Test
    public void testUpdate() {
        User user=new User();
        user.setPassword("qqqqq");
        user.setAge(73);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lt("age", "30");
//执行更新操作
        int result = this.userMapper.update(user, wrapper);
        System.out.println("result = " + result);
    }
    @Test
    public void testUpdate2() {
//更新的条件以及字段
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", 5).set("age", 23).set("password","rrrrr");
//执行更新操作
        int result = this.userMapper.update(null, wrapper);
        System.out.println("result = " + result);
    }
    @Test
    public void testDeleteById() {
//执行删除操作
        int result = this.userMapper.deleteById(1478181184830713858L);
        System.out.println("result = " + result);
    }
    @Test
    public void testDeleteByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("age",73);
        columnMap.put("name","张三");
//将columnMap中的元素设置为删除的条件，多个之间为and关系
        int result = this.userMapper.deleteByMap(columnMap);
        System.out.println("result = " + result);
    }
    @Test
    public void testDeleteByMap1() {
        User user = new User();
        user.setAge(73);
        user.setName("李四");
//将实体对象进行包装，包装为操作条件
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);

        int result = this.userMapper.delete(wrapper);
        System.out.println("result = " + result);
    }
    @Test
    public void testDeleteByMap3() {
//根据id集合批量删除
        int result = this.userMapper.deleteBatchIds(Arrays.asList(3,4,5));
        System.out.println("result = " + result);
    }
    @Test
    public void testSelectById() {
//根据id查询数据
        User user = this.userMapper.selectById(2L);
        System.out.println("result = " + user);
    }
    @Test
    public void testSelectBatchIds() {
//根据id集合批量查询
        List<User> users = this.userMapper.selectBatchIds(Arrays.asList(2L, 3L));
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testSelectOne() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("name", "李四");
//根据条件查询一条数据，如果结果超过一条会报错
        User user = this.userMapper.selectOne(wrapper);
        System.out.println(user);
    }
    @Test
    public void testSelectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.gt("age", 23); //年龄大于23岁
//根据条件查询数据条数
        Integer count = this.userMapper.selectCount(null);
        System.out.println("count = " + count);
    }
    @Test
    public void testSelectPage() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.gt("age", 20); //年龄大于20岁
        Page<User> page = new Page<>(2,3);
//根据条件查询数据
        IPage<User> iPage = this.userMapper.selectPage(page, null);
        System.out.println("数据总条数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        List<User> users = iPage.getRecords();
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }
    @Test
    public void testFind(){
        User user = userMapper.findById(2l);
        System.out.println(user);
    }

    @Test
    public void testWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//设置条件
        Map<String,Object> params = new HashMap<>();
        params.put("name", "张三");
        params.put("age", "20");
        params.put("password", "123456");
        //wrapper.allEq(params);
       // wrapper.allEq(params,false);
        wrapper.allEq((k,v)->(k.equals("name")),params);
// wrapper.allEq(params);//SELECT * FROM tb_user WHERE password IS NULL ANDname = ? AND age = ?
// wrapper.allEq(params,false); //SELECT * FROM tb_user WHERE name = ? AND age= ?
// wrapper.allEq((k, v) -> (k.equals("name") || k.equals("age")),params);//SELECT * FROM tb_user WHERE name = ? AND age = ?
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }

    }
    @Test
    public void testEq() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//SELECT id,user_name,password,name,age,email FROM tb_user WHERE password = ?AND age >= ? AND name IN (?,?,?)
        wrapper.eq("password", "123456")
                .gt("age", 20)
                .in("name", "李四", "王五", "赵六");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testWrapperLike() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//SELECT id,user_name,password,name,age,email FROM tb_user WHERE name LIKE ?
//Parameters: %曹%(String)
        //wrapper.like("name", "三");//  %三%
        //wrapper.likeLeft("name", "三");//  %三
        wrapper.likeRight("name", "三");//  三%
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testWrapperoRDER() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//SELECT id,user_name,password,name,age,email FROM tb_user ORDER BY age DESC
        wrapper.orderByDesc("age");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testWrapperOr() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//SELECT id,user_name,password,name,age,email FROM tb_user WHERE name = ? ORage = ?
        wrapper.eq("name","李四").or().eq("age", 24);
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testWrapperSelect() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//SELECT id,name,age FROM tb_user WHERE name = ? OR age = ?
        wrapper.eq("name", "李四")
                .or()
                .eq("age", 24)
                .select("id", "name", "age");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testAr(){
        User user=new User();
        user.setId(2l);
        User user1 = user.selectById();
        System.out.println(user1);
    }
    @Test
    public void testAR() {
        User user = new User();
        user.setName("刘备");
        user.setAge(30);
        user.setPassword("123456");
        user.setUserName("liubei");
        user.setEmail("liubei@sibd.cn");
        boolean insert = user.insert();
        System.out.println(user);
    }
    @Test
    public void testUpdateAll(){
        User user = new User();
        user.setAge(22);
        int result = this.userMapper.update(user, null);
        System.out.println("result = " + result);
    }
    @Test
    public void testUpdate33(){
        User user = new User();
        user.setAge(30);
        user.setId(2L);
        user.setVersion(1); //获取到version为1
        int result = this.userMapper.updateById(user);
        System.out.println("result = " + result);
    }
    @Test
    public void testDeleteByIdLogic(){
        this.userMapper.deleteById(2L);
    }
}
