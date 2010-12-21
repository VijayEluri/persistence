/* 
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.persistence.test.util;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.jboss.seam.solder.core.Veto;

/**
 * <p>
 * <strong>Hotel</strong> is the model/entity class that represents a hotel.
 * </p>
 * 
 * @author Gavin King
 * @author Dan Allen
 */
@Entity
@Table(name = "hotel")
@Indexed
@Veto
public class IndexedHotel implements Serializable
{
   private Long id;
   private String name;
   private String address;
   private String city;
   private String state;
   private String zip;
   private String country;
   private Integer stars;
   private BigDecimal price;

   @Inject
   private HelloService helloService;

   private boolean initalizerCalled = false;

   public IndexedHotel()
   {
   }

   @Inject
   public void create()
   {
      initalizerCalled = true;
   }

   public String sayHello()
   {
      return helloService.sayHello();
   }

   public IndexedHotel(final String name, final String address, final String city, final String state, final String zip, final String country)
   {
      this.name = name;
      this.address = address;
      this.city = city;
      this.state = state;
      this.zip = zip;
      this.country = country;
   }

   public IndexedHotel(final int price, final int stars, final String name, final String address, final String city, final String state, final String zip, final String country)
   {
      this.price = new BigDecimal(price);
      this.stars = stars;
      this.name = name;
      this.address = address;
      this.city = city;
      this.state = state;
      this.zip = zip;
      this.country = country;
   }

   @Id
   @GeneratedValue
   public Long getId()
   {
      return id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   @Size(max = 50)
   @NotNull
   @Field(index = Index.TOKENIZED, store = Store.NO)
   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   @Size(max = 100)
   @NotNull
   public String getAddress()
   {
      return address;
   }

   public void setAddress(final String address)
   {
      this.address = address;
   }

   @Size(max = 40)
   @NotNull
   public String getCity()
   {
      return city;
   }

   public void setCity(final String city)
   {
      this.city = city;
   }

   @Size(min = 3, max = 6)
   @NotNull
   public String getZip()
   {
      return zip;
   }

   public void setZip(final String zip)
   {
      this.zip = zip;
   }

   @Size(min = 2, max = 10)
   public String getState()
   {
      return state;
   }

   public void setState(final String state)
   {
      this.state = state;
   }

   @Size(min = 2, max = 40)
   @NotNull
   public String getCountry()
   {
      return country;
   }

   public void setCountry(final String country)
   {
      this.country = country;
   }

   @Min(1)
   @Max(5)
   public Integer getStars()
   {
      return stars;
   }

   public void setStars(final Integer stars)
   {
      this.stars = stars;
   }

   @Column(precision = 6, scale = 2)
   public BigDecimal getPrice()
   {
      return price;
   }

   public void setPrice(final BigDecimal price)
   {
      this.price = price;
   }

   @Transient
   public String getLocation()
   {
      return city + ", " + state + ", " + country;
   }

   @Override
   public String toString()
   {
      return "Hotel(" + name + "," + address + "," + city + "," + zip + ")";
   }

   @Transient
   public boolean isInitalizerCalled()
   {
      return initalizerCalled;
   }

}
