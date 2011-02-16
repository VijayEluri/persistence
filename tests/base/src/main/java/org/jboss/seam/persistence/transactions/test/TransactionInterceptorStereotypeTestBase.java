/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
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
package org.jboss.seam.persistence.transactions.test;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import junit.framework.Assert;

import org.jboss.seam.persistence.test.util.DontRollBackException;
import org.jboss.seam.persistence.test.util.EntityManagerProvider;
import org.jboss.seam.persistence.test.util.HelloService;
import org.jboss.seam.persistence.test.util.Hotel;
import org.jboss.seam.persistence.transaction.DefaultTransaction;
import org.jboss.seam.persistence.transaction.SeamTransaction;
import org.junit.Test;

/**
 * Tests the @Transactional interceptor
 * 
 * TODO: refactor the tests to share a common superclass
 * 
 * @author stuart
 * 
 */
public class TransactionInterceptorStereotypeTestBase
{

   public static Class<?>[] getTestClasses()
   {
      return new Class[] { TransactionInterceptorStereotypeTestBase.class, TransactionalStereotype.class, StereotypeTransactionManagedBean.class, HelloService.class, Hotel.class, EntityManagerProvider.class, DontRollBackException.class };
   }

   @Inject
   StereotypeTransactionManagedBean bean;

   @Inject
   @DefaultTransaction
   SeamTransaction transaction;

   @PersistenceContext
   EntityManager em;

   @Test
   public void testTransactionInterceptor() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException
   {
      bean.addHotel();
      assertHotels(1);
      try
      {
         bean.failToAddHotel();
      }
      catch (Exception e)
      {
      }
      assertHotels(1);
      try
      {
         bean.addHotelWithApplicationException();
      }
      catch (DontRollBackException e)
      {
      }
      assertHotels(2);
   }

   @Test(expected = TransactionRequiredException.class)
   public void testTransactionInterceptorMethodOverrides()
   {
      bean.tryAndAddHotelWithNoTransaction();
   }

   public void assertHotels(int count) throws NotSupportedException, SystemException
   {
      transaction.begin();
      em.joinTransaction();
      List<Hotel> hotels = em.createQuery("select h from Hotel h").getResultList();
      Assert.assertTrue("Wrong number of hotels: " + hotels.size(), hotels.size() == count);
      transaction.rollback();
   }
}
