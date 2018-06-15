package com.nest.admin.core.fromwork.jdbc.mybatis.repository;

import com.nest.admin.core.fromwork.jdbc.mybatis.MybatisEntityOperations;
import com.nest.admin.core.fromwork.jdbc.mybatis.MybatisJdbcEntityTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.mapping.model.JdbcPersistentEntityInformation;
import org.springframework.data.jdbc.repository.SimpleJdbcRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by wzp on 2018/5/17.
 */
public class MybatisSimpleRepository<T, ID>  implements MybatisRepository<T, ID> {

    private final JdbcPersistentEntityInformation<T, ID> entityInformation;
    private final MybatisEntityOperations entityOperations;
    /**
     * Creates a new {@link SimpleJdbcRepository}.
     *
     * @param entityOperations
     * @param entityInformation
     */
    public MybatisSimpleRepository(MybatisJdbcEntityTemplate entityOperations, JdbcPersistentEntityInformation entityInformation) {
        this.entityOperations = entityOperations;
        this.entityInformation = entityInformation;
    }
    /*
         * (non-Javadoc)
         * @see org.springframework.data.repository.CrudRepository#save(S)
         */
    @Override
    public <S extends T> S save(S instance) {

        entityOperations.save(instance, entityInformation.getJavaType());

        return instance;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
     */
    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {

        List<S> savedEntities = new ArrayList<>();
        entities.forEach(e -> savedEntities.add(save(e)));
        return savedEntities;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
     */
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityOperations.findById(id, entityInformation.getJavaType()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)
     */
    @Override
    public boolean existsById(ID id) {
        return entityOperations.existsById(id, entityInformation.getJavaType());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll()
     */
    @Override
    public Iterable<T> findAll() {
        return entityOperations.findAll(entityInformation.getJavaType());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)
     */
    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return entityOperations.findAllById(ids, entityInformation.getJavaType());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#count()
     */
    @Override
    public long count() {
        return entityOperations.count(entityInformation.getJavaType());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)
     */
    @Override
    public void deleteById(ID id) {
        entityOperations.deleteById(id, entityInformation.getJavaType());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Object)
     */
    @Override
    public void delete(T instance) {
        entityOperations.delete(instance, entityInformation.getJavaType());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)
     */
    @Override
    public void deleteAll(Iterable<? extends T> entities) {

        for (T entity : entities) {
            entityOperations.delete(entity, (Class<T>) entity.getClass());
        }
    }

    @Override
    public void deleteAll() {
        entityOperations.deleteAll(entityInformation.getJavaType());
    }
    @Override
    public Iterable findAll(Sort sort) {
        return null;
    }

    @Override
    public Page findAll(Pageable pageable) {
        return null;
    }


    @Override
    public List <T> selectList(String namespance, Map<String, Object> params) {

        return entityOperations.selectList(namespance,params,entityInformation.getJavaType());
    }
}
