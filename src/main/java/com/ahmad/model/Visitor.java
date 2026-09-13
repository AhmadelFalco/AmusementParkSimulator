package com.ahmad.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Visitor {
    private Long id;
    private String name;
    private double height;
    private LocalDate birthDate;
    private Visitor companion;
    private Employee assignedEmployee;

    public Visitor() {
    }

    // لإنشاء زائر جديد قبل حفظه في قاعدة البيانات
    public Visitor(String name, double height, LocalDate birthDate) {
        this.name = name;
        setHeight(height);
        this.birthDate = birthDate;
    }

    // لقراءة بيانات زائر موجود بالفعل في قاعدة البيانات
    public Visitor(Long id, String name, double height, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        setHeight(height);
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than zero.");
        }
        this.height = height;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        if (this.birthDate == null) {
            return 0;
        }
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    private VisitorType getType() {
        if (this.birthDate == null) {
            return null;
        }
        return (getAge() < 12) ? VisitorType.CHILD : VisitorType.ADULT;
    }

    public void setCompanion(Visitor companion) {
        if (!this.isChild() && companion != null) {
            throw new IllegalArgumentException("Only child visitors need a companion assigned.");
        }
        if (companion != null && companion.isChild()) {
            throw new IllegalArgumentException("A companion must be an adult.");
        }
        this.companion = companion;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        if (assignedEmployee != null && !this.isChild()) {
            throw new IllegalArgumentException("Only child visitors can be assigned an employee.");
        }
        this.assignedEmployee = assignedEmployee;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public Visitor getCompanion() {
        return companion;
    }

    public boolean hasAdultCompanion() {
        return this.companion != null;
    }

    public boolean hasAssignedStaff() {
        return this.assignedEmployee != null;
    }

    public boolean isChild() {
        return getType() == VisitorType.CHILD;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", birthDate=" + birthDate +
                ", age=" + getAge() +
                ", type=" + getType() +
                ", hasCompanion=" + (companion != null) +
                ", hasAssignedEmployee=" + (assignedEmployee != null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return Objects.equals(id, visitor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}