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
package org.jboss.seam.persistence.transaction.test.jboss;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.persistence.transaction.TransactionInterceptor;
import org.jboss.seam.persistence.transaction.test.util.JbossasTestUtils;
import org.jboss.seam.persistence.transactions.test.TransactionInterceptorStereotypeTestBase;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

/**
 * Tests the @Transactional interceptor
 * 
 * TODO: refactor the tests to share a common superclass
 * 
 * @author stuart
 * 
 */
@RunWith(Arquillian.class)
public class TransactionInterceptorStereotypeTest extends TransactionInterceptorStereotypeTestBase
{
   @Deployment
   public static Archive<?> createTestArchive()
   {

      WebArchive war = JbossasTestUtils.createTestArchive(false);
      war.addClasses(getTestClasses());
      war.addWebResource("META-INF/persistence.xml", "classes/META-INF/persistence.xml");
      war.addWebResource(new ByteArrayAsset(("<beans><interceptors><class>" + TransactionInterceptor.class.getName() + "</class></interceptors></beans>").getBytes()), "beans.xml");
      return war;
   }

}
