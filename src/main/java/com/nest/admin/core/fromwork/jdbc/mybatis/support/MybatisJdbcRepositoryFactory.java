package com.nest.admin.core.fromwork.jdbc.mybatis.support;

import com.nest.admin.core.fromwork.jdbc.mybatis.MybatisJdbcEntityTemplate;
import com.nest.admin.core.fromwork.jdbc.mybatis.repository.MybatisSimpleRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.mapping.model.JdbcPersistentEntityInformation;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactory;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * Created by wzp on 2018/5/17.
 */
public class MybatisJdbcRepositoryFactory extends JdbcRepositoryFactory {

    private final JdbcMappingContext context;
    private final ApplicationEventPublisher publisher;
    private final DataAccessStrategy accessStrategy;
    private final SqlSession sqlSession;


    public MybatisJdbcRepositoryFactory(ApplicationEventPublisher publisher, JdbcMappingContext context, DataAccessStrategy dataAccessStrategy,SqlSession sqlSession) {

        super(publisher, context, dataAccessStrategy);
        this.publisher = publisher;
        this.context = context;
        this.accessStrategy = dataAccessStrategy;
        this.sqlSession = sqlSession;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object getTargetRepository(RepositoryInformation repositoryInformation) {
        JdbcPersistentEntityInformation persistentEntityInformation = context
                .getRequiredPersistentEntityInformation(repositoryInformation.getDomainType());
        MybatisJdbcEntityTemplate template = new MybatisJdbcEntityTemplate(publisher, context, accessStrategy,sqlSession);
        return new MybatisSimpleRepository<>(template, persistentEntityInformation);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata repositoryMetadata) {
        return MybatisSimpleRepository.class;
    }
}
