package com.baum.baas.local.config

import com.baum.baas.global.support.JpaManager
import com.baum.baas.global.data.properties.DataSourceProperties
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean

/**
 * JPA 설정 클래스
 * - DataSourceAutoConfiguration exclude 후 수동 설정
 *
 * @author : kimdonggeol
 * @since : 2023. 11. 24.
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
    basePackages = ["com.baum.baas.local.repository.write"],
    entityManagerFactoryRef = "writeEntityManagerFactory",
    transactionManagerRef = "writeTransactionManager"
)
class WriteJpaConfig(
    private val dataSourceProperties: DataSourceProperties,
) {

    @Bean("writeEntityManagerFactory")
    fun writeEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        return JpaManager.connectionFactory(dataSourceProperties.getByName("write"))
    }

    @Bean("writeTransactionManager")
    fun writeTransactionManager(
        @Qualifier("writeEntityManagerFactory") entityManagerFactory: EntityManagerFactory,
    ): JpaTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}