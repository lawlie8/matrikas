package com.persistent.matrikas.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "scan")
public class Scan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tags tag;

    @Column(nullable = false)
    private LocalDate scanDate;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false)
    private String high;

    @Column(nullable = false)
    private String low;

    @Column(nullable = false)
    private String medium;

    @Column(nullable = false)
    private String critical;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public LocalDate getScanDate() {
        return scanDate;
    }

    public void setScanDate(LocalDate scanDate) {
        this.scanDate = scanDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }
}
