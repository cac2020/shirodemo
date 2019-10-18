package com.wjy.shirodemo.service;

import com.wjy.shirodemo.entity.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class UserService1  {

    public void saveUser(User user){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(user);

        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }


    public User findUserByName(String username) {
        /**
         * 通过Persistence获取EntityManagerFactory，
         * 传入参数对应配置文件中持久化单元persistence-unit的name
         * 通过EntityManagerFactory创建EntityManager
         * 获取EntityTransaction
         * 开启事务
         */
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 用来构建查询条件
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // 合成最终的查询语句
        CriteriaQuery<User> query = cb.createQuery(User.class);
        // 通过Root可以获取当前被查询的实体类中的属性，在和CriteriaBuilder创建查询条件
        Root<User> root = query.from(User.class);
        // 通过CriteriaBuilder构建的一个等于的查询条件
        Predicate predicate = cb.equal(root.get("username"), 5);
        // where接收的是一个可变参数，合成所有的查询条件
        query.where(predicate);
        // 传入CriteriaQuery，查询结果
        User clz = entityManager.createQuery(query).getSingleResult();


        return clz;
    }


}
