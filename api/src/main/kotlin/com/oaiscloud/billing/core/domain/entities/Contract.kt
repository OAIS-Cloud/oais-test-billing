package com.oaiscloud.billing.core.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.oaiscloud.billing.core.infra.database.Tables
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = Tables.CONTRACT)
class Contract : PanacheEntity() {
    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @OnDelete(action =  OnDeleteAction.RESTRICT)
    lateinit var currency: Currency;

    @Column(name = "currency_value")
    @JsonProperty("currency_value")
    lateinit var currencyValue: BigDecimal;

    @Column(name = "invoice_closing_day")
    @JsonProperty("invoice_closing_day")
    var invoiceClosingDay: Short = 0;

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