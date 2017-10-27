/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hwkei
 */
@Entity
@Table(name = "address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
    , @NamedQuery(name = "Address.findById", query = "SELECT a FROM Address a WHERE a.id = :id")
    , @NamedQuery(name = "Address.findByAddition", query = "SELECT a FROM Address a WHERE a.addition = :addition")
    , @NamedQuery(name = "Address.findByAddressType", query = "SELECT a FROM Address a WHERE a.addressType = :addressType")
    , @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city")
    , @NamedQuery(name = "Address.findByNumber", query = "SELECT a FROM Address a WHERE a.number = :number")
    , @NamedQuery(name = "Address.findByPostalcode", query = "SELECT a FROM Address a WHERE a.postalcode = :postalcode")
    , @NamedQuery(name = "Address.findByStreetname", query = "SELECT a FROM Address a WHERE a.streetname = :streetname")})
public class Address implements Serializable {

    public enum AddressType {
        POSTADRES, FACTUURADRES, BEZORGADRES
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "addition")
    private String addition;
    @Column(name = "address_type")
    @Enumerated(EnumType.ORDINAL)
    private AddressType addressType;
    @Size(max = 255)
    @Column(name = "city")
    private String city;
    @Column(name = "number")
    private Integer number;
    @Size(max = 255)
    @Column(name = "postalcode")
    private String postalcode;
    @Size(max = 255)
    @Column(name = "streetname")
    private String streetname;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne
    private Customer customerId;

    public Address() {
    }

    public Address(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshop3.domain.Address[ id=" + id + " ]";
    }

}
