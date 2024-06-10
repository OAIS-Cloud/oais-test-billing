package com.oaiscloud.billing.core.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.oaiscloud.billing.core.infra.database.Tables
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = Tables.CURRENCY)
class Currency : PanacheEntity() {
    @Column(nullable = false, unique = true, length = 4)
    lateinit var code: String;

    @Column(nullable = false)
    lateinit var name: String;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @JsonProperty("created_at")
    lateinit var createdAt: LocalDateTime;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @JsonProperty("updated_at")
    lateinit var updatedAt: LocalDateTime;

    @Column(name = "deleted_at")
    @JsonIgnore
    var deletedAt: LocalDateTime? = null;
}